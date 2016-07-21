package com.hansong.getty

import com.hansong.getty.event.PipeLine
import org.slf4j.LoggerFactory

import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
/**
 * 负责监听端口，接受新连接，分配给工作线程进行处理
 * Created by hansong.xhs on 2016/6/22.
 */
class NioServer extends Thread {

    /**服务端的套接字通道*/
    ServerSocketChannel ssc

    /**选择器*/
    Selector selector

    PipeLine pipeLine

    /**工作线程列表*/
    def workers = []

    /**当前工作线程索引*/
    int index

    static logger = LoggerFactory.getLogger(NioServer.name)

    NioServer(pipeLine) {
        this.pipeLine = pipeLine

        //加载配置
        GettyConfig.loadPros()

        init()
    }

    /**
     * 初始化服务器
     * @return
     */
    def init() {

        //绑定端口，开始监听
        selector = Selector.open()
        ssc = ServerSocketChannel.open()
        ssc.configureBlocking(false)
        ServerSocket ss = ssc.socket()
        ss.bind(new InetSocketAddress(GettyConfig.PORT))
        ssc.register(selector, SelectionKey.OP_ACCEPT)

        //启动worker线程
        GettyConfig.WORKER_NUM.times {
            def worker = new Worker()
            workers.add(worker)
            worker.start()
        }

        index = 0
    }


    void run() {
        logger.info("NioServer start on port : " + GettyConfig.PORT)

        while (true) {
            selector.select()
            logger.debug("Have new event")
            try {
                //遍历后删除key
                selector.selectedKeys().removeAll { key ->
                    if (key.isAcceptable()) {

                        logger.debug("accept event")

                        //接受新连接
                        def sc = ssc.accept()

                        //分配工作线程
                        Worker worker = workers[index]

                        //设置为非阻塞
                        sc.configureBlocking(false)

                        //创建连接上下文
                        def ctx = new ConnectionCtx()
                        ctx.with {
                            setWorker(worker)
                            setChannel(sc)
                            setPipeLine(CommonUtils.deepCopy(this.pipeLine) as PipeLine)
                        }

                        logger.debug("Worker.selector ${worker.selector}")

                        //把连接上下文放入工作线程队列，并且唤醒工作线程注册新的连接
                        worker.queue.add(ctx)
                        worker.selector.wakeup()

                        logger.info("New Connection {} accepted", sc.getRemoteAddress())

                        //轮转法分配线程
                        index = (index + 1) % GettyConfig.WORKER_NUM

                    }

                    //最后一句默认为true，代表删除key
                    true
                }
            } catch (Exception e) {
                logger.error("", e)
            }
        }
    }
}

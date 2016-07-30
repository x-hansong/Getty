# Getty
基于Groovy的NIO框架，仅供学习Java NIO使用。

# 运行一个简单的 Web 服务器
我实现了一系列处理`HTTP`请求的处理器，具体实现看代码。

- `LineBasedDecoder`：行解码器，按行解析数据
- `HttpRequestDecoder`：HTTP请求解析，目前只支持GET请求
- `HttpRequestHandler`：Http 请求处理器，目前只支持GET方法
- `HttpResponseHandler`：Http响应处理器

下面是写在`test`中的例子

    class WebServerTest {
        static void main(args) {
            def pipeLine = new PipeLine()
    
            def readChain = new HandlerChain()
            readChain.addLast(new LineBasedDecoder())
            readChain.addLast(new HttpRequestDecoder())
            readChain.addLast(new HttpRequestHandler())
            readChain.addLast(new HttpResponseHandler())
    
            def closeChain = new HandlerChain()
            closeChain.addLast(new ClosedHandler())
    
            pipeLine.addChain(readChain)
            pipeLine.addChain(closeChain)
    
            NioServer nioServer = new NioServer(pipeLine)
            nioServer.start()
        }
    }
    
另外，还可以使用配置文件`getty.properties`设置程序的运行参数。

    #用于拼接消息时使用的二进制数组的缓存区
    common_buffer_size=1024
    #工作线程读取tcp数据的缓存大小
    worker_rcv_buffer_size=1024
    #监听的端口
    port=4399
    #工作线程的数量
    worker_num=1
    #连接超时自动断开时间
    timeout=900
    #根目录
    root=.

更多资料查看：[【Getty】Java NIO框架设计与实现](http://blog.xiaohansong.com/2016/07/30/getty/)
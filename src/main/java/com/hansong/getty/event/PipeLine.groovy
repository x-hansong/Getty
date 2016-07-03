package com.hansong.getty.event

import org.slf4j.LoggerFactory


/**
 * 事件总线
 * Created by hansong.xhs on 2016/6/22.
 */
class PipeLine implements Notifier{

    static logger = LoggerFactory.getLogger(PipeLine.name)

    //监听器队列
    def listOfChain = []

    PipeLine(){}

    /**
     * 添加监听器到监听队列中
     * @param chain
     */
    void addChain(chain) {
        synchronized (listOfChain) {
            if (!listOfChain.contains(chain)) {
                listOfChain.add(chain)
            }
        }
    }

    /**
     * 触发所有可读事件回调
     */
    void fireOnRead(ctx) {
        logger.debug("fireOnRead")
        listOfChain.each { chain ->
            chain.fireOnRead(ctx)
        }
    }

    /**
     * 触发所有可写事件回调
     */
    void fireOnWrite(ctx) {
        listOfChain.each { chain ->
            chain.fireOnWrite(ctx)
        }
    }

    /**
     * 触发所有连接关闭事件回调
     */
    void fireOnClosed(ctx) {
        listOfChain.each { chain ->
            chain.fireOnClosed(ctx)
        }
    }
}
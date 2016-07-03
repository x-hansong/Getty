package com.hansong.getty.event

import org.slf4j.LoggerFactory

/**
 * Created by hansong.xhs on 2016/6/22.
 */
class EventAdapter implements ServerListener {

    //下个处理器的引用
    protected next

    static logger = LoggerFactory.getLogger(EventAdapter.name)

    void onRead(Object ctx) {
        logger.debug("EventAdapter.onRead involved")
    }

    void onWrite(Object ctx) {
        logger.debug("EventAdapter.onWrite involved")
    }

    void onClosed(Object ctx) {
        logger.debug("EventAdapter.onClosed involved")
    }

}

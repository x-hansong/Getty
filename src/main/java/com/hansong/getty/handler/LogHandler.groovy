package com.hansong.getty.handler

import com.hansong.getty.event.EventAdapter
import org.slf4j.LoggerFactory

/**
 * 打印log的处理器
 * Created by hansong.xhs on 2016/6/27.
 */
class LogHandler extends EventAdapter {

    static logger = LoggerFactory.getLogger(LogHandler.name)

    @Override
    void onRead(ctx) {
        logger.debug("LogHandler involved, {}", ctx.attachment)
    }
}
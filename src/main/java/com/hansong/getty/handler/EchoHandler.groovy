package com.hansong.getty.handler

import com.hansong.getty.BufferUtils
import com.hansong.getty.event.EventAdapter
import org.slf4j.LoggerFactory

/**
 * 回显处理器
 * Created by hansong.xhs on 2016/6/23.
 */
class EchoHandler extends EventAdapter{

    static logger = LoggerFactory.getLogger(EchoHandler.name)

    void onRead(ctx) {

        def buffer = ctx.attachment
        def worker = ctx.worker

        worker.write(ctx.channel, BufferUtils.readFrom(buffer))

        next.onRead(ctx)

        logger.debug("EchoHandler involved")
    }
}
package com.hansong.getty.handler

import com.hansong.getty.event.EventAdapter
import org.slf4j.LoggerFactory

/**
 * Created by hansong.xhs on 2016/6/23.
 */
class ClosedHandler extends EventAdapter{
    static logger = LoggerFactory.getLogger(EventAdapter.name)

    void onClosed(ctx) {

        logger.debug("closed connection ${ctx.channel}")

        //关闭连接
        ctx.channel.close()
    }
}
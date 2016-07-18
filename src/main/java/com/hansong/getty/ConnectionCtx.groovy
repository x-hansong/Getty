package com.hansong.getty

import com.hansong.getty.event.PipeLine

import java.nio.channels.SocketChannel

/**
 * Created by hansong.xhs on 2016/6/22.
 */
class ConnectionCtx {

    SocketChannel channel
    Object attachment
    Worker worker
    Long timeout
    PipeLine pipeLine

    ConnectionCtx() {
        this.timeout = System.currentTimeMillis() + GettyConfig.TIME_OUT
    }
}
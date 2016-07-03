package com.hansong.getty

import java.nio.channels.SocketChannel

/**
 * Created by hansong.xhs on 2016/6/22.
 */
class ConnectionCtx {

    SocketChannel channel
    Object attachment
    Worker worker
    Long timeout

    ConnectionCtx(channel, worker) {
        this.channel = channel
        this.worker = worker
        this.timeout = System.currentTimeMillis() + GettyConfig.TIME_OUT
    }

}
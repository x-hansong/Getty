package com.hansong.getty

import com.hansong.getty.event.PipeLine

import java.nio.channels.SocketChannel

/**
 * 连接上下文
 * Created by hansong.xhs on 2016/6/22.
 */
class ConnectionCtx {

    /**socket连接*/
    SocketChannel channel

    /**用于携带额外参数*/
    Object attachment

    /**处理当前连接的工作线程*/
    Worker worker

    /**连接超时时间*/
    Long timeout

    /**每个连接拥有自己的pipeline*/
    PipeLine pipeLine

    ConnectionCtx() {
        this.timeout = System.currentTimeMillis() + GettyConfig.TIME_OUT
    }

    /**
     * 重置超时时间
     * @return
     */
    void resetTimeout() {
        this.timeout = System.currentTimeMillis() + GettyConfig.TIME_OUT
    }
}
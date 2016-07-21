package com.hansong.getty.event

/**
 * 事件监听器
 * Created by hansong.xhs on 2016/6/22.
 */
interface ServerListener extends Serializable{

    /**
     * 可读事件回调
     * @param request
     */
    void onRead(ctx)

    /**
     * 可写事件回调
     * @param request
     * @param response
     */
    void onWrite(ctx)

    /**
     * 连接关闭回调
     * @param request
     */
    void onClosed(ctx)

}

package com.hansong.getty.event

/**
 * Created by hansong.xhs on 2016/6/27.
 */
interface Notifier extends Serializable{
    /**
     * 触发所有可读事件回调
     */
    void fireOnRead(ctx)

    /**
     * 触发所有可写事件回调
     */
    void fireOnWrite(ctx)

    /**
     * 触发所有连接关闭事件回调
     */
    void fireOnClosed(ctx)

}
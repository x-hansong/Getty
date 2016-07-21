package com.hansong.getty.event

/**
 * 事件处理器链
 * Created by hansong.xhs on 2016/6/27.
 */
class HandlerChain implements Notifier{

    EventAdapter head
    EventAdapter tail

    /**
     * 添加处理器到执行链的最后
     * @param handler
     */
    void addLast(handler) {
        if (tail != null) {
            tail.next = handler
            tail = tail.next
        } else {
            head = handler
            tail = head
        }
    }

    void fireOnRead(ctx) {
        head.onRead(ctx)
    }

    void fireOnWrite(ctx) {
        head.onWrite(ctx)
    }

    void fireOnClosed(ctx) {
        head.onClosed(ctx)
    }

}
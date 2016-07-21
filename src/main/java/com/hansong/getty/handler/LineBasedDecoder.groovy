package com.hansong.getty.handler

import com.hansong.getty.BufferUtils
import com.hansong.getty.event.EventAdapter

/**
 * 行解码器，按行解析数据
 * Created by hansong.xhs on 2016/6/27.
 */
class LineBasedDecoder extends EventAdapter {

    //缓存列表，存储没有解析的数据
    def remains = []

    void onRead(ctx) {
        def buffer = ctx.attachment

        remains.add(BufferUtils.readFrom(buffer))

        //拼接缓存列表
        def data = BufferUtils.combineBytesList(remains)

        //拼接完后清空缓存列表
        remains.clear()

        //找到最后一行的位置
        int endOfFrame = endOfFrame(data)

        //如果没有完整的一行，加入缓存列表中
        if (endOfFrame == -1) {
            remains.add(data)
            return
        } else { //解析每一行，

            def frame = data[0..endOfFrame]
            def lines = decode(frame)

            ctx.attachment = lines

            //将剩下的数据加入缓存列表
            if (endOfFrame < data.length - 1) {
                remains.add(data[endOfFrame + 1..data.length - 1])
            }

            next.onRead(ctx)
        }
    }

    /**
     * 解析数据
     * @param frame 原始二进制数据
     * @return 按换行符分割的数据列表
     */
    def decode(frame) {
        def lines = []
        int start = 0
        //从第一个非换行符开始
        while (start < frame.size() &&
                (frame[start] == '\n')) {
            start++
        }
        for (int i = 0; i < frame.size(); i++) {
            if (frame[i] == '\n') {
                int end = i - 1 >= 0 ? i - 1 : 0
                if (start <= end) {
                    lines.add(frame[start..end])
                    start = i + 1
                } else {
                    //添加空行
                    lines.add([])
                }

            }
        }

        lines
    }

    /**
     * 返回数组中最后一个'\r\n'的'\n'的位置
     * @param data
     * @return 如果找不到返回-1
     */
    int endOfFrame(data) {
        int endOfFrame = -1
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] == '\n') {
                endOfFrame = i
                break
            }
        }
        endOfFrame
    }
}
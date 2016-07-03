package com.hansong.getty

import java.nio.ByteBuffer

/**
 * Created by hansong.xhs on 2016/6/27.
 */
class BufferUtils {

    static ByteBuffer buffer = ByteBuffer.allocateDirect(GettyConfig.COMMON_BUFFER_SIZE)

    /**
     * 从ByteBuffer中读取所有数据
     * @param buffer
     * @return
     */
    static byte[] readFrom(buffer) {
        buffer.flip()

        def data = new byte[buffer.remaining()]
        buffer.get(data)

        data
    }

    /**
     * 合并字节数组列表为一个字节数组
     * @param remains
     * @return
     */
    static byte[] combineBytesList(remains) {
        buffer.clear()

        remains.each { bytes ->
            buffer.put(bytes)
        }

        readFrom(buffer)
    }
}
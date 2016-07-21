package com.hansong.getty.handler

import com.hansong.getty.event.EventAdapter
import org.slf4j.LoggerFactory

/**
 * Http响应处理器
 * Created by hansong.xhs on 2016/6/29.
 */
class HttpResponseHandler extends EventAdapter {

    static logger = LoggerFactory.getLogger(HttpResponseHandler.name)

    void onRead(ctx) {
        def response = ctx.attachment
        def worker = ctx.worker

        //先写入协议头部
        worker.write(ctx.channel, encodeHeader(response))

        switch (response.body) {
        //如果body是文件
            case File:
                worker.write(ctx.channel, response.body)
                break
            case String:
                worker.write(ctx.channel, response.body.bytes)
                break
        }
    }

    /**
     * 将response转化为byte[]
     * @param response
     * @return
     */
    byte[] encodeHeader(response) {
        def headerStr = response.headers.inject([]) { result, entry ->
            result << "${entry.key}: ${entry.value}"
        }.join('\r\n')

        //如果头部非空，加上换行符
        if (headerStr) {
            headerStr += "\r\n"
        }

        logger.debug(headerStr)

        def str = "${response.version} ${response.status}\r\n${headerStr}\r\n"
        logger.debug(str)

        str.bytes
    }
}
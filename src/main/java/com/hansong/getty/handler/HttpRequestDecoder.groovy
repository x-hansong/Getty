package com.hansong.getty.handler

import com.hansong.getty.http.HttpRequest
import com.hansong.getty.event.EventAdapter

import static com.hansong.getty.handler.HttpRequestDecoder.State.*

/**
 * Created by hansong.xhs on 2016/6/28.
 */
/**
 * HTTP请求解析，目前只支持GET请求
 */
class HttpRequestDecoder extends EventAdapter {

    enum State {
        SKIP_CONTROL_CHARS,
        READ_INITIAL,
        READ_HEADER,
        READ_VARIABLE_LENGTH_CONTENT,
        READ_FIXED_LENGTH_CONTENT,
        READ_CHUNK_SIZE,
        READ_CHUNKED_CONTENT,
        READ_CHUNK_DELIMITER,
        READ_CHUNK_FOOTER,
        BAD_MESSAGE,
        UPGRADED
    }

    def currentState = READ_INITIAL

    def request

    void onRead(ctx) {
        def lines = ctx.attachment

        request = resetRequest()

        lines.removeAll { data ->
            def line = new String(data as byte[]).trim()

            switch (currentState) {
                case READ_INITIAL:
                    decodeHeaderLine(line, request)
                    currentState = READ_HEADER
                    break
                case READ_HEADER:
                    if (line == "") {
                        currentState = READ_INITIAL
                        ctx.attachment = request
                        next.onRead(ctx)
                    } else {
                        decodeHeader(line, request)
                    }
                    break
            }
        }
    }

    /**
     * 解析请求行
     * @param line
     * @param request
     * @return
     */
    def decodeHeaderLine(line, request) {
        def words = line.tokenize()
        if (words.size() == 3) {
            request.with {
                method = words[0]
                url = words[1]
                version = words[2]
            }
        } else {
            throw new RuntimeException("头部行的格式不对")
        }
    }

    /**
     * 解析头部
     * @param line
     * @param request
     * @return
     */
    def decodeHeader(line, request) {
        def words = line.tokenize(":").collect { it.trim() }
        def headers = request.headers
        headers[words[0]] = words[1]
    }

    /**
     * 重置请求，如果请求还在解析中，返回缓存的请求
     * @return
     */
    def resetRequest() {
        if (request == null || currentState == READ_INITIAL) {
            return new HttpRequest()
        } else {
            return request
        }
    }
}
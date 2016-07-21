package com.hansong.getty.handler

import com.hansong.getty.GettyConfig
import com.hansong.getty.event.EventAdapter
import com.hansong.getty.http.HttpResponse
import com.hansong.getty.http.HttpStatus

import java.nio.file.Files
import java.nio.file.Paths

import static com.hansong.getty.http.HttpHeader.*
import static com.hansong.getty.http.HttpMethod.*

/**
 * Http 请求处理器，目前只支持GET方法
 * Created by hansong.xhs on 2016/6/29.
 */
class HttpRequestHandler extends EventAdapter{

    void onRead(ctx) {
        def request = ctx.attachment

        def response = new HttpResponse()
        response.version = request.version

        def method = request.method
        switch (method) {
            case GET:
                doGet(request, response)

                ctx.attachment = response
                next.onRead(ctx)
                break
            case POST:
                doPost(request, response)
                break
        }
        logger.debug("Handle Request: {}", request)
    }

    def doPost(request, response) {
        logger.debug(request.body)
    }

    def doGet(request, response) {
        def path = GettyConfig.ROOT + request.url

        File file = new File(path)

        if (file.exists()) {
            response.headers[CONTENT_TYPE] = Files.probeContentType(Paths.get(path))
            response.headers[CONTENT_LENGTH] = file.length()
            response.status = HttpStatus.OK
            response.body = file

        } else {
            response.status = HttpStatus.NOT_FOUND
            response.body = "<html>\n" +
                    "<body>\n" +
                    "<h1>404 NOT FOUND</h1>\n" +
                    "</body>\n" +
                    "</html>\n"
            response.headers[CONTENT_LENGTH] = response.body.length()
        }

    }
}
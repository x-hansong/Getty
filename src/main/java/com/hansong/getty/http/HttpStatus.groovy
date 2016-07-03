package com.hansong.getty.http

/**
 * Created by hansong.xhs on 2016/6/29.
 */
enum HttpStatus {
    OK(200, "OK"),

    BAD_REQUEST(400, "Bad Request"),

    UNAUTHORIZED(401, "Unauthorized"),

    NOT_FOUND(404, "Not Found"),

    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error")

    HttpStatus(code, reasonPhrase) {
        this.code = code
        this.reasonPhrase = reasonPhrase
    }

    final int code

    final String reasonPhrase

    String toString() {
        "${this.code} ${this.reasonPhrase}"
    }
}

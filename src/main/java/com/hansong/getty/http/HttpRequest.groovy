package com.hansong.getty.http

import groovy.transform.ToString

/**
 * Created by hansong.xhs on 2016/6/27.
 */
@ToString
class HttpRequest {

    def method
    def url
    def version

    def headers = [:]

    def body
}
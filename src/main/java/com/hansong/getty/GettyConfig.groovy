package com.hansong.getty

import groovy.transform.ToString
import org.slf4j.LoggerFactory

/**
 * Created by hansong.xhs on 2016/6/28.
 */
@ToString
class GettyConfig {

    static logger = LoggerFactory.getLogger(GettyConfig.name)

    //用于拼接消息时使用的二进制数组的缓存区
    static int COMMON_BUFFER_SIZE

    //工作线程读取tcp数据的缓存大小
    static int WORKER_RCV_BUFFER_SIZE

    //监听的端口
    static int PORT

    //工作线程的数量
    static int WORKER_NUM

    //根目录
    static String ROOT

    //连接超时自动断开时间
    static long TIME_OUT

    static String prosFile = "getty.properties"

    private GettyConfig() {

    }

    static void loadPros() {
        Properties properties = new Properties()

        try {

            def input = GettyConfig.class.getClassLoader().getResourceAsStream(prosFile)
            properties.load(input)

            COMMON_BUFFER_SIZE = properties.get("common_buffer_size", 1024) as Integer
            WORKER_RCV_BUFFER_SIZE = properties.get("worker_rcv_buffer_size", 1024) as Integer
            PORT = properties.get("port", 4399) as Integer
            WORKER_NUM = properties.get("worker_num", 4) as Integer
            ROOT = properties.get("root", ".") as String
            TIME_OUT = (properties.get("timeout", 900) as Long ) * 1000

        } catch (Exception e) {
            logger.error("load config failed", e)
        }
    }
}
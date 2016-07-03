package test

import java.nio.ByteBuffer

/**
 * Created by hansong.xhs on 2016/6/27.
 */
class ByteBufferTest extends GroovyTestCase {

    void testBuffer() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024)

        buffer.put("123\r\n321\r\n123\r\n\r\n123".bytes)
        buffer.flip()

        def data = new byte[buffer.remaining()]
        buffer.get(data)

        def str = new String(data)

        def i =  str.lastIndexOf("\r\n") + 2

//        def lines = str.substring(0, i).split("\r\n")
//
//        println lines.size()
//        println lines

        def sub = str.substring(0, i)
//        println sub

        sub.eachLine { line ->
            println line
        }


        println buffer.remaining()

        println "GET /js/jquery.form.js HTTP/1.1"[0..0]
        println "  Connection: keep-alive".tokenize(":").collect { it.trim()}
    }

    void testByteList() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024)

        buffer.put("123\r\n321\r\n123\r\n\r\n123".bytes)
        buffer.flip()

        def data = new byte[buffer.remaining()]
        buffer.get(data)

        def remain = []
        remain += data

        assert data[3] == '\r'

        remain.each { i ->
            println i
        }

    }


}

package test

import com.hansong.getty.handler.LineBasedDecoder

/**
 * Created by hansong.xhs on 2016/6/29.
 */
class LineBasedDecoderTest extends GroovyTestCase{

    def coder = new LineBasedDecoder()

    void testEndOfFrame() {
        def data = "123\r\n321\r\n123\r\n\r\n123".bytes

        def line = data as List

        println new String(line as byte[])

        assert coder.endOfFrame(data) == 16

    }

    void testDecode() {

        assert coder.decode("1\r\n2\r\n3\r\n\r\n".bytes) == [[49, 13], [50, 13], [51, 13], [13]]
        assert coder.decode("\r\n".bytes) == [[13]]
        assert coder.decode("1\r\n".bytes) == [[49, 13]]
    }


}
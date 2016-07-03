package test

import com.hansong.getty.NioServer
import com.hansong.getty.event.HandlerChain
import com.hansong.getty.event.PipeLine
import com.hansong.getty.handler.*
/**
 * Created by hansong.xhs on 2016/6/28.
 */
class WebServerTest {
    static void main(args) {
        def pipeLine = new PipeLine()

        def readChain = new HandlerChain()
        readChain.addLast(new LineBasedDecoder())
        readChain.addLast(new HttpRequestDecoder())
        readChain.addLast(new HttpRequestHandler())
        readChain.addLast(new HttpResponseHandler())

        def closeChain = new HandlerChain()
        closeChain.addLast(new ClosedHandler())

        pipeLine.addChain(readChain)
        pipeLine.addChain(closeChain)

        NioServer nioServer = new NioServer(pipeLine)
        nioServer.start()

    }
}

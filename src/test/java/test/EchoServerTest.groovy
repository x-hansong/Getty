package test

import com.hansong.getty.handler.ClosedHandler
import com.hansong.getty.handler.EchoHandler
import com.hansong.getty.NioServer
import com.hansong.getty.event.HandlerChain
import com.hansong.getty.event.PipeLine
import com.hansong.getty.handler.LogHandler

/**
 * Created by hansong.xhs on 2016/6/23.
 */
class EchoServerTest {
    static void main(args) {

        def pipeLine = new PipeLine()
        def readChain = new HandlerChain()
        readChain.addLast(new EchoHandler())
        readChain.addLast(new LogHandler())

        def closeChain = new HandlerChain()
        closeChain.addLast(new ClosedHandler())

        pipeLine.addChain(readChain)
        pipeLine.addChain(closeChain)

        NioServer nioServer = new NioServer(pipeLine)
        nioServer.start()

    }
}
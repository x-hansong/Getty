package com.hansong.getty

/**
 * Created by hansong.xhs on 2016/6/30.
 */
class CommonUtils {
    // standard deep copy implementation
    static def deepCopy(orig) {
        def bos = new ByteArrayOutputStream()
        def oos = new ObjectOutputStream(bos)
        oos.writeObject(orig)
        oos.flush()
        def bin = new ByteArrayInputStream(bos.toByteArray())
        def ois = new ObjectInputStream(bin)
        return ois.readObject()
    }

}

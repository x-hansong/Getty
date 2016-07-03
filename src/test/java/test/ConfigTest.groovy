package test

import com.hansong.getty.GettyConfig

/**
 * Created by hansong.xhs on 2016/6/28.
 */
class ConfigTest extends GroovyTestCase{

    void testConfig() {
        GettyConfig.loadPros()

        assert GettyConfig.COMMON_BUFFER_SIZE == 1024
        assert GettyConfig.WORKER_RCV_BUFFER_SIZE == 1024
    }
}
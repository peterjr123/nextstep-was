package http.support;

import org.junit.Assert;
import org.junit.Test;

public class UrlDecoderTest {
    @Test
    public void decodeTest() {
        String url = "java%40slipp.net";
        UrlDecoder decoder = new UrlDecoderImpl();
        Assert.assertEquals("java@slipp.net", decoder.decode(url));
    }
}

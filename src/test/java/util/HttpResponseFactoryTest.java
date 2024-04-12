package util;

import http.*;
import org.junit.Assert;
import org.junit.Test;

public class HttpResponseFactoryTest {
    @Test
    public void responseMessageContentTypeTest() {
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage();
        HttpResponseFactory httpResponseFactory = new HttpResponseFactoryImpl();

        httpRequestMessage.setUrl("/css/styles.css");
        HttpResponseMessage httpResponseMessage = httpResponseFactory.createHttpResponse(httpRequestMessage);
        Assert.assertEquals("text/css", httpResponseMessage.getHttpHeader().getHeader("Content-Type"));

        httpRequestMessage.setUrl("/js/bootstrap.min.js");
        httpResponseMessage = httpResponseFactory.createHttpResponse(httpRequestMessage);
        Assert.assertEquals("application/javascript", httpResponseMessage.getHttpHeader().getHeader("Content-Type"));
    }
}

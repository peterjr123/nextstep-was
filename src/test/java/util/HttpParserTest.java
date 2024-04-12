package util;

import http.*;
import org.junit.Assert;
import org.junit.Test;

public class HttpParserTest {
    @Test
    public void parsingHeaderTest() {
        String httpHeaderString = "" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";

        HttpHeaderParser parser = new HttpHeaderParserImpl(httpHeaderString);
        HttpHeader httpHeader = parser.parse();

        Assert.assertEquals("localhost:8080", httpHeader.getHeader("Host"));
        Assert.assertEquals("keep-alive", httpHeader.getHeader("Connection"));
        Assert.assertEquals("*/*", httpHeader.getHeader("Accept"));
    }

    @Test
    public void parsingMessageTest() {
        String httpMessageString = "" +
                "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n";

        HttpRequestParser parser = new HttpRequestParserImpl(httpMessageString);
        HttpRequestMessage message = parser.parse();

        Assert.assertEquals("/index.html", message.getUrl());
        Assert.assertEquals("localhost:8080", message.getHttpHeader().getHeader("Host"));
    }
}

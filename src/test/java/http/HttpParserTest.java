package http;

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

        Assert.assertEquals("/index.html", message.getRequestPath());
        Assert.assertEquals("localhost:8080", message.getHttpHeader().getHeader("Host"));
    }

    @Test
    public void parsingSignUpRequestTest() {
        String httpMessageString = "" +
                "GET /user/create?userId=peterjr123&password=password&name=joonsuk&email=peterjr123%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n";

        HttpRequestParser parser = new HttpRequestParserImpl(httpMessageString);
        HttpRequestMessage message = parser.parse();

        Assert.assertEquals("/user/create", message.getRequestPath());
        Assert.assertEquals("peterjr123", message.getQueryStringValue("userId"));
        Assert.assertEquals("password", message.getQueryStringValue("password"));
        Assert.assertEquals("joonsuk", message.getQueryStringValue("name"));
        Assert.assertEquals("peterjr123@slipp.net", message.getQueryStringValue("email"));
    }
}

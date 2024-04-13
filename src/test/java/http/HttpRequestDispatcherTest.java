package http;

import http.strategy.FileRequestHandlingStrategy;
import http.strategy.LoginRequestHandlingStrategy;
import http.strategy.RequestHandlingStrategy;
import http.strategy.SignUpRequestHandlingStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpRequestDispatcherTest {
    @Test
    public void dispatchTest() {
        // 1. file request
        String httpMessageString = "" +
                "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n";

        HttpRequestParser parser = new HttpRequestParserImpl(httpMessageString);
        HttpRequestMessage message = parser.parse();

        HttpRequestDispatcher dispatcher = new HttpRequestDispatcherImpl();
        RequestHandlingStrategy strategy = dispatcher.dispatch(message);

        Assert.assertTrue(strategy instanceof FileRequestHandlingStrategy);

        // 2. sign up
        httpMessageString = "" +
                "POST /user/create\n" +
                "Host: localhost:8080\n";

        parser = new HttpRequestParserImpl(httpMessageString);
        message = parser.parse();
        strategy = dispatcher.dispatch(message);

        Assert.assertTrue(strategy instanceof SignUpRequestHandlingStrategy);

        // 3. login
        httpMessageString = "" +
                "POST /user/login\n" +
                "Host: localhost:8080\n";

        parser = new HttpRequestParserImpl(httpMessageString);
        message = parser.parse();
        strategy = dispatcher.dispatch(message);

        Assert.assertTrue(strategy instanceof LoginRequestHandlingStrategy);
    }
}

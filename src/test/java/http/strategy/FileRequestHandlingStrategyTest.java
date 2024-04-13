package http.strategy;

import http.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileRequestHandlingStrategyTest {
    @Test
    public void getIndexFile() throws IOException {
        String httpMessageString = "" +
                "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n";

        HttpRequestParser parser = new HttpRequestParserImpl(httpMessageString);
        HttpRequestMessage message = parser.parse();

        RequestHandlingStrategy strategy = new FileRequestHandlingStrategy(message);

        HttpResponseMessage responseMessage = strategy.handleRequest();

        String filePath = "/index.html";
        Path path = Paths.get("./webapp"+ filePath);
        Assert.assertEquals(Files.size(path), responseMessage.getResponseBody().length);
        Assert.assertEquals("text/html", responseMessage.getHttpHeader().getHeader("Content-Type"));
    }
}

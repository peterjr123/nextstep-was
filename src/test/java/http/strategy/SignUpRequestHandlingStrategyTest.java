package http.strategy;

import http.HttpRequestMessage;
import http.HttpRequestParser;
import http.HttpRequestParserImpl;
import org.junit.Test;

public class SignUpRequestHandlingStrategyTest {
    @Test
    public void signUpByGet() {
        String httpMessageString = "" +
                "GET /user/create?userId=peterjr123&password=password&name=joonsuk&email=peterjr123%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n";

        HttpRequestParser parser = new HttpRequestParserImpl(httpMessageString);
        HttpRequestMessage message = parser.parse();

        RequestHandlingStrategy strategy = new SignUpRequestHandlingStrategy(message);
        strategy.handleRequest();
    }

    @Test
    public void signUpByPost() {
        String bodyData = "userId=javajigi&password=password&name=JaeSung&email=peterjr123@slipp.net";
        String httpMessageString = "" +
                "POST /user/create\n" +
                "Host: localhost:8080\n" +
                "Content-Length: "+ bodyData.length() +"\n";

        HttpRequestParser parser = new HttpRequestParserImpl(httpMessageString);
        HttpRequestMessage message = parser.parse();
        message.setBody(bodyData);

        RequestHandlingStrategy strategy = new SignUpRequestHandlingStrategy(message);
        strategy.handleRequest();
    }
}

package http.strategy;

import db.DataBase;
import http.HttpRequestMessage;
import http.HttpRequestParser;
import http.HttpRequestParserImpl;
import http.HttpResponseMessage;
import org.junit.Assert;
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

        Assert.assertEquals("peterjr123@slipp.net", DataBase.findUserById("peterjr123").getEmail());
    }

    @Test
    public void signUpByPost() {
        // name = 박준석, email = @
        String bodyData = "userId=peter&password=adawda&name=%EB%B0%95%EC%A4%80%EC%84%9D&email=peter%40slipp.net";
        String httpMessageString = "" +
                "POST /user/create\n" +
                "Host: localhost:8080\n" +
                "Content-Length: "+ bodyData.length() +"\n";

        HttpRequestParser parser = new HttpRequestParserImpl(httpMessageString);
        HttpRequestMessage message = parser.parse();
        message.setBody(bodyData);

        RequestHandlingStrategy strategy = new SignUpRequestHandlingStrategy(message);
        HttpResponseMessage responseMessage = strategy.handleRequest();

        Assert.assertTrue(responseMessage.getStatusCode().equals("303 See Other"));
        Assert.assertTrue(responseMessage.getHttpHeader().hasHeader("Location"));

//        Assert.assertEquals("박준석", DataBase.findUserById("peter").getName());
        Assert.assertEquals("peter@slipp.net", DataBase.findUserById("peter").getEmail());
    }
}

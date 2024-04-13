package http.strategy;

import db.DataBase;
import http.HttpRequestMessage;
import http.HttpResponseMessage;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginRequestHandlingStrategyTest {
    @Before
    public void setUp() {
        User user = new User("peter", "password", "joon", "peter@gmail.com");
        DataBase.addUser(user);
    }

    @Test
    public void loginResponseTest() {
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage();
        httpRequestMessage.setRequestPath("/user/login");
        httpRequestMessage.setBody("userId=peter&password=password");
        RequestHandlingStrategy requestHandlingStrategy = new LoginRequestHandlingStrategy(httpRequestMessage);

        HttpResponseMessage httpResponseMessage = requestHandlingStrategy.handleRequest();
        Assert.assertEquals("logined=true", httpResponseMessage.getHttpHeader().getHeader("Set-Cookie"));

        httpRequestMessage.setBody("userId=peter&password=wrong");
        requestHandlingStrategy = new LoginRequestHandlingStrategy(httpRequestMessage);
        httpResponseMessage = requestHandlingStrategy.handleRequest();

        Assert.assertEquals("logined=false", httpResponseMessage.getHttpHeader().getHeader("Set-Cookie"));
    }
}

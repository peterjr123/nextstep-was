package http.strategy;

import db.DataBase;
import http.HttpRequestMessage;
import http.HttpResponseMessage;
import model.User;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;

public class UserListRequestHandlingStrategyTest {
    @Test
    public void redirectByCookieTest() {
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage();
        httpRequestMessage.getHttpHeader().addHeader("Cookie", "logined=false");
        RequestHandlingStrategy strategy = new UserListRequestHandlingStrategy(httpRequestMessage);

        HttpResponseMessage httpResponseMessage = strategy.handleRequest();
        Assert.assertEquals("401 Unauthorized", httpResponseMessage.getStatusCode());

        httpRequestMessage = new HttpRequestMessage();
        httpRequestMessage.getHttpHeader().addHeader("Cookie", "logined=true");

        strategy = new UserListRequestHandlingStrategy(httpRequestMessage);
        httpResponseMessage = strategy.handleRequest();
        Assert.assertEquals("200 OK", httpResponseMessage.getStatusCode());

    }

    @Test
    public void replaceByRuntimeUserListTest() {
        User user1 = new User("peter", "password", "박준석", "peter@gmail.com");
        User user2 = new User("james", "password", "park", "james@gmail.com");
        DataBase.addUser(user1);
        DataBase.addUser(user2);

        HttpRequestMessage httpRequestMessage = new HttpRequestMessage();
        httpRequestMessage.getHttpHeader().addHeader("Cookie", "logined=true");

        RequestHandlingStrategy strategy = new UserListRequestHandlingStrategy(httpRequestMessage);
        HttpResponseMessage httpResponseMessage = strategy.handleRequest();

        String body = new String(httpResponseMessage.getResponseBody(), StandardCharsets.UTF_8);
        System.out.println(body);
        Assert.assertTrue(body.contains("<tbody>"));
        Assert.assertTrue(body.contains("<td>peter</td>"));
        Assert.assertTrue(body.contains("<td>park</td>"));
        Assert.assertTrue(body.contains("<td>peter@gmail.com</td>"));
        Assert.assertTrue(body.contains("<td>james</td>"));
        Assert.assertTrue(body.contains("</tbody>"));
    }
}

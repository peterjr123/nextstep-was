package http.strategy;

import db.DataBase;
import http.HttpRequestMessage;
import http.HttpRequestMethod;
import http.HttpResponseMessage;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import webserver.RequestHandler;

import java.util.Map;

public class SignUpRequestHandlingStrategy implements RequestHandlingStrategy {
    private static final Logger log = LoggerFactory.getLogger(SignUpRequestHandlingStrategy.class);

    HttpRequestMessage httpRequestMessage;
    public SignUpRequestHandlingStrategy(HttpRequestMessage httpRequestMessage) {
        this.httpRequestMessage = httpRequestMessage;
    }

    @Override
    public HttpResponseMessage handleRequest() {
        User user = null;
        if(httpRequestMessage.getRequestMethod() == HttpRequestMethod.GET) {
            user = createUserByQueryString();
        }
        else if(httpRequestMessage.getRequestMethod() == HttpRequestMethod.POST) {
            user = createUserByBody();
        }
        DataBase.addUser(user);
        log.debug("new user object created: " + user);


        HttpResponseMessage httpResponseMessage = new HttpResponseMessage();
        httpResponseMessage.setStatusCode("303 See Other");
        httpResponseMessage.getHttpHeader().addHeader("Location", "/index.html");
        return httpResponseMessage;
    }

    private User createUserByBody() {
        String body = httpRequestMessage.getBody();
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(body);

        return new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
    }

    private User createUserByQueryString() {
        User user = new User(
                httpRequestMessage.getQueryStringValue("userId"),
                httpRequestMessage.getQueryStringValue("password"),
                httpRequestMessage.getQueryStringValue("name"),
                httpRequestMessage.getQueryStringValue("email"));
        return user;
    }
}

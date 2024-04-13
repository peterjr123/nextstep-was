package http.strategy;

import db.DataBase;
import http.HttpHeader;
import http.HttpRequestMessage;
import http.HttpResponseMessage;
import model.User;
import util.HttpRequestUtils;

import javax.xml.crypto.Data;
import java.util.Map;

public class LoginRequestHandlingStrategy implements RequestHandlingStrategy {
    HttpRequestMessage httpRequestMessage;
    public LoginRequestHandlingStrategy(HttpRequestMessage httpRequestMessage) {
        this.httpRequestMessage = httpRequestMessage;
    }

    @Override
    public HttpResponseMessage handleRequest() {
        String body = httpRequestMessage.getBody();
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(body);

        String userId = parameters.get("userId");
        String password = parameters.get("password");
        boolean isValidUser = false;

        if(DataBase.findUserById(userId) != null) {
            User user = DataBase.findUserById(userId);
            if(user.getPassword().equals(password)) {
                isValidUser = true;
            }
        }


        HttpResponseMessage httpResponseMessage = new HttpResponseMessage();
        httpResponseMessage.setStatusCode("303 See Other");
        HttpHeader httpHeader = httpResponseMessage.getHttpHeader();
        httpHeader.addHeader("Content-Type", "text/html");

        if(isValidUser) {
            httpHeader.addHeader("Set-Cookie", "logined=true");
            httpHeader.addHeader("Location", "/index.html");
        }
        else {
            httpHeader.addHeader("Set-Cookie", "logined=false");
            httpHeader.addHeader("Location", "/user/login_failed.html");
        }

        return httpResponseMessage;
    }
}

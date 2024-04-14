package http.strategy;

import db.DataBase;
import http.HttpHeader;
import http.HttpRequestMessage;
import http.HttpResponseMessage;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserListRequestHandlingStrategy implements RequestHandlingStrategy {
    HttpRequestMessage httpRequestMessage;

    public UserListRequestHandlingStrategy(HttpRequestMessage httpRequestMessage) {
        this.httpRequestMessage = httpRequestMessage;
    }

    @Override
    public HttpResponseMessage handleRequest() {
        HttpResponseMessage httpResponseMessage;

        String cookieHeader = httpRequestMessage.getHttpHeader().getHeader("Cookie");
        Map<String, String> parameters = HttpRequestUtils.parseCookies(cookieHeader);
        if(parameters.containsKey("logined") && (parameters.get("logined").equals("true"))) {
            httpResponseMessage = handleOnAuthorized();
        }
        else {
            httpResponseMessage = handleOnUnauthorized();
        }

        return httpResponseMessage;
    }

    private HttpResponseMessage handleOnAuthorized() {
        HttpResponseMessage httpResponseMessage = new HttpResponseMessage();
        HttpHeader responseHeader = httpResponseMessage.getHttpHeader();

        httpResponseMessage.setStatusCode("200 OK");
        responseHeader.addHeader("Content-Type", "text/html");

        try {
            byte[] rawData = IOUtils.readFile("/user/list.html");
            String data = new String(rawData, StandardCharsets.UTF_8);

            String convertedData = addUserToHtml(data);
            System.out.println(convertedData);
            byte[] body = convertedData.getBytes(StandardCharsets.UTF_8);
            httpResponseMessage.setResponseBody(body);
            responseHeader.addHeader("Content-Length", String.valueOf(body.length));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return httpResponseMessage;
    }

    private String addUserToHtml(String data) {
        StringBuilder original = new StringBuilder(data);
        StringBuilder replacer = new StringBuilder();

        int userCount = 0;
        for(User user : DataBase.findAll()) {
            replacer.append("<tr>\n");
            replacer.append("<th scope=\"row\">"+ (++userCount) + "</th>\n");
            replacer.append("<td>" + user.getUserId() + "</td>\n");
            replacer.append("<td>" + user.getName() + "</td>\n");
            replacer.append("<td>" + user.getEmail() + "</td>\n");
            replacer.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n");
            replacer.append("</tr>\n");
        }

        int startIdx = original.indexOf("<tbody>") + "<tbody>".length();
        int endIdx = original.indexOf("</tbody>");
        original.replace(startIdx, endIdx, replacer.toString());

        return original.toString();
    }


    private HttpResponseMessage handleOnUnauthorized() {
        HttpResponseMessage httpResponseMessage = new HttpResponseMessage();
        HttpHeader responseHeader = httpResponseMessage.getHttpHeader();
        httpResponseMessage.setStatusCode("303 See Other");
        responseHeader.addHeader("Location", "/index.html");
        return httpResponseMessage;
    }
}

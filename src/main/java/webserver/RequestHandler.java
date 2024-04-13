package webserver;

import java.io.*;
import java.net.Socket;

import http.*;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            String requestMessage = IOUtils.readRequest(new BufferedReader(new InputStreamReader(in)));

            HttpRequestParser httpRequestParser = new HttpRequestParserImpl(requestMessage);
            HttpRequestMessage httpRequestMessage = httpRequestParser.parse();
            log.debug("New Http Request : " + httpRequestMessage.getUrl());

            handleRequest(new DataOutputStream(out), httpRequestMessage);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void handleRequest(DataOutputStream dos, HttpRequestMessage httpRequestMessage) {
        HttpResponseFactory factory = new HttpResponseFactoryImpl();
        HttpResponseMessage httpResponseMessage = factory.createHttpResponse(httpRequestMessage);

        if(httpRequestMessage.getRequestPath().equals("/user/create")) {
            User user = new User(
                    httpRequestMessage.getQueryStringValue("userId"),
                    httpRequestMessage.getQueryStringValue("password"),
                    httpRequestMessage.getQueryStringValue("name"),
                    httpRequestMessage.getQueryStringValue("email"));
            log.debug("new user object created: " + user);
        }

        write200Header(dos, httpResponseMessage.getHttpHeader());
        responseBody(dos, httpResponseMessage.getResponseBody());
    }

    private void write200Header(DataOutputStream dos, HttpHeader responseHeader) {

        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + responseHeader.getHeader("Content-Type") + "\r\n");
            dos.writeBytes("Content-Length: " + responseHeader.getHeader("Content-Length") + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}

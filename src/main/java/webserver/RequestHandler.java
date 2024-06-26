package webserver;

import java.io.*;
import java.net.Socket;

import http.*;
import http.strategy.RequestHandlingStrategy;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            String requestMessage = IOUtils.readRequestHeader(br);

            HttpRequestParser httpRequestParser = new HttpRequestParserImpl(requestMessage);
            HttpRequestMessage httpRequestMessage = httpRequestParser.parse();
            log.debug("New Http Request : " + httpRequestMessage.getRequestPath());

            if(httpRequestMessage.getHttpHeader().hasHeader("Content-Length")) {
                int contentLength = Integer.parseInt(httpRequestMessage.getHttpHeader().getHeader("Content-Length"));
                String body = IOUtils.readData(br, contentLength);
                log.debug("body : " + body);
                httpRequestMessage.setBody(body);
            }

            handleRequest(new DataOutputStream(out), httpRequestMessage);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    private void handleRequest(DataOutputStream dos, HttpRequestMessage httpRequestMessage) {
        HttpRequestDispatcher dispatcher = new HttpRequestDispatcherImpl();
        RequestHandlingStrategy requestHandlingStrategy = dispatcher.dispatch(httpRequestMessage);

        HttpResponseMessage responseMessage = requestHandlingStrategy.handleRequest();
        sendResponseMessage(dos, responseMessage);
    }

    private void sendResponseMessage(DataOutputStream dos, HttpResponseMessage responseMessage) {
        writeStatusCode(dos, responseMessage);
        writeHeader(dos, responseMessage.getHttpHeader());
        responseBody(dos, responseMessage.getResponseBody());
    }

    private void writeStatusCode(DataOutputStream dos, HttpResponseMessage responseMessage) {
        try {
            dos.writeBytes("HTTP/1.1 " + responseMessage.getStatusCode() + " \r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    private void writeHeader(DataOutputStream dos, HttpHeader responseHeader) {
        try {
            for(String name : responseHeader.getHeaderNames()) {
                dos.writeBytes(name + ": " + responseHeader.getHeader(name) + "\r\n");
            }
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

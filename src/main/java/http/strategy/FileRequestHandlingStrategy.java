package http.strategy;

import http.HttpHeader;
import http.HttpRequestMessage;
import http.HttpResponseMessage;
import util.IOUtils;

import java.io.IOException;

public class FileRequestHandlingStrategy implements RequestHandlingStrategy {
    HttpRequestMessage httpRequestMessage;
    public FileRequestHandlingStrategy(HttpRequestMessage httpRequestMessage) {
        this.httpRequestMessage = httpRequestMessage;
    }


    @Override
    public HttpResponseMessage handleRequest() {
        String contentType = determineContentType();
        byte[] body = getFileData();
        String contentLength = String.valueOf(body.length);

        HttpResponseMessage httpResponseMessage = new HttpResponseMessage();
        HttpHeader httpHeader = httpResponseMessage.getHttpHeader();
        httpHeader.addHeader("Content-Type", contentType);
        httpHeader.addHeader("Content-Length", contentLength);
        httpResponseMessage.setResponseBody(body);

        return httpResponseMessage;
    }

    private String determineContentType() {
        String requestPath = httpRequestMessage.getRequestPath();
        String extension = requestPath.substring(requestPath.lastIndexOf("."), requestPath.length());

        if(extension.equals(".js")) {
            return "application/javascript";
        }
        else if(extension.equals(".css")) {
            return "text/css";
        }
        else {
            return "text/html";
        }
    }

    private byte[] getFileData() {
        byte[] fileData;
        try {
            fileData = IOUtils.readFile(httpRequestMessage.getRequestPath());
        }
        catch(IOException e) {
            fileData = "hello world".getBytes();
        }
        return fileData;
    }
}

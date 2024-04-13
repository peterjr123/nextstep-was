package http;

import util.IOUtils;

import java.io.IOException;

public class HttpResponseFactoryImpl implements HttpResponseFactory {


    @Override
    public HttpResponseMessage createHttpResponse(HttpRequestMessage httpRequestMessage) {
        HttpResponseMessage httpResponseMessage = new HttpResponseMessage();

        httpResponseMessage.setResponseBody(createResponseBody(httpRequestMessage));
        httpResponseMessage.setHttpHeader(createResponseHeader(httpRequestMessage, httpResponseMessage.getResponseBody().length));

        return httpResponseMessage;
    }

    private byte[] createResponseBody(HttpRequestMessage httpRequestMessage) {
        byte[] body;
        try {
            body = IOUtils.readFile(httpRequestMessage.getRequestPath());
        }
        catch(IOException e) {
            body = "hello world".getBytes();
        }
        return body;
    }

    private HttpHeader createResponseHeader(HttpRequestMessage httpRequestMessage, int contentLength) {
        HttpHeader responseHeader = new HttpHeader();

        String requestPath = httpRequestMessage.getRequestPath();

        if(requestPath.contains(".")) {
            String extension = requestPath.substring(requestPath.lastIndexOf("."), requestPath.length());

            if(extension.equals(".js")) {
                responseHeader.addHeader("Content-Type", "application/javascript");
            }
            else if(extension.equals(".css")) {
                responseHeader.addHeader("Content-Type", "text/css");
            }
            else {
                responseHeader.addHeader("Content-Type", "text/html");
            }
        }
        else {
            responseHeader.addHeader("Content-Type", "text/html");
        }

        responseHeader.addHeader("Content-Length", String.valueOf(contentLength));

        return responseHeader;
    }
}

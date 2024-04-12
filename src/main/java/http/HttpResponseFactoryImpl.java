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
            body = IOUtils.readFile(httpRequestMessage.getUrl());
        }
        catch(IOException e) {
            body = "hello world".getBytes();
        }
        return body;
    }

    private HttpHeader createResponseHeader(HttpRequestMessage httpRequestMessage, int contentLength) {
        HttpHeader responseHeader = new HttpHeader();

        String url = httpRequestMessage.getUrl();
        String extension = url.substring(url.lastIndexOf("."), url.length());

        if(extension.equals(".js")) {
            responseHeader.addHeader("Content-Type", "application/javascript");
        }
        else if(extension.equals(".css")) {
            responseHeader.addHeader("Content-Type", "text/css");
        }
        else {
            responseHeader.addHeader("Content-Type", "text/html");
        }

        responseHeader.addHeader("Content-Length", String.valueOf(contentLength));

        return responseHeader;
    }
}

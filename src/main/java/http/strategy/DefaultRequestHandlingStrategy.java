package http.strategy;

import http.HttpRequestMessage;
import http.HttpResponseMessage;

public class DefaultRequestHandlingStrategy implements RequestHandlingStrategy {
    public DefaultRequestHandlingStrategy(HttpRequestMessage httpRequestMessage) {
    }

    @Override
    public HttpResponseMessage handleRequest() {
        return null;
    }
}

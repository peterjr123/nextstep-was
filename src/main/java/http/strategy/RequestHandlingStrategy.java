package http.strategy;

import http.HttpResponseMessage;

public interface RequestHandlingStrategy {
    HttpResponseMessage handleRequest();
}

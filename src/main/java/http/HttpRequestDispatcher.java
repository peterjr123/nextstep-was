package http;

import http.strategy.RequestHandlingStrategy;

public interface HttpRequestDispatcher {
    RequestHandlingStrategy dispatch(HttpRequestMessage httpRequestMessage);
}

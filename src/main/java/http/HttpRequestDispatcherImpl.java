package http;

import http.strategy.DefaultRequestHandlingStrategy;
import http.strategy.FileRequestHandlingStrategy;
import http.strategy.RequestHandlingStrategy;
import http.strategy.SignUpRequestHandlingStrategy;

public class HttpRequestDispatcherImpl implements HttpRequestDispatcher {
    @Override
    public RequestHandlingStrategy dispatch(HttpRequestMessage httpRequestMessage) {
        if(httpRequestMessage.getRequestMethod() == HttpRequestMethod.GET
                && httpRequestMessage.getRequestPath().contains(".")) {
            return new FileRequestHandlingStrategy(httpRequestMessage);
        }
        else if(httpRequestMessage.getRequestPath().equals("/user/create")) {
            return new SignUpRequestHandlingStrategy(httpRequestMessage);
        }
        else {
            return new DefaultRequestHandlingStrategy(httpRequestMessage);
        }
    }
}

package http;

import http.strategy.*;

public class HttpRequestDispatcherImpl implements HttpRequestDispatcher {
    @Override
    public RequestHandlingStrategy dispatch(HttpRequestMessage httpRequestMessage) {

        if(httpRequestMessage.getRequestPath().equals("/user/create")) {
            return new SignUpRequestHandlingStrategy(httpRequestMessage);
        }
        else if(httpRequestMessage.getRequestPath().equals("/user/login")) {
            return new LoginRequestHandlingStrategy(httpRequestMessage);
        }
        else if(httpRequestMessage.getRequestPath().equals("/user/list.html")) {
            return new UserListRequestHandlingStrategy(httpRequestMessage);
        }
        else if(httpRequestMessage.getRequestMethod() == HttpRequestMethod.GET
                && httpRequestMessage.getRequestPath().contains(".")) {
            return new FileRequestHandlingStrategy(httpRequestMessage);
        }
        else {
            return new DefaultRequestHandlingStrategy(httpRequestMessage);
        }
    }
}

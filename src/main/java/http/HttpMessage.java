package http;

public abstract class HttpMessage {
    HttpHeader httpHeader = new HttpHeader();

    HttpRequestMethod requestMethod;

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public void setHttpHeader(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }

    public void setRequestMethod(HttpRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
    public HttpRequestMethod getRequestMethod() {
        return requestMethod;
    }
}

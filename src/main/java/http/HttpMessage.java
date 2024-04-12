package http;

public abstract class HttpMessage {
    HttpHeader httpHeader = new HttpHeader();

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public void setHttpHeader(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }
}

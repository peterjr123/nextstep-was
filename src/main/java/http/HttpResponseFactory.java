package http;

public interface HttpResponseFactory {
    public HttpResponseMessage createHttpResponse(HttpRequestMessage httpRequestMessage);
}

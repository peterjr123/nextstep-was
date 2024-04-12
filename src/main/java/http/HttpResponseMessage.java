package http;

public class HttpResponseMessage extends HttpMessage{
    byte[] ResponseBody;

    public byte[] getResponseBody() {
        return ResponseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        ResponseBody = responseBody;
    }
}

package http;

public class HttpResponseMessage extends HttpMessage{
    byte[] ResponseBody = new byte[0];

    public byte[] getResponseBody() {
        return ResponseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        ResponseBody = responseBody;
    }
}

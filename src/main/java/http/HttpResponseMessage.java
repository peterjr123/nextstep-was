package http;

public class HttpResponseMessage extends HttpMessage{
    byte[] ResponseBody = new byte[0];
    String statusCode = "200 OK";

    public byte[] getResponseBody() {
        return ResponseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        ResponseBody = responseBody;
    }

    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}

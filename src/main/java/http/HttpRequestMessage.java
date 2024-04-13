package http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestMessage extends HttpMessage{
    private String url;
    private String body;
    private String requestPath;

    private Map<String, String> queryStrings = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestPath() {
        return requestPath;
    }
    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getQueryStringValue(String parameter) {
        return queryStrings.get(parameter);
    }
    public boolean hasQueryStringParameter(String parameter) {
        return queryStrings.containsKey(parameter);
    }
    public void addQueryString(String parameter, String value) {
        queryStrings.put(parameter, value);
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}

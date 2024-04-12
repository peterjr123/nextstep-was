package http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    Map<String, String> headerMap = new HashMap<>();

    public void addHeader(String name, String description) {
        headerMap.put(name, description);
    }

    public String getHeader(String name) {
        return headerMap.get(name);
    }
}

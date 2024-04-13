package http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    Map<String, String> headerMap = new HashMap<>();

    public void addHeader(String name, String description) {
        headerMap.put(name, description);
    }
    public boolean hasHeader(String name) {
        return headerMap.containsKey(name);
    }

    public String getHeader(String name) {
        return headerMap.get(name);
    }
}

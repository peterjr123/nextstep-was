package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionImpl implements HttpSession {
    private UUID uuid = UUID.randomUUID();
    private Map<String, Object> attributeMap = new HashMap<>();

    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributeMap.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributeMap.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attributeMap.remove(name);
    }

    @Override
    public void invalidate() {

    }
}

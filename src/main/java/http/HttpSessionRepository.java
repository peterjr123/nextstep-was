package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionRepository {
    private static Map<String, HttpSession> httpSessionMap = new HashMap<>();

    public static HttpSession createSession() {
        HttpSession session = new HttpSessionImpl();
        httpSessionMap.put(session.getId(), session);
        return session;
    }

    public static HttpSession getSession(String id) {
        return httpSessionMap.get(id);
    }

    public static void removeSession(String id) {
        httpSessionMap.remove(id);
    }
}

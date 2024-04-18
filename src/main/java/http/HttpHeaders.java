package http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

class HttpHeaders {
	private static final String CONTENT_LENGTH = "Content-Length";

	private static final Logger log = LoggerFactory.getLogger(HttpHeaders.class);

	private Map<String, String> headers = new HashMap<>();
	
	void add(String header) {
		log.debug("header : {}", header);
	    String[] splitedHeaders = header.split(":");
	    headers.put(splitedHeaders[0], splitedHeaders[1].trim());   
	}
	
    String getHeader(String name) {
        return headers.get(name);
    }
    
    int getIntHeader(String name) {
    	String header = getHeader(name);
    	return header == null ? 0 : Integer.parseInt(header);
    }

	public String getCookie(String name) {
		Map<String, String> cookies = HttpRequestUtils.parseCookies(headers.get("Cookie"));
		return cookies.get(name);
	}
    
    int getContentLength() {
    	return getIntHeader(CONTENT_LENGTH);
    }
}

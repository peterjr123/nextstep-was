package http;

import http.support.UrlDecoder;
import http.support.UrlDecoderImpl;
import util.HttpRequestUtils;

import java.util.Map;

public class HttpRequestParserImpl implements HttpRequestParser {
    String messageString;
    public HttpRequestParserImpl(String messageString) {
        this.messageString = messageString;
    }

    @Override
    public HttpRequestMessage parse() {
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage();
        String firstLine = messageString.substring(0, messageString.indexOf("\n"));
        String headerString = messageString.substring(messageString.indexOf("\n") + 1, messageString.length());

        parseFirstLine(httpRequestMessage, firstLine);
        HttpHeaderParser httpHeaderParser = new HttpHeaderParserImpl(headerString);
        httpRequestMessage.setHttpHeader(httpHeaderParser.parse());

        return httpRequestMessage;
    }

    private void parseFirstLine(HttpRequestMessage message, String firstLine) {
        String[] parsed = firstLine.split(" ");
        String method = parsed[0];
        String rawUrl = parsed[1];

        // http request method
        message.setRequestMethod(HttpRequestMethod.valueOf(method));

        // url
        UrlDecoder urlDecoder = new UrlDecoderImpl();
        String url = urlDecoder.decode(rawUrl);
        message.setUrl(url);

        // request path
        if(url.contains("?")) {
            int queryStringDelimiterIdx = url.indexOf("?");
            message.setRequestPath(url.substring(0, queryStringDelimiterIdx));

            // query string
            String queryString = url.substring(queryStringDelimiterIdx + 1);
            Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
            for(String parameter : parameters.keySet()) {
                message.addQueryString(parameter, parameters.get(parameter));
            }
        }
        else {
            message.setRequestPath(url);
        }
    }
}

package http;

import java.util.StringTokenizer;

public class HttpHeaderParserImpl implements HttpHeaderParser {
    String headerString;

    public HttpHeaderParserImpl(String headerString) {
        this.headerString = headerString;
    }
    @Override
    public HttpHeader parse() {
        HttpHeader httpHeader = new HttpHeader();
        StringTokenizer tokenizer = new StringTokenizer(headerString, "\n");
        while(tokenizer.hasMoreTokens()) {
            addHeader(httpHeader, tokenizer.nextToken());
        }
        return httpHeader;
    }

    private void addHeader(HttpHeader httpHeader, String singleHeaderString) {
        int delimiterIdx = singleHeaderString.indexOf(":");
        String headerName = singleHeaderString.substring(0, delimiterIdx);
        String headerDescriptions= singleHeaderString.substring(delimiterIdx+1, singleHeaderString.length());
        httpHeader.addHeader(headerName, headerDescriptions.trim());
    }
}

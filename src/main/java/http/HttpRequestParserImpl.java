package http;

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
        message.setUrl(parsed[1]);
    }
}

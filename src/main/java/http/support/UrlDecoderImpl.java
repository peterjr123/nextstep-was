package http.support;

public class UrlDecoderImpl implements UrlDecoder {
    @Override
    public String decode(String url) {
        return url.replace("%40", "@");
    }
}

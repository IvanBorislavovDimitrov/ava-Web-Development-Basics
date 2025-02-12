package javache.http;

import java.util.Map;

public interface HttpResponse {

    Map<String, HttpCookie> getCookies();

    void addCookie(HttpCookie httpCookie);

    Map<String, String> getHeaders();

    int getStatusCode();

    byte[] getContent();

    byte[] getBytes();

    void setStatusCode(int statusCode);

    void setContent(byte[] content);

    void addHeader(String header, String value);
}

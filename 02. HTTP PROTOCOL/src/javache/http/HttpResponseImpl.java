package javache.http;

import java.util.*;

public class HttpResponseImpl implements HttpResponse {

    private int statusCode;
    private byte[] content;
    private Map<String, String> headers;

    public HttpResponseImpl() {
        this.setContent(new byte[0]);
        this.headers = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public byte[] getBytes() {
        byte[] headersBytes = this.getHeaderBytes();
        byte[] bodyBytes = this.getContent();

        byte[] fullResponse = new byte[headersBytes.length + bodyBytes.length];

        int index = 0;
        for (byte headersByte : headersBytes) {
            fullResponse[index++] = headersByte;
        }

        for (byte bodyByte : bodyBytes) {
            fullResponse[index++] = bodyByte;
        }

        return fullResponse;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    private byte[] getHeaderBytes() {
        String responseLine = String.format("%s %d\r\n", "HTTP/1.1", this.statusCode);
        StringBuilder sb = new StringBuilder(responseLine);
        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(System.lineSeparator());
        }

        sb.append(String.format("Date: %s", new Date())).append(System.lineSeparator());
        sb.append("Server: Javache/1.0.0").append(System.lineSeparator());
        sb.append(System.lineSeparator());

        return sb.toString().getBytes();
    }
}
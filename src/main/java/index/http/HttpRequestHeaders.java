package index.http;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpRequestHeaders {
    private String contentType;
    private String boundary;
    private int contentLength;
    private String name;

    HttpRequestHeaders(char[] buffer, int size) {
        for (var header : new String(Arrays.copyOf(buffer, size)).split("\\r\\n")) {
            if (header.startsWith("Content-Type")) {
                contentType = header.substring("Content-Type: ".length());

                if (contentType.startsWith("multipart/form-data"))
                    boundary = contentType.substring("multipart/form-data; boundary=".length());

            } else if (header.startsWith("Content-Length")) {
                contentLength = Integer.parseInt(header.substring("Content-Length: ".length()));

            } else if (header.startsWith("Content-Disposition")) {
                Pattern pattern = Pattern.compile("Content-Disposition: form-data; name=\"(.+)\";");
                Matcher matcher = pattern.matcher(header);
                if (matcher.find())
                    name = matcher.group(1);
            }
        }
    }

    public String getContentType() {
        return contentType;
    }

    public String getBoundary() {
        return boundary;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getName() {
        return name;
    }
}
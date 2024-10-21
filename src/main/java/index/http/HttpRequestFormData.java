package index.http;

import index.util.ByteArrays;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpRequestFormData {
    private String name;
    private String contentType;
    private final byte[] body;

    HttpRequestFormData(byte[] buffer) {
        var CRLF = "\r\n";
        int idx = ByteArrays.indexOf(buffer, CRLF.repeat(2), 0);
        var headers = new String(Arrays.copyOf(buffer, idx)).split(CRLF);

        for (var header : headers) {
            if (header.startsWith("Content-Disposition")) {
                Pattern pattern = Pattern.compile("Content-Disposition: form-data; name=\"(.+?)\".*");
                Matcher matcher = pattern.matcher(header);
                if (matcher.find())
                    name = matcher.group(1);

            } else if (header.startsWith("Content-Type")) {
                contentType = header.substring("Content-Type: ".length());
            }
        }

        body = Arrays.copyOfRange(buffer, idx + CRLF.repeat(2).length(), buffer.length);
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}

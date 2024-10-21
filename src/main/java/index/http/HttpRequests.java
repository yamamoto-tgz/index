package index.http;

import index.util.ByteArrays;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class HttpRequests {
    public static HttpRequest read(InputStream inputStream) throws IOException {
        var headers = readHeaders(inputStream);

        if (headers.getContentType().startsWith("multipart/form-data")) {
            var contentLength = headers.getContentLength();
            var boundary = headers.getBoundary();
            var formDataList = readFormDataList(inputStream, contentLength, boundary);
            return new HttpRequest(headers, formDataList);
        }

        return new HttpRequest(headers);
    }

    private static HttpRequestHeaders readHeaders(InputStream inputStream) throws IOException {
        int size;
        char[] buffer = new char[4096];
        char CR = "\r".toCharArray()[0];
        char LF = "\n".toCharArray()[0];

        for (int i = 0; ; i++) {
            buffer[i] = (char) inputStream.read();
            if (i > 3) {
                if (buffer[i - 3] == CR && buffer[i - 2] == LF && buffer[i - 1] == CR && buffer[i] == LF) {
                    size = i - 3;
                    break;
                }
            }
        }
        return new HttpRequestHeaders(buffer, size);
    }

    private static List<HttpRequestFormData> readFormDataList(InputStream inputStream, int contentLength, String boundary) throws IOException {
        byte[] buffer = inputStream.readNBytes(contentLength);
        var delimiter = "--" + boundary + "\r\n";
        var endMarker = "--" + boundary + "--";
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0, idx; i < buffer.length; idx++, i += idx) {
            idx = ByteArrays.indexOf(buffer, delimiter, i);
            if (idx < 0) {
                idx = ByteArrays.indexOf(buffer, endMarker, i);
                indexes.add(idx);
                break;
            }
            indexes.add(idx);
        }

        List<HttpRequestFormData> formDataList = new ArrayList<>();

        for (int i = 0; i < indexes.size() - 1; i++) {
            var bytes = Arrays.copyOfRange(buffer, indexes.get(i) + delimiter.length(), indexes.get(i + 1));
            var formData = new HttpRequestFormData(bytes);
            formDataList.add(formData);
        }

        return formDataList;
    }
}

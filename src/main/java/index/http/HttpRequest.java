package index.http;

import java.util.List;

public class HttpRequest {
    private final HttpRequestHeaders headers;
    private List<HttpRequestFormData> formDataList;

    HttpRequest(HttpRequestHeaders headers) {
        this.headers = headers;
    }

    HttpRequest(HttpRequestHeaders headers, List<HttpRequestFormData> formDataList) {
        this.headers = headers;
        this.formDataList = formDataList;
    }

    public HttpRequestHeaders getHeaders() {
        return headers;
    }

    public List<HttpRequestFormData> getFormDataList() {
        return formDataList;
    }
}

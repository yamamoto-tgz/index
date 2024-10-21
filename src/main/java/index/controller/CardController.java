package index.controller;

import index.http.HttpRequest;
import index.http.HttpRequests;
import index.service.FormDataService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class CardController {
    private final FormDataService formDataService;

    public CardController(FormDataService formDataService) {
        this.formDataService = formDataService;
    }

    public void process(HttpRequest request, OutputStream outputStream) throws IOException {

        if (request.getHeaders().getMethod().equals("POST"))
            formDataService.add(request.getFormDataList());

        var response = """
                HTTP/1.1 200
                Server: index
                Content-Type: text/html
                Connection: closed
                Content-Length: 12
                
                <p>HELLO</p>""";

        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
    }
}

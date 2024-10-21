package index;

import index.http.HttpRequests;
import index.service.FormDataService;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

final class Server {
    private final Config config;
    private final FormDataService formDataService;

    Server(Config config, FormDataService imageService) {
        this.config = config;
        this.formDataService = imageService;
    }

    void start() {
        try {
            String port = config.getProperty("index.server.port");
            System.out.printf("PORT: %s%n", port);

            try (var serverSocket = new ServerSocket(Integer.parseInt(port))) {
                while (true) {
                    try (var clientSocket = serverSocket.accept()) {
                        try (var inputStream = clientSocket.getInputStream();
                             var outputStream = clientSocket.getOutputStream()) {

                            var request = HttpRequests.read(inputStream);
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
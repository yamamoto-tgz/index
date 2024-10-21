package index;

import index.controller.CardController;
import index.http.HttpRequests;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

final class Server {
    private final Config config;
    private final CardController cardController;

    Server(Config config, CardController cardController) {
        this.config = config;
        this.cardController = cardController;
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

                            if (request.getHeaders().getUri().equals("/api/cards"))
                                cardController.process(request, outputStream);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
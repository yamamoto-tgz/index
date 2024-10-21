package index;

import index.controller.CardController;
import index.service.FormDataService;

final class Main {
    public static void main(String[] args) {
        var config = new Config();
        var formDataService = new FormDataService(config);
        var cardController = new CardController(formDataService);
        var server = new Server(config, cardController);
        server.start();
    }
}

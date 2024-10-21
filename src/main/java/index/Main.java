package index;

import index.controller.CardController;
import index.repository.CardRepository;
import index.service.FormDataService;
import index.table.CardTable;

final class Main {
    public static void main(String[] args) {
        try {
            var config = new Config();

            var database = new Database(config);
            var cardTable = new CardTable(database);
            cardTable.create();

            var cardRepository = new CardRepository(database);
            var formDataService = new FormDataService(config, cardRepository);
            var cardController = new CardController(formDataService);
            var server = new Server(config, cardController);
            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

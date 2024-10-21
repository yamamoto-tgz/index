package index;

import index.controller.CardController;
import index.repository.CardRepository;
import index.service.FormDataService;
import index.table.CardTable;
import index.table.ImageTable;
import index.table.Table;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

final class Main {
    public static void main(String[] args) {
        try {
            var config = new Config();

            var database = new Database(config);

            Stream.of(new CardTable(database), new ImageTable(database))
                    .forEach(Table::create);

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

package index.service;

import index.Config;
import index.http.HttpRequestFormData;
import index.model.Card;
import index.repository.CardRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public final class FormDataService {
    private final Config config;
    private final CardRepository cardRepository;

    public FormDataService(Config config, CardRepository cardRepository) {
        this.config = config;
        this.cardRepository = cardRepository;
    }

    public void add(List<HttpRequestFormData> formDataList) throws IOException, SQLException {
        var card = new Card();

        for (var formData : formDataList) {
            if (formData.getContentType() != null && formData.getContentType().equals("image/jpeg")) {
                var filename = UUID.randomUUID() + ".jpg";
                var path = Paths.get(config.getProperty("index.image.dir"), filename);
                Files.write(path, formData.getBody(), StandardOpenOption.CREATE);

            } else if (formData.getName().equals("frontText")) {
                var frontText = new String(formData.getBody());
                card.setFrontText(frontText);

            } else if (formData.getName().equals("backText")) {
                var backText = new String(formData.getBody());
                card.setBackText(backText);
            }
        }

        cardRepository.add(card);
    }
}

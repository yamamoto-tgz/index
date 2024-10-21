package index.service;

import index.Config;
import index.http.HttpRequestFormData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

public final class FormDataService {
    private final Config config;

    public FormDataService(Config config) {
        this.config = config;
    }

    public void add(List<HttpRequestFormData> formDataList) throws IOException {
        for (var formData : formDataList) {
            if (formData.getContentType() != null && formData.getContentType().equals("image/jpeg")) {
                var filename = UUID.randomUUID() + ".jpg";
                var path = Paths.get(config.getProperty("index.image.dir"), filename);
                Files.write(path, formData.getBody(), StandardOpenOption.CREATE);
            }
        }
    }
}

package index;

import index.service.FormDataService;

final class Main {
    public static void main(String[] args) {
        var config = new Config();
        var formDataService = new FormDataService(config);
        var server = new Server(config, formDataService);
        server.start();
    }
}

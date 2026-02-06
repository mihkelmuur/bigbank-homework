package ee.mihkelmuur.bigbank.game;

import java.net.http.HttpClient;

class Main {

    static void main(String[] args) {
        // TODO: Consider switching to IoC/DI framework
        Runner runner = new Runner(new ApiClient(HttpClient.newHttpClient()), new AdConverter(new Decoder()), new AdPicker());
        runner.run();
    }

}

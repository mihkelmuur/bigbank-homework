package ee.mihkelmuur.bigbank.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

class ApiClient {

    private final HttpClient httpClient;

    ApiClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    String startGame() {
        HttpResponse<String> response = sendRequest("https://dragonsofmugloar.com/api/v2/game/start", true);
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        return jsonObject.get("gameId").getAsString();
    }

    List<Ad> getAds(String gameId) {
        HttpResponse<String> response = sendRequest("https://dragonsofmugloar.com/api/v2/" + gameId + "/messages", false);
        return new Gson().fromJson(response.body(),
                new TypeToken<List<Ad>>() {
                }.getType());
    }

    Result solve(String gameId, String adId) {
        HttpResponse<String> response = sendRequest("https://dragonsofmugloar.com/api/v2/" + gameId + "/solve/" + adId, true);
        return new Gson().fromJson(response.body(), Result.class);
    }

    void buyItem(String gameId, String itemId) {
        sendRequest("https://dragonsofmugloar.com/api/v2/" + gameId + "/shop/buy/" + itemId, true);
    }

    private HttpResponse<String> sendRequest(String uri, boolean post) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(uri));

        if (post) {
            requestBuilder.POST(HttpRequest.BodyPublishers.noBody()).build();
        } else {
            requestBuilder.GET();
        }

        try {
            return httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    record Ad(String adId, int reward, Integer encrypted, String probability, String message) {
    }

    record Result(boolean success, int lives, int gold, int score, int turn) {
    }

}

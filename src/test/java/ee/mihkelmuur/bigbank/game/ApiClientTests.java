package ee.mihkelmuur.bigbank.game;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiClientTests {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @InjectMocks
    private ApiClient apiClient;

    @Test
    void shouldStartGame() throws IOException, InterruptedException {
        // given
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://dragonsofmugloar.com/api/v2/game/start"))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        when(httpResponse.body()).thenReturn("{\"gameId\":\"aGameId\",\"anotherProperty\":\"value\"}");
        when(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);

        // when
        String gameId = apiClient.startGame();

        // then
        assertThat(gameId).isEqualTo("aGameId");
    }

    @Test
    void shouldGetAds() throws IOException, InterruptedException {
        // given
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://dragonsofmugloar.com/api/v2/aGameId/messages"))
                .GET().build();
        when(httpResponse.body()).thenReturn("[{\"adId\":\"anAdId\",\"reward\":10,\"encrypted\":null,\"probability\":\"some probability\",\"message\":\"some message\"}]");
        when(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);

        // when
        List<ApiClient.Ad> ads = apiClient.getAds("aGameId");

        // then
        assertThat(ads).hasSize(1);
        assertThat(ads.getFirst().adId()).isEqualTo("anAdId");
        assertThat(ads.getFirst().reward()).isEqualTo(10);
        assertThat(ads.getFirst().encrypted()).isNull();
        assertThat(ads.getFirst().probability()).isEqualTo("some probability");
        assertThat(ads.getFirst().message()).isEqualTo("some message");
    }

    @Test
    void shouldSolve() throws IOException, InterruptedException {
        // given
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://dragonsofmugloar.com/api/v2/aGameId/solve/anItemId"))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        when(httpResponse.body()).thenReturn("{\"success\":true,\"lives\":1,\"gold\":2,\"score\":3,\"turn\":4}");
        when(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);

        // when
        ApiClient.Result result = apiClient.solve("aGameId", "anItemId");

        // then
        assertThat(result.success()).isTrue();
        assertThat(result.lives()).isEqualTo(1);
        assertThat(result.gold()).isEqualTo(2);
        assertThat(result.score()).isEqualTo(3);
        assertThat(result.turn()).isEqualTo(4);
    }

    @Test
    void shouldBuyItem() throws IOException, InterruptedException {
        // given
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://dragonsofmugloar.com/api/v2/aGameId/shop/buy/anItemId"))
                .POST(HttpRequest.BodyPublishers.noBody()).build();

        // when
        apiClient.buyItem("aGameId", "anItemId");

        // then
        verify(httpClient).send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void shouldWrapCheckedExceptionIntoRuntimeException() throws IOException, InterruptedException {
        // given
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://dragonsofmugloar.com/api/v2/aGameId/shop/buy/anItemId"))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        when(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())).thenThrow(new IOException());

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> apiClient.buyItem("aGameId", "anItemId");

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(RuntimeException.class);
    }

}

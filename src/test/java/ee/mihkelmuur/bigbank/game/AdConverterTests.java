package ee.mihkelmuur.bigbank.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdConverterTests {

    @Mock
    private Decoder decoder;

    @InjectMocks
    private AdConverter adConverter;

    @Test
    void shouldConvertAd() {
        // given
        ApiClient.Ad anAd = new ApiClient.Ad("id", 10, null, "Sure thing", "A message");

        // when
        Ad convertedAd = adConverter.convert(anAd);

        // then
        assertThat(convertedAd.adId()).isEqualTo("id");
        assertThat(convertedAd.reward()).isEqualTo(10);
        assertThat(convertedAd.probability()).isSameAs(Probability.SURE_THING);
        assertThat(convertedAd.message()).isEqualTo("A message");
    }

    @Test
    void shouldConvertBase64EncodedAd() {
        // given
        ApiClient.Ad anAd = new ApiClient.Ad("di", 10, 1, "Sure thing encoded", "An encoded message");
        when(decoder.decodeBase64("di")).thenReturn("id");
        when(decoder.decodeBase64("Sure thing encoded")).thenReturn("Sure thing");
        when(decoder.decodeBase64("An encoded message")).thenReturn("A message");

        // when
        Ad convertedAd = adConverter.convert(anAd);

        // then
        assertThat(convertedAd.adId()).isEqualTo("id");
        assertThat(convertedAd.reward()).isEqualTo(10);
        assertThat(convertedAd.probability()).isSameAs(Probability.SURE_THING);
        assertThat(convertedAd.message()).isEqualTo("A message");
    }

    @Test
    void shouldConvertRot13EncodedAd() {
        // given
        ApiClient.Ad anAd = new ApiClient.Ad("di", 10, 2, "Sure thing encoded", "An encoded message");
        when(decoder.decodeRot13("di")).thenReturn("id");
        when(decoder.decodeRot13("Sure thing encoded")).thenReturn("Sure thing");
        when(decoder.decodeRot13("An encoded message")).thenReturn("A message");

        // when
        Ad convertedAd = adConverter.convert(anAd);

        // then
        assertThat(convertedAd.adId()).isEqualTo("id");
        assertThat(convertedAd.reward()).isEqualTo(10);
        assertThat(convertedAd.probability()).isSameAs(Probability.SURE_THING);
        assertThat(convertedAd.message()).isEqualTo("A message");
    }

    @Test
    void shouldConvertAds() {
        // given
        ApiClient.Ad anAd = new ApiClient.Ad("id", 10, null, "Sure thing", "A message");
        ApiClient.Ad anotherAd = new ApiClient.Ad("id2", 20, null, "Piece of cake", "Another message");
        List<ApiClient.Ad> ads = List.of(anAd,anotherAd);

        // when
        List<Ad> convertedAds = adConverter.convert(ads);

        // then
        assertThat(convertedAds).hasSize(2);
        assertThat(convertedAds.get(0).adId()).isEqualTo("id");
        assertThat(convertedAds.get(0).reward()).isEqualTo(10);
        assertThat(convertedAds.get(0).probability()).isSameAs(Probability.SURE_THING);
        assertThat(convertedAds.get(0).message()).isEqualTo("A message");
        assertThat(convertedAds.get(1).adId()).isEqualTo("id2");
        assertThat(convertedAds.get(1).reward()).isEqualTo(20);
        assertThat(convertedAds.get(1).probability()).isSameAs(Probability.PIECE_OF_CAKE);
        assertThat(convertedAds.get(1).message()).isEqualTo("Another message");
    }

}

package ee.mihkelmuur.bigbank.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AdPickerTests {

    private AdPicker adPicker = new AdPicker();

    @Test
    void shouldPreferSaferAd() {
        // given
        Ad anAd = new Ad("abc", 10, Probability.PIECE_OF_CAKE, "A message");
        Ad aSaferAd = new Ad("def", 10, Probability.SURE_THING, "Another message");
        List<Ad> ads = List.of(anAd, aSaferAd);

        // when
        Ad mostPromisingAd = adPicker.pickMostPromisingAd(ads);

        // then
        assertThat(mostPromisingAd).isSameAs(aSaferAd);
    }

    @Test
    void shouldPreferAdWithHigherRewardInCaseOfSameSafety() {
        // given
        Ad anAd = new Ad("abc", 10, Probability.SURE_THING, "A message");
        Ad adWithHigherReward = new Ad("def", 15, Probability.SURE_THING, "Another message");
        List<Ad> ads = List.of(anAd, adWithHigherReward);

        // when
        Ad mostPromisingAd = adPicker.pickMostPromisingAd(ads);

        // then
        assertThat(mostPromisingAd).isSameAs(adWithHigherReward);
    }

    @Test
    void shouldIgnoreAdWhichIsAboutStealing() {
        // given
        Ad anAd = new Ad("abc", 10, Probability.SURE_THING, "A message");
        Ad adWithHigherReward = new Ad("def", 15, Probability.SURE_THING, "Steal something");
        List<Ad> ads = List.of(anAd, adWithHigherReward);

        // when
        Ad mostPromisingAd = adPicker.pickMostPromisingAd(ads);

        // then
        assertThat(mostPromisingAd).isSameAs(anAd);
    }

}

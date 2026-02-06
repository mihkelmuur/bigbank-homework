package ee.mihkelmuur.bigbank.game;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProbabilityTests {

    @Test
    void shouldDeriveProbabilityFromStringValue() {
        // given
        String stringValue = "Sure thing";

        // when
        Probability probability = Probability.fromString(stringValue);

        // then
        assertThat(probability).isSameAs(Probability.SURE_THING);
    }

    @Test
    void shouldBeSaferWhenSafer() {
        // given
        Probability probability = Probability.SURE_THING;
        Probability anotherProbability = Probability.PIECE_OF_CAKE;

        // when
        boolean result = probability.isSaferThan(anotherProbability);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldNotBeSaferWhenTheSame() {
        // given
        Probability probability = Probability.SURE_THING;
        Probability anotherProbability = Probability.SURE_THING;

        // when
        boolean result = probability.isSaferThan(anotherProbability);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldNotBeSaferWhenIsLessSafe() {
        // given
        Probability probability = Probability.PIECE_OF_CAKE;
        Probability anotherProbability = Probability.SURE_THING;

        // when
        boolean result = probability.isSaferThan(anotherProbability);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldConvertToString() {
        // given
        Probability probability = Probability.SURE_THING;

        // when
        String string = probability.toString();

        // then
        assertThat(string).isEqualTo("Sure thing");
    }

}

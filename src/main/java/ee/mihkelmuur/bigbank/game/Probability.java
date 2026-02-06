package ee.mihkelmuur.bigbank.game;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

enum Probability {

    SURE_THING("Sure thing"),
    PIECE_OF_CAKE("Piece of cake"),
    WALK_IN_THE_PARK("Walk in the park"),
    QUITE_LIKELY("Quite likely"),
    GAMBLE("Gamble"),
    RISKY("Risky"),
    HMMM("Hmmm...."),
    PLAYING_WITH_FIRE("Playing with fire"),
    RATHER_DETRIMENTAL("Rather detrimental"),
    SUICIDE_MISSION("Suicide mission"),
    IMPOSSIBLE("Impossible");

    private static final Map<String, Probability> REVERSE_LOOKUP = Arrays.stream(values()).collect(Collectors.toMap(Probability::toString, Function.identity()));

    private final String value;

    Probability(String value) {
        this.value = value;
    }

    static Probability fromString(String probability) {
        return REVERSE_LOOKUP.get(probability);
    }

    boolean isSaferThan(Probability anotherProbability) {
        return this.ordinal() < anotherProbability.ordinal();
    }

    @Override
    public String toString() {
        return value;
    }

}

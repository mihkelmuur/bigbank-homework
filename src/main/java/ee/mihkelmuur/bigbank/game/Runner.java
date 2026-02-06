package ee.mihkelmuur.bigbank.game;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    private final ApiClient apiClient;
    private final AdConverter adConverter;
    private final AdPicker adPicker;

    Runner(ApiClient apiClient, AdConverter adConverter, AdPicker adPicker) {
        this.apiClient = apiClient;
        this.adConverter = adConverter;
        this.adPicker = adPicker;
    }

    void run() {
        String gameId = apiClient.startGame();
        logger.info("Game started with ID: {}", gameId);

        while (true) {
            List<ApiClient.Ad> ads = apiClient.getAds(gameId);
            Ad mostPromisingAd = adPicker.pickMostPromisingAd(adConverter.convert(ads));
            ApiClient.Result result = apiClient.solve(gameId, mostPromisingAd.adId());

            int turn = result.turn();
            int gold = result.gold();

            logger.info("Turn {}: attempted to solve ad with difficulty \"{}\": {}; lives {}, gold {}, score {}",
                    turn, mostPromisingAd.probability(), result.success() ? "success" : "failure", result.lives(), result.gold(), result.score());

            while (gold >= 300) {
                // TODO: Consider buying other types of items as well
                apiClient.buyItem(gameId, "cs");
                logger.info("Turn {}: bought claw sharpening", ++turn);
                gold -= 100;
            }

            if (result.lives() == 1 && result.gold() >= 50) {
                apiClient.buyItem(gameId, "hpot");
                logger.info("Turn {}: bought healing potion", ++turn);
            } else if (result.lives() == 0) {
                logger.info("Game over, no lives left");
                break;
            }
        }
    }

}

package ee.mihkelmuur.bigbank.game;

import java.util.List;

class AdPicker {

    Ad pickMostPromisingAd(List<Ad> ads) {
        Ad mostPromisingAd = null;

        for (Ad ad : ads) {
            if (ad.message().startsWith("Steal ")) {
                continue;
            }

            if (mostPromisingAd == null || ad.probability().isSaferThan(mostPromisingAd.probability()) ||
                    ad.probability() == mostPromisingAd.probability() && ad.reward() > mostPromisingAd.reward()) {
                mostPromisingAd = ad;
            }
        }

        return mostPromisingAd;
    }

}

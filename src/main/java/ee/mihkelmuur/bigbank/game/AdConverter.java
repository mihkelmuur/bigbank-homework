package ee.mihkelmuur.bigbank.game;

import java.util.List;

class AdConverter {

    private final Decoder decoder;

    AdConverter(Decoder decoder) {
        this.decoder = decoder;
    }

    List<Ad> convert(List<ApiClient.Ad> ads) {
        return ads.stream().map(this::convert).toList();
    }

    Ad convert(ApiClient.Ad ad) {
        String adId;
        String probability;
        String message;

        if (ad.encrypted() == null) {
            adId = ad.adId();
            probability = ad.probability();
            message = ad.message();
        } else if (ad.encrypted() == 1) {
            adId = decoder.decodeBase64(ad.adId());
            probability = decoder.decodeBase64(ad.probability());
            message = decoder.decodeBase64(ad.message());
        } else {
            adId = decoder.decodeRot13(ad.adId());
            probability = decoder.decodeRot13(ad.probability());
            message = decoder.decodeRot13(ad.message());
        }

        return new Ad(adId, ad.reward(), Probability.fromString(probability), message);
    }

}

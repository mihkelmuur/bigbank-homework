package ee.mihkelmuur.bigbank.game;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecoderTests {

    private Decoder decoder = new Decoder();

    @Test
    void shouldDecodeBase64String() {
        // given
        String input = "VGVzdCBzdHJpbmc=";

        // when
        String result = decoder.decodeBase64(input);

        // then
        assertThat(result).isEqualTo("Test string");
    }

    @Test
    void shouldDecodeRot13String() {
        // given
        String input = "Nabgure grfg fgevat";

        // when
        String result = decoder.decodeRot13(input);

        // then
        assertThat(result).isEqualTo("Another test string");
    }

}

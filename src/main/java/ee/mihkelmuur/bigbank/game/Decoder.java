package ee.mihkelmuur.bigbank.game;

import java.util.Base64;

class Decoder {

    String decodeBase64(String input) {
        return new String(Base64.getDecoder().decode(input));
    }

    /*
     * Decodes a string using ROT13 (Caesar cipher with shift 13). Only letters
     * are shifted; all other characters remain unchanged. Preserves case.
     */
    String decodeRot13(String input) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (c - base + 13) % 26 + base;
                result.append((char) shifted);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

}

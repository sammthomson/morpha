package com.samthomson.morpha;

import java.io.IOException;
import java.io.StringReader;


public class Morpha {
    /** Tries to find the lemma for `word` without using part-of-speech info. */
    public static String getLemma(String word) {
        return getLemma(word, null);
    }

    /** Tries to find the lemma for `word` with part-of-speech `postag`. */
    public static String getLemma(String word, String postag) {
        if (word == null || word.isEmpty()) return "";
        final String token = word.toLowerCase();
        final String cleanedToken = token.replaceAll("_", "-");
        final String cleanedInput = postag != null ? cleanedToken + "_" + postag.toUpperCase() : cleanedToken;
        try {
            final String result = new MorphaFlex(new StringReader(cleanedInput), postag != null).next();
            return (result == null) ? "" : result;
        } catch (IOException e) {
            return token;
        } catch (Error e) {
            if (e.getMessage().equals("Error: could not match input")) {
                return token;
            } else {
                throw e;
            }
        }
    }
}

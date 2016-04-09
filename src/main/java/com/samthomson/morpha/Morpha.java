package com.samthomson.morpha;

import java.io.IOException;
import java.io.StringReader;


public class Morpha {
    public static String getLemma(String word,
                                  String postag) {
        if (word == null || postag == null || word.isEmpty()) return "";
        final String token = word.toLowerCase();
        final String cleanedInput = token.replaceAll("_", "-") + "_" + postag.toUpperCase();
        return stemWithDefault(cleanedInput, true, token);
    }

    public static String getLemma(String word) {
        if (word == null || word.isEmpty()) return "";
        final String token = word.toLowerCase();
        final String cleanedInput = token.replaceAll("_", "-");
        return stemWithDefault(cleanedInput, false, token);
    }

    private static String stemWithDefault(String cleanedInput,
                                          boolean hasPos,
                                          String fallback) {
        try {
            return new MorphaFlex(new StringReader(cleanedInput), hasPos).next();
        } catch (IOException e) {
            return fallback;
        } catch (Error e) {
            if (e.getMessage().equals("Error: could not match input")) {
                return fallback;
            } else {
                throw e;
            }
        }
    }
}

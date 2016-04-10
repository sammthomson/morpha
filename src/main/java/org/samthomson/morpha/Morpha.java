/*
 * Copyright (C) 2012 University of Washington
 * Copyright (C) 2016 Sam Thomson <sthomson@cs.cmu.edu>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.samthomson.morpha;

import java.io.IOException;
import java.io.StringReader;


public final class Morpha {
    /** Lemmatizes `token` without using part-of-speech info. */
    public static Lemma stem(String token) {
        return stem(token, null);
    }

    /** Lemmatizes `token` with part-of-speech `postag`. */
    public static Lemma stem(String token, String postag) {
        if (token == null || token.isEmpty()) return Lemma.apply("");
        final String lower = token.toLowerCase();
        final String cleanedToken = lower.replaceAll("_", "-");
        final String cleanedInput = postag != null ? cleanedToken + "_" + postag.toUpperCase() : cleanedToken;
        try {
            final MorphaFlex morpha = new MorphaFlex(new StringReader(cleanedInput), postag != null);
            return parse(morpha.next());
        } catch (IOException e) {
            return Lemma.apply(lower);
        } catch (Error e) {
            if (e.getMessage().equals("Error: could not match input")) {
                return Lemma.apply(lower);
            } else {
                throw e;
            }
        }
    }

    /** Parses the response from `MorphaFlex.next`. Splits on the final '+' */
    private static Lemma parse(String response) {
        final int i = response.lastIndexOf('+');
        if (i < 0) {
            return Lemma.apply(response);
        } else {
            return Lemma.apply(
                    response.substring(0, i),
                    response.substring(i + 1, response.length()));
        }
    }

    /** A pair of `lemma` and `affix` */
    public static final class Lemma {
        final String lemma;
        final String affix;

        private Lemma(String lemma, String affix) {
            this.lemma = lemma;
            this.affix = affix;
        }

        /** Static constructor */
        public static Lemma apply(String lemma, String affix) { return new Lemma(lemma, affix); }

        /** Static constructor for Lemma with no affix */
        public static Lemma apply(String lemma) { return apply(lemma, ""); }

        @Override
        public String toString() {
            return "Lemma(" + ((affix.length() == 0) ? lemma : lemma + ", " + affix) + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) return false;
            final Lemma other = (Lemma) obj;
            return affix.equals(other.affix) && lemma.equals(other.lemma);
        }
    }
}

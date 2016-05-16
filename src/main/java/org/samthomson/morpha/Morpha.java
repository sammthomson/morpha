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


/**
 * This is a Java version of [the Morpha stemmer](http://www.informatics.sussex.ac.uk/research/groups/nlp/carroll/morph.html),
 * a fast and robust morphological analyser for English based on finite-state
 * techniques that returns the lemma and inflection type of a word, given the word
 * form and its part of speech. (The latter is optional but accuracy is degraded
 * if it is not present).
 *
 * Usage:
 *
 *   import org.samthomson.morpha.Morpha.Lemma;
 *   import static org.samthomson.morpha.Morpha.stem;
 *
 *   // when part-of-speech tags are available:
 *   Lemma sawVerb = stem("saw", "VBD");  // Lemma(see, ed)
 *   sawVerb.lemma;  // "see"
 *   sawVerb.affix;  // "ed"
 *   Lemma sawNoun = stem("saw", "NN");  // Lemma(saw)
 *   sawNoun.lemma;   // "saw"
 *   sawNoun.affix;   // ""
 *
 *   // when part-of-speech tags are not available a best effort is made:
 *   stem("saw");    // Lemma("see", "ed")
 *   stem("finding") // Lemma("find", "ing")
 */
public final class Morpha {
    /** Lemmatizes `token` without using part-of-speech info. */
    public static Lemma stem(String token) {
        return stem(token, null);
    }

    /** Lemmatizes `token` with part-of-speech `postag`. */
    public static Lemma stem(String token, String postag) {
        if (token == null || token.isEmpty()) return Lemma("");
        final String lower = token.toLowerCase();
        final String cleanedToken = lower.replaceAll("_", "-");
        final boolean hasPostag = postag != null;
        final String cleanedInput = hasPostag ? cleanedToken + "_" + postag.toUpperCase() : cleanedToken;
        try {
            final MorphaFlex morpha = new MorphaFlex(new StringReader(cleanedInput), hasPostag);
            return parse(morpha.next());
        } catch (IOException e) {
            return Lemma(lower);
        } catch (Error e) {
            if (e.getMessage().equals("Error: could not match input")) {
                return Lemma(lower);
            } else {
                throw e;
            }
        }
    }

    /** Parses the response from `MorphaFlex.next`. Splits on the final '+' */
    private static Lemma parse(String response) {
        final int i = response.lastIndexOf('+');
        if (i < 0) {
            return Lemma(response);
        } else {
            return Lemma(
                    response.substring(0, i),
                    response.substring(i + 1, response.length()));
        }
    }

    /** Static constructor */
    public static Lemma Lemma(String lemma, String affix) { return new Lemma(lemma, affix); }
    /** Static constructor for Lemma with no affix */
    public static Lemma Lemma(String lemma) { return Lemma(lemma, ""); }

    /** A pair of `lemma` and `affix` */
    public static final class Lemma {
        public final String lemma;
        public final String affix;

        private Lemma(String lemma, String affix) {
            this.lemma = lemma;
            this.affix = affix;
        }

        @Override
        public String toString() {
            return "Lemma(" + ((affix.length() == 0) ? lemma : lemma + ", " + affix) + ")";
        }

        @Override
        public int hashCode() {
            final int lemmaHash = lemma == null ? 0 : lemma.hashCode();
            final int affixHash = affix == null ? 0 : affix.hashCode();
            return 31 * lemmaHash + affixHash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) return false;
            final Lemma other = (Lemma) obj;
            return affix.equals(other.affix) && lemma.equals(other.lemma);
        }
    }
}

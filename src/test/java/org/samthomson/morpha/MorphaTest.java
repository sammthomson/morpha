package org.samthomson.morpha;

import org.junit.Test;

import static org.samthomson.morpha.Morpha.Lemma;
import static org.samthomson.morpha.Morpha.stem;
import static org.junit.Assert.assertEquals;


public class MorphaTest {
    @Test
    public void testWithoutPos() {
        assertEquals(Lemma("find", "ed"), stem("found"));
        assertEquals(Lemma("find", "ing"), stem("finding"));
        assertEquals(Lemma("fall", "ed"), stem("fell"));
    }

    @Test
    public void testLemmaIsLowercased() {
        assertEquals(Lemma("nuclear"), stem("Nuclear", "JJ"));
    }

    @Test
    public void testContractions() {
        assertEquals(Lemma("'s"),  stem("'s", "POS"));
        assertEquals(Lemma("have"),  stem("'ve", "VB"));
        assertEquals(Lemma("not"),  stem("n't", "RB"));
        assertEquals(Lemma("be", "s"), stem("'s", "VBZ"));
        assertEquals(Lemma("will"),  stem("'ll", "VB"));
        assertEquals(Lemma("be"),  stem("'re", "VBP"));
    }

    @Test
    public void testIrregularPastTense() {
        assertEquals(Lemma("fall", "ed"), stem("fell", "VBD"));
        assertEquals(Lemma("find", "ed"), stem("found", "VBD"));
        assertEquals(Lemma("find", "ed"), stem("found", "VBN"));
        assertEquals(Lemma("see",  "ed"), stem("saw", "VBD"));
        //assertEquals(Stem("person", "s"), getStem("people", "NNS"));  // Morpha doesn't get this right. Oh well.
    }

    @Test
    public void testPosIsNotIgnored() {
        assertEquals(Lemma("see", "ed"), stem("saw", "VBD"));
        assertEquals(Lemma("saw"), stem("saw", "NN"));
    }

    @Test
    public void testNoExceptionOnWhitespace() {
        assertEquals(Lemma("a b"), stem("a b",  "JJ"));
        assertEquals(Lemma("a\tb"), stem("a\tb", "JJ"));
        assertEquals(Lemma("a\nb"), stem("a\nb", "JJ"));
        assertEquals(Lemma("a\rb"), stem("a\rb", "JJ"));
    }

    @Test
    public void testAngleBracketsHandled() {
        // getStem used to throw "java.lang.Error: Error: could not match input" for ">"
        assertEquals(Lemma(">"), stem(">", "JJR"));
        assertEquals(Lemma("<"), stem("<", "JJR"));
    }

    @Test
    public void testNullIsRetained() {
        // regression test for https://github.com/knowitall/morpha/issues/1
        // getStem used to turn "null" into ""
        assertEquals(Lemma("null"), stem("null", "JJ"));
        assertEquals(Lemma("null", "s"), stem("nulls", "NN"));
    }
}

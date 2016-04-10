package org.samthomson.morpha;

import org.samthomson.morpha.Morpha.Lemma;
import org.junit.Test;

import static org.samthomson.morpha.Morpha.stem;
import static org.junit.Assert.assertEquals;


public class MorphaTest {
    @Test
    public void testWithoutPos() {
        assertEquals(Lemma.apply("find", "ed"), stem("found"));
        assertEquals(Lemma.apply("find", "ing"), stem("finding"));
        assertEquals(Lemma.apply("fall", "ed"), stem("fell"));
    }

    @Test
    public void testLemmaIsLowercased() {
        assertEquals(Lemma.apply("nuclear"), stem("Nuclear", "JJ"));
    }

    @Test
    public void testContractions() {
        assertEquals(Lemma.apply("'s"),  stem("'s", "POS"));
        assertEquals(Lemma.apply("have"),  stem("'ve", "VB"));
        assertEquals(Lemma.apply("not"),  stem("n't", "RB"));
        assertEquals(Lemma.apply("be", "s"), stem("'s", "VBZ"));
        assertEquals(Lemma.apply("will"),  stem("'ll", "VB"));
        assertEquals(Lemma.apply("be"),  stem("'re", "VBP"));
    }

    @Test
    public void testIrregularPastTense() {
        assertEquals(Lemma.apply("fall", "ed"), stem("fell", "VBD"));
        assertEquals(Lemma.apply("find", "ed"), stem("found", "VBD"));
        assertEquals(Lemma.apply("find", "ed"), stem("found", "VBN"));
        assertEquals(Lemma.apply("see",  "ed"), stem("saw", "VBD"));
        //assertEquals(Stem.apply("person", "s"), getStem("people", "NNS"));  // Morpha doesn't get this right. Oh well.
    }

    @Test
    public void testPosIsNotIgnored() {
        assertEquals(Lemma.apply("see", "ed"), stem("saw", "VBD"));
        assertEquals(Lemma.apply("saw"), stem("saw", "NN"));
    }

    @Test
    public void testNoExceptionOnWhitespace() {
        assertEquals(Lemma.apply("a b"), stem("a b",  "JJ"));
        assertEquals(Lemma.apply("a\tb"), stem("a\tb", "JJ"));
        assertEquals(Lemma.apply("a\nb"), stem("a\nb", "JJ"));
        assertEquals(Lemma.apply("a\rb"), stem("a\rb", "JJ"));
    }

    @Test
    public void testAngleBracketsHandled() {
        // getStem used to throw "java.lang.Error: Error: could not match input" for ">"
        assertEquals(Lemma.apply(">"), stem(">", "JJR"));
        assertEquals(Lemma.apply("<"), stem("<", "JJR"));
    }

    @Test
    public void testNullIsRetained() {
        // regression test for https://github.com/knowitall/morpha/issues/1
        // getStem used to turn "null" into ""
        assertEquals(Lemma.apply("null"), stem("null", "JJ"));
        assertEquals(Lemma.apply("null", "s"), stem("nulls", "NN"));
    }
}

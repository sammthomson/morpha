package com.samthomson.morpha;

import org.junit.Test;

import static com.samthomson.morpha.Morpha.getLemma;
import static org.junit.Assert.assertEquals;


public class MorphaTest {
    @Test
    public void testWithoutPos() {
        assertEquals("find", getLemma("found"));
        assertEquals("find", getLemma("finding"));
        assertEquals("fall", getLemma("fell"));
    }

    @Test
    public void testWordIsLowercased() {
        final String lemma = getLemma("Nuclear", "JJ");
        assertEquals("nuclear", lemma);
    }

    @Test
    public void testContractions() {
        assertEquals("'s", getLemma("'s", "POS"));
        assertEquals("have", getLemma("'ve", "VB"));
        assertEquals("not", getLemma("n't", "RB"));
        assertEquals("be", getLemma("'s", "VBZ"));
        assertEquals("will", getLemma("'ll", "VB"));
        assertEquals("be", getLemma("'re", "VBP"));
    }

    @Test
    public void testIrregulars() {
        assertEquals("fall", getLemma("fell", "VBD"));
        assertEquals("find", getLemma("found", "VBD"));
        assertEquals("find", getLemma("found", "VBN"));
        //assertEquals("lie", getLemma("lay", "VBD"));
        assertEquals("see", getLemma("saw", "VBD"));
        //assertEquals("person", getLemma("people", "NNS"));
    }

    @Test
    public void testAngleBracketsHandled() {
        // getLemma used to throw "java.lang.Error: Error: could not match input" for ">"
        assertEquals(">", getLemma(">", "JJR"));
        assertEquals("<", getLemma("<", "JJR"));
    }

    @Test
    public void testNullIsRetained() {
        // regression test for https://github.com/knowitall/morpha/issues/1
        // getLemma used to turn "null" into ""
        assertEquals("null", getLemma("null", "JJ"));
        assertEquals("null", getLemma("nulls", "NN"));
    }
}

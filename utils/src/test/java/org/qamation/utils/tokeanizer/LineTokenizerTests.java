package org.qamation.utils.tokeanizer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.qamation.utils.tokenizer.LineTokenizer;

public class LineTokenizerTests {

    @Before
    public void setUp(){}

    @Test
    public void testEOL() {
        String line = "bla 2 bla \n 3  bla ";
        LineTokenizer tokenizer = new LineTokenizer();
        ListerTokens listener = new ListerTokens();
        tokenizer.addTokenListener(listener);
        tokenizer.tokenizeString(line);
        Assert.assertEquals("blablabla",listener.getSval());
        Assert.assertEquals(3,listener.getDval(),0.01);
        Assert.assertEquals(true,listener.isEof());
        Assert.assertEquals(true,listener.isEol());
    }

    @After
    public void tearDown() {}

}

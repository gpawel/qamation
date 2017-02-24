package org.qamation.utils;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringFormatTests {
    @Before
    public void setUp() {}
    @After
    public void tearDown() {}

    @Test
    public void testNumberFormat() {
        int number = Integer.parseInt("123456");
        String result = String.format("%1$07d",number);
        System.out.println(result);
        Assert.assertEquals("0123456",result);
    }

    @Test
    public void testMD5Hash() {
        //System.out.println(StringUtils.getMD5("abc"));
        Assert.assertEquals("900150983cd24fb0d6963f7d28e17f72", StringUtils.getMD5("abc"));
        Assert.assertEquals("849cd30febce87c8c656579947fe4576", StringUtils.getMD5("AbCaBc"));
    }
}

package de.grobmeier.jjson.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class JSONAnnotationDecoderTest {

    @Test
    public void testDecode() throws Exception {
        JSONAnnotationDecoder decoder = new JSONAnnotationDecoder();
        AnnotatedSetTestClass result = 
            decoder.decode(AnnotatedSetTestClass.class, 
                    "{\"test1\":\"mytestvalue\",\"test2\":true,\"test3\":true," +
                    "\"nested\":{\"mystring\":\"blubber\"}}");
        Assert.assertEquals("mytestvalue",result.getTest1());
        Assert.assertEquals(true,result.isTest2());
        Assert.assertEquals(Boolean.TRUE,result.getTest3());
        Assert.assertEquals("blubber",result.getNested().getMystring());
    }

}

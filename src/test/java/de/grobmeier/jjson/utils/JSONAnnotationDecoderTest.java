package de.grobmeier.jjson.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class JSONAnnotationDecoderTest {

    @Test
    public void testDecode() {
        JSONAnnotationDecoder decoder = new JSONAnnotationDecoder();
        AnnotatedTestClass result = decoder.decode(AnnotatedTestClass.class, "");
        System.out.println(result);
    }

}

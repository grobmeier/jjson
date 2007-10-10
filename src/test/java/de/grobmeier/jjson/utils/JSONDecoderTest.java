package de.grobmeier.jjson.utils;

import junit.framework.TestCase;

import org.junit.Test;

import de.grobmeier.jjson.JSONValue;

public class JSONDecoderTest {

    @Test
    public final void testDecodeSimpleString() {
        JSONDecoder decoder = new JSONDecoder("\"hello");
        JSONValue result = decoder.decode();
        TestCase.assertEquals("\"hello\"",result.toJSON());
    }
    
    @Test
    public final void testDecodeString() {
        JSONDecoder decoder = new JSONDecoder("\"hello, its \\\"ME\\\" again!\"");
        JSONValue result = decoder.decode();
        TestCase.assertEquals("\"hello, its \\\"ME\\\" again!\"",result.toJSON());
    }
    
    @Test
    public final void testDecodeObject() {
        JSONDecoder decoder = new JSONDecoder("{\"key\":\"value\"}");
        JSONValue result = decoder.decode();
        System.out.println(result.toJSON());
    }

    @Test
    public final void testDecodeObject2() {
        JSONDecoder decoder = new JSONDecoder("{\"key\":\"value\",\"key2\":\"value2\"}");
        JSONValue result = decoder.decode();
        System.out.println(result.toJSON());
    }
}

package org.apache.commons.json.utils;

import junit.framework.TestCase;

import org.apache.commons.json.JSONValue;
import org.junit.Test;

public class JSONDecoderTest {

    @Test
    public final void testDecode() {
        JSONDecoder decoder = new JSONDecoder();
        JSONValue result = decoder.decode("\"hello, its \"ME\" again!\"");
        TestCase.assertEquals("\"hello, its \"ME\" again!\"\"",result.toJSON());
    }

}

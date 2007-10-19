/*
 *  Copyright 2007 Christian Grobmeier 
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at 
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  
 *  Unless required by applicable law or agreed to in writing, 
 *  software distributed under the License is distributed 
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 *  either express or implied. See the License for the specific 
 *  language governing permissions and limitations under the License.
 */
package de.grobmeier.jjson.utils;

import junit.framework.TestCase;

import org.junit.Test;

import de.grobmeier.jjson.JSONValue;

public class JSONDecoderTest {

    @Test
    public final void testDecodeSimpleString() {
        JSONDecoder decoder = new JSONDecoder("\"hello\"");
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
        TestCase.assertEquals("{\"key\":\"value\"}", result.toJSON());
    }

    @Test
    public final void testDecodeObject2() {
        JSONDecoder decoder = 
            new JSONDecoder("{\"key\":\"value\",\"key2\":\"value2\"}");
        JSONValue result = decoder.decode();
        TestCase.assertEquals(
                "{\"key\":\"value\",\"key2\":\"value2\"}", 
                result.toJSON());
    }
    
    @Test
    public final void testDecodeObject3() {
        JSONDecoder decoder = 
            new JSONDecoder("{\"key\":\"value\",\"key2\":{\"key3\":\"value2\"}}");
        JSONValue result = decoder.decode();
        TestCase.assertEquals(
                "{\"key\":\"value\",\"key2\":{\"key3\":\"value2\"}}", 
                result.toJSON());
    }
    
    @Test
    public final void testDecodeArray() {
        String orig = "[\"key\",\"key2\"]";
        JSONDecoder decoder = new JSONDecoder(orig);
        JSONValue result = decoder.decode();
        TestCase.assertEquals(orig, result.toJSON());
    }
    
    @Test
    public final void testDecodeArrayWithObjects() {
        JSONDecoder decoder = 
            new JSONDecoder("[\"key1\",\"key2\",{\"key3\":\"value3\"}]");
        JSONValue result = decoder.decode();
        TestCase.assertEquals(
                "[\"key1\",\"key2\",{\"key3\":\"value3\"}]", 
                result.toJSON());
    }
    
    @Test
    public final void testDecodeArrayWithNumber() {
        JSONDecoder decoder = 
            new JSONDecoder("[\"key1\",\"key2\",12345]");
        JSONValue result = decoder.decode();
        TestCase.assertEquals("[\"key1\",\"key2\",12345]", result.toJSON());
    }
    
    @Test
    public final void testDecodeArrayWithNumber2() {
        JSONDecoder decoder = 
            new JSONDecoder("[\"key1\",\"key2\",12345.56]");
        JSONValue result = decoder.decode();
        TestCase.assertEquals("[\"key1\",\"key2\",12345.56]", result.toJSON());
    }
    
    @Test
    public final void testDecodeArrayWithNumber3() {
        JSONDecoder decoder = 
            new JSONDecoder("[\"key1\",\"key2\",+12345.56]");
        JSONValue result = decoder.decode();
        TestCase.assertEquals("[\"key1\",\"key2\",12345.56]", result.toJSON());
    }
    
    @Test
    public final void testDecodeArrayWithNumber4() {
        JSONDecoder decoder = 
            new JSONDecoder("[\"key1\",\"key2\",-12345.56]");
        JSONValue result = decoder.decode();
        TestCase.assertEquals("[\"key1\",\"key2\",-12345.56]", result.toJSON());
    }
}

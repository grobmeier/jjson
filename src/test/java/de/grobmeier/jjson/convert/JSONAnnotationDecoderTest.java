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
package de.grobmeier.jjson.convert;

import junit.framework.Assert;

import org.junit.Test;

import de.grobmeier.jjson.convert.JSONAnnotationDecoder;

public class JSONAnnotationDecoderTest {

    @Test
    public void testDecode() throws Exception {
        JSONAnnotationDecoder decoder = new JSONAnnotationDecoder();
        AnnotatedSetTestClass result = 
            decoder.decode(AnnotatedSetTestClass.class, 
                    "{\"test1\":\"mytestvalue\",\"test2\":true,\"test3\":true," +
                    "\"nested\":{\"mystring\":\"blubber\"}," +
                    "\"nestedStringList\":[\"nested1\",\"nested2\",\"nested3\"]," +
                    "\"primitiveString\":[\"nestedX1\",\"nestedX2\",\"nestedX3\"]" +
                    "}");
        Assert.assertEquals("mytestvalue",result.getTest1());
        Assert.assertEquals(true,result.isTest2());
        Assert.assertEquals(Boolean.TRUE,result.getTest3());
        Assert.assertEquals("blubber",result.getNested().getMystring());
        Assert.assertEquals(3,result.getNestedStringList().size());
        Assert.assertEquals(3,result.getPrimitiveString().length);
    }

}

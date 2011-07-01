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
package de.grobmeier.jjson;

import junit.framework.TestCase;

import org.junit.Test;
/**
 * 
 */
public class JSONArrayTest {

    /**
     * Test method for {@link org.apache.commons.json.JSONArray#add(org.apache.commons.json.JSONValue)}.
     */
    @Test
    public final void testAdd() {
        JSONArray array = new JSONArray();
        array.add(new JSONString("blub"));
        array.add(new JSONString("blub2"));
        TestCase.assertEquals("[\"blub\",\"blub2\"]",array.toJSON());
        
        JSONObject o = new JSONObject();
        o.put("mykey", new JSONString("myvalue"));
        array.add(o);
        TestCase.assertEquals(
                "[\"blub\",\"blub2\",{\"mykey\":\"myvalue\"}]", array.toJSON());
    }

}

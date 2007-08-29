/**
 * 
 */
package org.apache.commons.json;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * 
 */
public class JSONObjectTest {

    /**
     * Test method for {@link org.apache.commons.json.JSONObject#
     * put(java.lang.String, org.apache.commons.json.JSONValue)}.
     */
    @Test
    public final void testPut() {
        JSONObject object = new JSONObject();
        object.put("mykey", new JSONString("myvalue"));
        TestCase.assertEquals("{\"mykey\":\"myvalue\"}",object.toJSON());
        
        object = new JSONObject();
        object.put("mykey", new JSONString("myvalue"));
        object.put("mykey2", new JSONString("myvalue2"));
        TestCase.assertEquals(
                "{\"mykey\":\"myvalue\",\"mykey2\":\"myvalue2\"}",object.toJSON());
        
        
        object = new JSONObject();
        JSONObject object2 = new JSONObject();
        object2.put("objykey", new JSONString("objvalue"));
        object.put("blub", object2);
        TestCase.assertEquals(
                "{\"blub\":{\"objykey\":\"objvalue\"}}", object.toJSON());
        
        object = new JSONObject();
        JSONArray array = new JSONArray();
        array.add(new JSONString("blub"));
        array.add(new JSONString("blub2"));
        object.put("blubs", array);
        TestCase.assertEquals("{\"blubs\":[\"blub\",\"blub2\"]}",object.toJSON());
        
        object = new JSONObject();
        object.put("bool", JSONBoolean.TRUE);
        TestCase.assertEquals("{\"bool\":true}",object.toJSON());
        
        JSONObject o = new JSONObject();
        o.put("my", new JSONNumber(14440.123E+5));
        TestCase.assertEquals("{\"my\":1.4440123E9}",o.toJSON());
    }

}

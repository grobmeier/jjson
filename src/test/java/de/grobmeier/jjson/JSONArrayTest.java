/**
 * 
 */
package org.apache.commons.json;

import org.junit.Test;
import junit.framework.TestCase;
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

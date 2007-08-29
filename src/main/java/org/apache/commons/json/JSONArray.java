/**
 * 
 */
package org.apache.commons.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 */
public class JSONArray implements JSONValue {
    private List<JSONValue> values = new ArrayList<JSONValue>();
    /**
     * 
     */
    public JSONArray() {
    }

    public void add(JSONValue value) {
        if(value == null) {
            value = JSONNull.NULL;
        }
        values.add(value);
    }
    
    /**
     * (non-Javadoc)
     * @see org.apache.commons.json.JSONValue#toJSON()
     */
    public String toJSON() {
        StringBuffer result = new StringBuffer();
        result.append("[");
        
        Iterator it = values.iterator();
        while(it.hasNext()) {
            JSONValue value = (JSONValue)it.next();
            result.append(value.toJSON());
            if(it.hasNext()) {
                result.append(",");
            }
        }
        result.append("]");
        return result.toString();
    }

}

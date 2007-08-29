package org.apache.commons.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JSONObject implements JSONValue {
    private Map<String, JSONValue> object = new HashMap<String, JSONValue>();
    
    /**
     * 
     */
    public JSONObject() {
    }
    
    /**
     * @param key
     * @param value
     */
    public void put(final String key, JSONValue value) {
        if(value == null) {
            value = JSONNull.NULL;
        }
        object.put(key, value);
    }
    
    public String toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        Set<String> keys = object.keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()) {
            String key = (String)it.next();
            buffer.append("\"");
            buffer.append(key);
            buffer.append("\":");
            buffer.append(object.get(key).toJSON());
            if(it.hasNext()) {
                buffer.append(",");
            }
        }
        buffer.append("}");
        return buffer.toString();
    }
}

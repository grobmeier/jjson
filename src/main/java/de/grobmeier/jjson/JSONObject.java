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
    
    /**
     * @return the object
     */
    public Map<String, JSONValue> getValue() {
        return object;
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

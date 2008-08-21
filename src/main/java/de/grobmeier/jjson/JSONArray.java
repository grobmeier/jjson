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
     * @return the values
     */
    public List<JSONValue> getValue() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<JSONValue> values) {
        this.values = values;
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

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

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import de.grobmeier.jjson.JSONValue;

public class JSONDecoder {
    /**
     * 
     */
    public JSONDecoder() {
    }
    
    public JSONValue decode(String input) {
        if(input.startsWith("\"") && input.endsWith("\"")) {
            return decodeString(input);
        } else if(input.startsWith("{") && input.endsWith("}")) {
            return decodeObject(input);
        }
        return null;
    }
    
    private JSONObject decodeObject(String input) {
        String temp = input.substring(1, input.length());
        // nach key suchen
        // alles nach doppelpunkt bis zum komma in decode schicken
        // Objekte ablegen, JSONObject instanziieren und zurück
        return null;
    }
    /**
     * @param input
     * @return
     */
    private JSONString decodeString(String input) {
        String temp = input.substring(1, input.length());
        return new JSONString(temp);
    }
}

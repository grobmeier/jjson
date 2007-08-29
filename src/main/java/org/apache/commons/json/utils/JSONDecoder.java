package org.apache.commons.json.utils;

import org.apache.commons.json.JSONObject;
import org.apache.commons.json.JSONString;
import org.apache.commons.json.JSONValue;

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

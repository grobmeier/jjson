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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import de.grobmeier.jjson.JSONArray;
import de.grobmeier.jjson.JSONNumber;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import de.grobmeier.jjson.JSONValue;

/**
 * 
 */
public class JSONDecoder {
    // Use English locale, cause the json spec allows only . as seperator
    private static NumberFormat numberFormat = 
        NumberFormat.getInstance(Locale.ENGLISH);
    
    private JSONReader reader = null;
    /**
     * 
     */
    public JSONDecoder(final String json) {
        reader = new JSONReader(json);
    }
    
   
    enum Opener {
        OBJECT('{'), 
        ARRAY('['), 
        STRING('"'), 
        PLUS('+'),
        MINUS('-'),
        DOT('.');
        
        private char sign = ' ';
        
        private Opener(char s) {
            this.sign = s;
        }
    }
    
    enum Closer {
        jsonobject('}'), jsonarray(']'), jsonstring('"');
        
        private char sign = ' ';
        
        private Closer(char s) {
            this.sign = s;
        }
    }

    /**
     * 
     */
    private class JSONReader {
        private char[] json = null;
        private int index = 0;
        /**
         * @param _json
         */
        public JSONReader(final String _json) {
            json = _json.trim().toCharArray();
        }
        
        /**
         * Reads one character and points to the next sign.
         * @return the read character
         */
        public char read() {
            char result = ' ';
            if(json.length != 0) {
                result = json[index];
            }
             
            if(index + 1 < json.length) {
                index++;
            } else {
                index = 0;
            }
            return result;
        }
        
        /**
         * 
         */
        public char back() {
            if(index - 1 >= 0) {
                index--;
            } else {
                index = 0;
            }
            return current();
        }
        
        /**
         * @return
         */
        public char current() {
            return json[index];
        }
        
        /**
         * @return
         */
        public boolean next() {
            if(index + 1 >= json.length) {
                return false;
            } else {
                index++;
                return true;
            }
        }
        /**
         * @return
         */
        public char readBefore() {
            if(index - 1 >= 0 && index < json.length) {
                return json[index-1];
            } 
            return ' '; 
        }
    }

    /**
     * @param c
     * @return
     */
    private boolean isOpener(char c) {
        if(reader.readBefore() == '\\') {
            return false;
        }
        Opener[] opener = Opener.values();
        for (Opener item : opener) {
            if(item.sign == c) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param c
     * @return
     */
    private boolean isCloser(char c) {
        if(reader.readBefore() == '\\') {
            return false;
        }
        Closer[] closer = Closer.values();
        for (Closer item : closer) {
            if(item.sign == c) {
                return true;
            }
        }
        return false;
    }
    
    public JSONValue decode() {
        char current = reader.current();
        if(current == Opener.OBJECT.sign) {
            return decodeObject();
        } else if(current == Opener.STRING.sign) {
            return decodeString();
        } else if(current == Opener.ARRAY.sign) {
            return decodeArray();
        } else if(
            Character.isDigit(current) || 
            current == Opener.MINUS.sign ||
            current == Opener.PLUS.sign || 
            current == Opener.DOT.sign) {
            return decodeNumber();
        }
        return null;
    }
    
    /**
     * @return
     */
    private JSONNumber decodeNumber() {
        StringBuffer sb = new StringBuffer();
        if(reader.current() != Opener.PLUS.sign) {
            sb.append(reader.current());
        }
        
        while(reader.next()) {
            char temp = reader.current();
            // Strings cannot have a opener inside
            if(isCloser(temp)) {
                break;
            } else {
                sb.append(temp);
            }
        }

        // Numbers have no closers, we have to point to the
        // last digit to work correctly for any surrounding objects/arrays
        reader.back();
        
        Number number = null;
        try {
            number = numberFormat.parse(sb.toString());
        } catch (ParseException e) {
            // TODO Integrate loggin
            e.printStackTrace();
        }
        // parsing to make sure the string is a number
        return new JSONNumber(number.toString());
    }
    
    /**
     * @return
     */
    private JSONObject decodeObject() {
        JSONObject result = new JSONObject();
        
        boolean hasNext = true;
        while(hasNext) {
            // key must be a string
            reader.next();
            JSONString key = decodeString();
            
            while(reader.next()) {
                char temp = reader.current();
                if(temp == ':') {
                    reader.next();
                    break;
                }
            }
            
            JSONValue value = decode();
            result.put(key.toString(), value);
            reader.next();
            if(reader.current() == Closer.jsonobject.sign) {
                hasNext = false;
            } 
        }
        return result;
    }
    
    /**
     * @return
     */
    private JSONArray decodeArray() {
        JSONArray result = new JSONArray();
        
        boolean hasNext = true;
        while(hasNext) {
            // key must be a string
            reader.next();
            JSONValue value = decode();
            result.add(value);
            reader.next();
            if(reader.current() == Closer.jsonarray.sign) {
                hasNext = false;
            } 
        }
        return result;
    }
    
    /**
     * @param input
     * @return
     */
    private JSONString decodeString() {
        StringBuilder result = new StringBuilder();
        if(reader.current() == Opener.STRING.sign) {
            while(reader.next()) {
                char temp = reader.current();
                // Strings cannot have a opener inside
                if(isCloser(temp)) {
                    break;
                } else {
                    result.append(temp);
                }
            }
        } else {
            return new JSONString("");
        }
        return new JSONString(result.toString());
    }
}

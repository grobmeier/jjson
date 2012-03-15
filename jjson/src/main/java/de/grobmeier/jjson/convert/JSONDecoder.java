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
package de.grobmeier.jjson.convert;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import de.grobmeier.jjson.JSONArray;
import de.grobmeier.jjson.JSONBoolean;
import de.grobmeier.jjson.JSONNull;
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
    
    private final static char CONTROL = '\\';
    
    /**
     * Special chars which open a type
     */
    enum Opener {
        OBJECT('{'), 
        ARRAY('['), 
        STRING('"'), 
        PLUS('+'),
        MINUS('-'),
        DOT('.'),
        NULL('n'),
        NULL_UPPER('N'),
        TRUE('t'),
        TRUE_UPPER('T'),
        FALSE('f'),
        FALSE_UPPER('F');
        
        private char sign = ' ';
        
        private Opener(char s) {
            this.sign = s;
        }
    }
    
    /**
     * Special chars which close a type
     */
    enum Closer {
        jsonobject('}'), jsonarray(']'), jsonstring('"');
        
        private char sign = ' ';
        
        private Closer(char s) {
            this.sign = s;
        }
    }

    /**
     * JSONReader is a helper class to navigate 
     * through the input json string. 
     */
    private class JSONReader {
        /* chararray of the original jsonstring */
        private char[] json = null;
        /* pointer to the chararray */
        private int index = 0;
        /**
         * Constructor. Needs the orignal JSON-String
         * to navigate onto it.
         * @param _json the original JSON-String
         */
        public JSONReader(final String _json) {
            json = _json.trim().toCharArray();
        }
        
        /**
         * Reads one character and then points to the next sign.
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
         * Moves the the cursor back one sign 
         * or does nothing, if it is the first sign.
         * @return the current char after the operation
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
         * Reads the current char without moving the cursor
         * @return the current char
         */
        public char current() {
            return json[index];
        }
        
        /**
         * Brings the pointer to the next position in the
         * array. 
         * @return false, if the pointer is allready at the last char and
         * cannot be moved. True, if the the operation is successfull.
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
         * Reads the sign before the current postion without
         * moving the pointer. Returns a blank char if the 
         * cursor is on the first position
         * @return the sign before the pointers position
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
        if(reader.readBefore() == CONTROL) {
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
        if(reader.readBefore() == CONTROL) {
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
        } else if(
            current == Opener.NULL.sign ||
            current == Opener.NULL_UPPER.sign) {
            return decodeNull();
        } else if(
            current == Opener.FALSE.sign ||
            current == Opener.FALSE_UPPER.sign) {
            return decodeFalse();
        }  else if(
            current == Opener.TRUE.sign ||
            current == Opener.TRUE_UPPER.sign) {
            return decodeTrue();
        }
        return null;
    }
    
    /**
     * @return
     */
    private JSONBoolean decodeTrue() {
        StringBuffer sb = new StringBuffer();
        sb.append(reader.current());
        int i = 1;
        while(i < 4) {
            reader.next();
            char temp = reader.current();
            sb.append(temp);
            i++;
        }

        // sanity check
        if(!"TRUE".equalsIgnoreCase(sb.toString())) {
            System.err.print("JSON expected true value, was: " + sb.toString());
        }
        return new JSONBoolean(true);
    }
    
    /**
     * @return
     */
    private JSONBoolean decodeFalse() {
        StringBuffer sb = new StringBuffer();
        sb.append(reader.current());
        int i = 1;
        while(i < 5) {
            reader.next();
            char temp = reader.current();
            sb.append(temp);
            i++;
        }

        // sanity check
        if(!"FALSE".equalsIgnoreCase(sb.toString())) {
            System.err.print("JSON expected true value, was: " + sb.toString());
        }
        return new JSONBoolean(false);
    }
    
    /**
     * @return
     */
    private JSONNull decodeNull() {
        StringBuffer sb = new StringBuffer();
        sb.append(reader.current());
        int i = 1;
        while(i < 4) {
            reader.next();
            char temp = reader.current();
            sb.append(temp);
            i++;
        }

        // sanity check
        if(!"NULL".equalsIgnoreCase(sb.toString())) {
            System.err.print("JSON expected null value, was: " + sb.toString());
        }
        return new JSONNull();
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
            if(isCloser(temp) || temp == ',') {
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
            
            if(reader.current() == Closer.jsonobject.sign) {
                hasNext = false;
                return result;
            }
            
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
            if(reader.current() == Closer.jsonarray.sign) {
                hasNext = false;
                return result;
            }
            JSONValue value = decode();
            result.add(value);
            
            if(reader.current() == Closer.jsonarray.sign) {
                hasNext = false;
            }
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
        
        while(reader.current()==' ') {
        	reader.next();
        }
        
        if(reader.current() == Opener.STRING.sign) {
            while(reader.next()) {
                char temp = reader.current();
                // Strings cannot have a opener inside
                if(Opener.STRING.sign == temp) {
                	if(CONTROL != reader.readBefore()) {
                		break;
                	}
                }
                result.append(temp);
            }
        } else {
            return new JSONString("");
        }
        return new JSONString(result.toString());
    }
}

package de.grobmeier.jjson.utils;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import de.grobmeier.jjson.JSONValue;

public class JSONDecoder {
    private JSONReader reader = null;
    /**
     * 
     */
    public JSONDecoder(final String json) {
        reader = new JSONReader(json);
    }
    
   
    enum Opener {
        jsonobject('{'), jsonarray('['), jsonstring('"');
        
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
                index = 0;
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
        if(reader.current() == Opener.jsonobject.sign) {
            return decodeObject();
        } else if(reader.current() == Opener.jsonstring.sign) {
            return decodeString();
        }
        return null;
    }
    
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
     * @param input
     * @return
     */
    private JSONString decodeString() {
        StringBuilder result = new StringBuilder();
        if(reader.current() == Opener.jsonstring.sign) {
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

package org.apache.commons.json;
/**
 * 
 */
public class JSONNull implements JSONValue {
    
    public static final JSONNull NULL = new JSONNull();
    
    /**
     * 
     */
    public JSONNull() {
        // no values
    }
    
    /**
     * (non-Javadoc)
     * @see org.apache.commons.json.JSONValue#toJSON()
     */
    public String toJSON() {
        return "null";
    }
}

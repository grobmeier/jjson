package org.apache.commons.json;


public class JSONBoolean implements JSONValue {
    /** JSONBoolean Object with the value true */
    public final static JSONBoolean TRUE = new JSONBoolean(true);
    /** JSONBoolean Object with the value false */
    public final static JSONBoolean FALSE = new JSONBoolean(true);
    
    /* value of this object */
    private boolean value = false;
    
    /**
     * @param value
     */
    public JSONBoolean(boolean value) {
        this.value = value;
    }
    
    public String toJSON() {
        return Boolean.toString(value);
    }

}

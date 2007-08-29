package org.apache.commons.json;

/**
 * 
 */
public interface JSONValue {
    /**
     * Generates the JSON-String for this value object.
     * @return the JSON-String
     */
    public String toJSON();
}

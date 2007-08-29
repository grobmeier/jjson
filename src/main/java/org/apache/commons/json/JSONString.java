package org.apache.commons.json;


public class JSONString implements JSONValue {
    private StringBuffer buffer = new StringBuffer();
    
    /**
     * @param value
     */
    public JSONString(final String value) {
        buffer.append(value);
    }

    /**
     * @param append
     */
    public void append(final String append) {
        buffer.append(append);
    }
    
    /**
     * (non-Javadoc)
     * @see org.apache.commons.json.JSONValue#toJSON()
     */
    public String toJSON() {
        StringBuffer result = new StringBuffer();
        result.append("\"");
        result.append(buffer);
        result.append("\"");
        return result.toString();
    }
}

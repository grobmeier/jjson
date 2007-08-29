package org.apache.commons.json;

import java.math.BigDecimal;


public class JSONNumber implements JSONValue {
    private String value = "0";
    /**
     * 
     */
    public JSONNumber(double value) {
       this.value = Double.toString(value); 
    }
    
    /**
     * @param value
     */
    public JSONNumber(float value) {
        this.value = Float.toString(value);
    }

    /**
     * @param value
     */
    public JSONNumber(BigDecimal value) {
        this.value = value.toPlainString(); 
    }
    
    public JSONNumber(int value) {
        this.value = Integer.toString(value);
    }
    /**
     * (non-Javadoc)
     * @see org.apache.commons.json.JSONValue#toJSON()
     */
    public String toJSON() {
        return value;
    }

}

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
package de.grobmeier.jjson;


import java.math.BigDecimal;


public class JSONNumber implements JSONValue {
    private String value = "0";
    /**
     * 
     */
    public JSONNumber(double value) {
       this.value = Double.toString(value); 
    }
    
    public JSONNumber(String value) {
        this.value = value;
    }
    
    public JSONNumber(long value) {
        this.value = Long.toString(value);
    }
    
    public JSONNumber(float value) {
        this.value = Float.toString(value);
    }

    public JSONNumber(BigDecimal value) {
        this.value = value.toPlainString(); 
    }
    
    public JSONNumber(int value) {
        this.value = Integer.toString(value);
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    public String toJSON() {
        return value;
    }

}

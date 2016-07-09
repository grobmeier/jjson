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

public class JSONBoolean implements JSONValue {
    /** JSONBoolean Object with the value true */
    public final static JSONBoolean TRUE = new JSONBoolean(true);
    /** JSONBoolean Object with the value false */
    public final static JSONBoolean FALSE = new JSONBoolean(false);
    
    /* value of this object */
    private boolean value = false;
    
    /**
     * @return the value
     */
    public boolean getValue() {
        return value;
    }

    public JSONBoolean(boolean value) {
        this.value = value;
    }
    
    public String toJSON() {
        return Boolean.toString(value);
    }

}

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



public class JSONString implements JSONValue {
    private StringBuffer buffer = new StringBuffer();
    
    public JSONString(final String value) {
        buffer.append(value);
    }

    public void append(final String append) {
        buffer.append(append);
    }
    
    /**
     * @return the buffer
     */
    public StringBuffer getValue() {
        return buffer;
    }

    public String toJSON() {
        StringBuffer result = new StringBuffer();
        result.append("\"");
        result.append(buffer);
        result.append("\"");
        return result.toString();
    }

    /**
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return buffer.toString();
    }
}

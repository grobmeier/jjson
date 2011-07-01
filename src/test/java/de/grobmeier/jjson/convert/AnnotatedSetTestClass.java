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

import java.util.List;

import de.grobmeier.jjson.convert.JSON;

@JSON
public class AnnotatedSetTestClass {
	@JSON
    private String test1 = null;

	@JSON
    private boolean test2 = false;
    
	@JSON
    private Boolean test3 = false;
    
	@JSON
    private AnnotatedNestedSetClass nested = null;
    
	@JSON 
    private List<String> nestedStringList = null;
    
	@JSON
    private String[] primitiveString = null;
    
    /**
     * @return the test1
     */
    public String getTest1() {
        return test1;
    }

    /**
     * @param test1 the test1 to set
     */
    public void setTest1(String test1) {
        this.test1 = test1;
    }

    /**
     * @return the test2
     */
    public boolean isTest2() {
        return test2;
    }

    /**
     * @param test2 the test2 to set
     */
    public void setTest2(boolean test2) {
        this.test2 = test2;
    }

    /**
     * @return the test3
     */
    public Boolean getTest3() {
        return test3;
    }

    /**
     * @param test3 the test3 to set
     */
    public void setTest3(Boolean test3) {
        this.test3 = test3;
    }

    /**
     * @return the nested
     */
    public AnnotatedNestedSetClass getNested() {
        return nested;
    }

    /**
     * @param nested the nested to set
     */
    public void setNested(AnnotatedNestedSetClass nested) {
        this.nested = nested;
    }

    /**
     * @return the nestedStringList
     */
    public List<String> getNestedStringList() {
        return nestedStringList;
    }

    /**
     * @param nestedStringList the nestedStringList to set
     */
    public void setNestedStringList(List<String> nestedStringList) {
        this.nestedStringList = nestedStringList;
    }

    /**
     * @return the primitiveString
     */
    public String[] getPrimitiveString() {
        return primitiveString;
    }

    /**
     * @param primitiveString the primitiveString to set
     */
    public void setPrimitiveString(String[] primitiveString) {
        this.primitiveString = primitiveString;
    }
}

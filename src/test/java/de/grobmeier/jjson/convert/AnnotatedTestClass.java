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

import de.grobmeier.jjson.convert.JSON;

@JSON
public class AnnotatedTestClass {
	@JSON
    private int value1 = 1;
    
	@JSON
    private String value2 = "blub";
    
	@JSON
    protected int value3 = 2;
    
	@JSON
    protected Integer value5 = 3;
    
	@JSON
    protected String value4 = "fasel";
    
    protected String noField = "bla";

    @JSON
    private Integer[] intArray = {5,6,7,8,9};
    
    @JSON
    protected Boolean value6 = Boolean.TRUE;
    
    @JSON
    protected boolean value7 = false;
    
    @JSON
    private AnotherAnnotatedTestClass test = new AnotherAnnotatedTestClass();
    
    /**
     * @return the value1
     */
    public int getValue1() {
        return value1;
    }

    /**
     * @return the value2
     */
    public String getValue2() {
        return value2;
    }

    /**
     * @return the value3
     */
    public int getValue3() {
        return value3;
    }

    /**
     * @return the value4
     */
    public String getValue4() {
        return value4;
    }

    /**
     * @return the noField
     */
    public String getNoField() {
        return noField;
    }

    /**
     * @return the value5
     */
    public Integer getValue5() {
        return value5;
    }

    /**
     * @return the intArray
     */
    public Integer[] getIntArray() {
        return intArray;
    }

    /**
     * @return the value6
     */
    public Boolean getValue6() {
        return value6;
    }

    /**
     * @return the value7
     */
    public boolean isValue7() {
        return value7;
    }

    /**
     * @return the test
     */
    public AnotherAnnotatedTestClass getTest() {
        return test;
    }
}

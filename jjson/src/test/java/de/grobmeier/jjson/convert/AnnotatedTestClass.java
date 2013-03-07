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
    protected long value8 = 1000;
    
    @JSON
    protected double value9 = 10.23;
    
    @JSON
    protected float value10 = 9.23f;
    
    @JSON
    protected byte value11 = 0x11;
    
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

    public long getValue8() {
		return value8;
	}

	public void setValue8(long value8) {
		this.value8 = value8;
	}

	public double getValue9() {
		return value9;
	}

	public void setValue9(double value9) {
		this.value9 = value9;
	}

	public float getValue10() {
		return value10;
	}

	public void setValue10(float value10) {
		this.value10 = value10;
	}

	public byte getValue11() {
		return value11;
	}

	public void setValue11(byte value11) {
		this.value11 = value11;
	}

	/**
     * @return the test
     */
    public AnotherAnnotatedTestClass getTest() {
        return test;
    }
}

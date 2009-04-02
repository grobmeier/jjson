package de.grobmeier.jjson.utils;

import de.grobmeier.jjson.annotations.JSONField;
import de.grobmeier.jjson.annotations.JSONObject;

@JSONObject
public class AnnotatedTestClass {
    @JSONField
    private int value1 = 1;
    
    @JSONField
    private String value2 = "blub";
    
    @JSONField
    protected int value3 = 2;
    
    @JSONField
    protected Integer value5 = 3;
    
    @JSONField
    protected String value4 = "fasel";
    
    protected String noField = "bla";

    @JSONField
    private Integer[] intArray = {5,6,7,8,9};
    
    @JSONField
    protected Boolean value6 = Boolean.TRUE;
    
    @JSONField
    protected boolean value7 = false;
    
    @JSONField
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

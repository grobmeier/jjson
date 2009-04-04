package de.grobmeier.jjson.utils;

import de.grobmeier.jjson.annotations.JSONField;
import de.grobmeier.jjson.annotations.JSONObject;

@JSONObject
public class AnnotatedSetTestClass {
    @JSONField
    private String test1 = null;

    @JSONField
    private boolean test2 = false;
    
    @JSONField
    private Boolean test3 = false;
    
    
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
    
    
}

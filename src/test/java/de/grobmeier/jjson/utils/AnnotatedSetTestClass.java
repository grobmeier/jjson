package de.grobmeier.jjson.utils;

import java.util.List;

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
    
    @JSONField
    private AnnotatedNestedSetClass nested = null;
    
    @JSONField 
    private List<String> nestedStringList = null;
    
    @JSONField
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

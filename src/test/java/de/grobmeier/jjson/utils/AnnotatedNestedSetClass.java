package de.grobmeier.jjson.utils;

import de.grobmeier.jjson.annotations.JSONObject;

@JSONObject
public class AnnotatedNestedSetClass {
    private String mystring = null;

    /**
     * @return the mystring
     */
    public String getMystring() {
        return mystring;
    }

    /**
     * @param mystring the mystring to set
     */
    public void setMystring(String mystring) {
        this.mystring = mystring;
    }
    
    
}

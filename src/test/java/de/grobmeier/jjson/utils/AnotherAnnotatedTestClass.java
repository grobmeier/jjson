package de.grobmeier.jjson.utils;

import de.grobmeier.jjson.annotations.JSONField;
import de.grobmeier.jjson.annotations.JSONObject;

@JSONObject
public class AnotherAnnotatedTestClass {
    @JSONField
    private String mys = "bla";

    /**
     * @return the mys
     */
    public String getMys() {
        return mys;
    }
    
    
}

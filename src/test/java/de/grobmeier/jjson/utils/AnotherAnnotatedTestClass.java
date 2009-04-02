package de.grobmeier.jjson.utils;

import java.util.ArrayList;
import java.util.List;

import de.grobmeier.jjson.annotations.JSONField;
import de.grobmeier.jjson.annotations.JSONObject;

@JSONObject
public class AnotherAnnotatedTestClass {
    @JSONField
    private String mys = "bla";
    
    @JSONField
    private List<String> mylist = new ArrayList<String>();
    
    AnotherAnnotatedTestClass() {
    	mylist.add("entry1");
    	mylist.add("entry2");
    	mylist.add("entry3");
    	mylist.add("entry4");
    	mylist.add("entry5");
    }
    /**
     * @return the mys
     */
    public String getMys() {
        return mys;
    }
	public List<String> getMylist() {
		return mylist;
	}
}

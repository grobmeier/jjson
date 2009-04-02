package de.grobmeier.jjson.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.grobmeier.jjson.annotations.JSONField;
import de.grobmeier.jjson.annotations.JSONObject;

@JSONObject
public class AnotherAnnotatedTestClass {
    @JSONField
    private String mys = "bla";
    
    @JSONField
    private List<String> mylist = new ArrayList<String>();
    
    @JSONField
    private Map<String, MyInnerClass> map = new HashMap<String, MyInnerClass>();
    
 	AnotherAnnotatedTestClass() {
    	mylist.add("entry1");
    	mylist.add("entry2");
    	mylist.add("entry3");
    	mylist.add("entry4");
    	mylist.add("entry5");
    	
    	map.put("key1", new MyInnerClass("1"));
    	map.put("key2", new MyInnerClass("2"));
    	map.put("key3", new MyInnerClass("3"));
    }
 
    public Map<String, MyInnerClass> getMap() {
		return map;
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
	
	@JSONObject
	private class MyInnerClass {
		@JSONField
		private String innerfield = null;
		
		public MyInnerClass(String count) {
			innerfield = "innerfield_" + count;
		}
		
		public String getInnerfield() {
			return innerfield;
		}
		
		
	}
}

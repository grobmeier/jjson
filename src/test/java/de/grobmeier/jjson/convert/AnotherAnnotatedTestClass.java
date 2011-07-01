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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.grobmeier.jjson.convert.JSON;

@JSON
public class AnotherAnnotatedTestClass {
	@JSON
    private String mys = "bla";
    
	@JSON
    private List<String> mylist = new LinkedList<String>();
    
	@JSON
    private Map<String, MyInnerClass> map = new LinkedHashMap<String, MyInnerClass>();
    
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
	
	@JSON
	private class MyInnerClass {
		@JSON
		private String innerfield = null;
		
		public MyInnerClass(String count) {
			innerfield = "innerfield_" + count;
		}
		
		public String getInnerfield() {
			return innerfield;
		}
		
		
	}
}

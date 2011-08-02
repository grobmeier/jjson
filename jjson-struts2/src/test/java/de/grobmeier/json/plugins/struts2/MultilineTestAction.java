/*
 *  Copyright 2011 Christian Grobmeier 
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
package de.grobmeier.json.plugins.struts2;

import com.opensymphony.xwork2.ActionSupport;

import de.grobmeier.jjson.convert.JSON;

@JSON
public class MultilineTestAction extends ActionSupport {
	/** Serial */
	private static final long serialVersionUID = -6707682090128966810L;
	
	@JSON(encodeLinebreaks = true)
	private String test = "mytest\nagain";
	
	@JSON(replaceLinebreaksWith = "%0A")
	private String test2 = "mytest\nagain";
	
	public String execute(){
		return SUCCESS;
	}
	
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}

	public String getTest2() {
		return test2;
	}

	public void setTest2(String test2) {
		this.test2 = test2;
	}
}

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

import junit.framework.Assert;

import org.apache.struts2.StrutsTestCase;
import org.junit.Before;
import org.junit.Test;

public class JsonResultTest extends StrutsTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testSimple() throws Exception {
		String executeAction = this.executeAction("/test");
		Assert.assertEquals("{\"test\":\"mytest\"}", executeAction);
	}
	
	@Test
	public void testCommenteJson() throws Exception {
		String executeAction = this.executeAction("/test2");
		Assert.assertEquals("/* {\"test\":\"mytest\"} */", executeAction);
	}
	
	@Test
	public void testMultiline() throws Exception {
		String executeAction = this.executeAction("/multilinetest");
		Assert.assertEquals("{\"test\":\"mytest\\nagain\",\"test2\":\"mytest%0Aagain\"}", executeAction);
	}
}

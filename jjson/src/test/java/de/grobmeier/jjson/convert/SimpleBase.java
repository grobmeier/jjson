/*
 *  Copyright 2014 Christian Grobmeier
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

@JSON
public class SimpleBase {
	@JSON
    public String test1 = null;

	@JSON
    private boolean test2 = false;
    
    private Boolean test3 = false;

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public boolean isTest2() {
        return test2;
    }

    public void setTest2(boolean test2) {
        this.test2 = test2;
    }

    @JSON
    public Boolean getTest3() {
        return test3;
    }

    public void setTest3(Boolean test3) {
        this.test3 = test3;
    }
}

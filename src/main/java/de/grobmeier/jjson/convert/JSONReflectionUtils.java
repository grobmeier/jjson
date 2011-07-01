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

class JSONReflectionUtils {
    // Getter/Setter prefix
    final static char[] GET = {'g','e','t'};
    final static char[] IS = {'i','s'};
    final static char[] SET = {'s','e','t'};
    
    static String createGetter(String fieldname, char[] prefix) {
        char[] chars = fieldname.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]); 
        char[] result = new char[prefix.length + chars.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(chars, 0, result, prefix.length, chars.length);
        return String.valueOf(result);
    }
    
    static String createSetter(String fieldname) {
        char[] chars = fieldname.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]); 
        char[] result = new char[SET.length + chars.length];
        System.arraycopy(SET, 0, result, 0, SET.length);
        System.arraycopy(chars, 0, result, SET.length, chars.length);
        return String.valueOf(result);
    }
}

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

import junit.framework.TestCase;

import org.junit.Test;

import de.grobmeier.jjson.convert.JSONAnnotationEncoder;

import java.util.Set;
import java.util.TreeSet;


public class JSONAnnotationEncoderTest {
    @Test
    public void testSimpleClass() throws Exception {
    	String expected = 
    		"{\"value1\":1,\"value2\":\"blub\",\"value3\":2,\"value5\":3,\"value4\":" +
    		"\"fasel\",\"intArray\":[5,6,7,8,9],\"value6\":true,\"value7\":false,\"value8\":1000," +
    		"\"value9\":10.23,\"value10\":9.23,\"value11\":17,\"test\":" +
    		"{\"mys\":\"bla\",\"mylist\":[\"entry1\",\"entry2\",\"entry3\",\"entry4\",\"entry5\"]," +
    		"\"map\":{\"key1\":{\"innerfield\":\"innerfield_1\"}," +
    		"\"key2\":{\"innerfield\":\"innerfield_2\"}," +
    		"\"key3\":{\"innerfield\":\"innerfield_3\"}}}}";
        AnnotatedTestClass c = new AnnotatedTestClass();
        JSONAnnotationEncoder encoder = new JSONAnnotationEncoder();
        String json = encoder.encode(c);
        TestCase.assertEquals(expected, json);
    }
    
    @Test
    public void testMultilineStringClass() throws Exception {
    	MultilineAnnotatedTestClass c = new MultilineAnnotatedTestClass();
    	JSONAnnotationEncoder encoder = new JSONAnnotationEncoder();
        String json = encoder.encode(c);
        
		char[] charArray = new char[19];
		int i = 0;
		charArray[i++] = '{';
		charArray[i++] = '"';
		charArray[i++] = 'm';
		charArray[i++] = 'y';
		charArray[i++] = 's';
		charArray[i++] = '"';
		charArray[i++] = ':';
		charArray[i++] = '"';
		charArray[i++] = 'b';
		charArray[i++] = 'l';
		charArray[i++] = 'a';
		charArray[i++] = '\\';
		charArray[i++] = 'n';
		charArray[i++] = 't';
		charArray[i++] = 'e';
		charArray[i++] = 's';
		charArray[i++] = 't';
		charArray[i++] = '"';
		charArray[i++] = '}';
		
		String part1 = new String(charArray);
		
		System.out.println(part1);
		System.out.println(json);
		TestCase.assertEquals(part1, json);
    }
    
    @Test
    public void testReplaceLineBreaksWithClass() throws Exception {
    	MultilineAnnotatedTestClass2 c = new MultilineAnnotatedTestClass2();
    	JSONAnnotationEncoder encoder = new JSONAnnotationEncoder();
        String json = encoder.encode(c);
        TestCase.assertEquals("{\"mys\":\"bla\\ntest\"}", json);
    }
    
    @Test
    public void testSpecialChar() throws Exception {
    	SpecialCharAnnotatedTestClass c = new SpecialCharAnnotatedTestClass(); 
    	JSONAnnotationEncoder encoder = new JSONAnnotationEncoder();
        String json = encoder.encode(c);
        TestCase.assertEquals("{\"value\":\"b[l]u{b}\\\"test\"}", json);
    }

    @Test
    public void testTreeSet() throws Exception {
        AnnotatedTreeSetTestClass c = new AnnotatedTreeSetTestClass();

        Set<String> values = new TreeSet<String>();
        values.add("beta");
        values.add("alpha");
        values.add("gamma");
        values.add("delta");
        values.add("epsilon");

        c.setValue(values);

        JSONAnnotationEncoder encoder = new JSONAnnotationEncoder();
        String json = encoder.encode(c);

        TestCase.assertEquals("{\"value\":[\"alpha\",\"beta\",\"delta\",\"epsilon\",\"gamma\"]}", json);
    }
}

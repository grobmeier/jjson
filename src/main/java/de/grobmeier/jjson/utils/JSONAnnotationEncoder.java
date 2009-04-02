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
package de.grobmeier.jjson.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.grobmeier.jjson.JSONException;
import de.grobmeier.jjson.annotations.JSONField;
import de.grobmeier.jjson.annotations.JSONObject;

/**
 * 
 */
public class JSONAnnotationEncoder {
    private final static char[] get = {'g','e','t'};
    private final static char[] is = {'i','s'};
    
    private final static String NULL = "null";
    
    public String encode(Object result) throws JSONException {
       StringBuilder builder = new StringBuilder();
       encode(result, builder);
       return builder.toString();
    }
    
    /**
     * @param builder
     * @param result
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
	public void encode(Object result, StringBuilder builder) throws JSONException {
        if(result.getClass().isAssignableFrom(String.class)) {
            encodeString(((String)result), builder);
        } else if(result.getClass().isAssignableFrom(Integer.class)) {
            encodeInteger((Integer)result, builder);
        } else if(result.getClass().isAssignableFrom(Boolean.class)) {
            encodeBoolean((Boolean)result, builder);
        } else if(hasInterface(result, List.class)) {
        	encodeList((List<Object>)result, builder);
        } else if(result.getClass().isAssignableFrom(Map.class)) {
            builder.append(NULL);
        } else {
            encodeObject(result, builder);
        }
    }
    
    private void encodeList(List<Object> result, StringBuilder builder) throws JSONException {
    	boolean first = true;
    	builder.append("[");
    	for (Iterator<Object> iterator = result.iterator(); iterator.hasNext();) {
    		 if(!first) {
                 builder.append(",");
             } else {
                 first = false;
             }
    		 
			Object object = iterator.next();
			encode(object, builder);
		}
    	builder.append("]");
	}

	private String encodeObject(Object c, StringBuilder builder) throws JSONException {
        if(c.getClass().getAnnotation(JSONObject.class) == null) {
            return null;
        }
        
        if(c == null) {
            return NULL;
        }
        
        boolean first = true;
        
        builder.append("{");
        
        Field[] fields = c.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] anons = field.getAnnotations();
            for (Annotation annotation : anons) {
                if(annotation.annotationType().isAssignableFrom(JSONField.class)) {
                    if(!first) {
                        builder.append(",");
                    } else {
                        first = false;
                    }
                    
                    String methodName = null;
                    // primitive boolean getters have «is« as prefix
                    if("boolean".equals(field.getType().toString())) {
                        methodName = createGetter(field.getName(), is);
                    } else {
                        methodName = createGetter(field.getName(), get);
                    }

                    try {
                        Method method = c.getClass().getMethod(methodName, (Class[])null);
                        Object result = method.invoke(c, (Object[])null);
                        
                        encodeString(field.getName(), builder);
                        builder.append(":");
                        encode(result, builder);
                    } catch (SecurityException e) {
                        throw new JSONException(e);
                    } catch (NoSuchMethodException e) {
                        throw new JSONException("No appropriate getter found: " + methodName ,e);
                    } catch (IllegalArgumentException e) {
                        throw new JSONException(e);
                    } catch (IllegalAccessException e) {
                        throw new JSONException(e);
                    } catch (InvocationTargetException e) {
                        throw new JSONException(e);
                    }
                }
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private void encodeString(String string, StringBuilder result) {
    	if(string == null) {
            result.append(NULL);
        } else {
        	result.append("\"");
        	result.append(string);
        	result.append("\"");
        }
    }

    private void encodeInteger(Integer integer, StringBuilder result) {
    	if(integer == null) {
            result.append(NULL);
        } else {
	    	result.append("");
	        result.append(integer);
	        result.append("");
        }
    }
    
    private void encodeBoolean(Boolean b, StringBuilder result) {
    	if(b == null) {
            result.append(NULL);
    	} else {
	        result.append("");
	        result.append(Boolean.toString(b));
	        result.append("");
    	}
    }
    
    @SuppressWarnings("unchecked")
	private boolean hasInterface(Object target, Class interfaceClass) {
		Class[] interfaces = target.getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			if(interfaceClass.getName().equals(interfaces[i].getName())) {
				return true;
			}
		}
		return false;
    }
    
    private String createGetter(String fieldname, char[] prefix) {
        char[] chars = fieldname.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]); 
        char[] result = new char[prefix.length + chars.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(chars, 0, result, prefix.length, chars.length);
        return String.valueOf(result);
    }
}

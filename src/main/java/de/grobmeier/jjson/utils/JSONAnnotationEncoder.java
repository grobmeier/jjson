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
    
    public String encodeObject(Object c) throws JSONException {
        if(c.getClass().getAnnotation(JSONObject.class) == null) {
            return null;
        }
        
        if(c == null) {
            return "{}";
        }
        
        boolean first = true;
        
        StringBuilder builder = new StringBuilder();
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
                    // primitive boolean getters have is as prefix
                    if("boolean".equals(field.getType().toString())) {
                        methodName = createIs(field.getName());
                    } else {
                        methodName = createGetter(field.getName());
                    }

                    try {
                        Method method = c.getClass().getMethod(methodName, (Class[])null);
                        if(method == null) {
                            
                        }
                        
                        Object result = method.invoke(c, (Object[])null);
                        
                        encodeString(field.getName(), builder);
                        builder.append(":");
                        
                        encode(builder, result);
                    } catch (SecurityException e) {
                        throw new JSONException(e);
                    } catch (NoSuchMethodException e) {
                        throw new JSONException(e);
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

    /**
     * @param builder
     * @param result
     * @throws JSONException
     */
    private void encode(StringBuilder builder, Object result) throws JSONException {
        if(result.getClass().isAssignableFrom(String.class)) {
            encodeString(((String)result), builder);
        } else if(result.getClass().isAssignableFrom(Integer.class)) {
            encodeInteger((Integer)result, builder);
        } else if(result.getClass().isAssignableFrom(Boolean.class)) {
            encodeBoolean((Boolean)result, builder);
        } else if(result.getClass().isAssignableFrom(List.class)) {
            builder.append("null");
        } else if(result.getClass().isAssignableFrom(Map.class)) {
            builder.append("null");
        } else {
            builder.append(encodeObject(result));
        }
    }

    private void encodeString(String string, StringBuilder result) {
        result.append("\"");
        result.append(string);
        result.append("\"");
    }

    private void encodeInteger(Integer integer, StringBuilder result) {
        result.append("");
        result.append(integer);
        result.append("");
    }
    
    private void encodeBoolean(Boolean b, StringBuilder result) {
        result.append("");
        result.append(Boolean.toString(b));
        result.append("");
    }
    
    private String createGetter(String fieldname) {
        char[] chars = fieldname.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]); 
        
        char[] result = new char[get.length + chars.length];
        System.arraycopy(get, 0, result, 0, get.length);
        System.arraycopy(chars, 0, result, get.length, chars.length);
        return String.valueOf(result);
    }
    
    private String createIs(String fieldname) {
        char[] chars = fieldname.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]); 
        
        char[] result = new char[is.length + chars.length];
        System.arraycopy(is, 0, result, 0, is.length);
        System.arraycopy(chars, 0, result, is.length, chars.length);
        return String.valueOf(result);
    }
}

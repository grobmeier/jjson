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

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.grobmeier.jjson.JSONException;

/**
 * 
 */
public class JSONAnnotationEncoder {
	// Key signs
    // TODO: use allready defined Openener/Closer Enums from basic decoder
    private static final String QUOTE = "\"";
	private static final String PRIMITIVE_BOOLEAN = "boolean";
	private static final String ARRAY_RIGHT = "]";
	private static final String ARRAY_LEFT = "[";
	private static final String BRACKET_RIGHT = "}";
	private static final String COLON = ":";
	private static final String COMMA = ",";
	private static final String EMTPY_STRING = "";
	private static final String BRACKET_LEFT = "{";

	/** Default format for dates */
	private final static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	private Map<String, SimpleDateFormat> formatterPool = new HashMap<String, SimpleDateFormat>();
	
	// Special values
    private final static String NULL = "null";
    
    public String encode(Object result) throws JSONException {
       StringBuilder builder = new StringBuilder();
       encode(result, builder, null);
       return builder.toString();
    }
    
    /**
     * @param builder
     * @param result
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
	public void encode(Object result, StringBuilder builder, JSON annotation) throws JSONException {
    	if(result == null) {
    		builder.append(NULL);
    	} else if(result.getClass().isAssignableFrom(String.class)) {
            encodeString(((String)result), builder);
        } else if(result.getClass().isAssignableFrom(Integer.class)) {
            encodeInteger((Integer)result, builder);
        } else if(result.getClass().isAssignableFrom(Boolean.class)) {
            encodeBoolean((Boolean)result, builder);
        } else if(hasInterface(result, List.class)) {
        	encodeList((List<Object>)result, builder);
        } else if(hasInterface(result, Map.class)) {
        	encodeMap((Map<Object, Object>)result, builder);
        } else if(result.getClass().isAssignableFrom(Date.class)) {
        	encodeDate((Date)result, builder, annotation);
        } else if(result.getClass().isArray()) {
        	encodeArray(result, builder);
        } else {
            encodeObject(result, builder);
        }
    }
    
    private void encodeArray(Object result, StringBuilder builder) throws JSONException {
    	builder.append(ARRAY_LEFT);
    	int length = Array.getLength(result);
    	for (int i = 0; i < length; i++) {
    		if(i > 0) {
                builder.append(COMMA);
            }
			encode(Array.get(result, i), builder, null);
		}
    	builder.append(ARRAY_RIGHT);
	}

	private void encodeDate(Date result, StringBuilder builder, JSON annotation) throws JSONException {
    	String customFormat = annotation.dateFormat();
    	if(customFormat != null && !"".equals(customFormat)) {
    		SimpleDateFormat format = formatterPool.get(customFormat);
    		if(format == null) {
    			format = new SimpleDateFormat(customFormat);
    			formatterPool.put(customFormat, format);
    		}
    		encodeString(format.format(result), builder);
    	} else {
    		encodeString(DEFAULT_FORMAT.format(result), builder);
    	}
    	
    }
    
    private void encodeMap(Map<Object, Object> result, StringBuilder builder) throws JSONException {
    	boolean first = true;
    	builder.append(BRACKET_LEFT);
    	Set<Entry<Object, Object>> entries = result.entrySet();
    	for (Iterator<Entry<Object, Object>> iterator = entries.iterator(); iterator.hasNext();) {
    		if(!first) {
                builder.append(COMMA);
            } else {
                first = false;
            }
			Entry<Object, Object> entry = (Entry<Object, Object>) iterator.next();
			encodeString(entry.getKey().toString(), builder);
			builder.append(COLON);
			encode(entry.getValue(), builder,null);
		}
    	builder.append(BRACKET_RIGHT);
    }
    
    private void encodeList(List<Object> result, StringBuilder builder) throws JSONException {
    	boolean first = true;
    	builder.append(ARRAY_LEFT);
    	for (Iterator<Object> iterator = result.iterator(); iterator.hasNext();) {
    		 if(!first) {
                 builder.append(COMMA);
             } else {
                 first = false;
             }
    		 
			Object object = iterator.next();
			encode(object, builder, null);
		}
    	builder.append(ARRAY_RIGHT);
	}

	private String encodeObject(Object c, StringBuilder builder) throws JSONException {
		if(c == null) {
			return NULL;
		}
		
        if(c.getClass().getAnnotation(JSON.class) == null) {
            return null;
        }
        
        builder.append(BRACKET_LEFT);
        
        serializeFields(c, builder);
        serializeMethods(c, builder);
        builder.append(BRACKET_RIGHT);
        return builder.toString();
    }

	private void serializeFields(Object c, StringBuilder builder)
			throws JSONException {
		boolean first = true;
        Field[] fields = c.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] anons = field.getAnnotations();
            for (Annotation annotation : anons) {
                if(annotation.annotationType().isAssignableFrom(JSON.class)) {
                    if(!first) {
                        builder.append(COMMA);
                    } else {
                        first = false;
                    }
                    
                    String methodName = null;
                    // primitive boolean getters have is as prefix
                    // Use class.getComponentType instead of this
                    if(PRIMITIVE_BOOLEAN.equals(field.getType().toString())) {
                        methodName = JSONReflectionUtils.createGetter(field.getName(), JSONReflectionUtils.IS);
                    } else {
                        methodName = JSONReflectionUtils.createGetter(field.getName(), JSONReflectionUtils.GET);
                    }

                    try {
                        Method method = c.getClass().getMethod(methodName, (Class[])null);
                        Object result = method.invoke(c, (Object[])null);
                        
                        encodeString(field.getName(), builder);
                        builder.append(COLON);
                        encode(result, builder,(JSON)annotation);
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
	}
	
	private void serializeMethods(Object c, StringBuilder builder) throws JSONException {
		boolean first = true;
		Method[] methods = c.getClass().getDeclaredMethods();
		for (Method method : methods) {
		    Annotation[] anons = method.getAnnotations();
		    for (Annotation annotation : anons) {
		        if(annotation.annotationType().isAssignableFrom(JSON.class)) {
		            if(!first) {
		                builder.append(COMMA);
		            } else {
		                first = false;
		            }
		           
		            try {
		                Object result = method.invoke(c, (Object[])null);
		                String name = method.getName();
		                if(name.startsWith("is")) {
		                	name = name.replaceFirst("is", "");
		                	name = name.substring(0, 1).toLowerCase() + name.substring(1);
		                }
		                if(name.startsWith("get")) {
		                	name = name.replaceFirst("get", "");
		                	name = name.substring(0, 1).toLowerCase() + name.substring(1);
		                }
		                encodeString(name, builder);
		                builder.append(COLON);
		                encode(result, builder, (JSON)annotation);
		            } catch (SecurityException e) {
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
	}

    private void encodeString(String string, StringBuilder result) {
    	if(string == null) {
            result.append(NULL);
        } else {
        	result.append(QUOTE);
        	result.append(string);
        	result.append(QUOTE);
        }
    }

    private void encodeInteger(Integer integer, StringBuilder result) {
    	if(integer == null) {
            result.append(NULL);
        } else {
			result.append(EMTPY_STRING);
	        result.append(integer);
	        result.append(EMTPY_STRING);
        }
    }
    
    private void encodeBoolean(Boolean b, StringBuilder result) {
    	if(b == null) {
            result.append(NULL);
    	} else {
	        result.append(EMTPY_STRING);
	        result.append(Boolean.toString(b));
	        result.append(EMTPY_STRING);
    	}
    }
    
	private boolean hasInterface(Object target, Class<?> interfaceClass) {
		Class<?>[] interfaces = target.getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			if(interfaceClass.getName().equals(interfaces[i].getName())) {
				return true;
			}
		}
		return false;
    }
    
    
}

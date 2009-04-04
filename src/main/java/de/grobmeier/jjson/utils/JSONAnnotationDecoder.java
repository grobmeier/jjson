package de.grobmeier.jjson.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.grobmeier.jjson.JSONBoolean;
import de.grobmeier.jjson.JSONException;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import de.grobmeier.jjson.JSONValue;

public class JSONAnnotationDecoder {
    private static final String PRIMITIVE_BOOLEAN = "boolean";
    
    public <T> T decode(Class<T> t, final String json) throws JSONException {
        JSONDecoder decoder = new JSONDecoder(json);
        JSONValue value = decoder.decode();
        JSONObject source = null;
        if(!(value instanceof JSONObject)) {
            throw new JSONException("Decoder expected JSON Object String");
        } else {
            source = (JSONObject)value;
        }
        
        T result = null;
        try {
            result = t.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        mapJSONObjectToObject(source, result);
        return result;
    }
    
    private void mapJSONObjectToObject(JSONObject source, Object target) throws JSONException {
        Map<String,JSONValue> values = source.getValue();
        Set<Entry<String, JSONValue>> set = values.entrySet();

        for (Entry<String, JSONValue> entry : set) {
            String methodName = JSONReflectionUtils.createSetter(entry.getKey());;
            JSONValue v = entry.getValue();
            try {
                if(v instanceof JSONString) {
                    Method method = target.getClass().getMethod(methodName,String.class);
                    method.invoke(target, ((JSONString)v).getValue().toString());
                } else if(v instanceof JSONBoolean) {
                    Method methodInvoke = null;
                    Method[] methods = target.getClass().getMethods();
                    for (Method method : methods) {
                        if(method.getName().equals(methodName)) {
                            Class[] params = method.getParameterTypes();
                            if(params.length != 1) {
                                throw new JSONException("Parameters do not follow POJO bean conventions");
                            } else {
                                if(Boolean.TYPE.getName().equals(params[0].getName())) {
                                    methodInvoke = target.getClass().getMethod(methodName,Boolean.TYPE);
                                } else {
                                    methodInvoke = target.getClass().getMethod(methodName,Boolean.class);
                                }
                            }
                        }
                    }
                    
                    methodInvoke.invoke(target, ((JSONBoolean)v).getValue());                    
                }
            } catch (SecurityException e) {
                throw new JSONException("Could not access POJO setter: " + methodName, e);
            } catch (NoSuchMethodException e) {
                throw new JSONException("Could not find POJO setter: " + methodName, e);
            } catch (IllegalArgumentException e) {
                throw new JSONException("Error putting arguments.", e);
            } catch (IllegalAccessException e) {
                throw new JSONException("Could not put argument at: " + methodName, e);
            } catch (InvocationTargetException e) {
                throw new JSONException("Could not work on object while accessing: " + methodName, e);
            }
        }
    }
}

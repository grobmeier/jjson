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

    public <T> T decode(Class<T> t, final String json) throws JSONException {
        JSONDecoder decoder = new JSONDecoder(json);
        JSONValue value = decoder.decode();
        JSONObject source = null;
        if (!(value instanceof JSONObject)) {
            throw new JSONException("Decoder expected JSON Object String");
        } else {
            source = (JSONObject) value;
        }

        T result = null;
        try {
            result = t.newInstance();
        } catch (InstantiationException e) {
            throw new JSONException("Could not instantiate class: " + t, e);
        } catch (IllegalAccessException e) {
            throw new JSONException("Could not access class: " + t, e);
        }

        putObject(result, source);
        return result;
    }

    private void putObject(Object target, JSONObject source) throws JSONException {
        Map<String, JSONValue> values = source.getValue();
        Set<Entry<String, JSONValue>> set = values.entrySet();

        for (Entry<String, JSONValue> entry : set) {
            System.out.println(entry.getKey());
            JSONValue v = entry.getValue();
            putValue(target, JSONReflectionUtils.createSetter(entry.getKey()), v);
        }
    }

    /**
     * @param target
     * @param methodName
     * @param value
     * @throws JSONException
     */
    private void putValue(Object target, String methodName, JSONValue value) throws JSONException {
        try {
            if (value instanceof JSONString) {
                putStringValue(target, methodName, value);
            } else if (value instanceof JSONBoolean) {
                putBooleanValue(target, methodName, value);
            } else if (value instanceof JSONObject) {
                putObjectValue(target, methodName, value);
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
        } catch (ClassNotFoundException e) {
            throw new JSONException("Could not find class for: " + methodName, e);
        } catch (InstantiationException e) {
            throw new JSONException("Could not instantiate class for: " + methodName, e);
        }
    }

    private void putObjectValue(Object target, String methodName, JSONValue value)
            throws JSONException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Method methodInvoke = null;
        Method[] methods = target.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length != 1) {
                    throw new JSONException("Parameters do not follow POJO bean conventions");
                } else {
                    Class<?> clazz = Class.forName(params[0].getName());
                    Object o = clazz.newInstance();
                    putObject(o, (JSONObject)value);

                    methodInvoke = target.getClass().getMethod(methodName, clazz);
                    methodInvoke.invoke(target, o);
                }
            }
        }
    }

    /**
     * @param target
     * @param methodName
     * @param value
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void putStringValue(Object target, String methodName, JSONValue value)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = target.getClass().getMethod(methodName, String.class);
        method.invoke(target, ((JSONString) value).getValue().toString());
    }

    /**
     * @param target
     * @param methodName
     * @param value
     * @throws JSONException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void putBooleanValue(Object target, String methodName, JSONValue value)
            throws JSONException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        Method methodInvoke = null;
        Method[] methods = target.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length != 1) {
                    throw new JSONException("Parameters do not follow POJO bean conventions");
                } else {
                    if (Boolean.TYPE.getName().equals(params[0].getName())) {
                        methodInvoke = target.getClass().getMethod(methodName, Boolean.TYPE);
                    } else {
                        methodInvoke = target.getClass().getMethod(methodName, Boolean.class);
                    }
                }
            }
        }
        methodInvoke.invoke(target, ((JSONBoolean) value).getValue());
    }
}

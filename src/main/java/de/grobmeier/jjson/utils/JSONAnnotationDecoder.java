package de.grobmeier.jjson.utils;

public class JSONAnnotationDecoder {
    
    public <T> T decode(Class<T> t, final String json) {
        T result = null;
        try {
            result = t.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
    
}

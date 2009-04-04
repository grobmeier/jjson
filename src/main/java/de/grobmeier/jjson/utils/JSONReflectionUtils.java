package de.grobmeier.jjson.utils;

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

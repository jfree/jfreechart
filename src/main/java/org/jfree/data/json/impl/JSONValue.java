/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * JSON.simple
 * -----------
 * The code in this file originates from the JSON.simple project by 
 * FangYidong<fangyidong@yahoo.com.cn>:
 * 
 *     https://code.google.com/p/json-simple/
 *  
 * which is licensed under the Apache Software License version 2.0.  
 * 
 * It has been modified locally and repackaged under 
 * org.jfree.data.json.impl.* to avoid conflicts with any other version that
 * may be present on the classpath.
 * 
 */

package org.jfree.data.json.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for JSON values.
 * <br><br>
 * This class is for internal use by JFreeChart, it is not 
 * part of the supported API and you should not call it directly.  If you need
 * JSON support in your project you should include JSON.simple 
 * (https://code.google.com/p/json-simple/) or some other JSON library directly
 * in your project.
 */
public class JSONValue {
    
//    /**
//     * Parse JSON text into java object from the input source. 
//     * Please use parseWithException() if you don't want to ignore the 
//     * exception.
//     * 
//     * @see com.orsoncharts.util.json.parser.JSONParser#parse(Reader)
//     * @see #parseWithException(Reader)
//     * 
//     * @param in  the input reader.
//     * @return Instance of the following:
//     *     com.orsoncharts.util.json.JSONObject,
//     *     com.orsoncharts.util.json.JSONArray,
//     *     java.lang.String,
//     *     java.lang.Number,
//     *     java.lang.Boolean,
//     *     null
//     */
//    public static Object parse(Reader in){
//        try {
//            JSONParser parser = new JSONParser();
//            return parser.parse(in);
//        }
//        catch (Exception e) {
//            return null;
//        }
//    }
//    
//    /**
//     * Parses an object from a string.
//     * 
//     * @param s  the string.
//     * 
//     * @return An object. 
//     */
//    public static Object parse(String s){
//        StringReader in = new StringReader(s);
//        return parse(in);
//    }
//    
//    /**
//     * Parse JSON text into java object from the input source.
//     * 
//     * @see com.orsoncharts.util.json.parser.JSONParser
//     * 
//     * @param in  the input reader ({@code null} not permitted).
//     * 
//     * @return Instance of the following:
//     *     com.orsoncharts.util.json.JSONObject,
//     *     com.orsoncharts.util.json.JSONArray,
//     *     java.lang.String,
//     *     java.lang.Number,
//     *     java.lang.Boolean,
//     *     null
//     * 
//     * @throws IOException if there is an I/O problem.
//     * @throws ParseException if there is a parsing problem.
//     */
//    public static Object parseWithException(Reader in) throws IOException, 
//            ParseException{
//        JSONParser parser = new JSONParser();
//        return parser.parse(in);
//    }
//    
//    /**
//     * Parses an object from a JSON string.
//     * 
//     * @param s  the string.
//     * 
//     * @return An object.
//     * 
//     * @throws ParseException if there is a parsing problem. 
//     */
//    public static Object parseWithException(String s) throws ParseException{
//        JSONParser parser = new JSONParser();
//        return parser.parse(s);
//    }
    
    /**
     * Encode an object into JSON text and write it to out.
     * <p>
     * If this object is a {@code Map} or a {@code List}, and it's 
     * also a {@link JSONStreamAware} or a  {@link JSONAware}, 
     * {@code JSONStreamAware} or {@code JSONAware} will be 
     * considered firstly.
     * <p>
     * DO NOT call this method from writeJSONString(Writer) of a class that 
     * implements both JSONStreamAware and (Map or List) with 
     * "this" as the first parameter, use JSONObject.writeJSONString(Map, 
     * Writer) or JSONArray.writeJSONString(List, Writer) instead. 
     * 
     * @see org.jfree.data.json.impl.JSONObject#writeJSONString(Map, Writer)
     * @see org.jfree.data.json.impl.JSONArray#writeJSONString(List, Writer)
     * 
     * @param value  the value.
     * @param out  the output writer.
     * @throws IOException if there is an I/O problem.  
     */
    public static void writeJSONString(Object value, Writer out) 
            throws IOException {
        if (value == null) {
            out.write("null");
            return;
        }
        
        if (value instanceof String) {        
            out.write('\"');
            out.write(escape((String) value));
            out.write('\"');
            return;
        }
        
        if (value instanceof Double) {
            if(((Double) value).isInfinite() || ((Double) value).isNaN()) {
                out.write("null");
            }
            else {
                out.write(value.toString());
            }
            return;
        }
        
        if (value instanceof Float) {
            if (((Float) value).isInfinite() || ((Float) value).isNaN()) {
                out.write("null");
            }
            else {
                out.write(value.toString());
            }
            return;
        }        
        
        if (value instanceof Number) {
            out.write(value.toString());
            return;
        }
        
        if (value instanceof Boolean) {
            out.write(value.toString());
            return;
        }
        
        if ((value instanceof JSONStreamAware)) {
            ((JSONStreamAware) value).writeJSONString(out);
            return;
        }
        
        if ((value instanceof JSONAware)) {
            out.write(((JSONAware) value).toJSONString());
            return;
        }
        
        if (value instanceof Map) {
            JSONObject.writeJSONString((Map) value, out);
            return;
        }
        
        if (value instanceof List) {
            JSONArray.writeJSONString((List) value, out);
            return;
        }
        
        out.write(value.toString());
    }

    /**
     * Convert an object to JSON text.
     * <p>
     * If this object is a Map or a List, and it's also a JSONAware, JSONAware 
     * will be considered firstly.
     * <p>
     * DO NOT call this method from toJSONString() of a class that implements 
     * both JSONAware and Map or List with 
     * "this" as the parameter, use JSONObject.toJSONString(Map) or 
     * JSONArray.toJSONString(List) instead. 
     * 
     * @see org.jfree.data.json.impl.JSONObject#toJSONString(Map)
     * @see org.jfree.data.json.impl.JSONArray#toJSONString(List)
     * 
     * @param value the value.
     * @return JSON text, or "null" if value is null or it's an NaN or an INF 
     * number.
     */
    public static String toJSONString(Object value){
        if (value == null) {
            return "null";
        }
        
        if (value instanceof String) {
            return "\"" + escape((String) value) + "\"";
        }
        
        if (value instanceof Double){
            if(((Double) value).isInfinite() || ((Double) value).isNaN()) {
                return "null";
            }
            else {
                return value.toString();
            }
        }
        
        if (value instanceof Float) {
            if (((Float) value).isInfinite() || ((Float) value).isNaN()) {
                return "null";
            }
            else {
                return value.toString();
            }
        }        
        
        if (value instanceof Number) {
            return value.toString();
        }
        
        if (value instanceof Boolean) {
            return value.toString();
        }
        
        if ((value instanceof JSONAware)) {
            return ((JSONAware) value).toJSONString();
        }
        
        if (value instanceof Map) {
            return JSONObject.toJSONString((Map) value);
        }
        
        if (value instanceof List) {
            return JSONArray.toJSONString((List) value);
        }
        
        return value.toString();
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters 
     * (U+0000 through U+001F).
     * 
     * @param s  the string to be escaped ({@code null} permitted).
     * 
     * @return A string.
     */
    public static String escape(String s) {
        if (s == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        escape(s, sb);
        return sb.toString();
    }

    /**
     * @param s - Must not be null.
     * @param sb
     */
    static void escape(String s, StringBuffer sb) {
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch(ch){
            case '"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '/':
                sb.append("\\/");
                break;
            default:
                //Reference: http://www.unicode.org/versions/Unicode5.1.0/
                if ((ch >= '\u0000' && ch <= '\u001F') 
                        || (ch >= '\u007F' && ch <= '\u009F') 
                        || (ch >= '\u2000' && ch <= '\u20FF')) {
                    String ss = Integer.toHexString(ch);
                    sb.append("\\u");
                    for (int k = 0; k < 4 - ss.length(); k++) {
                        sb.append('0');
                    }
                    sb.append(ss.toUpperCase());
                }
                else {
                    sb.append(ch);
                }
            }
        }//for
    }

}

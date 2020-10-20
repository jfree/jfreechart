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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A JSON object. Key value pairs are unordered.
 * <br><br>
 * This class is for internal use by JFreeChart, it is not 
 * part of the supported API and you should not call it directly.  If you need
 * JSON support in your project you should include JSON.simple 
 * (https://code.google.com/p/json-simple/) or some other JSON library directly
 * in your project.
 */
public class JSONObject extends HashMap implements Map, JSONAware, 
        JSONStreamAware {
    
    private static final long serialVersionUID = -503443796854799292L;

    /**
     * Encode a map into JSON text and write it to out.
     * If this map is also a {@link JSONAware} or {@link JSONStreamAware}, 
     * {@code JSONAware} or {@code JSONStreamAware} specific 
     * behaviours will be ignored at this top level.
     * 
     * @see org.jfree.data.json.impl.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param map  the map to write ({@code null} permitted).
     * @param out  the output writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    public static void writeJSONString(Map map, Writer out) throws IOException {
        if (map == null) {
            out.write("null");
            return;
        }
        boolean first = true;
        Iterator iter = map.entrySet().iterator();
        out.write('{');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                out.write(',');
            }
            Map.Entry entry = (Map.Entry) iter.next();
            out.write('\"');
            out.write(JSONValue.escape(String.valueOf(entry.getKey())));
            out.write('\"');
            out.write(':');
            JSONValue.writeJSONString(entry.getValue(), out);
        }
        out.write('}');
    }

    /**
     * Writes a JSON string representing this object instance to the specified
     * output writer.
     * 
     * @param out  the output writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    @Override
    public void writeJSONString(Writer out) throws IOException {
        writeJSONString(this, out);
    }
    
    /**
     * Convert a map to JSON text. The result is a JSON object. 
     * If this map is also a {@link JSONAware}, {@code JSONAware} specific 
     * behaviours will be omitted at this top level.
     * 
     * @see org.jfree.data.json.impl.JSONValue#toJSONString(Object)
     * 
     * @param map  the map ({@code null} permitted).
     * 
     * @return JSON text, or "null" if map is null.
     */
    public static String toJSONString(Map map){
        if (map == null) {
            return "null";
        }
        
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        Iterator iter = map.entrySet().iterator();
        
        sb.append('{');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                sb.append(',');
            }
            
            Map.Entry entry = (Map.Entry) iter.next();
            toJSONString(String.valueOf(entry.getKey()), entry.getValue(), sb);
        }
        sb.append('}');
        return sb.toString();
    }
    
    /**
     * Returns a JSON string representing this object.
     * 
     * @return A JSON string. 
     */
    @Override
    public String toJSONString(){
        return toJSONString(this);
    }
    
    /**
     * Writes a key and value to a JSON string.
     * 
     * @param key  the key ({@code null} permitted).
     * @param value  the value ({@code null} permitted).
     * @param sb  a string buffer ({@code null} not permitted).
     * 
     * @return A JSON string fragment representing the key and value. 
     */
    private static String toJSONString(String key, Object value, 
            StringBuffer sb) {
        sb.append('\"');
        if (key == null) {
            sb.append("null");
        }
        else {
            JSONValue.escape(key, sb);
        }
        sb.append('\"').append(':');
        
        sb.append(JSONValue.toJSONString(value));
        
        return sb.toString();
    }
    
    /**
     * Returns a string representation of this object.
     * 
     * @return A string. 
     */
    @Override
    public String toString(){
        return toJSONString();
    }

    /**
     * Returns a JSON string fragment containing the key and value.
     * 
     * @param key  the key ({@code null} permitted).
     * @param value  the value ({@code null} permitted).
     * 
     * @return A JSON string fragment. 
     */
    public static String toString(String key, Object value){
        StringBuffer sb = new StringBuffer();
        toJSONString(key, value, sb);
        return sb.toString();
    }
}


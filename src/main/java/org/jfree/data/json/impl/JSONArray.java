/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A JSON array.  
 * <br><br>
 * This class is for internal use by JFreeChart, it is not 
 * part of the supported API and you should not call it directly.  If you need
 * JSON support in your project you should include JSON.simple 
 * (https://code.google.com/p/json-simple/) or some other JSON library directly
 * in your project.
 */
public class JSONArray extends ArrayList implements List, JSONAware, 
        JSONStreamAware {

    private static final long serialVersionUID = 3957988303675231981L;

    /**
     * Encode a list into JSON text and write it to out. 
     * If this list is also a {@link JSONStreamAware} or a {@link JSONAware}, 
     * {@code JSONStreamAware} and {@code JSONAware} specific 
     * behaviours will be ignored at this top level.
     * 
     * @see org.jfree.data.json.impl.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param list  the list ({@code null} permitted).
     * @param out  the output writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    public static void writeJSONString(List list, Writer out) 
            throws IOException {
        if (list == null) {
            out.write("null");
            return;
        }
        
        boolean first = true;
        Iterator iter = list.iterator();
        out.write('[');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                out.write(',');
            }
            
            Object value = iter.next();
            if (value == null) {
                out.write("null");
                continue;
            }
            JSONValue.writeJSONString(value, out);
        }
        out.write(']');
    }
    
    /**
     * Writes this array to the specified output writer.
     * 
     * @param out  the output writer ({@code null} not permitted).
     * 
     * @throws IOException  if there is an I/O problem.
     */
    @Override
    public void writeJSONString(Writer out) throws IOException {
        writeJSONString(this, out);
    }
    
    /**
     * Convert a list to JSON text. The result is a JSON array. 
     * If this list is also a {@link JSONAware}, {@link JSONAware} specific 
     * behaviours will be omitted at this top level.
     * 
     * @see org.jfree.data.json.impl.JSONValue#toJSONString(Object)
     * 
     * @param list  the list ({@code null} permitted).
     * 
     * @return JSON text, or "null" if list is null.
     */
    public static String toJSONString(List list){
        if (list == null) {
            return "null";
        }
        
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        Iterator iter = list.iterator();
        sb.append('[');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                sb.append(',');
            }
            
            Object value = iter.next();
            if (value == null) {
                sb.append("null");
                continue;
            }
            sb.append(JSONValue.toJSONString(value));
        }
        sb.append(']');
        return sb.toString();
    }

    /**
     * Returns a JSON string representation of this list.
     * 
     * @return A string. 
     */
    @Override
    public String toJSONString(){
        return toJSONString(this);
    }
    
    /**
     * Returns a string representation of this list.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return toJSONString();
    }
    
}

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ----------------
 * ExportUtils.java
 * ----------------
 * (C) Copyright 2014-2020 by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.util;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;
import org.jfree.chart.ui.Drawable;

/**
 * Utility functions for exporting charts to SVG and PDF format.
 * 
 * @since 1.0.18
 */
public class ExportUtils {

    /**
     * Returns {@code true} if JFreeSVG is on the classpath, and 
     * {@code false} otherwise.  The JFreeSVG library can be found at
     * http://www.jfree.org/jfreesvg/
     * 
     * @return A boolean.
     */
    public static boolean isJFreeSVGAvailable() {
        Class<?> svgClass = null;
        try {
            svgClass = Class.forName("org.jfree.graphics2d.svg.SVGGraphics2D");
        } catch (ClassNotFoundException e) {
            // svgClass will be null so the function will return false
        }
        return (svgClass != null);
    }

    /**
     * Returns {@code true} if OrsonPDF is on the classpath, and 
     * {@code false} otherwise.  The OrsonPDF library can be found at
     * http://www.object-refinery.com/orsonpdf/
     * 
     * @return A boolean.
     */
    public static boolean isOrsonPDFAvailable() {
        Class<?> pdfDocumentClass = null;
        try {
            pdfDocumentClass = Class.forName("com.orsonpdf.PDFDocument");
        } catch (ClassNotFoundException e) {
            // pdfDocumentClass will be null so the function will return false
        }
        return (pdfDocumentClass != null);
    }

    /**
     * Writes the current content to the specified file in SVG format.  This 
     * will only work when the JFreeSVG library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * JFreeSVG.
     * 
     * @param drawable  the drawable ({@code null} not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * @param file  the output file ({@code null} not permitted).
     */
    public static void writeAsSVG(Drawable drawable, int w, int h, 
            File file) {
        if (!ExportUtils.isJFreeSVGAvailable()) {
            throw new IllegalStateException(
                    "JFreeSVG is not present on the classpath.");
        }
        Args.nullNotPermitted(drawable, "drawable");
        Args.nullNotPermitted(file, "file");
        try {
            Class<?> svg2Class = Class.forName(
                    "org.jfree.graphics2d.svg.SVGGraphics2D");
            Constructor<?> c1 = svg2Class.getConstructor(int.class, int.class);
            Graphics2D svg2 = (Graphics2D) c1.newInstance(w, h);
            Rectangle2D drawArea = new Rectangle2D.Double(0, 0, w, h);
            drawable.draw(svg2, drawArea);
            Class<?> svgUtilsClass = Class.forName(
                    "org.jfree.graphics2d.svg.SVGUtils");
            Method m1 = svg2Class.getMethod("getSVGElement", (Class[]) null);
            String element = (String) m1.invoke(svg2, (Object[]) null);
            Method m2 = svgUtilsClass.getMethod("writeToSVG", File.class, 
                    String.class);
            m2.invoke(svgUtilsClass, file, element);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Writes a {@link Drawable} to the specified file in PDF format.  This 
     * will only work when the OrsonPDF library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * OrsonPDF.
     * 
     * @param drawable  the drawable ({@code null} not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * @param file  the output file ({@code null} not permitted).
     */
    public static final void writeAsPDF(Drawable drawable, 
            int w, int h, File file) {
        if (!ExportUtils.isOrsonPDFAvailable()) {
            throw new IllegalStateException(
                    "OrsonPDF is not present on the classpath.");
        }
        Args.nullNotPermitted(drawable, "drawable");
        Args.nullNotPermitted(file, "file");
        try {
            Class<?> pdfDocClass = Class.forName("com.orsonpdf.PDFDocument");
            Object pdfDoc = pdfDocClass.newInstance();
            Method m = pdfDocClass.getMethod("createPage", Rectangle2D.class);
            Rectangle2D rect = new Rectangle(w, h);
            Object page = m.invoke(pdfDoc, rect);
            Method m2 = page.getClass().getMethod("getGraphics2D");
            Graphics2D g2 = (Graphics2D) m2.invoke(page);
            Rectangle2D drawArea = new Rectangle2D.Double(0, 0, w, h);
            drawable.draw(g2, drawArea);
            Method m3 = pdfDocClass.getMethod("writeToFile", File.class);
            m3.invoke(pdfDoc, file);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Writes the current content to the specified file in PNG format.
     * 
     * @param drawable  the drawable ({@code null} not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * @param file  the output file ({@code null} not permitted).
     * 
     * @throws FileNotFoundException if the file is not found.
     * @throws IOException if there is an I/O problem.
     */
    public static void writeAsPNG(Drawable drawable, int w, int h, 
            File file) throws FileNotFoundException, IOException {
        BufferedImage image = new BufferedImage(w, h, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        drawable.draw(g2, new Rectangle(w, h));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            ImageIO.write(image, "png", out);
        }
        finally {
            out.close();
        }
    }

    /**
     * Writes the current content to the specified file in JPEG format.
     * 
     * @param drawable  the drawable ({@code null} not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * @param file  the output file ({@code null} not permitted).
     * 
     * @throws FileNotFoundException if the file is not found.
     * @throws IOException if there is an I/O problem.
     */
    public static void writeAsJPEG(Drawable drawable, int w, int h, 
            File file) throws FileNotFoundException, IOException {
        BufferedImage image = new BufferedImage(w, h, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        drawable.draw(g2, new Rectangle(w, h));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            ImageIO.write(image, "jpg", out);
        }
        finally {
            out.close();
        }
    }
 
}

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * ---------------------------------
 * StandardCategoryURLGenerator.java
 * ---------------------------------
 * (C) Copyright 2002-2014, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributors:     David Gilbert (for Object Refinery Limited);
 *                   Cleland Early;
 *
 * Changes:
 * --------
 * 05-Aug-2002 : Version 1, contributed by Richard Atkinson;
 * 29-Aug-2002 : Reversed seriesParameterName and itemParameterName in
 *               constructor.  Never should have been the other way round.
 *               Also updated JavaDoc (RA);
 * 09-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 05-Nov-2002 : Base dataset is now TableDataset not CategoryDataset (DG);
 * 23-Mar-2003 : Implemented Serializable (DG);
 * 13-Aug-2003 : Implemented Cloneable (DG);
 * 23-Dec-2003 : Added fix for bug 861282 (DG);
 * 21-May-2004 : Added URL encoding - see patch 947854 (DG);
 * 13-Jan-2004 : Fixed for compliance with XHTML 1.0 (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 02-Feb-2007 : Removed author tags from all over JFreeChart sources (DG);
 * 17-Apr-2007 : Use new URLUtilities class to encode URLs (DG);
 * 02-Jul-2013 : Use ParamChecks (DG);
 *
 */

package org.jfree.chart.urls;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.jfree.chart.util.ParamChecks;

import org.jfree.data.category.CategoryDataset;
import org.jfree.util.ObjectUtilities;

/**
 * A URL generator that can be assigned to a
 * {@link org.jfree.chart.renderer.category.CategoryItemRenderer}.
 */
public class StandardCategoryURLGenerator implements CategoryURLGenerator,
        Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2276668053074881909L;

    /** Prefix to the URL */
    private String prefix = "index.html";

    /** Series parameter name to go in each URL */
    private String seriesParameterName = "series";

    /** Category parameter name to go in each URL */
    private String categoryParameterName = "category";

    /**
     * Creates a new generator with default settings.
     */
    public StandardCategoryURLGenerator() {
        super();
    }

    /**
     * Constructor that overrides default prefix to the URL.
     *
     * @param prefix  the prefix to the URL (<code>null</code> not permitted).
     */
    public StandardCategoryURLGenerator(String prefix) {
        ParamChecks.nullNotPermitted(prefix, "prefix");
        this.prefix = prefix;
    }

    /**
     * Constructor that overrides all the defaults.
     *
     * @param prefix  the prefix to the URL (<code>null</code> not permitted).
     * @param seriesParameterName  the name of the series parameter to go in
     *                             each URL (<code>null</code> not permitted).
     * @param categoryParameterName  the name of the category parameter to go in
     *                               each URL (<code>null</code> not permitted).
     */
    public StandardCategoryURLGenerator(String prefix, 
            String seriesParameterName, String categoryParameterName) {

        ParamChecks.nullNotPermitted(prefix, "prefix");
        ParamChecks.nullNotPermitted(seriesParameterName, 
                "seriesParameterName");
        ParamChecks.nullNotPermitted(categoryParameterName, 
                "categoryParameterName");
        this.prefix = prefix;
        this.seriesParameterName = seriesParameterName;
        this.categoryParameterName = categoryParameterName;

    }

    /**
     * Generates a URL for a particular item within a series.
     *
     * @param dataset  the dataset.
     * @param series  the series index (zero-based).
     * @param category  the category index (zero-based).
     *
     * @return The generated URL.
     */
    @Override
    public String generateURL(CategoryDataset dataset, int series, 
            int category) {
        String url = this.prefix;
        Comparable seriesKey = dataset.getRowKey(series);
        Comparable categoryKey = dataset.getColumnKey(category);
        boolean firstParameter = !url.contains("?");
        url += firstParameter ? "?" : "&amp;";
        try {
            url += this.seriesParameterName + "=" + URLEncoder.encode(
                    seriesKey.toString(), "UTF-8");
            url += "&amp;" + this.categoryParameterName + "="
                    + URLEncoder.encode(categoryKey.toString(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex); // this won't happen :)
        }
        return url;
    }

    /**
     * Returns an independent copy of the URL generator.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException not thrown by this class, but
     *         subclasses (if any) might.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        // all attributes are immutable, so we can just return the super.clone()
        // FIXME: in fact, the generator itself is immutable, so cloning is
        // not necessary
        return super.clone();
    }

    /**
     * Tests the generator for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardCategoryURLGenerator)) {
            return false;
        }
        StandardCategoryURLGenerator that = (StandardCategoryURLGenerator) obj;
        if (!ObjectUtilities.equal(this.prefix, that.prefix)) {
            return false;
        }

        if (!ObjectUtilities.equal(this.seriesParameterName,
                that.seriesParameterName)) {
            return false;
        }
        if (!ObjectUtilities.equal(this.categoryParameterName,
                that.categoryParameterName)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result;
        result = (this.prefix != null ? this.prefix.hashCode() : 0);
        result = 29 * result
            + (this.seriesParameterName != null
                    ? this.seriesParameterName.hashCode() : 0);
        result = 29 * result
            + (this.categoryParameterName != null
                    ? this.categoryParameterName.hashCode() : 0);
        return result;
    }

}

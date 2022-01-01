/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * (C) Copyright 2002-2021, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributors:     David Gilbert;
 *                   Cleland Early;
 *
 */

package org.jfree.chart.urls;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import org.jfree.chart.internal.Args;

import org.jfree.data.category.CategoryDataset;

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
     * @param prefix  the prefix to the URL ({@code null} not permitted).
     */
    public StandardCategoryURLGenerator(String prefix) {
        Args.nullNotPermitted(prefix, "prefix");
        this.prefix = prefix;
    }

    /**
     * Constructor that overrides all the defaults.
     *
     * @param prefix  the prefix to the URL ({@code null} not permitted).
     * @param seriesParameterName  the name of the series parameter to go in
     *                             each URL ({@code null} not permitted).
     * @param categoryParameterName  the name of the category parameter to go in
     *                               each URL ({@code null} not permitted).
     */
    public StandardCategoryURLGenerator(String prefix, 
            String seriesParameterName, String categoryParameterName) {

        Args.nullNotPermitted(prefix, "prefix");
        Args.nullNotPermitted(seriesParameterName, 
                "seriesParameterName");
        Args.nullNotPermitted(categoryParameterName, 
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
     * @param obj  the object ({@code null} permitted).
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
        if (!Objects.equals(this.prefix, that.prefix)) {
            return false;
        }

        if (!Objects.equals(this.seriesParameterName, that.seriesParameterName)) {
            return false;
        }
        if (!Objects.equals(this.categoryParameterName, that.categoryParameterName)) {
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

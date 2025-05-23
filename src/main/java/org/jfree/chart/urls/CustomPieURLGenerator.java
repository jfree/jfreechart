/* ======================================
 * JFreeChart : a free Java chart library
 * ======================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * --------------------------
 * CustomPieURLGenerator.java
 * --------------------------
 * (C) Copyright 2004-present, by David Basten and Contributors.
 *
 * Original Author:  David Basten;
 * Contributors:     -;
 *
 */

package org.jfree.chart.urls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.plot.pie.MultiplePiePlot;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.data.general.PieDataset;

/**
 * A custom URL generator for pie charts.  This implementation supports both
 * the standard {@link PiePlot} as well as {@link MultiplePiePlot}.
 *
 * @param <K> the dataset key type
 */
public class CustomPieURLGenerator<K extends Comparable<K>> implements PieURLGenerator<K>,
        Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 7100607670144900503L;

    /** Storage for the URLs - a list to support multi pie plots. */
    private final List<Map<K, String>> urlMaps;

    /**
     * Creates a new {@code CustomPieURLGenerator} instance, initially
     * empty.  Call {@link #addURLs(Map)} to specify the URL fragments to be
     * used.
     */
    public CustomPieURLGenerator() {
        this.urlMaps = new ArrayList<>();
    }

    /**
     * Generates a URL fragment.
     *
     * @param dataset  the dataset (ignored).
     * @param key  the item key.
     * @param plotIndex  the plot index.
     *
     * @return A string containing the generated URL.
     *
     * @see #getURL(Comparable, int)
     */
    @Override
    public String generateURL(PieDataset<K> dataset, K key, int plotIndex) {
        return getURL(key, plotIndex);
    }

    /**
     * Returns the number of URL maps stored by the generator.  For a standard
     * pie chart, only one map is required, but for a {@link MultiplePiePlot}
     * the generator will use multiple maps.
     *
     * @return The list count.
     *
     * @see #addURLs(Map)
     */
    public int getListCount() {
        return this.urlMaps.size();
    }

    /**
     * Returns the number of URLs in a given map (specified by its position
     * in the map list).
     *
     * @param plotIndex  the plot index (zero based).
     *
     * @return The URL count.
     *
     * @see #getListCount()
     */
    public int getURLCount(int plotIndex) {
        int result = 0;
        Map<K, String> urlMap = this.urlMaps.get(plotIndex);
        if (urlMap != null) {
            result = urlMap.size();
        }
        return result;
    }

    /**
     * Returns the URL for a section in the specified map.
     *
     * @param key  the key.
     * @param plotIndex  the plot index.
     *
     * @return The URL.
     */
    public String getURL(K key, int plotIndex) {
        String result = null;
        if (plotIndex < getListCount()) {
            Map<K, String> urlMap = this.urlMaps.get(plotIndex);
            if (urlMap != null) {
                result = (String) urlMap.get(key);
            }
        }
        return result;
    }

    /**
     * Adds a map containing {@code (key, URL)} mappings where each
     * {@code key} is an instance of {@code K}
     * (corresponding to the key for an item in a pie dataset) and each
     * {@code URL} is a {@code String} representing a URL fragment.
     * <br><br>
     * The map is appended to an internal list...you can add multiple maps
     * if you are working with, say, a {@link MultiplePiePlot}.
     *
     * @param urlMap  the URLs ({@code null} permitted).
     */
    public void addURLs(Map<K, String> urlMap) {
        this.urlMaps.add(urlMap);
    }

    /**
     * Tests if this object is equal to another.
     *
     * @param obj  the other object.
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CustomPieURLGenerator) {
            CustomPieURLGenerator<K> generator = (CustomPieURLGenerator<K>) obj;
            if (getListCount() != generator.getListCount()) {
                return false;
            }
            for (int pieItem = 0; pieItem < getListCount(); pieItem++) {
                if (getURLCount(pieItem) != generator.getURLCount(pieItem)) {
                    return false;
                }
                Set<K> keySet = this.urlMaps.get(pieItem).keySet();
                for (K key : keySet) {
                    if (!getURL(key, pieItem).equals(generator.getURL(key, pieItem))) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a clone of the generator.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        CustomPieURLGenerator<K> urlGen = new CustomPieURLGenerator<K>();
        for (Map<K, String> map : this.urlMaps) {
            urlGen.addURLs(new HashMap<>(map));
        }
        return urlGen;
    }

}

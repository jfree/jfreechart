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
 * ----------------
 * DatasetTags.java
 * ----------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Jan-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.xml;

/**
 * Constants for the tags that identify the elements in the XML files.
 */
public interface DatasetTags {

    /** The 'PieDataset' element name. */
    String PIEDATASET_TAG = "PieDataset";

    /** The 'CategoryDataset' element name. */
    String CATEGORYDATASET_TAG = "CategoryDataset";

    /** The 'Series' element name. */
    String SERIES_TAG = "Series";

    /** The 'Item' element name. */
    String ITEM_TAG = "Item";

    /** The 'Key' element name. */
    String KEY_TAG = "Key";

    /** The 'Value' element name. */
    String VALUE_TAG = "Value";

}

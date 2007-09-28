/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * --------------------------------
 * ToolTipTagFragmentGenerator.java
 * --------------------------------
 * (C) Copyright 2003-2007, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 *
 * Changes
 * -------
 * 12-Aug-2003 : Version 1 (RA);
 * 
 */
 
package org.jfree.chart.imagemap;

/**
 * Interface for generating the tooltip fragment of an HTML image map area tag.
 * The fragment should be <code>XHTML 1.0</code> compliant.
 */
public interface ToolTipTagFragmentGenerator {

    /**
     * Generates a tooltip string to go in an HTML image map.  To allow for
     * varying standards compliance among browsers, this method is expected
     * to return an 'alt' attribute IN ADDITION TO whatever it does to create
     * the tooltip (often a 'title' attribute). 
     *
     * @param toolTipText  the tooltip.
     * 
     * @return The formatted HTML area tag attribute(s).
     */
    public String generateToolTipFragment(String toolTipText);

}

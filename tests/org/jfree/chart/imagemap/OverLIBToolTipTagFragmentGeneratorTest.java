/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * -------------------------------------------
 * OverLIBToolTipTagFragmentGeneratorTest.java
 * -------------------------------------------
 * (C) Copyright 2009-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Mar-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.imagemap;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests for the {@link OverLIBToolTipTagFragmentGenerator} class.
 */
public class OverLIBToolTipTagFragmentGeneratorTest {

    /**
     * Some checks for the generateURLFragment() method.
     */
    @Test
    public void testGenerateURLFragment() {
        OverLIBToolTipTagFragmentGenerator g
                = new OverLIBToolTipTagFragmentGenerator();
        assertEquals(" onMouseOver=\"return overlib('abc');\""
                + " onMouseOut=\"return nd();\"",
                g.generateToolTipFragment("abc"));
        assertEquals(" onMouseOver=\"return overlib("
                + "'It\\'s \\\"A\\\", 100.0');\" onMouseOut=\"return nd();\"",
                g.generateToolTipFragment("It\'s \"A\", 100.0"));
    }

}

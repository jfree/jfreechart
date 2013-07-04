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
 * -----------------------------------------
 * StandardURLTagFragmentGeneratorTests.java
 * -----------------------------------------
 * (C) Copyright 2007, 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Dec-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.imagemap.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.imagemap.StandardURLTagFragmentGenerator;

/**
 * Tests for the {@link StandardURLTagFragmentGeneratorTests} class.
 */
public class StandardURLTagFragmentGeneratorTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(StandardURLTagFragmentGeneratorTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public StandardURLTagFragmentGeneratorTests(String name) {
        super(name);
    }

    /**
     * Some checks for the generateURLFragment() method.
     */
    public void testGenerateURLFragment() {
        StandardURLTagFragmentGenerator g
                = new StandardURLTagFragmentGenerator();
        assertEquals(" href=\"abc\"", g.generateURLFragment("abc"));
        assertEquals(" href=\"images/abc.png\"",
                g.generateURLFragment("images/abc.png"));
        assertEquals(" href=\"http://www.jfree.org/images/abc.png\"",
                g.generateURLFragment("http://www.jfree.org/images/abc.png"));
    }

}

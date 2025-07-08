/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
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
 * -----------------------------------------
 * StandardCategoryToolTipGeneratorTest.java
 * -----------------------------------------
 * (C) Copyright 2004-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.labels;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.jfree.chart.util.PublicCloneable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link StandardCategoryToolTipGenerator} class.
 */
public class StandardCategoryToolTipGeneratorTest {

    /**
     * Use EqualsVerifier to ensure correct implementation of equals and
     * hashCode.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(StandardCategoryToolTipGenerator.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .withRedefinedSuperclass()
                .verify();
    }

    /**
     * Tests the equals() method.
     */
    @Test
    public void testEquals() {

        StandardCategoryToolTipGenerator g1
                = new StandardCategoryToolTipGenerator();
        StandardCategoryToolTipGenerator g2
                = new StandardCategoryToolTipGenerator();
        assertEquals(g1, g2);
        assertEquals(g2, g1);

        g1 = new StandardCategoryToolTipGenerator("{0}",
                new DecimalFormat("0.000"));
        assertNotEquals(g1, g2);
        g2 = new StandardCategoryToolTipGenerator("{0}",
                new DecimalFormat("0.000"));
        assertEquals(g1, g2);

        g1 = new StandardCategoryToolTipGenerator("{1}",
                new DecimalFormat("0.000"));
        assertNotEquals(g1, g2);
        g2 = new StandardCategoryToolTipGenerator("{1}",
                new DecimalFormat("0.000"));
        assertEquals(g1, g2);

        g1 = new StandardCategoryToolTipGenerator("{2}",
                new SimpleDateFormat("d-MMM"));
        assertNotEquals(g1, g2);
        g2 = new StandardCategoryToolTipGenerator("{2}",
                new SimpleDateFormat("d-MMM"));
        assertEquals(g1, g2);

    }

    /**
     * Simple check that hashCode is implemented.
     */
    @Test
    public void testHashCode() {
        StandardCategoryToolTipGenerator g1
                = new StandardCategoryToolTipGenerator();
        StandardCategoryToolTipGenerator g2
                = new StandardCategoryToolTipGenerator();
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        StandardCategoryToolTipGenerator g1
                = new StandardCategoryToolTipGenerator();
        StandardCategoryToolTipGenerator g2 
                = (StandardCategoryToolTipGenerator) g1.clone();
        assertNotSame(g1, g2);
        assertSame(g1.getClass(), g2.getClass());
        assertEquals(g1, g2);
    }

    /**
     * Check to ensure that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        StandardCategoryToolTipGenerator g1
                = new StandardCategoryToolTipGenerator();
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        StandardCategoryToolTipGenerator g1
                = new StandardCategoryToolTipGenerator("{2}",
                DateFormat.getInstance());
        StandardCategoryToolTipGenerator g2 = TestUtils.serialised(g1);
        assertEquals(g1, g2);
    }

    /**
     * A test for bug 1481087.
     */
    @Test
    public void testEquals1481087() {
        StandardCategoryToolTipGenerator g1
                = new StandardCategoryToolTipGenerator("{0}",
                new DecimalFormat("0.00"));
        StandardCategoryItemLabelGenerator g2
                = new StandardCategoryItemLabelGenerator("{0}",
                new DecimalFormat("0.00"));
        assertNotEquals(g1, g2);
    }

}

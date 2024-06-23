package org.jfree.chart.renderer.xy;

import java.awt.geom.Rectangle2D;

import org.jfree.chart.TestUtils;
import org.jfree.chart.ui.GradientPaintTransformType;
import org.jfree.chart.ui.StandardGradientPaintTransformer;
import org.jfree.chart.util.CloneUtils;
import org.jfree.chart.util.PublicCloneable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link XYBezierRenderer} class.
 */
class XYBezierRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    void testEquals() {
        XYBezierRenderer r1 = new XYBezierRenderer();
        XYBezierRenderer r2 = new XYBezierRenderer();
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        r1.setPrecision(9);
        assertNotEquals(r1, r2);
        r2.setPrecision(9);
        assertEquals(r1, r2);

        r1.setFillType(XYBezierRenderer.FillType.TO_ZERO);
        assertNotEquals(r1, r2);
        r2.setFillType(XYBezierRenderer.FillType.TO_ZERO);
        assertEquals(r1, r2);

        r1.setGradientPaintTransformer(null);
        assertNotEquals(r1, r2);
        r2.setGradientPaintTransformer(null);
        assertEquals(r1, r2);

        r1.setGradientPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.HORIZONTAL));
        assertNotEquals(r1, r2);
        r2.setGradientPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.HORIZONTAL));
        assertEquals(r1, r2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    void testHashcode() {
        XYBezierRenderer r1 = new XYBezierRenderer();
        XYBezierRenderer r2 = new XYBezierRenderer();
        assertEquals(r1, r2);
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    void testCloning() throws CloneNotSupportedException {
        Rectangle2D legendShape = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        XYBezierRenderer r1 = new XYBezierRenderer();
        r1.setLegendLine(legendShape);
        XYBezierRenderer r2 = (XYBezierRenderer) CloneUtils.clone(r1);
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    void testPublicCloneable() {
        XYBezierRenderer r1 = new XYBezierRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    void testSerialization() {
        XYBezierRenderer r1 = new XYBezierRenderer();
        XYBezierRenderer r2 = TestUtils.serialised(r1);
        assertEquals(r1, r2);
    }


}
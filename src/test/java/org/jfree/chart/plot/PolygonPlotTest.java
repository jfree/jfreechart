package org.jfree.chart.plot;

import org.jfree.chart.swing.UIUtils;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link PolygonPlot} class.
 */
public class PolygonPlotTest {
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        PolygonPlot plot1 = new PolygonPlot("Polygon plot");
        PolygonPlot plot2 = new PolygonPlot("Polygon plot");
        assertTrue(plot1.equals(plot2));
        plot1.setTitle("Polygon plot 1");
        assertFalse(plot1.equals(plot2));
    }
}

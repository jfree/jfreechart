package org.jfree.chart.plot;

import org.jfree.chart.swing.UIUtils;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        PolygonPlot plot1 = new PolygonPlot("Polygon 1");
        PolygonPlot plot2 = new PolygonPlot("Ploygon 2");
        assertFalse(plot1.equals(plot2));
        assertFalse(plot2.equals(plot1));
    }
}
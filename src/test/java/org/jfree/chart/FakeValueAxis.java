package org.jfree.chart;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.ui.RectangleEdge;

/**
 * Dummy extension of ValueAxis for JUnit testing using the EqualsVerifier
 * library.
 */
public class FakeValueAxis extends ValueAxis {

    public FakeValueAxis(String label, TickUnitSource standardTickUnits) {
        super(label, standardTickUnits);
    }

    @Override
    public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge) {
        return 0;
    }

    @Override
    public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge) {
        return 0;
    }

    @Override
    protected void autoAdjustRange() {
    }

    @Override
    public void configure() {
    }

    @Override
    public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
        return new AxisState();
    }

    @Override
    public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
        return new ArrayList<>();
    }
}

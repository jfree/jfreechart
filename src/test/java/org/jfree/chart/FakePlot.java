package org.jfree.chart;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;

/**
 * Dummy extension of Plot for JUnit testing using the EqualsVerifier library
 */
public class FakePlot extends Plot {

    @Override
    public String getPlotType() {
        return "Fake";
    }

    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
                     PlotState parentState, PlotRenderingInfo info) {
        ;
    }
}

package org.jfree.chart;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;

/**
 * Dummy extension of Plot for JUnit testing using the EqualsVerifier library
 */
public class FakePlot extends Plot {

    private String fakeName = "";
    
    @Override
    public String getPlotType() {
        return "Fake";
    }

    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
                     PlotState parentState, PlotRenderingInfo info) {
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (! (obj instanceof FakePlot)) {
            return false;
        }
        FakePlot that = (FakePlot) obj;
        if (!that.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(this.fakeName, that.fakeName))
        {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Ensures symmetry between super/subclass implementations of equals. For
     * more detail, see http://jqno.nl/equalsverifier/manual/inheritance.
     *
     * @param other Object
     * 
     * @return true ONLY if the parameter is THIS class type
     */
    @Override
    public boolean canEqual(Object other) {
        // Solves Problem: equals not symmetric
        return (other instanceof FakePlot);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + Objects.hashCode(this.fakeName);
        return hash;
    }

}

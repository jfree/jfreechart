/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * ----------------------
 * DefaultPolarPlotConfiguration.java
 * ----------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  Martin Hoeller;
 * Contributor(s):   Dimitry Polivaev;
 *
 */


package org.jfree.chart.swing.configuration;



import java.util.Map;

import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PolarPlot;

class DefaultPolarPlotConfiguration extends DefaultPlotConfiguration {
    private double manualTickUnit;

    private double angleOffset;

   DefaultPolarPlotConfiguration(PolarPlot plot, String name) {
        super(plot, name);
        this.angleOffset = plot.getAngleOffset();
        this.manualTickUnit = plot.getAngleTickUnit().getSize();
    }


   DefaultPolarPlotConfiguration(Map<String, String> properties, String name) {
       super(properties, name);
       this.manualTickUnit = Double.valueOf(properties.get(name + "." + "manualTickUnit"));
       this.angleOffset = Double.valueOf(properties.get(name + "." + "angleOffset"));
   }

   @Override
   void fillProperties(Map<String, String> properties) {
       super.fillProperties(properties);
       properties.put(name + "." + "manualTickUnit", Double.toString(manualTickUnit));
       properties.put(name + "." + "angleOffset", Double.toString(angleOffset));
   }

   @Override
    void updatePlotProperties(Plot plot) {
        super.updatePlotProperties(plot);
        PolarPlot pp = (PolarPlot) plot;
        pp.setAngleTickUnit(new NumberTickUnit(this.manualTickUnit));
        pp.setAngleOffset(this.angleOffset);
    }
}

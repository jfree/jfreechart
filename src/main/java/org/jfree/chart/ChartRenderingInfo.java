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
 * -----------------------
 * ChartRenderingInfo.java
 * -----------------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.SerialUtils;

/**
 * A structure for storing rendering information from one call to the
 * JFreeChart.draw() method.
 * <P>
 * An instance of the {@link JFreeChart} class can draw itself within an
 * arbitrary rectangle on any {@code Graphics2D}.  It is assumed that
 * client code will sometimes render the same chart in more than one view, so
 * the {@link JFreeChart} instance does not retain any information about its
 * rendered dimensions.  This information can be useful sometimes, so you have
 * the option to collect the information at each call to
 * {@code JFreeChart.draw()}, by passing an instance of this
 * {@code ChartRenderingInfo} class.
 */
public class ChartRenderingInfo implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2751952018173406822L;

    /** The area in which the chart is drawn. */
    private transient Rectangle2D chartArea;

    /** Rendering info for the chart's plot (and subplots, if any). */
    private PlotRenderingInfo plotInfo;

    /**
     * Storage for the chart entities.  Since retaining entity information for
     * charts with a large number of data points consumes a lot of memory, it
     * is intended that you can set this to {@code null} to prevent the
     * information being collected.
     */
    private EntityCollection entities;

    /**
     * Constructs a new ChartRenderingInfo structure that can be used to
     * collect information about the dimensions of a rendered chart.
     */
    public ChartRenderingInfo() {
        this(new StandardEntityCollection());
    }

    /**
     * Constructs a new instance. If an entity collection is supplied, it will
     * be populated with information about the entities in a chart.  If it is
     * {@code null}, no entity information (including tool tips) will
     * be collected.
     *
     * @param entities  an entity collection ({@code null} permitted).
     */
    public ChartRenderingInfo(EntityCollection entities) {
        this.chartArea = new Rectangle2D.Double();
        this.plotInfo = new PlotRenderingInfo(this);
        this.entities = entities;
    }

    /**
     * Returns the area in which the chart was drawn.
     *
     * @return The area in which the chart was drawn.
     *
     * @see #setChartArea(Rectangle2D)
     */
    public Rectangle2D getChartArea() {
        return this.chartArea;
    }

    /**
     * Sets the area in which the chart was drawn.
     *
     * @param area  the chart area.
     *
     * @see #getChartArea()
     */
    public void setChartArea(Rectangle2D area) {
        this.chartArea.setRect(area);
    }

    /**
     * Returns the collection of entities maintained by this instance.
     *
     * @return The entity collection (possibly {@code null}).
     *
     * @see #setEntityCollection(EntityCollection)
     */
    public EntityCollection getEntityCollection() {
        return this.entities;
    }

    /**
     * Sets the entity collection.
     *
     * @param entities  the entity collection ({@code null} permitted).
     *
     * @see #getEntityCollection()
     */
    public void setEntityCollection(EntityCollection entities) {
        this.entities = entities;
    }

    /**
     * Clears the information recorded by this object.
     */
    public void clear() {
        this.chartArea.setRect(0.0, 0.0, 0.0, 0.0);
        this.plotInfo = new PlotRenderingInfo(this);
        if (this.entities != null) {
            this.entities.clear();
        }
    }

    /**
     * Returns the rendering info for the chart's plot.
     *
     * @return The rendering info for the plot.
     */
    public PlotRenderingInfo getPlotInfo() {
        return this.plotInfo;
    }

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the object to test against ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ChartRenderingInfo)) {
            return false;
        }
        ChartRenderingInfo that = (ChartRenderingInfo) obj;
        if (!Objects.equals(this.chartArea, that.chartArea)) {
            return false;
        }
        if (!Objects.equals(this.plotInfo, that.plotInfo)) {
            return false;
        }
        if (!Objects.equals(this.entities, that.entities)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.chartArea);
        hash = 79 * hash + Objects.hashCode(this.entities);
        hash = 79 * hash + Objects.hashCode(this.plotInfo);
        return hash;
    }

    /**
     * Returns a clone of this object.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the object cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        ChartRenderingInfo clone = (ChartRenderingInfo) super.clone();
        if (this.chartArea != null) {
            clone.chartArea = (Rectangle2D) this.chartArea.clone();
        }
        if (this.entities instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) this.entities;
            clone.entities = (EntityCollection) pc.clone();
        }
        return clone;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeShape(this.chartArea, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.chartArea = (Rectangle2D) SerialUtils.readShape(stream);
    }

}

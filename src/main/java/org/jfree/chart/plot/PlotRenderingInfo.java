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
 * PlotRenderingInfo.java
 * ----------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.internal.SerialUtils;

/**
 * Stores information about the dimensions of a plot and its subplots.
 */
public class PlotRenderingInfo implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 8446720134379617220L;

    /** The owner of this info. */
    private final ChartRenderingInfo owner;

    /** The plot area. */
    private transient Rectangle2D plotArea;

    /** The data area. */
    private transient Rectangle2D dataArea;

    /**
     * Storage for the plot rendering info objects belonging to the subplots.
     */
    private List<PlotRenderingInfo> subplotInfo;

    /**
     * Creates a new instance.
     *
     * @param owner  the owner ({@code null} permitted).
     */
    public PlotRenderingInfo(ChartRenderingInfo owner) {
        this.owner = owner;
        this.dataArea = new Rectangle2D.Double();
        this.subplotInfo = new ArrayList<>();
    }

    /**
     * Returns the owner (as specified in the constructor).
     *
     * @return The owner (possibly {@code null}).
     */
    public ChartRenderingInfo getOwner() {
        return this.owner;
    }

    /**
     * Returns the plot area (in Java2D space).
     *
     * @return The plot area (possibly {@code null}).
     *
     * @see #setPlotArea(Rectangle2D)
     */
    public Rectangle2D getPlotArea() {
        return this.plotArea;
    }

    /**
     * Sets the plot area.
     *
     * @param area  the plot area (in Java2D space, {@code null}
     *     permitted but discouraged)
     *
     * @see #getPlotArea()
     */
    public void setPlotArea(Rectangle2D area) {
        this.plotArea = area;
    }

    /**
     * Returns the plot's data area (in Java2D space).
     *
     * @return The data area (possibly {@code null}).
     *
     * @see #setDataArea(Rectangle2D)
     */
    public Rectangle2D getDataArea() {
        return this.dataArea;
    }

    /**
     * Sets the data area.
     *
     * @param area  the data area (in Java2D space, {@code null} permitted
     *     but discouraged).
     *
     * @see #getDataArea()
     */
    public void setDataArea(Rectangle2D area) {
        this.dataArea = area;
    }

    /**
     * Returns the number of subplots (possibly zero).
     *
     * @return The subplot count.
     */
    public int getSubplotCount() {
        return this.subplotInfo.size();
    }

    /**
     * Adds the info for a subplot.
     *
     * @param info  the subplot info.
     *
     * @see #getSubplotInfo(int)
     */
    public void addSubplotInfo(PlotRenderingInfo info) {
        this.subplotInfo.add(info);
    }

    /**
     * Returns the info for a subplot.
     *
     * @param index  the subplot index.
     *
     * @return The info.
     *
     * @see #addSubplotInfo(PlotRenderingInfo)
     */
    public PlotRenderingInfo getSubplotInfo(int index) {
        return this.subplotInfo.get(index);
    }

    /**
     * Returns the index of the subplot that contains the specified
     * (x, y) point (the "source" point).  The source point will usually
     * come from a mouse click on a {@link org.jfree.chart.swing.ChartPanel},
     * and this method is then used to determine the subplot that
     * contains the source point.
     *
     * @param source  the source point (in Java2D space, {@code null} not
     * permitted).
     *
     * @return The subplot index (or -1 if no subplot contains {@code source}).
     */
    public int getSubplotIndex(Point2D source) {
        Args.nullNotPermitted(source, "source");
        int subplotCount = getSubplotCount();
        for (int i = 0; i < subplotCount; i++) {
            PlotRenderingInfo info = getSubplotInfo(i);
            Rectangle2D area = info.getDataArea();
            if (area.contains(source)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Tests this instance for equality against an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlotRenderingInfo)) {
            return false;
        }
        PlotRenderingInfo that = (PlotRenderingInfo) obj;
        if (!Objects.equals(this.dataArea, that.dataArea)) {
            return false;
        }
        if (!Objects.equals(this.plotArea, that.plotArea)) {
            return false;
        }
        if (!Objects.equals(this.subplotInfo, that.subplotInfo)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.plotArea);
        hash = 53 * hash + Objects.hashCode(this.dataArea);
        hash = 53 * hash + Objects.hashCode(this.subplotInfo);
        return hash;
    }

    /**
     * Returns a clone of this object.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        PlotRenderingInfo clone = (PlotRenderingInfo) super.clone();
        if (this.plotArea != null) {
            clone.plotArea = (Rectangle2D) this.plotArea.clone();
        }
        if (this.dataArea != null) {
            clone.dataArea = (Rectangle2D) this.dataArea.clone();
        }
        clone.subplotInfo = new ArrayList<>(this.subplotInfo.size());
        for (int i = 0; i < this.subplotInfo.size(); i++) {
            clone.subplotInfo.add(CloneUtils.clone(this.subplotInfo.get(i)));
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
        SerialUtils.writeShape(this.dataArea, stream);
        SerialUtils.writeShape(this.plotArea, stream);
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
        this.dataArea = (Rectangle2D) SerialUtils.readShape(stream);
        this.plotArea = (Rectangle2D) SerialUtils.readShape(stream);
    }

}

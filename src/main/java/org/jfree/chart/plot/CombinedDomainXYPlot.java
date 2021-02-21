/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * -------------------------
 * CombinedDomainXYPlot.java
 * -------------------------
 * (C) Copyright 2001-2021, by Bill Kelemen and Contributors.
 *
 * Original Author:  Bill Kelemen;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *                   Anthony Boulestreau;
 *                   David Basten;
 *                   Kevin Frechette (for ISTI);
 *                   Nicolas Brodu;
 *                   Petr Kubanek (bug 1606205);
 *                   Vladimir Shirokov (bug 986);
 */

package org.jfree.chart.plot;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.ShadowGenerator;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.XYDataset;

/**
 * An extension of {@link XYPlot} that contains multiple subplots that share a
 * common domain axis.
 */
public class CombinedDomainXYPlot extends XYPlot
        implements PlotChangeListener {

    /** For serialization. */
    private static final long serialVersionUID = -7765545541261907383L;

    /** Storage for the subplot references (possibly empty but never null). */
    private List<XYPlot> subplots;

    /** The gap between subplots. */
    private double gap = 5.0;

    /** Temporary storage for the subplot areas. */
    private transient Rectangle2D[] subplotAreas;
    // TODO:  the subplot areas needs to be moved out of the plot into the plot
    //        state

    /**
     * Default constructor.
     */
    public CombinedDomainXYPlot() {
        this(new NumberAxis());
    }

    /**
     * Creates a new combined plot that shares a domain axis among multiple
     * subplots.
     *
     * @param domainAxis  the shared axis.
     */
    public CombinedDomainXYPlot(ValueAxis domainAxis) {
        super(null,        // no data in the parent plot
              domainAxis,
              null,        // no range axis
              null);       // no renderer
        this.subplots = new java.util.ArrayList<XYPlot>();
    }

    /**
     * Returns a string describing the type of plot.
     *
     * @return The type of plot.
     */
    @Override
    public String getPlotType() {
        return "Combined_Domain_XYPlot";
    }

    /**
     * Returns the gap between subplots, measured in Java2D units.
     *
     * @return The gap (in Java2D units).
     *
     * @see #setGap(double)
     */
    public double getGap() {
        return this.gap;
    }

    /**
     * Sets the amount of space between subplots and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param gap  the gap between subplots (in Java2D units).
     *
     * @see #getGap()
     */
    public void setGap(double gap) {
        this.gap = gap;
        fireChangeEvent();
    }

    /**
     * Returns {@code true} if the range is pannable for at least one subplot,
     * and {@code false} otherwise.
     * 
     * @return A boolean. 
     */
    @Override
    public boolean isRangePannable() {
        for (XYPlot subplot : this.subplots) {
            if (subplot.isRangePannable()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Sets the flag, on each of the subplots, that controls whether or not the 
     * range is pannable.
     * 
     * @param pannable  the new flag value. 
     */
    @Override
    public void setRangePannable(boolean pannable) {
        for (XYPlot subplot : this.subplots) {
            subplot.setRangePannable(pannable);
        }        
    }

    /**
     * Sets the orientation for the plot (also changes the orientation for all
     * the subplots to match).
     *
     * @param orientation  the orientation ({@code null} not allowed).
     */
    @Override
    public void setOrientation(PlotOrientation orientation) {
        super.setOrientation(orientation);
        for (XYPlot p : this.subplots) {
            p.setOrientation(orientation);
        }
    }

    /**
     * Sets the shadow generator for the plot (and all subplots) and sends
     * a {@link PlotChangeEvent} to all registered listeners.
     * 
     * @param generator  the new generator ({@code null} permitted).
     */
    @Override
    public void setShadowGenerator(ShadowGenerator generator) {
        setNotify(false);
        super.setShadowGenerator(generator);
        for (XYPlot p : this.subplots) {
            p.setShadowGenerator(generator);
        }
        setNotify(true);
    }

    /**
     * Returns a range representing the extent of the data values in this plot
     * (obtained from the subplots) that will be rendered against the specified
     * axis.  NOTE: This method is intended for internal JFreeChart use, and
     * is public only so that code in the axis classes can call it.  Since
     * only the domain axis is shared between subplots, the JFreeChart code
     * will only call this method for the domain values (although this is not
     * checked/enforced).
     *
     * @param axis  the axis.
     *
     * @return The range (possibly {@code null}).
     */
    @Override
    public Range getDataRange(ValueAxis axis) {
        if (this.subplots == null) {
            return null;
        }
        Range result = null;
        for (XYPlot p : this.subplots) {
            result = Range.combine(result, p.getDataRange(axis));
        }
        return result;
    }

    /**
     * Adds a subplot (with a default 'weight' of 1) and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     * <P>
     * The domain axis for the subplot will be set to {@code null}.  You
     * must ensure that the subplot has a non-null range axis.
     *
     * @param subplot  the subplot ({@code null} not permitted).
     */
    public void add(XYPlot subplot) {
        // defer argument checking
        add(subplot, 1);
    }

    /**
     * Adds a subplot with the specified weight and sends a
     * {@link PlotChangeEvent} to all registered listeners.  The weight
     * determines how much space is allocated to the subplot relative to all
     * the other subplots.
     * <P>
     * The domain axis for the subplot will be set to {@code null}.  You
     * must ensure that the subplot has a non-null range axis.
     *
     * @param subplot  the subplot ({@code null} not permitted).
     * @param weight  the weight (must be &gt;= 1).
     */
    public void add(XYPlot subplot, int weight) {
        Args.nullNotPermitted(subplot, "subplot");
        if (weight <= 0) {
            throw new IllegalArgumentException("Require weight >= 1.");
        }

        // store the plot and its weight
        subplot.setParent(this);
        subplot.setWeight(weight);
        subplot.setInsets(RectangleInsets.ZERO_INSETS, false);
        subplot.setDomainAxis(null);
        subplot.addChangeListener(this);
        this.subplots.add(subplot);

        ValueAxis axis = getDomainAxis();
        if (axis != null) {
            axis.configure();
        }
        fireChangeEvent();
    }

    /**
     * Removes a subplot from the combined chart and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param subplot  the subplot ({@code null} not permitted).
     */
    public void remove(XYPlot subplot) {
        Args.nullNotPermitted(subplot, "subplot");
        int position = -1;
        int size = this.subplots.size();
        int i = 0;
        while (position == -1 && i < size) {
            if (this.subplots.get(i) == subplot) {
                position = i;
            }
            i++;
        }
        if (position != -1) {
            this.subplots.remove(position);
            subplot.setParent(null);
            subplot.removeChangeListener(this);
            ValueAxis domain = getDomainAxis();
            if (domain != null) {
                domain.configure();
            }
            fireChangeEvent();
        }
    }

    /**
     * Returns the list of subplots.  The returned list may be empty, but is
     * never {@code null}.
     *
     * @return An unmodifiable list of subplots.
     */
    public List getSubplots() {
        return Collections.unmodifiableList(this.subplots);
    }

    /**
     * Calculates the axis space required.
     *
     * @param g2  the graphics device.
     * @param plotArea  the plot area.
     *
     * @return The space.
     */
    @Override
    protected AxisSpace calculateAxisSpace(Graphics2D g2,
            Rectangle2D plotArea) {

        AxisSpace space = new AxisSpace();
        PlotOrientation orientation = getOrientation();

        // work out the space required by the domain axis...
        AxisSpace fixed = getFixedDomainAxisSpace();
        if (fixed != null) {
            if (orientation == PlotOrientation.HORIZONTAL) {
                space.setLeft(fixed.getLeft());
                space.setRight(fixed.getRight());
            }
            else if (orientation == PlotOrientation.VERTICAL) {
                space.setTop(fixed.getTop());
                space.setBottom(fixed.getBottom());
            }
        }
        else {
            ValueAxis xAxis = getDomainAxis();
            RectangleEdge xEdge = Plot.resolveDomainAxisLocation(
                    getDomainAxisLocation(), orientation);
            if (xAxis != null) {
                space = xAxis.reserveSpace(g2, this, plotArea, xEdge, space);
            }
        }

        Rectangle2D adjustedPlotArea = space.shrink(plotArea, null);

        // work out the maximum height or width of the non-shared axes...
        int n = this.subplots.size();
        int totalWeight = 0;
        for (int i = 0; i < n; i++) {
            XYPlot sub = (XYPlot) this.subplots.get(i);
            totalWeight += sub.getWeight();
        }
        this.subplotAreas = new Rectangle2D[n];
        double x = adjustedPlotArea.getX();
        double y = adjustedPlotArea.getY();
        double usableSize = 0.0;
        if (orientation == PlotOrientation.HORIZONTAL) {
            usableSize = adjustedPlotArea.getWidth() - this.gap * (n - 1);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            usableSize = adjustedPlotArea.getHeight() - this.gap * (n - 1);
        }

        for (int i = 0; i < n; i++) {
            XYPlot plot = (XYPlot) this.subplots.get(i);

            // calculate sub-plot area
            if (orientation == PlotOrientation.HORIZONTAL) {
                double w = usableSize * plot.getWeight() / totalWeight;
                this.subplotAreas[i] = new Rectangle2D.Double(x, y, w,
                        adjustedPlotArea.getHeight());
                x = x + w + this.gap;
            }
            else if (orientation == PlotOrientation.VERTICAL) {
                double h = usableSize * plot.getWeight() / totalWeight;
                this.subplotAreas[i] = new Rectangle2D.Double(x, y,
                        adjustedPlotArea.getWidth(), h);
                y = y + h + this.gap;
            }

            AxisSpace subSpace = plot.calculateRangeAxisSpace(g2,
                    this.subplotAreas[i], null);
            space.ensureAtLeast(subSpace);

        }

        return space;
    }

    /**
     * Draws the plot within the specified area on a graphics device.
     *
     * @param g2  the graphics device.
     * @param area  the plot area (in Java2D space).
     * @param anchor  an anchor point in Java2D space ({@code null}
     *                permitted).
     * @param parentState  the state from the parent plot, if there is one
     *                     ({@code null} permitted).
     * @param info  collects chart drawing information ({@code null}
     *              permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
            PlotState parentState, PlotRenderingInfo info) {

        // set up info collection...
        if (info != null) {
            info.setPlotArea(area);
        }

        // adjust the drawing area for plot insets (if any)...
        RectangleInsets insets = getInsets();
        insets.trim(area);

        setFixedRangeAxisSpaceForSubplots(null);
        AxisSpace space = calculateAxisSpace(g2, area);
        Rectangle2D dataArea = space.shrink(area, null);

        // set the width and height of non-shared axis of all sub-plots
        setFixedRangeAxisSpaceForSubplots(space);

        // draw the shared axis
        ValueAxis axis = getDomainAxis();
        RectangleEdge edge = getDomainAxisEdge();
        double cursor = RectangleEdge.coordinate(dataArea, edge);
        AxisState axisState = axis.draw(g2, cursor, area, dataArea, edge, info);
        if (parentState == null) {
            parentState = new PlotState();
        }
        parentState.getSharedAxisStates().put(axis, axisState);

        // draw all the subplots
        for (int i = 0; i < this.subplots.size(); i++) {
            XYPlot plot = (XYPlot) this.subplots.get(i);
            PlotRenderingInfo subplotInfo = null;
            if (info != null) {
                subplotInfo = new PlotRenderingInfo(info.getOwner());
                info.addSubplotInfo(subplotInfo);
            }
            plot.draw(g2, this.subplotAreas[i], anchor, parentState,
                    subplotInfo);
        }

        if (info != null) {
            info.setDataArea(dataArea);
        }

    }

    /**
     * Returns a collection of legend items for the plot.
     *
     * @return The legend items.
     */
    @Override
    public LegendItemCollection getLegendItems() {
        LegendItemCollection result = getFixedLegendItems();
        if (result == null) {
            result = new LegendItemCollection();
            if (this.subplots != null) {
                Iterator iterator = this.subplots.iterator();
                while (iterator.hasNext()) {
                    XYPlot plot = (XYPlot) iterator.next();
                    LegendItemCollection more = plot.getLegendItems();
                    result.addAll(more);
                }
            }
        }
        return result;
    }

    /**
     * Multiplies the range on the range axis/axes by the specified factor.
     *
     * @param factor  the zoom factor.
     * @param info  the plot rendering info ({@code null} not permitted).
     * @param source  the source point ({@code null} not permitted).
     */
    @Override
    public void zoomRangeAxes(double factor, PlotRenderingInfo info,
                              Point2D source) {
        zoomRangeAxes(factor, info, source, false);
    }

    /**
     * Multiplies the range on the range axis/axes by the specified factor.
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     * @param useAnchor  use source point as zoom anchor?
     */
    @Override
    public void zoomRangeAxes(double factor, PlotRenderingInfo state,
            Point2D source, boolean useAnchor) {
        // delegate 'state' and 'source' argument checks...
        XYPlot subplot = findSubplot(state, source);
        if (subplot != null) {
            subplot.zoomRangeAxes(factor, state, source, useAnchor);
        } else {
            // if the source point doesn't fall within a subplot, we do the
            // zoom on all subplots...
            for (XYPlot p : this.subplots) {
                p.zoomRangeAxes(factor, state, source, useAnchor);
            }
        }
    }

    /**
     * Zooms in on the range axes.
     *
     * @param lowerPercent  the lower bound.
     * @param upperPercent  the upper bound.
     * @param info  the plot rendering info ({@code null} not permitted).
     * @param source  the source point ({@code null} not permitted).
     */
    @Override
    public void zoomRangeAxes(double lowerPercent, double upperPercent,
                              PlotRenderingInfo info, Point2D source) {
        // delegate 'info' and 'source' argument checks...
        XYPlot subplot = findSubplot(info, source);
        if (subplot != null) {
            subplot.zoomRangeAxes(lowerPercent, upperPercent, info, source);
        } else {
            // if the source point doesn't fall within a subplot, we do the
            // zoom on all subplots...
            for (XYPlot p : this.subplots) {
                p.zoomRangeAxes(lowerPercent, upperPercent, info, source);
            }
        }
    }

    /**
     * Pans all range axes by the specified percentage.
     *
     * @param panRange the distance to pan (as a percentage of the axis length).
     * @param info  the plot info ({@code null} not permitted).
     * @param source the source point where the pan action started.
     */
    @Override
    public void panRangeAxes(double panRange, PlotRenderingInfo info,
            Point2D source) {
        XYPlot subplot = findSubplot(info, source);
        if (subplot == null) {
            return;
        }
        if (!subplot.isRangePannable()) {
            return;
        }
        PlotRenderingInfo subplotInfo = info.getSubplotInfo(
                info.getSubplotIndex(source));
        if (subplotInfo == null) {
            return;
        }
        for (int i = 0; i < subplot.getRangeAxisCount(); i++) {
            ValueAxis rangeAxis = subplot.getRangeAxis(i);
            if (rangeAxis != null) {
                rangeAxis.pan(panRange);
            }
        }
    }

    /**
     * Returns the subplot (if any) that contains the (x, y) point (specified
     * in Java2D space).
     *
     * @param info  the chart rendering info ({@code null} not permitted).
     * @param source  the source point ({@code null} not permitted).
     *
     * @return A subplot (possibly {@code null}).
     */
    public XYPlot findSubplot(PlotRenderingInfo info, Point2D source) {
        Args.nullNotPermitted(info, "info");
        Args.nullNotPermitted(source, "source");
        XYPlot result = null;
        int subplotIndex = info.getSubplotIndex(source);
        if (subplotIndex >= 0) {
            result =  (XYPlot) this.subplots.get(subplotIndex);
        }
        return result;
    }

    /**
     * Sets the item renderer FOR ALL SUBPLOTS.  Registered listeners are
     * notified that the plot has been modified.
     * <P>
     * Note: usually you will want to set the renderer independently for each
     * subplot, which is NOT what this method does.
     *
     * @param renderer the new renderer.
     */
    @Override
    public void setRenderer(XYItemRenderer renderer) {
        super.setRenderer(renderer);  // not strictly necessary, since the
                                      // renderer set for the
                                      // parent plot is not used
        for (XYPlot p : this.subplots) {
            p.setRenderer(renderer);
        }
    }

    /**
     * Sets the fixed range axis space and sends a {@link PlotChangeEvent} to
     * all registered listeners.
     *
     * @param space  the space ({@code null} permitted).
     */
    @Override
    public void setFixedRangeAxisSpace(AxisSpace space) {
        super.setFixedRangeAxisSpace(space);
        setFixedRangeAxisSpaceForSubplots(space);
        fireChangeEvent();
    }

    /**
     * Sets the size (width or height, depending on the orientation of the
     * plot) for the domain axis of each subplot.
     *
     * @param space  the space.
     */
    protected void setFixedRangeAxisSpaceForSubplots(AxisSpace space) {
        for (XYPlot p : this.subplots) {
            p.setFixedRangeAxisSpace(space, false);
        }
    }

    /**
     * Handles a 'click' on the plot by updating the anchor values.
     *
     * @param x  x-coordinate, where the click occurred.
     * @param y  y-coordinate, where the click occurred.
     * @param info  object containing information about the plot dimensions.
     */
    @Override
    public void handleClick(int x, int y, PlotRenderingInfo info) {
        Rectangle2D dataArea = info.getDataArea();
        if (dataArea.contains(x, y)) {
            for (int i = 0; i < this.subplots.size(); i++) {
                XYPlot subplot = (XYPlot) this.subplots.get(i);
                PlotRenderingInfo subplotInfo = info.getSubplotInfo(i);
                subplot.handleClick(x, y, subplotInfo);
            }
        }
    }

    /**
     * Receives notification of a change to the plot's dataset.
     * <P>
     * The axis ranges are updated if necessary.
     *
     * @param event  information about the event (not used here).
     */
    @Override
    public void datasetChanged(DatasetChangeEvent event) {
        super.datasetChanged(event);
        if (this.subplots == null) {
            return;  // this can happen during plot construction
        }
        XYDataset dataset = null;
        if (event.getDataset() instanceof XYDataset) {
            dataset = (XYDataset) event.getDataset();
        }
        for (XYPlot subplot : this.subplots) {
            if (subplot.indexOf(dataset) >= 0) {
                subplot.configureRangeAxes();
            }
        }
    }

    /**
     * Receives a {@link PlotChangeEvent} and responds by notifying all
     * listeners.
     *
     * @param event  the event.
     */
    @Override
    public void plotChanged(PlotChangeEvent event) {
        notifyListeners(event);
    }

    /**
     * Tests this plot for equality with another object.
     *
     * @param obj  the other object.
     *
     * @return {@code true} or {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CombinedDomainXYPlot)) {
            return false;
        }
        CombinedDomainXYPlot that = (CombinedDomainXYPlot) obj;
        if (this.gap != that.gap) {
            return false;
        }
        if (!Objects.equals(this.subplots, that.subplots)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a clone of the annotation.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  this class will not throw this
     *         exception, but subclasses (if any) might.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {

        CombinedDomainXYPlot result = (CombinedDomainXYPlot) super.clone();
        result.subplots = (List) ObjectUtils.deepClone(this.subplots);
        for (Iterator it = result.subplots.iterator(); it.hasNext();) {
            Plot child = (Plot) it.next();
            child.setParent(result);
        }

        // after setting up all the subplots, the shared domain axis may need
        // reconfiguring
        ValueAxis domainAxis = result.getDomainAxis();
        if (domainAxis != null) {
            domainAxis.configure();
        }

        return result;

    }

}

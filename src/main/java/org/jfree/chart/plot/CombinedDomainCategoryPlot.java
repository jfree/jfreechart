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
 * -------------------------------
 * CombinedDomainCategoryPlot.java
 * -------------------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Nicolas Brodu;
 *
 */

package org.jfree.chart.plot;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jfree.chart.ChartElementVisitor;

import org.jfree.chart.legend.LegendItemCollection;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.util.ShadowGenerator;
import org.jfree.data.Range;

/**
 * A combined category plot where the domain axis is shared.
 */
public class CombinedDomainCategoryPlot extends CategoryPlot
        implements PlotChangeListener {

    /** For serialization. */
    private static final long serialVersionUID = 8207194522653701572L;

    /** Storage for the subplot references. */
    private List<CategoryPlot> subplots;

    /** The gap between subplots. */
    private double gap;

    /** Temporary storage for the subplot areas. */
    private transient Rectangle2D[] subplotAreas;
    // FIXME:  move the above to the plot state

    /**
     * Default constructor.
     */
    public CombinedDomainCategoryPlot() {
        this(new CategoryAxis());
    }

    /**
     * Creates a new plot.
     *
     * @param domainAxis  the shared domain axis ({@code null} not
     *                    permitted).
     */
    public CombinedDomainCategoryPlot(CategoryAxis domainAxis) {
        super(null, domainAxis, null, null);
        this.subplots = new ArrayList<>();
        this.gap = 5.0;
    }

    /**
     * Returns the space between subplots.  The default value is 5.0.
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
     * Adds a subplot to the combined chart and sends a {@link PlotChangeEvent}
     * to all registered listeners.
     * <br><br>
     * The domain axis for the subplot will be set to {@code null}.  You
     * must ensure that the subplot has a non-null range axis.
     *
     * @param subplot  the subplot ({@code null} not permitted).
     */
    public void add(CategoryPlot subplot) {
        add(subplot, 1);
    }

    /**
     * Adds a subplot to the combined chart and sends a {@link PlotChangeEvent}
     * to all registered listeners.
     * <br><br>
     * The domain axis for the subplot will be set to {@code null}.  You
     * must ensure that the subplot has a non-null range axis.
     *
     * @param subplot  the subplot ({@code null} not permitted).
     * @param weight  the weight (must be &gt;= 1).
     */
    public void add(CategoryPlot subplot, int weight) {
        Args.nullNotPermitted(subplot, "subplot");
        if (weight < 1) {
            throw new IllegalArgumentException("Require weight >= 1.");
        }
        subplot.setParent(this);
        subplot.setWeight(weight);
        subplot.setInsets(new RectangleInsets(0.0, 0.0, 0.0, 0.0));
        subplot.setDomainAxis(null);
        subplot.setOrientation(getOrientation());
        subplot.addChangeListener(this);
        this.subplots.add(subplot);
        CategoryAxis axis = getDomainAxis();
        if (axis != null) {
            axis.configure();
        }
        fireChangeEvent();
    }

    /**
     * Removes a subplot from the combined chart.  Potentially, this removes
     * some unique categories from the overall union of the datasets...so the
     * domain axis is reconfigured, then a {@link PlotChangeEvent} is sent to
     * all registered listeners.
     *
     * @param subplot  the subplot ({@code null} not permitted).
     */
    public void remove(CategoryPlot subplot) {
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
            CategoryAxis domain = getDomainAxis();
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
    public List<CategoryPlot> getSubplots() {
        if (this.subplots != null) {
            return Collections.unmodifiableList(this.subplots);
        }
        else {
            return Collections.EMPTY_LIST;
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
    public CategoryPlot findSubplot(PlotRenderingInfo info, Point2D source) {
        Args.nullNotPermitted(info, "info");
        Args.nullNotPermitted(source, "source");
        CategoryPlot result = null;
        int subplotIndex = info.getSubplotIndex(source);
        if (subplotIndex >= 0) {
            result =  (CategoryPlot) this.subplots.get(subplotIndex);
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
     * @param info  the plot rendering info ({@code null} not permitted).
     * @param source  the source point ({@code null} not permitted).
     * @param useAnchor  zoom about the anchor point?
     */
    @Override
    public void zoomRangeAxes(double factor, PlotRenderingInfo info,
                              Point2D source, boolean useAnchor) {
        // delegate 'info' and 'source' argument checks...
        CategoryPlot subplot = findSubplot(info, source);
        if (subplot != null) {
            subplot.zoomRangeAxes(factor, info, source, useAnchor);
        }
        else {
            // if the source point doesn't fall within a subplot, we do the
            // zoom on all subplots...
            for (CategoryPlot categoryPlot : getSubplots()) {
                subplot = categoryPlot;
                subplot.zoomRangeAxes(factor, info, source, useAnchor);
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
        CategoryPlot subplot = findSubplot(info, source);
        if (subplot != null) {
            subplot.zoomRangeAxes(lowerPercent, upperPercent, info, source);
        }
        else {
            // if the source point doesn't fall within a subplot, we do the
            // zoom on all subplots...
            for (CategoryPlot categoryPlot : getSubplots()) {
                subplot = categoryPlot;
                subplot.zoomRangeAxes(lowerPercent, upperPercent, info, source);
            }
        }
    }

    /**
     * Calculates the space required for the axes.
     *
     * @param g2  the graphics device.
     * @param plotArea  the plot area.
     *
     * @return The space required for the axes.
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
            CategoryAxis categoryAxis = getDomainAxis();
            RectangleEdge categoryEdge = Plot.resolveDomainAxisLocation(
                    getDomainAxisLocation(), orientation);
            if (categoryAxis != null) {
                space = categoryAxis.reserveSpace(g2, this, plotArea,
                        categoryEdge, space);
            }
            else {
                if (getDrawSharedDomainAxis()) {
                    space = getDomainAxis().reserveSpace(g2, this, plotArea,
                            categoryEdge, space);
                }
            }
        }

        Rectangle2D adjustedPlotArea = space.shrink(plotArea, null);

        // work out the maximum height or width of the non-shared axes...
        int n = this.subplots.size();
        int totalWeight = 0;
        for (int i = 0; i < n; i++) {
            CategoryPlot sub = (CategoryPlot) this.subplots.get(i);
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
            CategoryPlot plot = (CategoryPlot) this.subplots.get(i);

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
     * Receives a chart element visitor.  Many plot subclasses will override
     * this method to handle their subcomponents.
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     */
    @Override
    public void receive(ChartElementVisitor visitor) {
        subplots.forEach(subplot -> {
            subplot.receive(visitor);
        });
        super.receive(visitor);
    }

    /**
     * Draws the plot on a Java 2D graphics device (such as the screen or a
     * printer).  Will perform all the placement calculations for each of the
     * sub-plots and then tell these to draw themselves.
     *
     * @param g2  the graphics device.
     * @param area  the area within which the plot (including axis labels)
     *              should be drawn.
     * @param anchor  the anchor point ({@code null} permitted).
     * @param parentState  the state from the parent plot, if there is one.
     * @param info  collects information about the drawing ({@code null}
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
        area.setRect(area.getX() + insets.getLeft(),
                area.getY() + insets.getTop(),
                area.getWidth() - insets.getLeft() - insets.getRight(),
                area.getHeight() - insets.getTop() - insets.getBottom());


        // calculate the data area...
        setFixedRangeAxisSpaceForSubplots(null);
        AxisSpace space = calculateAxisSpace(g2, area);
        Rectangle2D dataArea = space.shrink(area, null);

        // set the width and height of non-shared axis of all sub-plots
        setFixedRangeAxisSpaceForSubplots(space);

        // draw the shared axis
        CategoryAxis axis = getDomainAxis();
        RectangleEdge domainEdge = getDomainAxisEdge();
        double cursor = RectangleEdge.coordinate(dataArea, domainEdge);
        AxisState axisState = axis.draw(g2, cursor, area, dataArea,
                domainEdge, info);
        if (parentState == null) {
            parentState = new PlotState();
        }
        parentState.getSharedAxisStates().put(axis, axisState);

        // draw all the subplots
        for (int i = 0; i < this.subplots.size(); i++) {
            CategoryPlot plot = (CategoryPlot) this.subplots.get(i);
            PlotRenderingInfo subplotInfo = null;
            if (info != null) {
                subplotInfo = new PlotRenderingInfo(info.getOwner());
                info.addSubplotInfo(subplotInfo);
            }
            Point2D subAnchor = null;
            if (anchor != null && this.subplotAreas[i].contains(anchor)) {
                subAnchor = anchor;
            }
            plot.draw(g2, this.subplotAreas[i], subAnchor, parentState,
                    subplotInfo);
        }

        if (info != null) {
            info.setDataArea(dataArea);
        }

    }

    /**
     * Sets the size (width or height, depending on the orientation of the
     * plot) for the range axis of each subplot.
     *
     * @param space  the space ({@code null} permitted).
     */
    protected void setFixedRangeAxisSpaceForSubplots(AxisSpace space) {
        for (CategoryPlot plot : this.subplots) {
            plot.setFixedRangeAxisSpace(space, false);
        }
    }

    /**
     * Sets the orientation of the plot (and all subplots).
     *
     * @param orientation  the orientation ({@code null} not permitted).
     */
    @Override
    public void setOrientation(PlotOrientation orientation) {
        super.setOrientation(orientation);
        for (CategoryPlot plot : this.subplots) {
            plot.setOrientation(orientation);
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
        for (CategoryPlot plot : this.subplots) {
            plot.setShadowGenerator(generator);
        }
        setNotify(true);
    }

    /**
     * Returns a range representing the extent of the data values in this plot
     * (obtained from the subplots) that will be rendered against the specified
     * axis.  NOTE: This method is intended for internal JFreeChart use, and
     * is public only so that code in the axis classes can call it.  Since,
     * for this class, the domain axis is a {@link CategoryAxis}
     * (not a {@code ValueAxis}) and subplots have independent range axes,
     * the JFreeChart code will never call this method (although this is not
     * checked/enforced).
      *
      * @param axis  the axis.
      *
      * @return The range.
      */
    @Override
     public Range getDataRange(ValueAxis axis) {
         // override is only for documentation purposes
         return super.getDataRange(axis);
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
                for (CategoryPlot plot : this.subplots) {
                    LegendItemCollection more = plot.getLegendItems();
                    result.addAll(more);
                }
            }
        }
        return result;
    }

    /**
     * Returns an unmodifiable list of the categories contained in all the
     * subplots.
     *
     * @return The list.
     */
    @Override
    public List getCategories() {
        List result = new java.util.ArrayList();
        if (this.subplots != null) {
            for (CategoryPlot plot : this.subplots) {
                List more = plot.getCategories();
                for (Object o : more) {
                    Comparable category = (Comparable) o;
                    if (!result.contains(category)) {
                        result.add(category);
                    }
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Overridden to return the categories in the subplots.
     *
     * @param axis  ignored.
     *
     * @return A list of the categories in the subplots.
     */
    @Override
    public List getCategoriesForAxis(CategoryAxis axis) {
        // FIXME:  this code means that it is not possible to use more than
        // one domain axis for the combined plots...
        return getCategories();
    }

    /**
     * Handles a 'click' on the plot.
     *
     * @param x  x-coordinate of the click.
     * @param y  y-coordinate of the click.
     * @param info  information about the plot's dimensions.
     *
     */
    @Override
    public void handleClick(int x, int y, PlotRenderingInfo info) {

        Rectangle2D dataArea = info.getDataArea();
        if (dataArea.contains(x, y)) {
            for (int i = 0; i < this.subplots.size(); i++) {
                CategoryPlot subplot = (CategoryPlot) this.subplots.get(i);
                PlotRenderingInfo subplotInfo = info.getSubplotInfo(i);
                subplot.handleClick(x, y, subplotInfo);
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
     * Tests the plot for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CombinedDomainCategoryPlot)) {
            return false;
        }
        CombinedDomainCategoryPlot that = (CombinedDomainCategoryPlot) obj;
        if (this.gap != that.gap) {
            return false;
        }
        if (!Objects.equals(this.subplots, that.subplots)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a clone of the plot.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  this class will not throw this
     *         exception, but subclasses (if any) might.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        CombinedDomainCategoryPlot result
            = (CombinedDomainCategoryPlot) super.clone();
        result.subplots = (List<CategoryPlot>) CloneUtils.cloneList(this.subplots);
        for (Iterator it = result.subplots.iterator(); it.hasNext();) {
            Plot child = (Plot) it.next();
            child.setParent(result);
        }
        return result;

    }

}

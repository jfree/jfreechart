/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * --------------
 * PolarPlot.java
 * --------------
 * (C) Copyright 2004-2013, by Solution Engineering, Inc. and Contributors.
 *
 * Original Author:  Daniel Bridenbecker, Solution Engineering, Inc.;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *                   Martin Hoeller (patches 1871902 and 2850344);
 *
 * Changes
 * -------
 * 19-Jan-2004 : Version 1, contributed by DB with minor changes by DG (DG);
 * 07-Apr-2004 : Changed text bounds calculation (DG);
 * 05-May-2005 : Updated draw() method parameters (DG);
 * 09-Jun-2005 : Fixed getDataRange() and equals() methods (DG);
 * 25-Oct-2005 : Implemented Zoomable (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 07-Feb-2007 : Fixed bug 1599761, data value less than axis minimum (DG);
 * 21-Mar-2007 : Fixed serialization bug (DG);
 * 24-Sep-2007 : Implemented new zooming methods (DG);
 * 17-Feb-2007 : Added angle tick unit attribute (see patch 1871902 by
 *               Martin Hoeller) (DG);
 * 18-Dec-2008 : Use ResourceBundleWrapper - see patch 1607918 by
 *               Jess Thrysoee (DG);
 * 03-Sep-2009 : Applied patch 2850344 by Martin Hoeller (DG);
 * 27-Nov-2009 : Added support for multiple datasets, renderers and axes (DG);
 * 09-Dec-2009 : Extended getLegendItems() to handle multiple datasets (DG);
 * 25-Jun-2010 : Better support for multiple axes (MH);
 * 03-Oct-2011 : Added support for angleOffset and direction (MH);
 * 12-Nov-2011 : Fixed bug 3432721, log-axis doesn't work (MH);
 * 12-Dec-2011 : Added support for radiusMinorGridilnesVisible (MH);
 * 02-Jul-2013 : Use ParamChecks (DG);
 * 
 */

package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.ValueTick;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.renderer.PolarItemRenderer;
import org.jfree.chart.util.ParamChecks;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.Range;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectList;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

/**
 * Plots data that is in (theta, radius) pairs where
 * theta equal to zero is due north and increases clockwise.
 */
public class PolarPlot extends Plot implements ValueAxisPlot, Zoomable,
        RendererChangeListener, Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 3794383185924179525L;

    /** The default margin. */
    private static final int DEFAULT_MARGIN = 20;

    /** The annotation margin. */
    private static final double ANNOTATION_MARGIN = 7.0;

    /**
     * The default angle tick unit size.
     *
     * @since 1.0.10
     */
    public static final double DEFAULT_ANGLE_TICK_UNIT_SIZE = 45.0;

    /**
     * The default angle offset.
     *
     * @since 1.0.14
     */
    public static final double DEFAULT_ANGLE_OFFSET = -90.0;

    /** The default grid line stroke. */
    public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(
            0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
            0.0f, new float[]{2.0f, 2.0f}, 0.0f);

    /** The default grid line paint. */
    public static final Paint DEFAULT_GRIDLINE_PAINT = Color.gray;

    /** The resourceBundle for the localization. */
    protected static ResourceBundle localizationResources
            = ResourceBundleWrapper.getBundle(
                    "org.jfree.chart.plot.LocalizationBundle");

    /** The angles that are marked with gridlines. */
    private List angleTicks;

    /** The range axis (used for the y-values). */
    private ObjectList axes;

    /** The axis locations. */
    private ObjectList axisLocations;

    /** Storage for the datasets. */
    private ObjectList datasets;

    /** Storage for the renderers. */
    private ObjectList renderers;

    /**
     * The tick unit that controls the spacing between the angular grid lines.
     *
     * @since 1.0.10
     */
    private TickUnit angleTickUnit;

    /**
     * An offset for the angles, to start with 0 degrees at north, east, south
     * or west.
     *
     * @since 1.0.14
     */
    private double angleOffset;

    /**
     * A flag indicating if the angles increase counterclockwise or clockwise.
     *
     * @since 1.0.14
     */
    private boolean counterClockwise;

    /** A flag that controls whether or not the angle labels are visible. */
    private boolean angleLabelsVisible = true;

    /** The font used to display the angle labels - never null. */
    private Font angleLabelFont = new Font("SansSerif", Font.PLAIN, 12);

    /** The paint used to display the angle labels. */
    private transient Paint angleLabelPaint = Color.black;

    /** A flag that controls whether the angular grid-lines are visible. */
    private boolean angleGridlinesVisible;

    /** The stroke used to draw the angular grid-lines. */
    private transient Stroke angleGridlineStroke;

    /** The paint used to draw the angular grid-lines. */
    private transient Paint angleGridlinePaint;

    /** A flag that controls whether the radius grid-lines are visible. */
    private boolean radiusGridlinesVisible;

    /** The stroke used to draw the radius grid-lines. */
    private transient Stroke radiusGridlineStroke;

    /** The paint used to draw the radius grid-lines. */
    private transient Paint radiusGridlinePaint;

    /**
     * A flag that controls whether the radial minor grid-lines are visible.
     * @since 1.0.15
     */
    private boolean radiusMinorGridlinesVisible;

    /** The annotations for the plot. */
    private List cornerTextItems = new ArrayList();

    /**
     * The actual margin in pixels.
     *
     * @since 1.0.14
     */
    private int margin;

    /**
     * An optional collection of legend items that can be returned by the
     * getLegendItems() method.
     */
    private LegendItemCollection fixedLegendItems;

    /**
     * Storage for the mapping between datasets/renderers and range axes.  The
     * keys in the map are Integer objects, corresponding to the dataset
     * index.  The values in the map are List objects containing Integer
     * objects (corresponding to the axis indices).  If the map contains no
     * entry for a dataset, it is assumed to map to the primary domain axis
     * (index = 0).
     */
    private Map datasetToAxesMap;

    /**
     * Default constructor.
     */
    public PolarPlot() {
        this(null, null, null);
    }

   /**
     * Creates a new plot.
     *
     * @param dataset  the dataset (<code>null</code> permitted).
     * @param radiusAxis  the radius axis (<code>null</code> permitted).
     * @param renderer  the renderer (<code>null</code> permitted).
     */
    public PolarPlot(XYDataset dataset, ValueAxis radiusAxis,
                PolarItemRenderer renderer) {

        super();

        this.datasets = new ObjectList();
        this.datasets.set(0, dataset);
        if (dataset != null) {
            dataset.addChangeListener(this);
        }
        this.angleTickUnit = new NumberTickUnit(DEFAULT_ANGLE_TICK_UNIT_SIZE);

        this.axes = new ObjectList();
        this.datasetToAxesMap = new TreeMap();
        this.axes.set(0, radiusAxis);
        if (radiusAxis != null) {
            radiusAxis.setPlot(this);
            radiusAxis.addChangeListener(this);
        }

        // define the default locations for up to 8 axes...
        this.axisLocations = new ObjectList();
        this.axisLocations.set(0, PolarAxisLocation.EAST_ABOVE);
        this.axisLocations.set(1, PolarAxisLocation.NORTH_LEFT);
        this.axisLocations.set(2, PolarAxisLocation.WEST_BELOW);
        this.axisLocations.set(3, PolarAxisLocation.SOUTH_RIGHT);
        this.axisLocations.set(4, PolarAxisLocation.EAST_BELOW);
        this.axisLocations.set(5, PolarAxisLocation.NORTH_RIGHT);
        this.axisLocations.set(6, PolarAxisLocation.WEST_ABOVE);
        this.axisLocations.set(7, PolarAxisLocation.SOUTH_LEFT);

        this.renderers = new ObjectList();
        this.renderers.set(0, renderer);
        if (renderer != null) {
            renderer.setPlot(this);
            renderer.addChangeListener(this);
        }

        this.angleOffset = DEFAULT_ANGLE_OFFSET;
        this.counterClockwise = false;
        this.angleGridlinesVisible = true;
        this.angleGridlineStroke = DEFAULT_GRIDLINE_STROKE;
        this.angleGridlinePaint = DEFAULT_GRIDLINE_PAINT;

        this.radiusGridlinesVisible = true;
        this.radiusMinorGridlinesVisible = true;
        this.radiusGridlineStroke = DEFAULT_GRIDLINE_STROKE;
        this.radiusGridlinePaint = DEFAULT_GRIDLINE_PAINT;
        this.margin = DEFAULT_MARGIN;
    }

    /**
     * Returns the plot type as a string.
     *
     * @return A short string describing the type of plot.
     */
    public String getPlotType() {
       return PolarPlot.localizationResources.getString("Polar_Plot");
    }

    /**
     * Returns the primary axis for the plot.
     *
     * @return The primary axis (possibly <code>null</code>).
     *
     * @see #setAxis(ValueAxis)
     */
    public ValueAxis getAxis() {
        return getAxis(0);
    }

    /**
     * Returns an axis for the plot.
     *
     * @param index  the axis index.
     *
     * @return The axis (<code>null</code> possible).
     *
     * @see #setAxis(int, ValueAxis)
     *
     * @since 1.0.14
     */
    public ValueAxis getAxis(int index) {
        ValueAxis result = null;
        if (index < this.axes.size()) {
            result = (ValueAxis) this.axes.get(index);
        }
        return result;
    }

    /**
     * Sets the primary axis for the plot and sends a {@link PlotChangeEvent}
     * to all registered listeners.
     *
     * @param axis  the new primary axis (<code>null</code> permitted).
     */
    public void setAxis(ValueAxis axis) {
        setAxis(0, axis);
    }

    /**
     * Sets an axis for the plot and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param index  the axis index.
     * @param axis  the axis (<code>null</code> permitted).
     *
     * @see #getAxis(int)
     *
     * @since 1.0.14
     */
    public void setAxis(int index, ValueAxis axis) {
        setAxis(index, axis, true);
    }

    /**
     * Sets an axis for the plot and, if requested, sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param index  the axis index.
     * @param axis  the axis (<code>null</code> permitted).
     * @param notify  notify listeners?
     *
     * @see #getAxis(int)
     *
     * @since 1.0.14
     */
    public void setAxis(int index, ValueAxis axis, boolean notify) {
        ValueAxis existing = getAxis(index);
        if (existing != null) {
            existing.removeChangeListener(this);
        }
        if (axis != null) {
            axis.setPlot(this);
        }
        this.axes.set(index, axis);
        if (axis != null) {
            axis.configure();
            axis.addChangeListener(this);
        }
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the location of the primary axis.
     *
     * @return The location (never <code>null</code>).
     *
     * @see #setAxisLocation(PolarAxisLocation)
     *
     * @since 1.0.14
     */
    public PolarAxisLocation getAxisLocation() {
        return getAxisLocation(0);
    }

    /**
     * Returns the location for an axis.
     *
     * @param index  the axis index.
     *
     * @return The location (never <code>null</code>).
     *
     * @see #setAxisLocation(int, PolarAxisLocation)
     *
     * @since 1.0.14
     */
    public PolarAxisLocation getAxisLocation(int index) {
        PolarAxisLocation result = null;
        if (index < this.axisLocations.size()) {
            result = (PolarAxisLocation) this.axisLocations.get(index);
        }
        return result;
    }

    /**
     * Sets the location of the primary axis and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param location  the location (<code>null</code> not permitted).
     *
     * @see #getAxisLocation()
     *
     * @since 1.0.14
     */
    public void setAxisLocation(PolarAxisLocation location) {
        // delegate...
        setAxisLocation(0, location, true);
    }

    /**
     * Sets the location of the primary axis and, if requested, sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param location  the location (<code>null</code> not permitted).
     * @param notify  notify listeners?
     *
     * @see #getAxisLocation()
     *
     * @since 1.0.14
     */
    public void setAxisLocation(PolarAxisLocation location, boolean notify) {
        // delegate...
        setAxisLocation(0, location, notify);
    }

    /**
     * Sets the location for an axis and sends a {@link PlotChangeEvent}
     * to all registered listeners.
     *
     * @param index  the axis index.
     * @param location  the location (<code>null</code> not permitted).
     *
     * @see #getAxisLocation(int)
     *
     * @since 1.0.14
     */
    public void setAxisLocation(int index, PolarAxisLocation location) {
        // delegate...
        setAxisLocation(index, location, true);
    }

    /**
     * Sets the axis location for an axis and, if requested, sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param index  the axis index.
     * @param location  the location (<code>null</code> not permitted).
     * @param notify  notify listeners?
     *
     * @since 1.0.14
     */
    public void setAxisLocation(int index, PolarAxisLocation location,
            boolean notify) {
        ParamChecks.nullNotPermitted(location, "location");
        this.axisLocations.set(index, location);
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the number of domain axes.
     *
     * @return The axis count.
     *
     * @since 1.0.14
     **/
    public int getAxisCount() {
        return this.axes.size();
    }

    /**
     * Returns the primary dataset for the plot.
     *
     * @return The primary dataset (possibly <code>null</code>).
     *
     * @see #setDataset(XYDataset)
     */
    public XYDataset getDataset() {
        return getDataset(0);
    }

    /**
     * Returns the dataset with the specified index, if any.
     *
     * @param index  the dataset index.
     *
     * @return The dataset (possibly <code>null</code>).
     *
     * @see #setDataset(int, XYDataset)
     *
     * @since 1.0.14
     */
    public XYDataset getDataset(int index) {
        XYDataset result = null;
        if (index < this.datasets.size()) {
            result = (XYDataset) this.datasets.get(index);
        }
        return result;
    }

    /**
     * Sets the primary dataset for the plot, replacing the existing dataset
     * if there is one, and sends a {@code link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param dataset  the dataset (<code>null</code> permitted).
     *
     * @see #getDataset()
     */
    public void setDataset(XYDataset dataset) {
        setDataset(0, dataset);
    }

    /**
     * Sets a dataset for the plot, replacing the existing dataset at the same
     * index if there is one, and sends a {@code link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param index  the dataset index.
     * @param dataset  the dataset (<code>null</code> permitted).
     *
     * @see #getDataset(int)
     *
     * @since 1.0.14
     */
    public void setDataset(int index, XYDataset dataset) {
        XYDataset existing = getDataset(index);
        if (existing != null) {
            existing.removeChangeListener(this);
        }
        this.datasets.set(index, dataset);
        if (dataset != null) {
            dataset.addChangeListener(this);
        }

        // send a dataset change event to self...
        DatasetChangeEvent event = new DatasetChangeEvent(this, dataset);
        datasetChanged(event);
    }

    /**
     * Returns the number of datasets.
     *
     * @return The number of datasets.
     *
     * @since 1.0.14
     */
    public int getDatasetCount() {
        return this.datasets.size();
    }

    /**
     * Returns the index of the specified dataset, or <code>-1</code> if the
     * dataset does not belong to the plot.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     *
     * @return The index.
     *
     * @since 1.0.14
     */
    public int indexOf(XYDataset dataset) {
        int result = -1;
        for (int i = 0; i < this.datasets.size(); i++) {
            if (dataset == this.datasets.get(i)) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Returns the primary renderer.
     *
     * @return The renderer (possibly <code>null</code>).
     *
     * @see #setRenderer(PolarItemRenderer)
     */
    public PolarItemRenderer getRenderer() {
        return getRenderer(0);
    }

    /**
     * Returns the renderer at the specified index, if there is one.
     *
     * @param index  the renderer index.
     *
     * @return The renderer (possibly <code>null</code>).
     *
     * @see #setRenderer(int, PolarItemRenderer)
     *
     * @since 1.0.14
     */
    public PolarItemRenderer getRenderer(int index) {
        PolarItemRenderer result = null;
        if (index < this.renderers.size()) {
            result = (PolarItemRenderer) this.renderers.get(index);
        }
        return result;
    }

    /**
     * Sets the primary renderer, and notifies all listeners of a change to the
     * plot.  If the renderer is set to <code>null</code>, no data items will
     * be drawn for the corresponding dataset.
     *
     * @param renderer  the new renderer (<code>null</code> permitted).
     *
     * @see #getRenderer()
     */
    public void setRenderer(PolarItemRenderer renderer) {
        setRenderer(0, renderer);
    }

    /**
     * Sets a renderer and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param index  the index.
     * @param renderer  the renderer.
     *
     * @see #getRenderer(int)
     *
     * @since 1.0.14
     */
    public void setRenderer(int index, PolarItemRenderer renderer) {
        setRenderer(index, renderer, true);
    }

    /**
     * Sets a renderer and, if requested, sends a {@link PlotChangeEvent} to
     * all registered listeners.
     *
     * @param index  the index.
     * @param renderer  the renderer.
     * @param notify  notify listeners?
     *
     * @see #getRenderer(int)
     *
     * @since 1.0.14
     */
    public void setRenderer(int index, PolarItemRenderer renderer,
                            boolean notify) {
        PolarItemRenderer existing = getRenderer(index);
        if (existing != null) {
            existing.removeChangeListener(this);
        }
        this.renderers.set(index, renderer);
        if (renderer != null) {
            renderer.setPlot(this);
            renderer.addChangeListener(this);
        }
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the tick unit that controls the spacing of the angular grid
     * lines.
     *
     * @return The tick unit (never <code>null</code>).
     *
     * @since 1.0.10
     */
    public TickUnit getAngleTickUnit() {
        return this.angleTickUnit;
    }

    /**
     * Sets the tick unit that controls the spacing of the angular grid
     * lines, and sends a {@link PlotChangeEvent} to all registered listeners.
     *
     * @param unit  the tick unit (<code>null</code> not permitted).
     *
     * @since 1.0.10
     */
    public void setAngleTickUnit(TickUnit unit) {
        ParamChecks.nullNotPermitted(unit, "unit");
        this.angleTickUnit = unit;
        fireChangeEvent();
    }

    /**
     * Returns the offset that is used for all angles.
     *
     * @return The offset for the angles.
     * @since 1.0.14
     */
    public double getAngleOffset() {
        return this.angleOffset;
    }

    /**
     * Sets the offset that is used for all angles and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * This is useful to let 0 degrees be at the north, east, south or west
     * side of the chart.
     *
     * @param offset The offset
     * @since 1.0.14
     */
    public void setAngleOffset(double offset) {
        this.angleOffset = offset;
        fireChangeEvent();
    }

    /**
     * Get the direction for growing angle degrees.
     *
     * @return <code>true</code> if angle increases counterclockwise,
     *         <code>false</code> otherwise.
     * @since 1.0.14
     */
    public boolean isCounterClockwise() {
        return this.counterClockwise;
    }

    /**
     * Sets the flag for increasing angle degrees direction.
     *
     * <code>true</code> for counterclockwise, <code>false</code> for
     * clockwise.
     *
     * @param counterClockwise The flag.
     * @since 1.0.14
     */
    public void setCounterClockwise(boolean counterClockwise)
    {
        this.counterClockwise = counterClockwise;
    }

    /**
     * Returns a flag that controls whether or not the angle labels are visible.
     *
     * @return A boolean.
     *
     * @see #setAngleLabelsVisible(boolean)
     */
    public boolean isAngleLabelsVisible() {
        return this.angleLabelsVisible;
    }

    /**
     * Sets the flag that controls whether or not the angle labels are visible,
     * and sends a {@link PlotChangeEvent} to all registered listeners.
     *
     * @param visible  the flag.
     *
     * @see #isAngleLabelsVisible()
     */
    public void setAngleLabelsVisible(boolean visible) {
        if (this.angleLabelsVisible != visible) {
            this.angleLabelsVisible = visible;
            fireChangeEvent();
        }
    }

    /**
     * Returns the font used to display the angle labels.
     *
     * @return A font (never <code>null</code>).
     *
     * @see #setAngleLabelFont(Font)
     */
    public Font getAngleLabelFont() {
        return this.angleLabelFont;
    }

    /**
     * Sets the font used to display the angle labels and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param font  the font (<code>null</code> not permitted).
     *
     * @see #getAngleLabelFont()
     */
    public void setAngleLabelFont(Font font) {
        ParamChecks.nullNotPermitted(font, "font");
        this.angleLabelFont = font;
        fireChangeEvent();
    }

    /**
     * Returns the paint used to display the angle labels.
     *
     * @return A paint (never <code>null</code>).
     *
     * @see #setAngleLabelPaint(Paint)
     */
    public Paint getAngleLabelPaint() {
        return this.angleLabelPaint;
    }

    /**
     * Sets the paint used to display the angle labels and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void setAngleLabelPaint(Paint paint) {
        ParamChecks.nullNotPermitted(paint, "paint");
        this.angleLabelPaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns <code>true</code> if the angular gridlines are visible, and
     * <code>false</code> otherwise.
     *
     * @return <code>true</code> or <code>false</code>.
     *
     * @see #setAngleGridlinesVisible(boolean)
     */
    public boolean isAngleGridlinesVisible() {
        return this.angleGridlinesVisible;
    }

    /**
     * Sets the flag that controls whether or not the angular grid-lines are
     * visible.
     * <p>
     * If the flag value is changed, a {@link PlotChangeEvent} is sent to all
     * registered listeners.
     *
     * @param visible  the new value of the flag.
     *
     * @see #isAngleGridlinesVisible()
     */
    public void setAngleGridlinesVisible(boolean visible) {
        if (this.angleGridlinesVisible != visible) {
            this.angleGridlinesVisible = visible;
            fireChangeEvent();
        }
    }

    /**
     * Returns the stroke for the grid-lines (if any) plotted against the
     * angular axis.
     *
     * @return The stroke (possibly <code>null</code>).
     *
     * @see #setAngleGridlineStroke(Stroke)
     */
    public Stroke getAngleGridlineStroke() {
        return this.angleGridlineStroke;
    }

    /**
     * Sets the stroke for the grid lines plotted against the angular axis and
     * sends a {@link PlotChangeEvent} to all registered listeners.
     * <p>
     * If you set this to <code>null</code>, no grid lines will be drawn.
     *
     * @param stroke  the stroke (<code>null</code> permitted).
     *
     * @see #getAngleGridlineStroke()
     */
    public void setAngleGridlineStroke(Stroke stroke) {
        this.angleGridlineStroke = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the paint for the grid lines (if any) plotted against the
     * angular axis.
     *
     * @return The paint (possibly <code>null</code>).
     *
     * @see #setAngleGridlinePaint(Paint)
     */
    public Paint getAngleGridlinePaint() {
        return this.angleGridlinePaint;
    }

    /**
     * Sets the paint for the grid lines plotted against the angular axis.
     * <p>
     * If you set this to <code>null</code>, no grid lines will be drawn.
     *
     * @param paint  the paint (<code>null</code> permitted).
     *
     * @see #getAngleGridlinePaint()
     */
    public void setAngleGridlinePaint(Paint paint) {
        this.angleGridlinePaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns <code>true</code> if the radius axis grid is visible, and
     * <code>false</code> otherwise.
     *
     * @return <code>true</code> or <code>false</code>.
     *
     * @see #setRadiusGridlinesVisible(boolean)
     */
    public boolean isRadiusGridlinesVisible() {
        return this.radiusGridlinesVisible;
    }

    /**
     * Sets the flag that controls whether or not the radius axis grid lines
     * are visible.
     * <p>
     * If the flag value is changed, a {@link PlotChangeEvent} is sent to all
     * registered listeners.
     *
     * @param visible  the new value of the flag.
     *
     * @see #isRadiusGridlinesVisible()
     */
    public void setRadiusGridlinesVisible(boolean visible) {
        if (this.radiusGridlinesVisible != visible) {
            this.radiusGridlinesVisible = visible;
            fireChangeEvent();
        }
    }

    /**
     * Returns the stroke for the grid lines (if any) plotted against the
     * radius axis.
     *
     * @return The stroke (possibly <code>null</code>).
     *
     * @see #setRadiusGridlineStroke(Stroke)
     */
    public Stroke getRadiusGridlineStroke() {
        return this.radiusGridlineStroke;
    }

    /**
     * Sets the stroke for the grid lines plotted against the radius axis and
     * sends a {@link PlotChangeEvent} to all registered listeners.
     * <p>
     * If you set this to <code>null</code>, no grid lines will be drawn.
     *
     * @param stroke  the stroke (<code>null</code> permitted).
     *
     * @see #getRadiusGridlineStroke()
     */
    public void setRadiusGridlineStroke(Stroke stroke) {
        this.radiusGridlineStroke = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the paint for the grid lines (if any) plotted against the radius
     * axis.
     *
     * @return The paint (possibly <code>null</code>).
     *
     * @see #setRadiusGridlinePaint(Paint)
     */
    public Paint getRadiusGridlinePaint() {
        return this.radiusGridlinePaint;
    }

    /**
     * Sets the paint for the grid lines plotted against the radius axis and
     * sends a {@link PlotChangeEvent} to all registered listeners.
     * <p>
     * If you set this to <code>null</code>, no grid lines will be drawn.
     *
     * @param paint  the paint (<code>null</code> permitted).
     *
     * @see #getRadiusGridlinePaint()
     */
    public void setRadiusGridlinePaint(Paint paint) {
        this.radiusGridlinePaint = paint;
        fireChangeEvent();
    }

    /**
     * Return the current value of the flag indicating if radial minor
     * grid-lines will be drawn or not.
     *
     * @return Returns <code>true</code> if radial minor grid-lines are drawn.
     * @since 1.0.15
     */
    public boolean isRadiusMinorGridlinesVisible() {
        return this.radiusMinorGridlinesVisible;
    }

    /**
     * Set the flag that determines if radial minor grid-lines will be drawn.
     *
     * @param flag <code>true</code> to draw the radial minor grid-lines,
     *             <code>false</code> to hide them.
     * @since 1.0.15
     */
    public void setRadiusMinorGridlinesVisible(boolean flag) {
        this.radiusMinorGridlinesVisible = flag;
    }

    /**
     * Returns the margin around the plot area.
     *
     * @return The actual margin in pixels.
     *
     * @since 1.0.14
     */
    public int getMargin() {
        return this.margin;
    }

    /**
     * Set the margin around the plot area and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param margin The new margin in pixels.
     *
     * @since 1.0.14
     */
    public void setMargin(int margin) {
        this.margin = margin;
        fireChangeEvent();
    }

    /**
     * Returns the fixed legend items, if any.
     *
     * @return The legend items (possibly <code>null</code>).
     *
     * @see #setFixedLegendItems(LegendItemCollection)
     *
     * @since 1.0.14
     */
    public LegendItemCollection getFixedLegendItems() {
        return this.fixedLegendItems;
    }

    /**
     * Sets the fixed legend items for the plot.  Leave this set to
     * <code>null</code> if you prefer the legend items to be created
     * automatically.
     *
     * @param items  the legend items (<code>null</code> permitted).
     *
     * @see #getFixedLegendItems()
     *
     * @since 1.0.14
     */
    public void setFixedLegendItems(LegendItemCollection items) {
        this.fixedLegendItems = items;
        fireChangeEvent();
    }

    /**
     * Add text to be displayed in the lower right hand corner and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param text  the text to display (<code>null</code> not permitted).
     *
     * @see #removeCornerTextItem(String)
     */
    public void addCornerTextItem(String text) {
        ParamChecks.nullNotPermitted(text, "text");
        this.cornerTextItems.add(text);
        fireChangeEvent();
    }

    /**
     * Remove the given text from the list of corner text items and
     * sends a {@link PlotChangeEvent} to all registered listeners.
     *
     * @param text  the text to remove (<code>null</code> ignored).
     *
     * @see #addCornerTextItem(String)
     */
    public void removeCornerTextItem(String text) {
        boolean removed = this.cornerTextItems.remove(text);
        if (removed) {
            fireChangeEvent();
        }
    }

    /**
     * Clear the list of corner text items and sends a {@link PlotChangeEvent}
     * to all registered listeners.
     *
     * @see #addCornerTextItem(String)
     * @see #removeCornerTextItem(String)
     */
    public void clearCornerTextItems() {
        if (this.cornerTextItems.size() > 0) {
            this.cornerTextItems.clear();
            fireChangeEvent();
        }
    }

    /**
     * Generates a list of tick values for the angular tick marks.
     *
     * @return A list of {@link NumberTick} instances.
     *
     * @since 1.0.10
     */
    protected List refreshAngleTicks() {
        List ticks = new ArrayList();
        for (double currentTickVal = 0.0; currentTickVal < 360.0;
                currentTickVal += this.angleTickUnit.getSize()) {

            TextAnchor ta = calculateTextAnchor(currentTickVal);
            NumberTick tick = new NumberTick(new Double(currentTickVal),
                this.angleTickUnit.valueToString(currentTickVal),
                ta, TextAnchor.CENTER, 0.0);
            ticks.add(tick);
        }
        return ticks;
    }

    /**
     * Calculate the text position for the given degrees.
     *
     * @param angleDegrees  the angle in degrees.
     * 
     * @return The optimal text anchor.
     * @since 1.0.14
     */
    protected TextAnchor calculateTextAnchor(double angleDegrees) {
        TextAnchor ta = TextAnchor.CENTER;

        // normalize angle
        double offset = this.angleOffset;
        while (offset < 0.0) {
            offset += 360.0;
        }
        double normalizedAngle = (((this.counterClockwise ? -1 : 1)
                * angleDegrees) + offset) % 360;
        while (this.counterClockwise && (normalizedAngle < 0.0)) {
            normalizedAngle += 360.0;
        }

        if (normalizedAngle == 0.0) {
            ta = TextAnchor.CENTER_LEFT;
        }
        else if (normalizedAngle > 0.0 && normalizedAngle < 90.0) {
            ta = TextAnchor.TOP_LEFT;
        }
        else if (normalizedAngle == 90.0) {
            ta = TextAnchor.TOP_CENTER;
        }
        else if (normalizedAngle > 90.0 && normalizedAngle < 180.0) {
            ta = TextAnchor.TOP_RIGHT;
        }
        else if (normalizedAngle == 180) {
            ta = TextAnchor.CENTER_RIGHT;
        }
        else if (normalizedAngle > 180.0 && normalizedAngle < 270.0) {
            ta = TextAnchor.BOTTOM_RIGHT;
        }
        else if (normalizedAngle == 270) {
            ta = TextAnchor.BOTTOM_CENTER;
        }
        else if (normalizedAngle > 270.0 && normalizedAngle < 360.0) {
            ta = TextAnchor.BOTTOM_LEFT;
        }
        return ta;
    }

    /**
     * Maps a dataset to a particular axis.  All data will be plotted
     * against axis zero by default, no mapping is required for this case.
     *
     * @param index  the dataset index (zero-based).
     * @param axisIndex  the axis index.
     *
     * @since 1.0.14
     */
    public void mapDatasetToAxis(int index, int axisIndex) {
        List axisIndices = new java.util.ArrayList(1);
        axisIndices.add(new Integer(axisIndex));
        mapDatasetToAxes(index, axisIndices);
    }

    /**
     * Maps the specified dataset to the axes in the list.  Note that the
     * conversion of data values into Java2D space is always performed using
     * the first axis in the list.
     *
     * @param index  the dataset index (zero-based).
     * @param axisIndices  the axis indices (<code>null</code> permitted).
     *
     * @since 1.0.14
     */
    public void mapDatasetToAxes(int index, List axisIndices) {
        if (index < 0) {
            throw new IllegalArgumentException("Requires 'index' >= 0.");
        }
        checkAxisIndices(axisIndices);
        Integer key = new Integer(index);
        this.datasetToAxesMap.put(key, new ArrayList(axisIndices));
        // fake a dataset change event to update axes...
        datasetChanged(new DatasetChangeEvent(this, getDataset(index)));
    }

    /**
     * This method is used to perform argument checking on the list of
     * axis indices passed to mapDatasetToAxes().
     *
     * @param indices  the list of indices (<code>null</code> permitted).
     */
    private void checkAxisIndices(List indices) {
        // axisIndices can be:
        // 1.  null;
        // 2.  non-empty, containing only Integer objects that are unique.
        if (indices == null) {
            return;  // OK
        }
        int count = indices.size();
        if (count == 0) {
            throw new IllegalArgumentException("Empty list not permitted.");
        }
        HashSet set = new HashSet();
        for (int i = 0; i < count; i++) {
            Object item = indices.get(i);
            if (!(item instanceof Integer)) {
                throw new IllegalArgumentException(
                        "Indices must be Integer instances.");
            }
            if (set.contains(item)) {
                throw new IllegalArgumentException("Indices must be unique.");
            }
            set.add(item);
        }
    }

    /**
     * Returns the axis for a dataset.
     *
     * @param index  the dataset index.
     *
     * @return The axis.
     *
     * @since 1.0.14
     */
    public ValueAxis getAxisForDataset(int index) {
        ValueAxis valueAxis;
        List axisIndices = (List) this.datasetToAxesMap.get(
                new Integer(index));
        if (axisIndices != null) {
            // the first axis in the list is used for data <--> Java2D
            Integer axisIndex = (Integer) axisIndices.get(0);
            valueAxis = getAxis(axisIndex.intValue());
        }
        else {
            valueAxis = getAxis(0);
        }
        return valueAxis;
    }

    /**
     * Returns the index of the given axis.
     *
     * @param axis  the axis.
     *
     * @return The axis index or -1 if axis is not used in this plot.
     *
     * @since 1.0.14
     */
    public int getAxisIndex(ValueAxis axis) {
        int result = this.axes.indexOf(axis);
        if (result < 0) {
            // try the parent plot
            Plot parent = getParent();
            if (parent instanceof PolarPlot) {
                PolarPlot p = (PolarPlot) parent;
                result = p.getAxisIndex(axis);
            }
        }
        return result;
    }

    /**
     * Returns the index of the specified renderer, or <code>-1</code> if the
     * renderer is not assigned to this plot.
     *
     * @param renderer  the renderer (<code>null</code> permitted).
     *
     * @return The renderer index.
     *
     * @since 1.0.14
     */
    public int getIndexOf(PolarItemRenderer renderer) {
        return this.renderers.indexOf(renderer);
    }

    /**
     * Draws the plot on a Java 2D graphics device (such as the screen or a
     * printer).
     * <P>
     * This plot relies on a {@link PolarItemRenderer} to draw each
     * item in the plot.  This allows the visual representation of the data to
     * be changed easily.
     * <P>
     * The optional info argument collects information about the rendering of
     * the plot (dimensions, tooltip information etc).  Just pass in
     * <code>null</code> if you do not need this information.
     *
     * @param g2  the graphics device.
     * @param area  the area within which the plot (including axes and
     *              labels) should be drawn.
     * @param anchor  the anchor point (<code>null</code> permitted).
     * @param parentState  ignored.
     * @param info  collects chart drawing information (<code>null</code>
     *              permitted).
     */
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
            PlotState parentState, PlotRenderingInfo info) {

        // if the plot area is too small, just return...
        boolean b1 = (area.getWidth() <= MINIMUM_WIDTH_TO_DRAW);
        boolean b2 = (area.getHeight() <= MINIMUM_HEIGHT_TO_DRAW);
        if (b1 || b2) {
            return;
        }

        // record the plot area...
        if (info != null) {
            info.setPlotArea(area);
        }

        // adjust the drawing area for the plot insets (if any)...
        RectangleInsets insets = getInsets();
        insets.trim(area);

        Rectangle2D dataArea = area;
        if (info != null) {
            info.setDataArea(dataArea);
        }

        // draw the plot background and axes...
        drawBackground(g2, dataArea);
        int axisCount = this.axes.size();
        AxisState state = null;
        for (int i = 0; i < axisCount; i++) {
            ValueAxis axis = getAxis(i);
            if (axis != null) {
                PolarAxisLocation location
                        = (PolarAxisLocation) this.axisLocations.get(i);
                AxisState s = this.drawAxis(axis, location, g2, dataArea);
                if (i == 0) {
                    state = s;
                }
            }
        }

        // now for each dataset, get the renderer and the appropriate axis
        // and render the dataset...
        Shape originalClip = g2.getClip();
        Composite originalComposite = g2.getComposite();

        g2.clip(dataArea);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                getForegroundAlpha()));
        this.angleTicks = refreshAngleTicks();
        drawGridlines(g2, dataArea, this.angleTicks, state.getTicks());
        render(g2, dataArea, info);
        g2.setClip(originalClip);
        g2.setComposite(originalComposite);
        drawOutline(g2, dataArea);
        drawCornerTextItems(g2, dataArea);
    }

    /**
     * Draws the corner text items.
     *
     * @param g2  the drawing surface.
     * @param area  the area.
     */
    protected void drawCornerTextItems(Graphics2D g2, Rectangle2D area) {
        if (this.cornerTextItems.isEmpty()) {
            return;
        }

        g2.setColor(Color.black);
        double width = 0.0;
        double height = 0.0;
        for (Iterator it = this.cornerTextItems.iterator(); it.hasNext();) {
            String msg = (String) it.next();
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D bounds = TextUtilities.getTextBounds(msg, g2, fm);
            width = Math.max(width, bounds.getWidth());
            height += bounds.getHeight();
        }

        double xadj = ANNOTATION_MARGIN * 2.0;
        double yadj = ANNOTATION_MARGIN;
        width += xadj;
        height += yadj;

        double x = area.getMaxX() - width;
        double y = area.getMaxY() - height;
        g2.drawRect((int) x, (int) y, (int) width, (int) height);
        x += ANNOTATION_MARGIN;
        for (Iterator it = this.cornerTextItems.iterator(); it.hasNext();) {
            String msg = (String) it.next();
            Rectangle2D bounds = TextUtilities.getTextBounds(msg, g2,
                    g2.getFontMetrics());
            y += bounds.getHeight();
            g2.drawString(msg, (int) x, (int) y);
        }
    }

    /**
     * Draws the axis with the specified index.
     *
     * @param axis  the axis.
     * @param location  the axis location.
     * @param g2  the graphics target.
     * @param plotArea  the plot area.
     *
     * @return The axis state.
     *
     * @since 1.0.14
     */
    protected AxisState drawAxis(ValueAxis axis, PolarAxisLocation location,
            Graphics2D g2, Rectangle2D plotArea) {

        double centerX = plotArea.getCenterX();
        double centerY = plotArea.getCenterY();
        double r = Math.min(plotArea.getWidth() / 2.0,
                plotArea.getHeight() / 2.0) - this.margin;
        double x = centerX - r;
        double y = centerY - r;

        Rectangle2D dataArea = null;
        AxisState result = null;
        if (location == PolarAxisLocation.NORTH_RIGHT) {
            dataArea = new Rectangle2D.Double(x, y, r, r);
            result = axis.draw(g2, centerX, plotArea, dataArea,
                    RectangleEdge.RIGHT, null);
        }
        else if (location == PolarAxisLocation.NORTH_LEFT) {
            dataArea = new Rectangle2D.Double(centerX, y, r, r);
            result = axis.draw(g2, centerX, plotArea, dataArea,
                    RectangleEdge.LEFT, null);
        }
        else if (location == PolarAxisLocation.SOUTH_LEFT) {
            dataArea = new Rectangle2D.Double(centerX, centerY, r, r);
            result = axis.draw(g2, centerX, plotArea, dataArea,
                    RectangleEdge.LEFT, null);
        }
        else if (location == PolarAxisLocation.SOUTH_RIGHT) {
            dataArea = new Rectangle2D.Double(x, centerY, r, r);
            result = axis.draw(g2, centerX, plotArea, dataArea,
                    RectangleEdge.RIGHT, null);
        }
        else if (location == PolarAxisLocation.EAST_ABOVE) {
            dataArea = new Rectangle2D.Double(centerX, centerY, r, r);
            result = axis.draw(g2, centerY, plotArea, dataArea,
                    RectangleEdge.TOP, null);
        }
        else if (location == PolarAxisLocation.EAST_BELOW) {
            dataArea = new Rectangle2D.Double(centerX, y, r, r);
            result = axis.draw(g2, centerY, plotArea, dataArea,
                    RectangleEdge.BOTTOM, null);
        }
        else if (location == PolarAxisLocation.WEST_ABOVE) {
            dataArea = new Rectangle2D.Double(x, centerY, r, r);
            result = axis.draw(g2, centerY, plotArea, dataArea,
                    RectangleEdge.TOP, null);
        }
        else if (location == PolarAxisLocation.WEST_BELOW) {
            dataArea = new Rectangle2D.Double(x, y, r, r);
            result = axis.draw(g2, centerY, plotArea, dataArea,
                    RectangleEdge.BOTTOM, null);
        }

        return result;
    }

    /**
     * Draws a representation of the data within the dataArea region, using the
     * current m_Renderer.
     *
     * @param g2  the graphics device.
     * @param dataArea  the region in which the data is to be drawn.
     * @param info  an optional object for collection dimension
     *              information (<code>null</code> permitted).
     */
    protected void render(Graphics2D g2, Rectangle2D dataArea,
            PlotRenderingInfo info) {

        // now get the data and plot it (the visual representation will depend
        // on the m_Renderer that has been set)...
        boolean hasData = false;
        int datasetCount = this.datasets.size();
        for (int i = datasetCount - 1; i >= 0; i--) {
            XYDataset dataset = getDataset(i);
            if (dataset == null) {
                continue;
            }
            PolarItemRenderer renderer = getRenderer(i);
            if (renderer == null) {
                continue;
            }
            if (!DatasetUtilities.isEmptyOrNull(dataset)) {
                hasData = true;
                int seriesCount = dataset.getSeriesCount();
                for (int series = 0; series < seriesCount; series++) {
                    renderer.drawSeries(g2, dataArea, info, this, dataset,
                            series);
                }
            }
        }
        if (!hasData) {
            drawNoDataMessage(g2, dataArea);
        }
    }

    /**
     * Draws the gridlines for the plot, if they are visible.
     *
     * @param g2  the graphics device.
     * @param dataArea  the data area.
     * @param angularTicks  the ticks for the angular axis.
     * @param radialTicks  the ticks for the radial axis.
     */
    protected void drawGridlines(Graphics2D g2, Rectangle2D dataArea,
                                 List angularTicks, List radialTicks) {

        PolarItemRenderer renderer = getRenderer();
        // no renderer, no gridlines...
        if (renderer == null) {
            return;
        }

        // draw the domain grid lines, if any...
        if (isAngleGridlinesVisible()) {
            Stroke gridStroke = getAngleGridlineStroke();
            Paint gridPaint = getAngleGridlinePaint();
            if ((gridStroke != null) && (gridPaint != null)) {
                renderer.drawAngularGridLines(g2, this, angularTicks,
                        dataArea);
            }
        }

        // draw the radius grid lines, if any...
        if (isRadiusGridlinesVisible()) {
            Stroke gridStroke = getRadiusGridlineStroke();
            Paint gridPaint = getRadiusGridlinePaint();
            if ((gridStroke != null) && (gridPaint != null)) {
                List ticks = buildRadialTicks(radialTicks);
                renderer.drawRadialGridLines(g2, this, getAxis(),
                        ticks, dataArea);
            }
        }
    }

    /**
     * Create a list of ticks based on the given list and plot properties.
     * Only ticks of a specific type may be in the result list.
     *
     * @param allTicks A list of all available ticks for the primary axis.
     *        <code>null</code> not permitted.
     * @return Ticks to use for radial gridlines.
     * @since 1.0.15
     */
    protected List buildRadialTicks(List allTicks)
    {
        List ticks = new ArrayList();
        Iterator it = allTicks.iterator();
        while (it.hasNext()) {
            ValueTick tick = (ValueTick) it.next();
            if (isRadiusMinorGridlinesVisible() ||
                    TickType.MAJOR.equals(tick.getTickType())) {
                ticks.add(tick);
            }
        }
        return ticks;
    }

    /**
     * Zooms the axis ranges by the specified percentage about the anchor point.
     *
     * @param percent  the amount of the zoom.
     */
    public void zoom(double percent) {
        for (int axisIdx = 0; axisIdx < getAxisCount(); axisIdx++) {
            final ValueAxis axis = getAxis(axisIdx);
            if (axis != null) {
                if (percent > 0.0) {
                    double radius = axis.getUpperBound();
                    double scaledRadius = radius * percent;
                    axis.setUpperBound(scaledRadius);
                    axis.setAutoRange(false);
                }
                else {
                    axis.setAutoRange(true);
                }
            }
        }
    }

    /**
     * A utility method that returns a list of datasets that are mapped to a
     * particular axis.
     *
     * @param axisIndex  the axis index (<code>null</code> not permitted).
     *
     * @return A list of datasets.
     *
     * @since 1.0.14
     */
    private List getDatasetsMappedToAxis(Integer axisIndex) {
        ParamChecks.nullNotPermitted(axisIndex, "axisIndex");
        List result = new ArrayList();
        for (int i = 0; i < this.datasets.size(); i++) {
            List mappedAxes = (List) this.datasetToAxesMap.get(new Integer(i));
            if (mappedAxes == null) {
                if (axisIndex.equals(ZERO)) {
                    result.add(this.datasets.get(i));
                }
            }
            else {
                if (mappedAxes.contains(axisIndex)) {
                    result.add(this.datasets.get(i));
                }
            }
        }
        return result;
    }

    /**
     * Returns the range for the specified axis.
     *
     * @param axis  the axis.
     *
     * @return The range.
     */
    public Range getDataRange(ValueAxis axis) {
        Range result = null;
        int axisIdx = getAxisIndex(axis);
        List mappedDatasets = new ArrayList();

        if (axisIdx >= 0) {
            mappedDatasets = getDatasetsMappedToAxis(new Integer(axisIdx));
        }

        // iterate through the datasets that map to the axis and get the union
        // of the ranges.
        Iterator iterator = mappedDatasets.iterator();
        int datasetIdx = -1;
        while (iterator.hasNext()) {
            datasetIdx++;
            XYDataset d = (XYDataset) iterator.next();
            if (d != null) {
                // FIXME better ask the renderer instead of DatasetUtilities
                result = Range.combine(result,
                        DatasetUtilities.findRangeBounds(d));
            }
        }

        return result;
    }

    /**
     * Receives notification of a change to the plot's m_Dataset.
     * <P>
     * The axis ranges are updated if necessary.
     *
     * @param event  information about the event (not used here).
     */
    public void datasetChanged(DatasetChangeEvent event) {
        for (int i = 0; i < this.axes.size(); i++) {
            final ValueAxis axis = (ValueAxis) this.axes.get(i);
            if (axis != null) {
                axis.configure();
            }
        }
        if (getParent() != null) {
            getParent().datasetChanged(event);
        }
        else {
            super.datasetChanged(event);
        }
    }

    /**
     * Notifies all registered listeners of a property change.
     * <P>
     * One source of property change events is the plot's m_Renderer.
     *
     * @param event  information about the property change.
     */
    public void rendererChanged(RendererChangeEvent event) {
        fireChangeEvent();
    }

    /**
     * Returns the legend items for the plot.  Each legend item is generated by
     * the plot's m_Renderer, since the m_Renderer is responsible for the visual
     * representation of the data.
     *
     * @return The legend items.
     */
    public LegendItemCollection getLegendItems() {
        if (this.fixedLegendItems != null) {
            return this.fixedLegendItems;
        }
        LegendItemCollection result = new LegendItemCollection();
        int count = this.datasets.size();
        for (int datasetIndex = 0; datasetIndex < count; datasetIndex++) {
            XYDataset dataset = getDataset(datasetIndex);
            PolarItemRenderer renderer = getRenderer(datasetIndex);
            if (dataset != null && renderer != null) {
                int seriesCount = dataset.getSeriesCount();
                for (int i = 0; i < seriesCount; i++) {
                    LegendItem item = renderer.getLegendItem(i);
                    result.add(item);
                }
            }
        }
        return result;
    }

    /**
     * Tests this plot for equality with another object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return <code>true</code> or <code>false</code>.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PolarPlot)) {
            return false;
        }
        PolarPlot that = (PolarPlot) obj;
        if (!this.axes.equals(that.axes)) {
            return false;
        }
        if (!this.axisLocations.equals(that.axisLocations)) {
            return false;
        }
        if (!this.renderers.equals(that.renderers)) {
            return false;
        }
        if (!this.angleTickUnit.equals(that.angleTickUnit)) {
            return false;
        }
        if (this.angleGridlinesVisible != that.angleGridlinesVisible) {
            return false;
        }
        if (this.angleOffset != that.angleOffset)
        {
            return false;
        }
        if (this.counterClockwise != that.counterClockwise)
        {
            return false;
        }
        if (this.angleLabelsVisible != that.angleLabelsVisible) {
            return false;
        }
        if (!this.angleLabelFont.equals(that.angleLabelFont)) {
            return false;
        }
        if (!PaintUtilities.equal(this.angleLabelPaint, that.angleLabelPaint)) {
            return false;
        }
        if (!ObjectUtilities.equal(this.angleGridlineStroke,
                that.angleGridlineStroke)) {
            return false;
        }
        if (!PaintUtilities.equal(
            this.angleGridlinePaint, that.angleGridlinePaint
        )) {
            return false;
        }
        if (this.radiusGridlinesVisible != that.radiusGridlinesVisible) {
            return false;
        }
        if (!ObjectUtilities.equal(this.radiusGridlineStroke,
                that.radiusGridlineStroke)) {
            return false;
        }
        if (!PaintUtilities.equal(this.radiusGridlinePaint,
                that.radiusGridlinePaint)) {
            return false;
        }
        if (this.radiusMinorGridlinesVisible !=
            that.radiusMinorGridlinesVisible) {
            return false;
        }
        if (!this.cornerTextItems.equals(that.cornerTextItems)) {
            return false;
        }
        if (this.margin != that.margin) {
            return false;
        }
        if (!ObjectUtilities.equal(this.fixedLegendItems,
                that.fixedLegendItems)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a clone of the plot.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  this can occur if some component of
     *         the plot cannot be cloned.
     */
    public Object clone() throws CloneNotSupportedException {

        PolarPlot clone = (PolarPlot) super.clone();
        clone.axes = (ObjectList) ObjectUtilities.clone(this.axes);
        for (int i = 0; i < this.axes.size(); i++) {
            ValueAxis axis = (ValueAxis) this.axes.get(i);
            if (axis != null) {
                ValueAxis clonedAxis = (ValueAxis) axis.clone();
                clone.axes.set(i, clonedAxis);
                clonedAxis.setPlot(clone);
                clonedAxis.addChangeListener(clone);
            }
        }

        // the datasets are not cloned, but listeners need to be added...
        clone.datasets = (ObjectList) ObjectUtilities.clone(this.datasets);
        for (int i = 0; i < clone.datasets.size(); ++i) {
            XYDataset d = getDataset(i);
            if (d != null) {
                d.addChangeListener(clone);
            }
        }

        clone.renderers = (ObjectList) ObjectUtilities.clone(this.renderers);
        for (int i = 0; i < this.renderers.size(); i++) {
            PolarItemRenderer renderer2 = (PolarItemRenderer) this.renderers.get(i);
            if (renderer2 instanceof PublicCloneable) {
                PublicCloneable pc = (PublicCloneable) renderer2;
                PolarItemRenderer rc = (PolarItemRenderer) pc.clone();
                clone.renderers.set(i, rc);
                rc.setPlot(clone);
                rc.addChangeListener(clone);
            }
        }

        clone.cornerTextItems = new ArrayList(this.cornerTextItems);

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
        SerialUtilities.writeStroke(this.angleGridlineStroke, stream);
        SerialUtilities.writePaint(this.angleGridlinePaint, stream);
        SerialUtilities.writeStroke(this.radiusGridlineStroke, stream);
        SerialUtilities.writePaint(this.radiusGridlinePaint, stream);
        SerialUtilities.writePaint(this.angleLabelPaint, stream);
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
        this.angleGridlineStroke = SerialUtilities.readStroke(stream);
        this.angleGridlinePaint = SerialUtilities.readPaint(stream);
        this.radiusGridlineStroke = SerialUtilities.readStroke(stream);
        this.radiusGridlinePaint = SerialUtilities.readPaint(stream);
        this.angleLabelPaint = SerialUtilities.readPaint(stream);

        int rangeAxisCount = this.axes.size();
        for (int i = 0; i < rangeAxisCount; i++) {
            Axis axis = (Axis) this.axes.get(i);
            if (axis != null) {
                axis.setPlot(this);
                axis.addChangeListener(this);
            }
        }
        int datasetCount = this.datasets.size();
        for (int i = 0; i < datasetCount; i++) {
            Dataset dataset = (Dataset) this.datasets.get(i);
            if (dataset != null) {
                dataset.addChangeListener(this);
            }
        }
        int rendererCount = this.renderers.size();
        for (int i = 0; i < rendererCount; i++) {
            PolarItemRenderer renderer = (PolarItemRenderer) this.renderers.get(i);
            if (renderer != null) {
                renderer.addChangeListener(this);
            }
        }
    }

    /**
     * This method is required by the {@link Zoomable} interface, but since
     * the plot does not have any domain axes, it does nothing.
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     */
    public void zoomDomainAxes(double factor, PlotRenderingInfo state,
                               Point2D source) {
        // do nothing
    }

    /**
     * This method is required by the {@link Zoomable} interface, but since
     * the plot does not have any domain axes, it does nothing.
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     * @param useAnchor  use source point as zoom anchor?
     *
     * @since 1.0.7
     */
    public void zoomDomainAxes(double factor, PlotRenderingInfo state,
                               Point2D source, boolean useAnchor) {
        // do nothing
    }

    /**
     * This method is required by the {@link Zoomable} interface, but since
     * the plot does not have any domain axes, it does nothing.
     *
     * @param lowerPercent  the new lower bound.
     * @param upperPercent  the new upper bound.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     */
    public void zoomDomainAxes(double lowerPercent, double upperPercent,
                               PlotRenderingInfo state, Point2D source) {
        // do nothing
    }

    /**
     * Multiplies the range on the range axis/axes by the specified factor.
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     */
    public void zoomRangeAxes(double factor, PlotRenderingInfo state,
                              Point2D source) {
        zoom(factor);
    }

    /**
     * Multiplies the range on the range axis by the specified factor.
     *
     * @param factor  the zoom factor.
     * @param info  the plot rendering info.
     * @param source  the source point (in Java2D space).
     * @param useAnchor  use source point as zoom anchor?
     *
     * @see #zoomDomainAxes(double, PlotRenderingInfo, Point2D, boolean)
     *
     * @since 1.0.7
     */
    public void zoomRangeAxes(double factor, PlotRenderingInfo info,
                              Point2D source, boolean useAnchor) {
        // get the source coordinate - this plot has always a VERTICAL
        // orientation
        final double sourceX = source.getX();

        for (int axisIdx = 0; axisIdx < getAxisCount(); axisIdx++) {
            final ValueAxis axis = getAxis(axisIdx);
            if (axis != null) {
                if (useAnchor) {
                    double anchorX = axis.java2DToValue(sourceX,
                            info.getDataArea(), RectangleEdge.BOTTOM);
                    axis.resizeRange(factor, anchorX);
                }
                else {
                    axis.resizeRange(factor);
                }
            }
        }
    }

    /**
     * Zooms in on the range axes.
     *
     * @param lowerPercent  the new lower bound.
     * @param upperPercent  the new upper bound.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     */
    public void zoomRangeAxes(double lowerPercent, double upperPercent,
                              PlotRenderingInfo state, Point2D source) {
        zoom((upperPercent + lowerPercent) / 2.0);
    }

    /**
     * Returns <code>false</code> always.
     *
     * @return <code>false</code> always.
     */
    public boolean isDomainZoomable() {
        return false;
    }

    /**
     * Returns <code>true</code> to indicate that the range axis is zoomable.
     *
     * @return <code>true</code>.
     */
    public boolean isRangeZoomable() {
        return true;
    }

    /**
     * Returns the orientation of the plot.
     *
     * @return The orientation.
     */
    public PlotOrientation getOrientation() {
        return PlotOrientation.HORIZONTAL;
    }

    /**
     * Translates a (theta, radius) pair into Java2D coordinates.  If
     * <code>radius</code> is less than the lower bound of the axis, then
     * this method returns the centre point.
     *
     * @param angleDegrees  the angle in degrees.
     * @param radius  the radius.
     * @param axis  the axis.
     * @param dataArea  the data area.
     *
     * @return A point in Java2D space.
     *
     * @since 1.0.14
     */
    public Point translateToJava2D(double angleDegrees, double radius,
            ValueAxis axis, Rectangle2D dataArea) {

        if (counterClockwise) {
            angleDegrees = -angleDegrees;
        }
        double radians = Math.toRadians(angleDegrees + this.angleOffset);

        double minx = dataArea.getMinX() + this.margin;
        double maxx = dataArea.getMaxX() - this.margin;
        double miny = dataArea.getMinY() + this.margin;
        double maxy = dataArea.getMaxY() - this.margin;

        double halfWidth = (maxx - minx) / 2.0;
        double halfHeight = (maxy - miny) / 2.0;

        double midX = minx + halfWidth;
        double midY = miny + halfHeight;

        double l = Math.min(halfWidth, halfHeight);
        Rectangle2D quadrant = new Rectangle2D.Double(midX, midY, l, l);

        double axisMin = axis.getLowerBound();
        double adjustedRadius = Math.max(radius, axisMin);

        double length = axis.valueToJava2D(adjustedRadius, quadrant, RectangleEdge.BOTTOM) - midX;
        float x = (float) (midX + Math.cos(radians) * length);
        float y = (float) (midY + Math.sin(radians) * length);

        int ix = Math.round(x);
        int iy = Math.round(y);

        Point p = new Point(ix, iy);
        return p;

    }

    /**
     * Translates a (theta, radius) pair into Java2D coordinates.  If
     * <code>radius</code> is less than the lower bound of the axis, then
     * this method returns the centre point.
     *
     * @param angleDegrees  the angle in degrees.
     * @param radius  the radius.
     * @param dataArea  the data area.
     *
     * @return A point in Java2D space.
     *
     * @deprecated Since 1.0.14, use {@link #translateToJava2D(double, double,
     * org.jfree.chart.axis.ValueAxis, java.awt.geom.Rectangle2D)} instead.
     */
    public Point translateValueThetaRadiusToJava2D(double angleDegrees,
            double radius, Rectangle2D dataArea) {

        return translateToJava2D(angleDegrees, radius, getAxis(), dataArea);
    }

    /**
     * Returns the upper bound of the radius axis.
     *
     * @return The upper bound.
     *
     * @deprecated Since 1.0.14, use {@link #getAxis()} and call the
     *         getUpperBound() method.
     */
    public double getMaxRadius() {
        return getAxis().getUpperBound();
    }

    /**
     * Returns the number of series in the dataset for this plot.  If the
     * dataset is <code>null</code>, the method returns 0.
     *
     * @return The series count.
     *
     * @deprecated Since 1.0.14, grab a reference to the dataset and check
     *     the series count directly.
     */
    public int getSeriesCount() {
        int result = 0;
        XYDataset dataset = getDataset(0);
        if (dataset != null) {
            result = dataset.getSeriesCount();
        }
        return result;
    }

    /**
     * A utility method for drawing the axes.
     *
     * @param g2  the graphics device.
     * @param plotArea  the plot area.
     * @param dataArea  the data area.
     *
     * @return A map containing the axis states.
     *
     * @deprecated As of version 1.0.14, this method is no longer used.
     */
    protected AxisState drawAxis(Graphics2D g2, Rectangle2D plotArea,
                                 Rectangle2D dataArea) {
        return getAxis().draw(g2, dataArea.getMinY(), plotArea, dataArea,
                RectangleEdge.TOP, null);
    }

}

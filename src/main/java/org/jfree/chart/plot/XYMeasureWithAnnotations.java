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
 * ---------------------
 * XYMeasureWithAnnotations.java
 * ---------------------
 * (C) Copyright 2021, by Object Refinery Limited.
 *
 * Original Author:  Yuri Blankenstein (for ESI TNO);
 *
 */
package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYMeasurementAnnotation;
import org.jfree.chart.annotations.XYMeasurementAnnotation.Orientation;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.geom.Point2DNumber;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.util.Args;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 * Allows the user to measure differences between points in the domain or range
 * of an XYPlot. A user can start a measurement by clicking the mouse according
 * to {@link #getMarkPredicate()}. A second click of the mouse according to
 * {@link #getMarkPredicate()} will add an {@link XYMeasurementAnnotation} to
 * the plot.
 */
public class XYMeasureWithAnnotations extends CrosshairOverlay
        implements ChartMouseListener {
    private static final long serialVersionUID = -7754581177397547320L;
    
    /** An enumeration that specifies which axis is measured. */
    public enum MeasurementAxis {
        /** Measure the {@link XYPlot#getDomainAxis() domain axis}*/
        DOMAIN,
        /** Measure the {@link XYPlot#getRangeAxis() range axis}*/
        RANGE
    };

    /** An enumeration that specifies which axis is measured. */
    private final MeasurementAxis measurementAxis;
    /** A crosshair that illustrates what will be marked if the user starts a measurement. */
    private final Crosshair movingCrosshair;
    /** A crosshair that illustrates the start of a measurement. */
    private final Crosshair markedCrosshair;
    /** The start of a measurement. */
    private transient Point2DNumber markedPoint;
    /** The {@link MouseEvent} {@link Predicate} to filter mark events. */
    private Predicate<MouseEvent> markPredicate = InputEvent::isAltDown;
    /** The label provider to use for creating annotation labels. */
    private BiFunction<Number, Number, String> annotationLabelProvider = 
            (first, second) -> String.format("Difference: %f", 
                    Math.abs(second.doubleValue() - first.doubleValue()));

    /**
     * Registers this utility to a {@link ChartPanel chartPanel} for a specific
     * {@link MeasurementAxis axis}.
     * 
     * @param chartPanel the chart panel to register to.
     * @param axis       the axis to measure.
     * @return the created {@link XYMeasureWithAnnotations measure utility} that
     *         was registered to {@code chartPanel}
     */
    public static XYMeasureWithAnnotations addToChartPanel(
            ChartPanel chartPanel, MeasurementAxis axis) {
        XYMeasureWithAnnotations instance = new XYMeasureWithAnnotations(axis);
        instance.addToChartPanel(chartPanel);
        return instance;
    }

    /**
     * Creates this measurement overlay and starts listening for user mouse
     * events.
     * 
     * @param axis the axis to measure.
     */
    public XYMeasureWithAnnotations(MeasurementAxis axis) {
        Args.nullNotPermitted(axis, "axis");
        measurementAxis = axis;
        movingCrosshair = new Crosshair(Double.NaN, Color.GRAY,
                new BasicStroke(1f));
        markedCrosshair = new Crosshair(Double.NaN, Color.BLACK,
                new BasicStroke(1f));
        addCrosshair(movingCrosshair);
        addCrosshair(markedCrosshair);
    }
    
    private void addCrosshair(Crosshair crosshair) {
        switch (measurementAxis) {
        case DOMAIN:
            addDomainCrosshair(crosshair);
            break;
        case RANGE:
            addRangeCrosshair(crosshair);
            break;
        }
    }

    /**
     * Registers this utility to a {@link ChartPanel chartPanel}.
     * 
     * @param chartPanel the chart panel to register to.
     */
    public void addToChartPanel(ChartPanel chartPanel) {
        Args.nullNotPermitted(chartPanel, "chartPanel");
        chartPanel.addOverlay(this);
        chartPanel.addChartMouseListener(this);
    }

    /**
     * Unregisters this utility from a {@link ChartPanel chartPanel}.
     * 
     * @param chartPanel the chart panel to unregister from.
     */
    public void removeFromChartPanel(ChartPanel chartPanel) {
        Args.nullNotPermitted(chartPanel, "chartPanel");
        chartPanel.removeChartMouseListener(this);
        chartPanel.removeOverlay(this);
    }
    
    /**
     * Sets the label provider to use for creating annotation labels.
     * 
     * @param annotationLabelProvider the label provider for the annotation
     */
    public void setAnnotationLabelProvider(
            BiFunction<Number, Number, String> annotationLabelProvider) {
        Args.nullNotPermitted(annotationLabelProvider, "annotationLabelProvider");
        this.annotationLabelProvider = annotationLabelProvider;
    }

    /**
     * Returns the {@link MouseEvent} {@link Predicate} to filter mark events.
     * 
     * @return the {@link MouseEvent} {@link Predicate} to filter mark events.
     */
    public Predicate<MouseEvent> getMarkPredicate() {
        return markPredicate;
    }

    /**
     * Sets the {@link MouseEvent} {@link Predicate} to filter mark events.
     * 
     * @param markPredicate the {@link MouseEvent} {@link Predicate} to filter
     *                      mark events.
     */
    public void setMarkPredicate(Predicate<MouseEvent> markPredicate) {
        Args.nullNotPermitted(markPredicate, "markPredicate");
        this.markPredicate = markPredicate;
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
        Point2D crosshairPoint = getCrosshairPoint(event);
        double crosshairValue = Double.NaN;
        if (crosshairPoint != null) {
            if (measurementAxis == MeasurementAxis.DOMAIN) {
                crosshairValue = crosshairPoint.getX();
            } else {
                crosshairValue = crosshairPoint.getY();
            }
        }
        movingCrosshair.setValue(crosshairValue);
        event.getTrigger().consume();
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        if (markPredicate.test(event.getTrigger())) {
            Point2DNumber crosshairPoint = getCrosshairPoint(event);
            if (null != crosshairPoint && null != markedPoint) {
                // Add the annotation
                addAnnotation(event.getChart().getXYPlot(), markedPoint,
                        crosshairPoint, event.getTrigger());
                markedPoint = null;
                markedCrosshair.setValue(Double.NaN);
            } else {
                markedPoint = crosshairPoint;
                double crosshairValue = Double.NaN;
                if (crosshairPoint != null) {
                    if (measurementAxis == MeasurementAxis.DOMAIN) {
                        crosshairValue = crosshairPoint.getX();
                    } else {
                        crosshairValue = crosshairPoint.getY();
                    }
                }
                markedCrosshair.setValue(crosshairValue);
            }
            event.getTrigger().consume();
        }
    }

    /**
     * Calculates the point to snap the measurement to, returns {@code null} if
     * not applicable.
     * 
     * @param event contains the current mouse location
     * @return the point to snap the measurement to
     */
    protected Point2DNumber getCrosshairPoint(ChartMouseEvent event) {
        if (!(event.getEntity() instanceof XYItemEntity)) {
            return null;
        }

        XYItemEntity entity = (XYItemEntity) event.getEntity();
        XYDataset dataset = entity.getDataset();
        if (dataset instanceof IntervalXYDataset) {
            IntervalXYDataset intervalDataset = (IntervalXYDataset) dataset;
            
            int mouseX = event.getTrigger().getX();
            int mouseY = event.getTrigger().getY();
            Rectangle2D entityBounds = entity.getArea().getBounds2D();
            PlotOrientation plotOrientation = event.getChart().getXYPlot().getOrientation();
            
            Number x;
            Number y;
            if (measurementAxis == MeasurementAxis.DOMAIN) {
                boolean snapDomainToStart = plotOrientation == PlotOrientation.VERTICAL ?
                    mouseX < entityBounds.getCenterX() :
                    mouseY > entityBounds.getCenterY();
                x = snapDomainToStart
                        ? intervalDataset.getStartX(entity.getSeriesIndex(), entity.getItem())
                        : intervalDataset.getEndX(entity.getSeriesIndex(), entity.getItem());
                y = intervalDataset.getY(entity.getSeriesIndex(), entity.getItem());
            } else {
                x = intervalDataset.getX(entity.getSeriesIndex(), entity.getItem());
                boolean snapRangeToStart = plotOrientation == PlotOrientation.VERTICAL ?
                        mouseY > entityBounds.getCenterY():
                        mouseX < entityBounds.getCenterX();
                y = snapRangeToStart ?
                        intervalDataset.getStartY(entity.getSeriesIndex(), entity.getItem()) :
                        intervalDataset.getEndY(entity.getSeriesIndex(), entity.getItem());
            }
            return new Point2DNumber(x, y);
        } else {
            return new Point2DNumber(
                    dataset.getX(entity.getSeriesIndex(), entity.getItem()),
                    dataset.getY(entity.getSeriesIndex(), entity.getItem()));
        }
    }

    /**
     * This method is called when a measurement has been made by the user. This
     * method adds an annotation to the {@code plot}.
     * 
     * @param plot   the plot for the annotation
     * @param first  the first measurement point
     * @param second the second measurement point
     * @param event  the mouse event of the second mouse click.
     */
    protected void addAnnotation(XYPlot plot, Point2DNumber first,
            Point2DNumber second, MouseEvent event) {
        String label;
        XYMeasurementAnnotation.Orientation orientation;
        if (measurementAxis == MeasurementAxis.DOMAIN) {
            label = createMeasurementLabel(first.getX(), second.getX(), event);
            orientation = plot.getOrientation() == PlotOrientation.VERTICAL
                    ? Orientation.HORIZONTAL
                    : Orientation.VERTICAL;
        } else {
            label = createMeasurementLabel(first.getY(), second.getY(), event);
            orientation = plot.getOrientation() == PlotOrientation.VERTICAL
                    ? Orientation.VERTICAL
                    : Orientation.HORIZONTAL;
        }
        plot.addAnnotation(
                createMeasurementAnnotation(orientation, label, first, second));
    }

    /**
     * Creates a label for a measurement. By default this call is delegated to
     * the {@link #setAnnotationLabelProvider(BiFunction) label provider}.
     * 
     * @param firstValue  the first measurement value
     * @param secondValue the second measurement value
     * @param event       the mouse event of the second mouse click.
     * @return the label to put use for the measurement annotation, should not
     *         be {@code null}.
     */
    protected String createMeasurementLabel(Number firstValue,
            Number secondValue, MouseEvent event) {
        return annotationLabelProvider.apply(firstValue, secondValue);
    }

    /**
     * Creates an annotation for a measurement. By default an
     * {@link XYMeasurementAnnotation} will be created.
     * 
     * @param orientation orientation to use for the
     *                    {@link XYMeasurementAnnotation}
     * @param label       the annotation text
     * @param first       the first measurement point
     * @param second      the second measurement point
     * @return the created measurement annotation
     */
    protected XYAnnotation createMeasurementAnnotation(
            XYMeasurementAnnotation.Orientation orientation, String label,
            Point2DNumber first, Point2DNumber second) {
        return new XYMeasurementAnnotation(orientation, label, first, second);
    }
}

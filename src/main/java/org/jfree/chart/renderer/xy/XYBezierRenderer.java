/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * XYBezierRenderer.java
 * ---------------------
 * (C) Copyright 2021-present, by Javier Robes and Contributors.
 *
 * Original Author:  Javier Robes;
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.util.GradientPaintTransformer;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.util.StandardGradientPaintTransformer;
import org.jfree.chart.internal.Args;
import org.jfree.data.xy.XYDataset;


/**
 * A renderer that connects data points with Bezier cubic curves and/or
 * draws shapes at each data point.  This renderer is designed for use with
 * the {@link XYPlot} class.
 */
public class XYBezierRenderer extends XYLineAndShapeRenderer {

	/**
     * An enumeration of the fill types for the renderer.
     * 
     * @since 1.0.17
     */
    public enum FillType {
       
        /** No fill. */
        NONE,
        
        /** Fill down to zero. */
        TO_ZERO,
        
        /** Fill to the lower bound. */
        TO_LOWER_BOUND,
        
        /** Fill to the upper bound. */
        TO_UPPER_BOUND
    }
    
    /**
     * Represents state information that applies to a single rendering of
     * a chart.
     */
    public static class XYBezierState extends State {
        
        /** The area to fill under the curve. */
        public GeneralPath fillArea;
        
        /** The points. */
        public List<Point2D> points;
        
        /**
         * Creates a new state instance.
         * 
         * @param info  the plot rendering info. 
         */
        public XYBezierState(PlotRenderingInfo info) {
            super(info);
            this.fillArea = new GeneralPath();
            this.points = new ArrayList<>();
        }
    }
    
    /**
     * Resolution of Bezier curves (number of line segments between points)
     */
    private int precision;
    
    /**
     *  Tension defines how sharply does the curve bends
     */
    private double tension;

    /**
     * A flag that can be set to specify 
     * to fill the area under the Bezier curve.
     */
    private FillType fillType;

    /** The gradient transformer. */
    private GradientPaintTransformer gradientPaintTransformer;
    
    /**
     * Creates a new instance with the precision attribute defaulting to 5,
     * the tension attribute defaulting to 2  
     * and no fill of the area 'under' the Bezier curve.
     */
    public XYBezierRenderer() {
        this(5, 25, FillType.NONE);
    }

    /**
     * Creates a new renderer with the specified precision and tension
     * and no fill of the area 'under' (between '0' and) the Bezier curve.
     *
     * @param precision  the number of points between data items.
     * @param tension  value to define how sharply the curve bends
     */
    public XYBezierRenderer(int precision, double tension) {
        this(precision, tension ,FillType.NONE);
    }

    /**
     * Creates a new renderer with the specified precision
     * and specified fill of the area 'under' (between '0' and) the Bezier curve.
     *
     * @param precision  the number of points between data items.
     * @param tension  value to define how sharply the Bezier curve bends
     * @param fillType  the type of fill beneath the curve ({@code null} 
     *     not permitted).
     * 
     * @since 1.0.17
     */
    public XYBezierRenderer(int precision, double tension, FillType fillType) {
        super();
        if (precision <= 0) {
            throw new IllegalArgumentException("Requires precision > 0.");
        }
        if (tension <= 0) {
            throw new IllegalArgumentException("Requires precision > 0.");
        }
        Args.nullNotPermitted(fillType, "fillType");
        this.precision = precision;
        this.tension = tension;
        this.fillType = fillType;
        this.gradientPaintTransformer = new StandardGradientPaintTransformer();
    }

    /**
     * Returns the number of line segments used to approximate the Bezier
     * curve between data points.
     *
     * @return The number of line segments.
     *
     * @see #setPrecision(int)
     */
    public int getPrecision() {
        return this.precision;
    }

    /**
     * Set the resolution of Bezier curves and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param p  number of line segments between points (must be &gt; 0).
     *
     * @see #getPrecision()
     */
    public void setPrecision(int p) {
        if (p <= 0) {
            throw new IllegalArgumentException("Requires p > 0.");
        }
        this.precision = p;
        fireChangeEvent();
    }

    /**
     * Returns the value of the tension which defines how sharply 
     * does the curve bends
     *
     * @return The value of tesion.
     *
     * @see #setTension(double)
     */
    public double getTension() {
        return this.tension;
    }

    /**
     * Set the value of the tension which defines how sharply 
     * does the curve bends and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param t  value of tension (must be &gt; 0).
     *
     * @see #getTension()
     */
    public void setTension(double t) {
        if (t <= 0) {
            throw new IllegalArgumentException("Requires tension > 0.");
        }
        this.tension = t;
        fireChangeEvent();
    }
    
    /**
     * Returns the type of fill that the renderer draws beneath the curve.
     *
     * @return The type of fill (never {@code null}).
     *
     * @see #setFillType(FillType) 
     * 
     * @since 1.0.17
     */
    public FillType getFillType() {
        return this.fillType;
    }

    /**
     * Set the fill type and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param fillType   the fill type ({@code null} not permitted).
     *
     * @see #getFillType()
     * 
     * @since 1.0.17
     */
    public void setFillType(FillType fillType) {
        this.fillType = fillType;
        fireChangeEvent();
    }

    /**
     * Returns the gradient paint transformer, or {@code null}.
     * 
     * @return The gradient paint transformer (possibly {@code null}).
     * 
     * @since 1.0.17
     */
    public GradientPaintTransformer getGradientPaintTransformer() {
        return this.gradientPaintTransformer;
    }
    
    /**
     * Sets the gradient paint transformer and sends a 
     * {@link RendererChangeEvent} to all registered listeners.
     * 
     * @param gpt  the transformer ({@code null} permitted).
     * 
     * @since 1.0.17
     */
    public void setGradientPaintTransformer(GradientPaintTransformer gpt) {
        this.gradientPaintTransformer = gpt;
        fireChangeEvent();
    }
    
    /**
     * Initialises the renderer.
     * <P>
     * This method will be called before the first item is rendered, giving the
     * renderer an opportunity to initialise any state information it wants to
     * maintain.  The renderer can do nothing if it chooses.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area inside the axes.
     * @param plot  the plot.
     * @param data  the data.
     * @param info  an optional info collection object to return data back to
     *              the caller.
     *
     * @return The renderer state.
     */
    @Override
    public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea,
            XYPlot plot, XYDataset data, PlotRenderingInfo info) {

        setDrawSeriesLineAsPath(true);
        XYBezierState state = new XYBezierState(info);
        state.setProcessVisibleItemsOnly(false);
        return state;
    }
  
    
    /**
     * Draws the item (first pass). This method draws the lines
     * connecting the items. Instead of drawing separate lines,
     * a GeneralPath is constructed and drawn at the end of
     * the series painting.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param plot  the plot (can be used to obtain standard color information
     *              etc).
     * @param dataset  the dataset.
     * @param pass  the pass.
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     * @param xAxis  the domain axis.
     * @param yAxis  the range axis.
     * @param dataArea  the area within which the data is being drawn.
     */
    @Override
    protected void drawPrimaryLineAsPath(XYItemRendererState state,
            Graphics2D g2, XYPlot plot, XYDataset dataset, int pass,
            int series, int item, ValueAxis xAxis, ValueAxis yAxis,
            Rectangle2D dataArea) {

        XYBezierState s = (XYBezierState) state;
        RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
        RectangleEdge yAxisLocation = plot.getRangeAxisEdge();

        // get the data points
        double x1 = dataset.getXValue(series, item);
        double y1 = dataset.getYValue(series, item);
        double transX1 = xAxis.valueToJava2D(x1, dataArea, xAxisLocation);
        double transY1 = yAxis.valueToJava2D(y1, dataArea, yAxisLocation);

        // Collect points
        if (!Double.isNaN(transX1) && !Double.isNaN(transY1)) {
            Point2D p = plot.getOrientation() == PlotOrientation.HORIZONTAL 
                ? new Point2D.Float((float) transY1, (float) transX1) 
                : new Point2D.Float((float) transX1, (float) transY1);
            if (!s.points.contains(p)) {
                s.points.add(p);
            }
        }
        
        if (item == dataset.getItemCount(series) - 1) {     // construct path
            if (s.points.size() > 1) {
                Point2D origin;
                if (this.fillType == FillType.TO_ZERO) {
                    float xz = (float) xAxis.valueToJava2D(0, dataArea, 
                            yAxisLocation);
                    float yz = (float) yAxis.valueToJava2D(0, dataArea, 
                            yAxisLocation);
                    origin = plot.getOrientation() == PlotOrientation.HORIZONTAL
                            ? new Point2D.Float(yz, xz) 
                            : new Point2D.Float(xz, yz);
                } else if (this.fillType == FillType.TO_LOWER_BOUND) {
                    float xlb = (float) xAxis.valueToJava2D(
                            xAxis.getLowerBound(), dataArea, xAxisLocation);
                    float ylb = (float) yAxis.valueToJava2D(
                            yAxis.getLowerBound(), dataArea, yAxisLocation);
                    origin = plot.getOrientation() == PlotOrientation.HORIZONTAL
                            ? new Point2D.Float(ylb, xlb) 
                            : new Point2D.Float(xlb, ylb);
                } else {// fillType == TO_UPPER_BOUND
                    float xub = (float) xAxis.valueToJava2D(
                            xAxis.getUpperBound(), dataArea, xAxisLocation);
                    float yub = (float) yAxis.valueToJava2D(
                            yAxis.getUpperBound(), dataArea, yAxisLocation);
                    origin = plot.getOrientation() == PlotOrientation.HORIZONTAL
                            ? new Point2D.Float(yub, xub)
                            : new Point2D.Float(xub, yub);
                }
                
                // we need at least two points to draw something
                Point2D cp0 = s.points.get(0);
                s.seriesPath.moveTo(cp0.getX(), cp0.getY());
                if (this.fillType != FillType.NONE) {
                    if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
                        s.fillArea.moveTo(origin.getX(), cp0.getY());
                    } else {
                        s.fillArea.moveTo(cp0.getX(), origin.getY());
                    }
                    s.fillArea.lineTo(cp0.getX(), cp0.getY());
                }
                if (s.points.size() == 2) {
                    // we need at least 3 points to Bezier. Draw simple line
                    // for two points
                    Point2D cp1 = s.points.get(1);
                    if (this.fillType != FillType.NONE) {
                        s.fillArea.lineTo(cp1.getX(), cp1.getY());
                        s.fillArea.lineTo(cp1.getX(), origin.getY());
                        s.fillArea.closePath();
                    }
                    s.seriesPath.lineTo(cp1.getX(), cp1.getY());
                }
                else if (s.points.size() == 3) {
                    // with 3 points only initial and end Bezier curves are required.

                	Point2D[] pInitial = getInitalPoints(s);
                	addBezierPointsToSeriesPath(pInitial, s);
                	Point2D[] pFinal = getFinalPoints(s);
                	addBezierPointsToSeriesPath(pFinal, s);
                	
                }
                else {
                    // construct Bezier curve
                    int np = s.points.size(); // number of points
    				for(int i = 0; i < np - 1; i++) {
    					if(i == 0) {
    						// 3 points, 2 lines (initial an final Bezier curves)
    						Point2D[] initial3Points = new Point2D[3];
    						initial3Points[0] = s.points.get(0);
    						initial3Points[1] = s.points.get(1);
    						initial3Points[2] = s.points.get(2);
    						Point2D[] pInitial = calcSegmentPointsInitial(initial3Points);
    	                	addBezierPointsToSeriesPath(pInitial, s);
    					}
    					if(i == np - 2) {
    						Point2D[] final3Points = new Point2D[4];
    						final3Points[1] = s.points.get(np-3);
    						final3Points[2] = s.points.get(np-2);
    						final3Points[3] = s.points.get(np-1); 
    						// No need for final3Points[0]. Not required
    						Point2D[] pFinal = calcSegmentPointsFinal(final3Points);
    						addBezierPointsToSeriesPath(pFinal, s);
    					}
    					if ((i != 0) && (i != (np - 2))){
        					Point2D[] original4Points = new Point2D[4];
        					original4Points[0] = s.points.get(i - 1);
        					original4Points[1] = s.points.get(i);
        					original4Points[2] = s.points.get(i + 1);
        					original4Points[3] = s.points.get(i + 2);
        					Point2D[] pMedium = calculateSegmentPoints(original4Points);
    	                	addBezierPointsToSeriesPath(pMedium, s);
    					}
    				}                   
                }
                // Add last point @ y=0 for fillPath and close path
                if (this.fillType != FillType.NONE) {
                    if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
                        s.fillArea.lineTo(origin.getX(), s.points.get(
                                s.points.size() - 1).getY());
                    } else {
                        s.fillArea.lineTo(s.points.get(
                                s.points.size() - 1).getX(), origin.getY());
                    }
                    s.fillArea.closePath();
                }
                // fill under the curve...
                if (this.fillType != FillType.NONE) {
                    Paint fp = getSeriesFillPaint(series);
                    if (this.gradientPaintTransformer != null 
                            && fp instanceof GradientPaint) {
                        GradientPaint gp = this.gradientPaintTransformer
                                .transform((GradientPaint) fp, s.fillArea);
                        g2.setPaint(gp);
                    } else {
                        g2.setPaint(fp);                        
                    }
                    g2.fill(s.fillArea);
                    s.fillArea.reset();
                }
                // then draw the line...
                drawFirstPassShape(g2, pass, series, item, s.seriesPath);
            }
            // reset points vector
            s.points = new ArrayList<>();
        }
    }
    
	private void addBezierPointsToSeriesPath(Point2D[] segmentPoints, XYBezierState s) {
		double x;
		double y;
		for (int t = 0 ; t <= this.precision; t++) {
			double k = (double)t / this.precision;
			double r = 1- k;
					
			x = Math.pow(r, 3) * segmentPoints[0].getX() + 3 * k * Math.pow(r, 2) * segmentPoints[1].getX()
					+ 3 * Math.pow(k, 2) * (1 - k) * segmentPoints[2].getX() + Math.pow(k, 3) * segmentPoints[3].getX();
			y = Math.pow(r, 3) * segmentPoints[0].getY() + 3 * k * Math.pow(r, 2) * segmentPoints[1].getY()
					+ 3 * Math.pow(k, 2) * (1 - k) * segmentPoints[2].getY() + Math.pow(k, 3) * segmentPoints[3].getY();
			s.seriesPath.lineTo(x, y);
            if (this.fillType != FillType.NONE) {
                s.fillArea.lineTo(x, y);
            }
		}
	}
	
	private Point2D[] getFinalPoints(XYBezierState s) {
		Point2D[] final3Points = new Point2D[4];
		final3Points[1] = s.points.get(0);
		final3Points[2] = s.points.get(1);
		final3Points[3] = s.points.get(2); 
		// No need for final3Points[0]. Not required
		Point2D[] pFinal = calcSegmentPointsFinal(final3Points);//TENSION = 1.5
		return pFinal;
	}

	private Point2D[] getInitalPoints(XYBezierState s) {
		Point2D[] initial3Points = new Point2D[3];
		initial3Points[0] = s.points.get(0);
		initial3Points[1] = s.points.get(1);
		initial3Points[2] = s.points.get(2);
		Point2D[] pInitial = calcSegmentPointsInitial(initial3Points);// TENSION = 1.5
		return pInitial;
	}

	private Point2D[] calculateSegmentPoints(Point2D[] original4Points) {
		Point2D[] points = new Point2D[4];
		points[0] = original4Points[1];
		points[3] = original4Points[2];
		for(int i = 1; i < 3; i++) {
			Point2D aux1 = calcUnitaryVector(original4Points[i-1], original4Points[i]);
			Point2D aux2 = calcUnitaryVector(original4Points[i+1], original4Points[i]);
			Point2D aux3 = calcUnitaryVector(aux2, aux1);
			
			double x = original4Points[i].getX() + Math.pow(-1.0, i+1) * tension * aux3.getX();
			double y = original4Points[i].getY() + Math.pow(-1.0, i+1) * tension * aux3.getY();
			points[i] = new Point2D.Double(x, y);
		}
		return points;
	}
	
	private Point2D[] calcSegmentPointsInitial(Point2D[] original3P) {
		Point2D[] points = new Point2D[4];
		points[0] = original3P[0];// Endpoint 1
		points[3] = original3P[1];// Endpoint 2
		// Control point 1
		Point2D auxInitial = calcUnitaryVector(original3P[0], original3P[1]);
		points[1] = original3P[0];// new Point2D.Double(x0, y0);
		// Control point 2
		Point2D aux2 = calcUnitaryVector(original3P[2], original3P[1]);
		Point2D aux3 = calcUnitaryVector(auxInitial, aux2);
		double x = original3P[1].getX() + tension * aux3.getX();
		double y = original3P[1].getY() + tension * aux3.getY();
		points[2] = new Point2D.Double(x, y);
		return points;
	}
	
	private Point2D[] calcSegmentPointsFinal(Point2D[] original3P) {
		/*
		 * Each segment is defined by its two endpoints and two control points. A
		 * control point determines the tangent at the corresponding endpoint.
		 */
		Point2D[] points = new Point2D[4];
		points[0] = original3P[2];// Endpoint 1
		points[3] = original3P[3];// Endpoint 2
		// Control point 2: points[2]
		Point2D auxInitial = calcUnitaryVector(original3P[3], original3P[2]);
		points[2] = original3P[3];// new Point2D.Double(x0, y0);
		// Control point 1
		Point2D aux1 = calcUnitaryVector(original3P[3], original3P[2]);
		Point2D aux2 = calcUnitaryVector(original3P[1], original3P[2]);
		Point2D aux3 = calcUnitaryVector(aux1, aux2);
		double x = original3P[2].getX() + tension * aux3.getX();
		double y = original3P[2].getY() + tension * aux3.getY();
		points[1] = new Point2D.Double(x, y);
		return points;
	}
  
	private Point2D calcUnitaryVector(Point2D pOrigin, Point2D pEnd) {
		double module = Math.sqrt(Math.pow(pEnd.getX() - pOrigin.getX(), 2) + 
				Math.pow(pEnd.getY() - pOrigin.getY(), 2));
		if (module == 0) {
			return null;
		}
		return new Point2D.Double((pEnd.getX() - pOrigin.getX()) / module, 
				(pEnd.getY() - pOrigin.getY()) /module);
	}   
    
 
    /**
     * Tests this renderer for equality with an arbitrary object.
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
        if (!(obj instanceof XYBezierRenderer)) {
            return false;
        }
        XYBezierRenderer that = (XYBezierRenderer) obj;
        if (this.precision != that.precision) {
            return false;
        }
        if (this.fillType != that.fillType) {
            return false;
        }
        if (!Objects.equals(this.gradientPaintTransformer, that.gradientPaintTransformer)) {
            return false;
        }
        return super.equals(obj);
    }
}
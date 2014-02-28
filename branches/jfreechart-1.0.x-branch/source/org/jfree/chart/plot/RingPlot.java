/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * -------------
 * RingPlot.java
 * -------------
 * (C) Copyright 2004-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limtied);
 * Contributor(s):   Christoph Beck (bug 2121818);
 *
 * Changes
 * -------
 * 08-Nov-2004 : Version 1 (DG);
 * 22-Feb-2005 : Renamed DonutPlot --> RingPlot (DG);
 * 06-Jun-2005 : Added default constructor and fixed equals() method to handle
 *               GradientPaint (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 20-Dec-2005 : Fixed problem with entity shape (bug 1386328) (DG);
 * 27-Sep-2006 : Updated drawItem() method for new lookup methods (DG);
 * 12-Oct-2006 : Added configurable section depth (DG);
 * 14-Feb-2007 : Added notification in setSectionDepth() method (DG);
 * 23-Sep-2008 : Fix for bug 2121818 by Christoph Beck (DG);
 * 13-Jul-2009 : Added support for shadow generator (DG);
 * 11-Oct-2011 : Check sectionOutlineVisible - bug 3237879 (DG);
 * 02-Jul-2013 : Use ParamChecks (DG);
 * 28-Feb-2014 : Add center text feature (DG);
 *
 */

package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;

import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.chart.util.LineUtilities;
import org.jfree.chart.util.ParamChecks;
import org.jfree.data.general.PieDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.Rotation;
import org.jfree.util.ShapeUtilities;
import org.jfree.util.UnitType;

/**
 * A customised pie plot that leaves a hole in the middle.
 */
public class RingPlot extends PiePlot implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 1556064784129676620L;

    /** The center text mode. */
    private CenterTextMode centerTextMode = CenterTextMode.NONE;
    
    /** 
     * Text to display in the middle of the chart (used for 
     * CenterTextMode.FIXED). 
     */
    private String centerText;
    
    /**
     * The formatter used when displaying the first data value from the
     * dataset (CenterTextMode.VALUE).
     */
    private Format centerTextFormatter = new DecimalFormat("0.00");
    
    /** The font used to display the center text. */
    private Font centerTextFont;
    
    /** The color used to display the center text. */
    private Color centerTextColor;
    
    /**
     * A flag that controls whether or not separators are drawn between the
     * sections of the chart.
     */
    private boolean separatorsVisible;

    /** The stroke used to draw separators. */
    private transient Stroke separatorStroke;

    /** The paint used to draw separators. */
    private transient Paint separatorPaint;

    /**
     * The length of the inner separator extension (as a percentage of the
     * depth of the sections).
     */
    private double innerSeparatorExtension;

    /**
     * The length of the outer separator extension (as a percentage of the
     * depth of the sections).
     */
    private double outerSeparatorExtension;

    /**
     * The depth of the section as a percentage of the diameter.
     */
    private double sectionDepth;

    /**
     * Creates a new plot with a <code>null</code> dataset.
     */
    public RingPlot() {
        this(null);
    }

    /**
     * Creates a new plot for the specified dataset.
     *
     * @param dataset  the dataset (<code>null</code> permitted).
     */
    public RingPlot(PieDataset dataset) {
        super(dataset);
        this.centerTextMode = CenterTextMode.NONE;
        this.centerText = null;
        this.centerTextFormatter = new DecimalFormat("0.00");
        this.centerTextFont = DEFAULT_LABEL_FONT;
        this.centerTextColor = Color.BLACK;
        this.separatorsVisible = true;
        this.separatorStroke = new BasicStroke(0.5f);
        this.separatorPaint = Color.gray;
        this.innerSeparatorExtension = 0.20;  // 20%
        this.outerSeparatorExtension = 0.20;  // 20%
        this.sectionDepth = 0.20; // 20%
    }

    /**
     * Returns the mode for displaying text in the center of the plot.  The
     * default value is {@link CenterTextMode#NONE} therefore no text
     * will be displayed by default.
     * 
     * @return The mode (never <code>null</code>).
     * 
     * @since 1.0.18
     */
    public CenterTextMode getCenterTextMode() {
        return this.centerTextMode;
    }
    
    /**
     * Sets the mode for displaying text in the center of the plot and sends 
     * a change event to all registered listeners.  For
     * {@link CenterTextMode#FIXED}, the display text will come from the 
     * <code>centerText</code> attribute (see {@link #getCenterText()}).
     * For {@link CenterTextMode#VALUE}, the center text will be the value from
     * the first section in the dataset.
     * 
     * @param mode  the mode (<code>null</code> not permitted).
     * 
     * @since 1.0.18
     */
    public void setCenterTextMode(CenterTextMode mode) {
        ParamChecks.nullNotPermitted(mode, "mode");
        this.centerTextMode = mode;
        fireChangeEvent();
    }
    
    /**
     * Returns the text to display in the center of the plot when the mode
     * is {@link CenterTextMode#FIXED}.
     * 
     * @return The text (possibly <code>null</code>).
     * 
     * @since 1.0.18.
     */
    public String getCenterText() {
        return this.centerText;
    }
    
    /**
     * Sets the text to display in the center of the plot and sends a
     * change event to all registered listeners.  If the text is set to 
     * <code>null</code>, no text will be displayed.
     * 
     * @param text  the text (<code>null</code> permitted).
     * 
     * @since 1.0.18
     */
    public void setCenterText(String text) {
        this.centerText = text;
        fireChangeEvent();
    }
    
    /**
     * Returns the formatter used to format the center text value for the mode
     * {@link CenterTextMode#VALUE}.  The default value is 
     * <code>DecimalFormat("0.00");</code>.
     * 
     * @return The formatter (never <code>null</code>).
     * 
     * @since 1.0.18
     */
    public Format getCenterTextFormatter() {
        return this.centerTextFormatter;
    }
    
    /**
     * Sets the formatter used to format the center text value and sends a
     * change event to all registered listeners.
     * 
     * @param formatter  the formatter (<code>null</code> not permitted).
     * 
     * @since 1.0.18
     */
    public void setCenterTextFormatter(Format formatter) {
        ParamChecks.nullNotPermitted(formatter, "formatter");
        this.centerTextFormatter = formatter;
    }
    
    /**
     * Returns the font used to display the center text.  The default value
     * is {@link PiePlot#DEFAULT_LABEL_FONT}.
     * 
     * @return The font (never <code>null</code>).
     * 
     * @since 1.0.18
     */
    public Font getCenterTextFont() {
        return this.centerTextFont;
    }
    
    /**
     * Sets the font used to display the center text and sends a change event
     * to all registered listeners.
     * 
     * @param font  the font (<code>null</code> not permitted).
     * 
     * @since 1.0.18
     */
    public void setCenterTextFont(Font font) {
        ParamChecks.nullNotPermitted(font, "font");
        this.centerTextFont = font;
        fireChangeEvent();
    }
    
    /**
     * Returns the color for the center text.  The default value is
     * <code>Color.BLACK</code>.
     * 
     * @return The color (never <code>null</code>). 
     * 
     * @since 1.0.18
     */
    public Color getCenterTextColor() {
        return this.centerTextColor;
    }
    
    /**
     * Sets the color for the center text and sends a change event to all 
     * registered listeners.
     * 
     * @param color  the color (<code>null</code> not permitted).
     * 
     * @since 1.0.18
     */
    public void setCenterTextColor(Color color) {
        ParamChecks.nullNotPermitted(color, "color");
        this.centerTextColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns a flag that indicates whether or not separators are drawn between
     * the sections in the chart.
     *
     * @return A boolean.
     *
     * @see #setSeparatorsVisible(boolean)
     */
    public boolean getSeparatorsVisible() {
        return this.separatorsVisible;
    }

    /**
     * Sets the flag that controls whether or not separators are drawn between
     * the sections in the chart, and sends a change event to all registered 
     * listeners.
     *
     * @param visible  the flag.
     *
     * @see #getSeparatorsVisible()
     */
    public void setSeparatorsVisible(boolean visible) {
        this.separatorsVisible = visible;
        fireChangeEvent();
    }

    /**
     * Returns the separator stroke.
     *
     * @return The stroke (never <code>null</code>).
     *
     * @see #setSeparatorStroke(Stroke)
     */
    public Stroke getSeparatorStroke() {
        return this.separatorStroke;
    }

    /**
     * Sets the stroke used to draw the separator between sections and sends
     * a change event to all registered listeners.
     *
     * @param stroke  the stroke (<code>null</code> not permitted).
     *
     * @see #getSeparatorStroke()
     */
    public void setSeparatorStroke(Stroke stroke) {
        ParamChecks.nullNotPermitted(stroke, "stroke");
        this.separatorStroke = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the separator paint.
     *
     * @return The paint (never <code>null</code>).
     *
     * @see #setSeparatorPaint(Paint)
     */
    public Paint getSeparatorPaint() {
        return this.separatorPaint;
    }

    /**
     * Sets the paint used to draw the separator between sections and sends a
     * change event to all registered listeners.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     *
     * @see #getSeparatorPaint()
     */
    public void setSeparatorPaint(Paint paint) {
        ParamChecks.nullNotPermitted(paint, "paint");
        this.separatorPaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns the length of the inner extension of the separator line that
     * is drawn between sections, expressed as a percentage of the depth of
     * the section.
     *
     * @return The inner separator extension (as a percentage).
     *
     * @see #setInnerSeparatorExtension(double)
     */
    public double getInnerSeparatorExtension() {
        return this.innerSeparatorExtension;
    }

    /**
     * Sets the length of the inner extension of the separator line that is
     * drawn between sections, as a percentage of the depth of the
     * sections, and sends a change event to all registered listeners.
     *
     * @param percent  the percentage.
     *
     * @see #getInnerSeparatorExtension()
     * @see #setOuterSeparatorExtension(double)
     */
    public void setInnerSeparatorExtension(double percent) {
        this.innerSeparatorExtension = percent;
        fireChangeEvent();
    }

    /**
     * Returns the length of the outer extension of the separator line that
     * is drawn between sections, expressed as a percentage of the depth of
     * the section.
     *
     * @return The outer separator extension (as a percentage).
     *
     * @see #setOuterSeparatorExtension(double)
     */
    public double getOuterSeparatorExtension() {
        return this.outerSeparatorExtension;
    }

    /**
     * Sets the length of the outer extension of the separator line that is
     * drawn between sections, as a percentage of the depth of the
     * sections, and sends a change event to all registered listeners.
     *
     * @param percent  the percentage.
     *
     * @see #getOuterSeparatorExtension()
     */
    public void setOuterSeparatorExtension(double percent) {
        this.outerSeparatorExtension = percent;
        fireChangeEvent();
    }

    /**
     * Returns the depth of each section, expressed as a percentage of the
     * plot radius.
     *
     * @return The depth of each section.
     *
     * @see #setSectionDepth(double)
     * @since 1.0.3
     */
    public double getSectionDepth() {
        return this.sectionDepth;
    }

    /**
     * The section depth is given as percentage of the plot radius.
     * Specifying 1.0 results in a straightforward pie chart.
     *
     * @param sectionDepth  the section depth.
     *
     * @see #getSectionDepth()
     * @since 1.0.3
     */
    public void setSectionDepth(double sectionDepth) {
        this.sectionDepth = sectionDepth;
        fireChangeEvent();
    }

    /**
     * Initialises the plot state (which will store the total of all dataset
     * values, among other things).  This method is called once at the
     * beginning of each drawing.
     *
     * @param g2  the graphics device.
     * @param plotArea  the plot area (<code>null</code> not permitted).
     * @param plot  the plot.
     * @param index  the secondary index (<code>null</code> for primary
     *               renderer).
     * @param info  collects chart rendering information for return to caller.
     *
     * @return A state object (maintains state information relevant to one
     *         chart drawing).
     */
    @Override
    public PiePlotState initialise(Graphics2D g2, Rectangle2D plotArea,
            PiePlot plot, Integer index, PlotRenderingInfo info) {
        PiePlotState state = super.initialise(g2, plotArea, plot, index, info);
        state.setPassesRequired(3);
        return state;
    }

    /**
     * Draws a single data item.
     *
     * @param g2  the graphics device (<code>null</code> not permitted).
     * @param section  the section index.
     * @param dataArea  the data plot area.
     * @param state  state information for one chart.
     * @param currentPass  the current pass index.
     */
    @Override
    protected void drawItem(Graphics2D g2, int section, Rectangle2D dataArea,
            PiePlotState state, int currentPass) {

        PieDataset dataset = getDataset();
        Number n = dataset.getValue(section);
        if (n == null) {
            return;
        }
        double value = n.doubleValue();
        double angle1 = 0.0;
        double angle2 = 0.0;

        Rotation direction = getDirection();
        if (direction == Rotation.CLOCKWISE) {
            angle1 = state.getLatestAngle();
            angle2 = angle1 - value / state.getTotal() * 360.0;
        }
        else if (direction == Rotation.ANTICLOCKWISE) {
            angle1 = state.getLatestAngle();
            angle2 = angle1 + value / state.getTotal() * 360.0;
        }
        else {
            throw new IllegalStateException("Rotation type not recognised.");
        }

        double angle = (angle2 - angle1);
        if (Math.abs(angle) > getMinimumArcAngleToDraw()) {
            Comparable key = getSectionKey(section);
            double ep = 0.0;
            double mep = getMaximumExplodePercent();
            if (mep > 0.0) {
                ep = getExplodePercent(key) / mep;
            }
            Rectangle2D arcBounds = getArcBounds(state.getPieArea(),
                    state.getExplodedPieArea(), angle1, angle, ep);
            Arc2D.Double arc = new Arc2D.Double(arcBounds, angle1, angle,
                    Arc2D.OPEN);

            // create the bounds for the inner arc
            double depth = this.sectionDepth / 2.0;
            RectangleInsets s = new RectangleInsets(UnitType.RELATIVE,
                depth, depth, depth, depth);
            Rectangle2D innerArcBounds = new Rectangle2D.Double();
            innerArcBounds.setRect(arcBounds);
            s.trim(innerArcBounds);
            // calculate inner arc in reverse direction, for later
            // GeneralPath construction
            Arc2D.Double arc2 = new Arc2D.Double(innerArcBounds, angle1
                    + angle, -angle, Arc2D.OPEN);
            GeneralPath path = new GeneralPath();
            path.moveTo((float) arc.getStartPoint().getX(),
                    (float) arc.getStartPoint().getY());
            path.append(arc.getPathIterator(null), false);
            path.append(arc2.getPathIterator(null), true);
            path.closePath();

            Line2D separator = new Line2D.Double(arc2.getEndPoint(),
                    arc.getStartPoint());

            if (currentPass == 0) {
                Paint shadowPaint = getShadowPaint();
                double shadowXOffset = getShadowXOffset();
                double shadowYOffset = getShadowYOffset();
                if (shadowPaint != null && getShadowGenerator() == null) {
                    Shape shadowArc = ShapeUtilities.createTranslatedShape(
                            path, (float) shadowXOffset, (float) shadowYOffset);
                    g2.setPaint(shadowPaint);
                    g2.fill(shadowArc);
                }
            }
            else if (currentPass == 1) {
                Paint paint = lookupSectionPaint(key);
                g2.setPaint(paint);
                g2.fill(path);
                Paint outlinePaint = lookupSectionOutlinePaint(key);
                Stroke outlineStroke = lookupSectionOutlineStroke(key);
                if (getSectionOutlinesVisible() && outlinePaint != null 
                        && outlineStroke != null) {
                    g2.setPaint(outlinePaint);
                    g2.setStroke(outlineStroke);
                    g2.draw(path);
                }
                
                if (section == 0) {
                    String nstr = null;
                    if (this.centerTextMode.equals(CenterTextMode.VALUE)) {
                        nstr = this.centerTextFormatter.format(n);
                    } else if (this.centerTextMode.equals(CenterTextMode.FIXED)) {
                        nstr = this.centerText;
                    }
                    if (nstr != null) {
                        g2.setFont(this.centerTextFont);
                        g2.setPaint(this.centerTextColor);
                        TextUtilities.drawAlignedString(nstr, g2, 
                            (float) dataArea.getCenterX(), 
                            (float) dataArea.getCenterY(),  
                            TextAnchor.CENTER);                        
                    }
                }

                // add an entity for the pie section
                if (state.getInfo() != null) {
                    EntityCollection entities = state.getEntityCollection();
                    if (entities != null) {
                        String tip = null;
                        PieToolTipGenerator toolTipGenerator
                                = getToolTipGenerator();
                        if (toolTipGenerator != null) {
                            tip = toolTipGenerator.generateToolTip(dataset,
                                    key);
                        }
                        String url = null;
                        PieURLGenerator urlGenerator = getURLGenerator();
                        if (urlGenerator != null) {
                            url = urlGenerator.generateURL(dataset, key,
                                    getPieIndex());
                        }
                        PieSectionEntity entity = new PieSectionEntity(path,
                                dataset, getPieIndex(), section, key, tip,
                                url);
                        entities.add(entity);
                    }
                }
            }
            else if (currentPass == 2) {
                if (this.separatorsVisible) {
                    Line2D extendedSeparator = LineUtilities.extendLine(
                            separator, this.innerSeparatorExtension,
                            this.outerSeparatorExtension);
                    g2.setStroke(this.separatorStroke);
                    g2.setPaint(this.separatorPaint);
                    g2.draw(extendedSeparator);
                }
            }
        }
        state.setLatestAngle(angle2);
    }

    /**
     * This method overrides the default value for cases where the ring plot
     * is very thin.  This fixes bug 2121818.
     *
     * @return The label link depth, as a percentage of the plot's radius.
     */
    @Override
    protected double getLabelLinkDepth() {
        return Math.min(super.getLabelLinkDepth(), getSectionDepth() / 2);
    }

    /**
     * Tests this plot for equality with an arbitrary object.
     *
     * @param obj  the object to test against (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RingPlot)) {
            return false;
        }
        RingPlot that = (RingPlot) obj;
        if (!this.centerTextMode.equals(that.centerTextMode)) {
            return false;
        }
        if (!ObjectUtilities.equal(this.centerText, that.centerText)) {
            return false;
        }
        if (!this.centerTextFormatter.equals(that.centerTextFormatter)) {
            return false;
        }
        if (!this.centerTextFont.equals(that.centerTextFont)) {
            return false;
        }
        if (!this.centerTextColor.equals(that.centerTextColor)) {
            return false;
        }
        if (this.separatorsVisible != that.separatorsVisible) {
            return false;
        }
        if (!ObjectUtilities.equal(this.separatorStroke,
                that.separatorStroke)) {
            return false;
        }
        if (!PaintUtilities.equal(this.separatorPaint, that.separatorPaint)) {
            return false;
        }
        if (this.innerSeparatorExtension != that.innerSeparatorExtension) {
            return false;
        }
        if (this.outerSeparatorExtension != that.outerSeparatorExtension) {
            return false;
        }
        if (this.sectionDepth != that.sectionDepth) {
            return false;
        }
        return super.equals(obj);
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
        SerialUtilities.writeStroke(this.separatorStroke, stream);
        SerialUtilities.writePaint(this.separatorPaint, stream);
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
        this.separatorStroke = SerialUtilities.readStroke(stream);
        this.separatorPaint = SerialUtilities.readPaint(stream);
    }

}

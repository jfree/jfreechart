/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * SWTGraphics2D.java
 * ------------------
 * (C) Copyright 2006, 2007, by Henry Proudhon and Contributors.
 *
 * Original Author:  Henry Proudhon (henry.proudhon AT insa-lyon.fr);
 * Contributor(s):   Cedric Chabanois (cchabanois AT no-log.org);
 *                   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 14-Jun-2006 : New class (HP);
 * 29-Jan-2007 : fixed the fillRect method (HP);
 * 31-Jan-2007 : moved the dummy JPanel to SWTUtils.java,
 *               implemented the drawLine method (HP);
 * 07-Apr-2007 : dispose some of the swt ressources, 
 *               thanks to silent for pointing this out (HP);
 * 23-May-2007 : removed resource leaks by adding a resource pool (CC);
 * 15-Jun-2007 : Fixed compile error for JDK 1.4 (DG);
 * 
 */

package org.jfree.experimental.swt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.Transform;

/**
 * This is a class utility to draw Graphics2D stuff on a swt composite.
 * It is presently developed to use JFreeChart with the Standard 
 * Widget Toolkit but may be of a wider use later.
 */
public class SWTGraphics2D extends Graphics2D {
    
    /** The swt graphic composite */
    private GC gc;
    
    /** A HashMap to store the Swt color resources. */
    private Map colorsPool = new HashMap();

    /** A HashMap to store the Swt font resources. */
    private Map fontsPool = new HashMap();

    /** A HashMap to store the Swt color resources. */
    private List resourcePool = new ArrayList();

    /**
     * Creates a new instance.
     * 
     * @param gc  the graphics context.
     */
    public SWTGraphics2D(GC gc) {
        super();
        this.gc = gc;
    }

    /**
     * Add given swt resource to the resource pool. All resources added
     * to the resource pool will be dispose when {@link #dispose()} is called
     *  
     * @param resource the resource to add to the pool.
     * @return the swt <code>Resource</code> just added.
     */
    private Resource addToResourcePool(Resource resource) {
    	resourcePool.add(resource);
    	return resource;
    }
    
    /**
     * Dispose the resource pool.
     */
    private void disposeResourcePool() {
    	for (Iterator it = resourcePool.iterator();it.hasNext();) {
    		Resource resource = (Resource)it.next();
    		resource.dispose();
    	}
    	resourcePool.clear();
    	colorsPool.clear();
    	resourcePool.clear();
    }

    /**
     * Internal method to convert a AWT font object into 
     * a SWT font resource. If a corresponding SWT font
     * instance is already in the pool, it will be used 
     * instead of creating a new one. This is used in 
     * {@link #setFont()} for instance. 
     * 
     * @param font The AWT font to convert.
     * @return The SWT font instance.
     */
    private org.eclipse.swt.graphics.Font getSwtFontFromPool(Font font) {
    	org.eclipse.swt.graphics.Font swtFont = (org.eclipse.swt.graphics.Font)
    	fontsPool.get(font);
    	if (swtFont == null) {
    	    swtFont = new org.eclipse.swt.graphics.Font( 
    		    gc.getDevice(), 
    		    SWTUtils.toSwtFontData(gc.getDevice(), font, true));
    	    addToResourcePool(swtFont);
    	    fontsPool.put(font, swtFont);
    	}
    	return swtFont;
    }
    
    /**
     * Internal method to convert a AWT color object into 
     * a SWT color resource. If a corresponding SWT color
     * instance is already in the pool, it will be used 
     * instead of creating a new one. This is used in 
     * {@link #setColor()} for instance. 
     * 
     * @param awtColor The AWT color to convert.
     * @return A SWT color instance.
     */
    private org.eclipse.swt.graphics.Color getSwtColorFromPool(Color awtColor) {
	org.eclipse.swt.graphics.Color swtColor = (org.eclipse.swt.graphics.Color)
        // we can't use the following valueOf() method, because it won't 
        // compile with JDK1.4
	//this.colorsPool.get(Integer.valueOf(awtColor.getRGB()));
	this.colorsPool.get(new Integer(awtColor.getRGB()));
        if (swtColor == null) {
	    swtColor = SWTUtils.toSwtColor(gc.getDevice(), awtColor);
	    addToResourcePool(swtColor);
	    // see comment above
            //this.colorsPool.put(Integer.valueOf(awtColor.getRGB()), swtColor);
            this.colorsPool.put(new Integer(awtColor.getRGB()), swtColor);
	}
	return swtColor;
    }

    /**
     * Perform a switch between foreground and background 
     * color of gc. This is needed for consistency with 
     * the awt behaviour, and is required notably for the 
     * filling methods.
     */
    private void switchColors() {
        org.eclipse.swt.graphics.Color bg = gc.getBackground();
        org.eclipse.swt.graphics.Color fg = gc.getForeground();
        gc.setBackground(fg);
        gc.setForeground(bg);
    }
    
    /**
     * Converts an AWT <code>Shape</code> into a SWT <code>Path</code>.
     * 
     * @param shape  the shape.
     * 
     * @return The path.
     */
    private Path toSwtPath(Shape shape) {
        int type;
        float[] coords = new float[6];
        Path path = new Path(this.gc.getDevice());
        PathIterator pit = shape.getPathIterator(null);
        while (!pit.isDone()) {
            type = pit.currentSegment(coords);
            switch (type) {
                case (PathIterator.SEG_MOVETO):
                    path.moveTo(coords[0], coords[1]);
                    break;
                case (PathIterator.SEG_LINETO):
                    path.lineTo(coords[0], coords[1]);
                    break;
                case (PathIterator.SEG_QUADTO):
                    path.quadTo(coords[0], coords[1], coords[2], coords[3]);
                    break;
                case (PathIterator.SEG_CUBICTO):
                    path.cubicTo(coords[0], coords[1], coords[2], 
                            coords[3], coords[4], coords[5]);
                    break;
                case (PathIterator.SEG_CLOSE):
                    path.close();
                    break;
                default:
                    break;
            }
            pit.next();
        }
        return path;
    }
    
    /**
     * Converts an AWT transform into the equivalent SWT transform.
     * 
     * @param awtTransform  the AWT transform.
     * 
     * @return The SWT transform.
     */
    private Transform toSwtTransform(AffineTransform awtTransform) {
        Transform t = new Transform(gc.getDevice());
        double[] matrix = new double[6];
        awtTransform.getMatrix(matrix);
        t.setElements((float) matrix[0], (float) matrix[1],
                (float) matrix[2], (float) matrix[3],
                (float) matrix[4], (float) matrix[5]); 
        return t;
    }
    
    /**
     * Converts an SWT transform into the equivalent AWT transform.
     * 
     * @param swtTransform  the SWT transform.
     * 
     * @return The AWT transform.
     */
    private AffineTransform toAwtTransform(Transform swtTransform) {
        float[] elements = new float[6];
        swtTransform.getElements(elements);
        AffineTransform awtTransform = new AffineTransform(elements);
        return awtTransform;
    }
    
    /* (non-Javadoc)
     * @see java.awt.Graphics2D#draw(java.awt.Shape)
     */
    public void draw(Shape shape) {
        Path path = toSwtPath(shape);
        gc.drawPath(path);
        path.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#drawImage(java.awt.Image, 
     * java.awt.geom.AffineTransform, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image image, AffineTransform xform,
            ImageObserver obs) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#drawImage(java.awt.image.BufferedImage, 
     * java.awt.image.BufferedImageOp, int, int)
     */
    public void drawImage(BufferedImage image, BufferedImageOp op, int x, 
            int y) {
        org.eclipse.swt.graphics.Image im 
            = new org.eclipse.swt.graphics.Image(gc.getDevice(), 
                    convertToSWT(image));
        gc.drawImage(im, x, y);
        im.dispose();
    }

    /**
     * Draws an image at (x, y).
     * 
     * @param image  the image.
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    public void drawImage(org.eclipse.swt.graphics.Image image, int x, int y) {
        gc.drawImage(image, x, y);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#drawRenderedImage(java.awt.image.RenderedImage,
     * java.awt.geom.AffineTransform)
     */
    public void drawRenderedImage(RenderedImage image, AffineTransform xform) {
        // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#drawRenderableImage(
     * java.awt.image.renderable.RenderableImage, java.awt.geom.AffineTransform)
     */
    public void drawRenderableImage(RenderableImage image, 
            AffineTransform xform) {
        // TODO Auto-generated method stub

    }

    /**
     * Draws a string on the receiver. note that 
     * to be consistent with the awt method, 
     * the y has to be modified with the ascent of the font. 
     * 
     * @see java.awt.Graphics#drawString(java.lang.String, int, int)
     */
    public void drawString(String text, int x, int y) {
        float fm = gc.getFontMetrics().getAscent();
        gc.drawString(text, x, (int) (y - fm), true);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#drawString(java.lang.String, float, float)
     */
    public void drawString(String text, float x, float y) {
        float fm = gc.getFontMetrics().getAscent();
        gc.drawString(text, (int) x, (int) ( y - fm ), true);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#drawString(
     * java.text.AttributedCharacterIterator, int, int)
     */
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#drawString(
     * java.text.AttributedCharacterIterator, float, float)
     */
    public void drawString(AttributedCharacterIterator iterator, float x, 
            float y) {
        // TODO Auto-generated method stub

    }

    /** fill an arbitrary shape on the swt graphic composite 
     * with the current stroke and paint.
     * note that for consistency with the awt method, it is needed 
     * to switch temporarily the foreground and background colors.
     * @see java.awt.Graphics2D#fill(java.awt.Shape)
     */
    public void fill(Shape shape) {
        Path path = toSwtPath(shape);
        switchColors();
        this.gc.fillPath(path);
        switchColors();
        path.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#hit(java.awt.Rectangle, java.awt.Shape, boolean)
     */
    public boolean hit(Rectangle rect, Shape text, boolean onStroke) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getDeviceConfiguration()
     */
    public GraphicsConfiguration getDeviceConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#setComposite(java.awt.Composite)
     */
    public void setComposite(Composite comp) {
        // TODO Auto-generated method stub
    }

    /**
     * Set the paint associated with the swt graphic composite.
     * @see java.awt.Graphics2D#setPaint(java.awt.Paint)
     */
    public void setPaint(Paint paint) {
        if (paint instanceof Color) {
            setColor((Color) paint);
        }
        else {
            throw new RuntimeException("Can only handle 'Color' at present.");
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#setStroke(java.awt.Stroke)
     */
    public void setStroke(Stroke stroke) {
        if (stroke instanceof BasicStroke) {
            BasicStroke bs = (BasicStroke) stroke;
            // linewidth
            gc.setLineWidth((int) bs.getLineWidth());

            // line join
            switch (bs.getLineJoin()) {
                case BasicStroke.JOIN_BEVEL :
                    gc.setLineJoin(SWT.JOIN_BEVEL);
                    break;
                case BasicStroke.JOIN_MITER :
                    gc.setLineJoin(SWT.JOIN_MITER);
                    break;
                case BasicStroke.JOIN_ROUND :
                    gc.setLineJoin(SWT.JOIN_ROUND);
                    break;
            }

            // line cap
            switch (bs.getEndCap()) {
                case BasicStroke.CAP_BUTT :
                    gc.setLineCap(SWT.CAP_FLAT);
                    break;
                case BasicStroke.CAP_ROUND :
                    gc.setLineCap(SWT.CAP_ROUND);
                    break;
                case BasicStroke.CAP_SQUARE :
                    gc.setLineCap(SWT.CAP_SQUARE);
                    break;
            }

            // set the line style to solid by default
            gc.setLineStyle(SWT.LINE_SOLID);

            // apply dash style if any
            float[] dashes = bs.getDashArray();
            if (dashes != null) {
                int[] swtDashes = new int[dashes.length];
                for (int i = 0; i < swtDashes.length; i++) {
                    swtDashes[i] = (int) dashes[i];
                }
                gc.setLineDash(swtDashes);
            }
        }
        else {
            throw new RuntimeException(
                    "Can only handle 'Basic Stroke' at present.");
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#setRenderingHint(java.awt.RenderingHints.Key, 
     * java.lang.Object)
     */
    public void setRenderingHint(Key hintKey, Object hintValue) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getRenderingHint(java.awt.RenderingHints.Key)
     */
    public Object getRenderingHint(Key hintKey) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#setRenderingHints(java.util.Map)
     */
    public void setRenderingHints(Map hints) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#addRenderingHints(java.util.Map)
     */
    public void addRenderingHints(Map hints) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getRenderingHints()
     */
    public RenderingHints getRenderingHints() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#translate(int, int)
     */
    public void translate(int x, int y) {
        Transform swtTransform = new Transform(gc.getDevice()); 
        gc.getTransform(swtTransform);
        swtTransform.translate(x, y);
        gc.setTransform(swtTransform);
        swtTransform.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#translate(double, double)
     */
    public void translate(double tx, double ty) {
        translate((int) tx, (int) ty);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#rotate(double)
     */
    public void rotate(double theta) {
        Transform swtTransform = new Transform(gc.getDevice()); 
        gc.getTransform(swtTransform);
        swtTransform.rotate( (float) (theta * 180 / Math.PI));
        gc.setTransform(swtTransform);
        swtTransform.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#rotate(double, double, double)
     */
    public void rotate(double theta, double x, double y) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#scale(double, double)
     */
    public void scale(double scaleX, double scaleY) {
        Transform swtTransform = new Transform(gc.getDevice()); 
        gc.getTransform(swtTransform);
        swtTransform.scale((float) scaleX, (float) scaleY);
        gc.setTransform(swtTransform);
        swtTransform.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#shear(double, double)
     */
    public void shear(double shearX, double shearY) {
        Transform swtTransform = new Transform(gc.getDevice()); 
        gc.getTransform(swtTransform);
        Transform shear = new Transform(gc.getDevice(), 1f, (float) shearX, 
                (float) shearY, 1f, 0, 0);
        swtTransform.multiply(shear);
        gc.setTransform(swtTransform);
        swtTransform.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#transform(java.awt.geom.AffineTransform)
     */
    public void transform(AffineTransform Tx) {
        Transform swtTransform = new Transform(gc.getDevice()); 
        gc.getTransform(swtTransform);
        Transform swtMatrix = toSwtTransform(Tx);
        swtTransform.multiply(swtMatrix);
        gc.setTransform(swtTransform);
        swtMatrix.dispose();
        swtTransform.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#setTransform(java.awt.geom.AffineTransform)
     */
    public void setTransform(AffineTransform Tx) {
        gc.setTransform(toSwtTransform(Tx));
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getTransform()
     */
    public AffineTransform getTransform() {
        Transform swtTransform = new Transform(gc.getDevice()); 
        gc.getTransform(swtTransform);
        AffineTransform awtTransform = toAwtTransform(swtTransform);
        swtTransform.dispose();
        return awtTransform; 
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getPaint()
     */
    public Paint getPaint() {
        return SWTUtils.toAwtColor(gc.getForeground());
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getComposite()
     */
    public Composite getComposite() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#setBackground(java.awt.Color)
     */
    public void setBackground(Color color) {
        gc.getBackground().dispose();
        org.eclipse.swt.graphics.Color swtColor = SWTUtils.toSwtColor(gc.getDevice(), color);
        gc.setBackground(swtColor);
        swtColor.dispose(); 
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getBackground()
     */
    public Color getBackground() {
        return SWTUtils.toAwtColor(gc.getBackground());
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getStroke()
     */
    public Stroke getStroke() {
        return new BasicStroke(gc.getLineWidth(), gc.getLineCap(), 
                gc.getLineJoin());
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#clip(java.awt.Shape)
     */
    public void clip(Shape s) {
        Path path = toSwtPath(s);
        gc.setClipping(path);
        path.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#getFontRenderContext()
     */
    public FontRenderContext getFontRenderContext() {
        FontRenderContext fontRenderContext 
            = new FontRenderContext(new AffineTransform(), true, true);
        return fontRenderContext;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics2D#drawGlyphVector(java.awt.font.GlyphVector, 
     * float, float)
     */
    public void drawGlyphVector(GlyphVector g, float x, float y) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#create()
     */
    public Graphics create() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#getColor()
     */
    public Color getColor() {
        return SWTUtils.toAwtColor(gc.getForeground());
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#setColor(java.awt.Color)
     */
    public void setColor(Color color) {
    	org.eclipse.swt.graphics.Color swtColor = getSwtColorFromPool(color);
        gc.setForeground(swtColor);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#setPaintMode()
     */
    public void setPaintMode() {
        // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#setXORMode(java.awt.Color)
     */
    public void setXORMode(Color color) {
        // TODO Auto-generated method stub

    }

    /**
     * Returns the font in form of an awt font created 
     * with the parameters of the font of the swt graphic 
     * composite.
     * @see java.awt.Graphics#getFont()
     */
    public Font getFont() {
        // retrieve the swt font description in an os indept way
        FontData[] fontData = gc.getFont().getFontData();
        // create a new awt font with the appropiate data
        return SWTUtils.toAwtFont(gc.getDevice(), fontData[0], true);
    }

    /**
     * Set the font swt graphic composite from the specified 
     * awt font. Be careful that the newly created swt font 
     * must be disposed separately.
     * @see java.awt.Graphics#setFont(java.awt.Font)
     */
    public void setFont(Font font) {
        org.eclipse.swt.graphics.Font swtFont = getSwtFontFromPool(font);
        gc.setFont(swtFont);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#getFontMetrics(java.awt.Font)
     */
    public FontMetrics getFontMetrics(Font font) {
        return SWTUtils.DUMMY_PANEL.getFontMetrics(font);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#getClipBounds()
     */
    public Rectangle getClipBounds() {
        org.eclipse.swt.graphics.Rectangle clip = gc.getClipping();
        return new Rectangle(clip.x, clip.y, clip.width, clip.height);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#clipRect(int, int, int, int)
     */
    public void clipRect(int x, int y, int width, int height) {
        org.eclipse.swt.graphics.Rectangle clip = gc.getClipping();
        clip.intersects(x, y, width, height);
        gc.setClipping(clip);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#setClip(int, int, int, int)
     */
    public void setClip(int x, int y, int width, int height) {
        gc.setClipping(x, y, width, height);
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#getClip()
     */
    public Shape getClip() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#setClip(java.awt.Shape)
     */
    public void setClip(Shape clip) {
        if (clip == null) 
            return;
        Path clipPath = toSwtPath(clip);
        gc.setClipping(clipPath);
        clipPath.dispose();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#copyArea(int, int, int, int, int, int)
     */
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        // TODO Auto-generated method stub

    }

    /**
     * Draws a line on the swt graphic composite.
     * @see java.awt.Graphics#drawLine(int, int, int, int)
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        gc.drawLine(x1, y1, x2, y2);
    }

    /**
     * Fill a rectangle area on the swt graphic composite.
     * The <code>fillRectangle</code> method of the <code>GC</code> 
     * class uses the background color so we must switch colors.
     * @see java.awt.Graphics#fillRect(int, int, int, int)
     */
    public void fillRect(int x, int y, int width, int height) {
        this.switchColors();
        gc.fillRectangle(x, y, width, height);
        this.switchColors();
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#clearRect(int, int, int, int)
     */
    public void clearRect(int x, int y, int width, int height) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawRoundRect(int, int, int, int, int, int)
     */
    public void drawRoundRect(int x, int y, int width, int height,
            int arcWidth, int arcHeight) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#fillRoundRect(int, int, int, int, int, int)
     */
    public void fillRoundRect(int x, int y, int width, int height,
            int arcWidth, int arcHeight) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawOval(int, int, int, int)
     */
    public void drawOval(int x, int y, int width, int height) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#fillOval(int, int, int, int)
     */
    public void fillOval(int x, int y, int width, int height) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawArc(int, int, int, int, int, int)
     */
    public void drawArc(int x, int y, int width, int height, int arcStart,
            int arcAngle) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#fillArc(int, int, int, int, int, int)
     */
    public void fillArc(int x, int y, int width, int height, int arcStart,
            int arcAngle) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawPolyline(int[], int[], int)
     */
    public void drawPolyline(int [] xPoints, int [] yPoints, int npoints) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawPolygon(int[], int[], int)
     */
    public void drawPolygon(int [] xPoints, int [] yPoints, int npoints) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#fillPolygon(int[], int[], int)
     */
    public void fillPolygon(int [] xPoints, int [] yPoints, int npoints) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, 
     * java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image image, int x, int y, 
            ImageObserver observer) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, 
     * java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image image, int x, int y, int width, int height,
            ImageObserver observer) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, 
     * java.awt.Color, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image image, int x, int y, Color bgcolor,
            ImageObserver observer) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, 
     * java.awt.Color, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image image, int x, int y, int width, int height,
            Color bgcolor, ImageObserver observer) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, 
     * int, int, int, int, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image image, int dx1, int dy1, int dx2, int dy2,
            int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, 
     * int, int, int, int, java.awt.Color, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image image, int dx1, int dy1, int dx2, int dy2,
            int sx1, int sy1, int sx2, int sy2, Color bgcolor,
            ImageObserver observer) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see java.awt.Graphics#dispose()
     */
    public void dispose() {
    	// we dispose resources we own but user must dispose gc
        disposeResourcePool();
    }

    static ImageData convertToSWT(BufferedImage bufferedImage) {
        if (bufferedImage.getColorModel() instanceof DirectColorModel) {
            DirectColorModel colorModel 
                    = (DirectColorModel) bufferedImage.getColorModel();
            PaletteData palette = new PaletteData(colorModel.getRedMask(),
                    colorModel.getGreenMask(), colorModel.getBlueMask());
            ImageData data = new ImageData(bufferedImage.getWidth(), 
                    bufferedImage.getHeight(), colorModel.getPixelSize(),
                    palette);
            WritableRaster raster = bufferedImage.getRaster();
            int[] pixelArray = new int[3];
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                  raster.getPixel(x, y, pixelArray);
                  int pixel = palette.getPixel(new RGB(pixelArray[0], 
                          pixelArray[1], pixelArray[2]));
                  data.setPixel(x, y, pixel);
                }
            }
            return data;
        } 
        else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
            IndexColorModel colorModel 
                    = (IndexColorModel) bufferedImage.getColorModel();
            int size = colorModel.getMapSize();
            byte[] reds = new byte[size];
            byte[] greens = new byte[size];
            byte[] blues = new byte[size];
            colorModel.getReds(reds);
            colorModel.getGreens(greens);
            colorModel.getBlues(blues);
            RGB[] rgbs = new RGB[size];
            for (int i = 0; i < rgbs.length; i++) {
                rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, 
                        blues[i] & 0xFF);
            }
            PaletteData palette = new PaletteData(rgbs);
            ImageData data = new ImageData(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), colorModel.getPixelSize(),
                    palette);
            data.transparentPixel = colorModel.getTransparentPixel();
            WritableRaster raster = bufferedImage.getRaster();
            int[] pixelArray = new int[1];
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    raster.getPixel(x, y, pixelArray);
                    data.setPixel(x, y, pixelArray[0]);
                }
            }
            return data;
        }
        return null;
    }
}

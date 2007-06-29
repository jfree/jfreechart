/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
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
 * --------------------
 * SWTStrokeCanvas.java
 * --------------------
 * (C) Copyright 2006, by Henry Proudhon and Contributors.
 *
 * Original Author:  Henry Proudhon (henry.proudhon AT insa-lyon.fr);
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 01-Aug-2006 : New class (HP);
 * 
 */

package org.jfree.experimental.chart.swt.editor;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * A control for displaying a <code>Stroke</code> sample.
 */
class SWTStrokeCanvas extends Canvas {
    
    /**
     * Creates a new instance.
     * 
     * @param parent  the parent.
     * @param style  the style.
     * @param image  the image.
     */
    public SWTStrokeCanvas(Composite parent, int style, Image image) {
        this(parent, style);
    }

    /**
     * Creates a new instance.
     * 
     * @param parent  the parent.
     * @param style  the style.
     */
    public SWTStrokeCanvas(Composite parent, int style) {
        super(parent, style);
        addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                BasicStroke stroke = (BasicStroke) getStroke();
                if (stroke != null) {
                    int x, y;
                    Rectangle rect = getClientArea();
                    x = (rect.width - 100) / 2;
                    y = (rect.height - 16) / 2;
                    Transform swtTransform = new Transform(e.gc.getDevice()); 
                    e.gc.getTransform(swtTransform);
                    swtTransform.translate(x, y);
                    e.gc.setTransform(swtTransform);
                    swtTransform.dispose();
                    e.gc.setBackground(getDisplay().getSystemColor(
                            SWT.COLOR_BLACK));
                    e.gc.setLineWidth((int) stroke.getLineWidth());
                    e.gc.drawLine(10, 8, 90, 8);
                }
            }
        });
    }
    
    /**
     * Sets the stroke.
     * 
     * @param stroke  the stroke.
     */
    public void setStroke(Stroke stroke) {
        if (stroke instanceof BasicStroke) {
            this.setData( stroke );
        }
        else { 
            throw new RuntimeException(
                "Can only handle 'Basic Stroke' at present.");
        }
    }

    /**
     * Returns the stroke.
     * 
     * @return The stroke.
     */
    public BasicStroke getStroke() {
        return (BasicStroke) this.getData();
    }
}

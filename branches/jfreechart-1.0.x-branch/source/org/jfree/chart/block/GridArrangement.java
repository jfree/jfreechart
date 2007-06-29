/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * GridArrangement.java
 * --------------------
 * (C) Copyright 2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: GridArrangement.java,v 1.6.2.1 2005/10/25 20:39:38 mungady Exp $
 *
 * Changes:
 * --------
 * 08-Feb-2005 : Version 1 (DG);
 * 
 */

package org.jfree.chart.block;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.jfree.ui.Size2D;

/**
 * Arranges blocks in a grid within their container.
 */
public class GridArrangement implements Arrangement, Serializable {
    
    /** For serialization. */
    private static final long serialVersionUID = -2563758090144655938L;
    
    /** The rows. */
    private int rows;
    
    /** The columns. */
    private int columns;
    
    /**
     * Creates a new grid arrangement.
     * 
     * @param rows  the row count.
     * @param columns  the column count.
     */
    public GridArrangement(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
    
    /**
     * Adds a block and a key which can be used to determine the position of 
     * the block in the arrangement.  This method is called by the container 
     * (you don't need to call this method directly) and gives the arrangement
     * an opportunity to record the details if they are required.
     * 
     * @param block  the block.
     * @param key  the key (<code>null</code> permitted).
     */
    public void add(Block block, Object key) {
        // can safely ignore   
    }
    
    /**
     * Arranges the blocks within the specified container, subject to the given
     * constraint.
     * 
     * @param container  the container.
     * @param constraint  the constraint.
     * @param g2  the graphics device.
     * 
     * @return The size following the arrangement.
     */
    public Size2D arrange(BlockContainer container, Graphics2D g2,
                          RectangleConstraint constraint) {
        LengthConstraintType w = constraint.getWidthConstraintType();
        LengthConstraintType h = constraint.getHeightConstraintType();
        if (w == LengthConstraintType.NONE) {
            if (h == LengthConstraintType.NONE) {
                return arrangeNN(container, g2);  
            }
            else if (h == LengthConstraintType.FIXED) {
                
                throw new RuntimeException("Not yet implemented.");  
            }
            else if (h == LengthConstraintType.RANGE) {
                // find optimum height, then map to range
                throw new RuntimeException("Not yet implemented.");  
            }
        }
        else if (w == LengthConstraintType.FIXED) {
            if (h == LengthConstraintType.NONE) {
                // find optimum height
                return arrangeFN(container, g2, constraint);  
            }
            else if (h == LengthConstraintType.FIXED) {
                return arrangeFF(container, g2, constraint);
            }
            else if (h == LengthConstraintType.RANGE) {
                // find optimum height and map to range
                return arrangeFR(container, g2, constraint);  
            }
        }
        else if (w == LengthConstraintType.RANGE) {
            // find optimum width and map to range
            if (h == LengthConstraintType.NONE) {
                // find optimum height
                throw new RuntimeException("Not yet implemented.");  
            }
            else if (h == LengthConstraintType.FIXED) {
                // fixed width
                throw new RuntimeException("Not yet implemented.");  
            }
            else if (h == LengthConstraintType.RANGE) {
                throw new RuntimeException("Not yet implemented.");  
            }
        }
        return new Size2D();  // TODO: complete this
    }
    
    /**
     * Arranges the container with no constraint on the width or height.
     * 
     * @param container  the container.
     * @param g2  the graphics device.
     * 
     * @return The size.
     */
    protected Size2D arrangeNN(BlockContainer container, Graphics2D g2) {
        double maxW = 0.0;
        double maxH = 0.0;
        List blocks = container.getBlocks();
        Iterator iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block b = (Block) iterator.next();
            Size2D s = b.arrange(g2, RectangleConstraint.NONE);
            maxW = Math.max(maxW, s.width);
            maxH = Math.max(maxH, s.height);
        }
        double width = this.columns * maxW;
        double height = this.rows * maxH;
        RectangleConstraint c = new RectangleConstraint(width, height);
        return arrangeFF(container, g2, c);
    }
    
    /**
     * Arranges the container with a fixed overall width and height.
     * 
     * @param container  the container.
     * @param g2  the graphics device.
     * @param constraint  the constraint.
     * 
     * @return The size following the arrangement.
     */
    protected Size2D arrangeFF(BlockContainer container, Graphics2D g2,
                               RectangleConstraint constraint) {
        double width = constraint.getWidth() /  this.columns;
        double height = constraint.getHeight() / this.rows;
        List blocks = container.getBlocks();
        for (int c = 0; c < this.columns; c++) {
            for (int r = 0; r < this.rows; r++) {
                int index = r * this.columns + c;
                if (index == blocks.size()) {
                    break;   
                }
                Block b = (Block) blocks.get(index);
                b.setBounds(new Rectangle2D.Double(
                    c * width, r * height, width, height
                ));
            }
        }
        return new Size2D(this.columns * width, this.rows * height);
    }

    /**
     * Arrange with a fixed width and a height within a given range.
     * 
     * @param container  the container.
     * @param constraint  the constraint.
     * @param g2  the graphics device.
     * 
     * @return The size of the arrangement.
     */
    protected Size2D arrangeFR(BlockContainer container, Graphics2D g2,
                               RectangleConstraint constraint) {
        
        RectangleConstraint c1 = constraint.toUnconstrainedHeight();
        Size2D size1 = arrange(container, g2, c1);

        if (constraint.getHeightRange().contains(size1.getHeight())) {
            return size1;   
        }
        else {
            double h = constraint.getHeightRange().constrain(size1.getHeight());
            RectangleConstraint c2 = constraint.toFixedHeight(h);
            return arrange(container, g2, c2);
        }
    }

    /**
     * Arrange with a fixed width and a height within a given range.
     * 
     * @param container  the container.
     * @param g2  the graphics device.
     * @param constraint  the constraint.
     * 
     * @return The size of the arrangement.
     */
    protected Size2D arrangeFN(BlockContainer container, Graphics2D g2,
                               RectangleConstraint constraint) {
        
        double width = constraint.getWidth() /  this.columns;
        RectangleConstraint constraint2 = constraint.toFixedWidth(width);
        List blocks = container.getBlocks();
        double maxH = 0.0;
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                int index = r * this.columns + c;
                if (index == blocks.size()) {
                    break;   
                }
                Block b = (Block) blocks.get(index);
                Size2D s = b.arrange(g2, constraint2);
                maxH = Math.max(maxH, s.getHeight());
            }
        }
        RectangleConstraint constraint3 = constraint.toFixedHeight(
            maxH * this.rows
        );
        return arrange(container, g2, constraint3);
    }

    /**
     * Clears any cached layout information retained by the arrangement.
     */
    public void clear() {
        // nothing to clear   
    }
    
    /**
     * Compares this layout manager for equality with an arbitrary object.
     * 
     * @param obj  the object.
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GridArrangement)) {
            return false;   
        }
        GridArrangement that = (GridArrangement) obj;
        if (this.columns != that.columns) {
            return false;   
        }
        if (this.rows != that.rows) {
            return false;   
        }
        return true;
    }

}

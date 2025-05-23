package org.jfree.chart.legend;

import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.block.Size2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class NoneWidthConstraint implements WidthConstraint {
    private transient Shape line;
    private transient Shape shape;

    /**
     * @param g2
     * @param constraint
     * @return
     */
    @Override
    public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
        if (constraint.getHeightConstraintType() == LengthConstraintType.NONE) {
            return arrangeNN(g2);
        }
        else if (constraint.getHeightConstraintType() == LengthConstraintType.RANGE) {
            throw new RuntimeException("Not yet implemented.");
        }
        else if (constraint.getHeightConstraintType() == LengthConstraintType.FIXED) {
            throw new RuntimeException("Not yet implemented.");
        }
        return null;
    }
    /**
     * Performs the layout with no constraint, so the content size is
     * determined by the bounds of the shape and/or line drawn to represent
     * the series.
     *
     * @param g2  the graphics device.
     *
     * @return  The content size.
     */
    public Size2D arrangeNN(Graphics2D g2) {
        Rectangle2D contentSize = new Rectangle2D.Double();
        if (this.line != null) {
            contentSize.setRect(this.line.getBounds2D());
        }
        if (this.shape != null) {
            contentSize = contentSize.createUnion(this.shape.getBounds2D());
        }
        return new Size2D(contentSize.getWidth(), contentSize.getHeight());
    }

}

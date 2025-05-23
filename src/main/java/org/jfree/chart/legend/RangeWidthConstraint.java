package org.jfree.chart.legend;

import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.block.Size2D;

import java.awt.*;

public class RangeWidthConstraint implements WidthConstraint {
    /**
     * @param g2
     * @param constraint
     * @return
     */
    @Override
    public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
        if (constraint.getHeightConstraintType() == LengthConstraintType.NONE) {
            throw new RuntimeException("Not yet implemented.");
        }
        else if (constraint.getHeightConstraintType() == LengthConstraintType.RANGE) {
            throw new RuntimeException("Not yet implemented.");
        }
        else if (constraint.getHeightConstraintType() == LengthConstraintType.FIXED) {
            throw new RuntimeException("Not yet implemented.");
        }
        return null;
    }
}

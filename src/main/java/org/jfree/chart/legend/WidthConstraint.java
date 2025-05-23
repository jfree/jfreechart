package org.jfree.chart.legend;

import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.block.Size2D;

import java.awt.*;

public interface WidthConstraint {
    Size2D arrange(Graphics2D g2, RectangleConstraint constraint);
}

package org.jfree.chart.ui;

import java.awt.GradientPaint;
import java.awt.Shape;

/**
 * The interface for a class that can transform a <code>GradientPaint</code> to
 * fit an arbitrary shape.
 */
public interface GradientPaintTransformer {
    
    /**
     * Transforms a <code>GradientPaint</code> instance to fit some target 
     * shape.  Classes that implement this method typically return a new
     * instance of <code>GradientPaint</code>.
     * 
     * @param paint  the original paint (not <code>null</code>).
     * @param target  the reference area (not <code>null</code>).
     * 
     * @return A transformed paint.
     */
    public GradientPaint transform(GradientPaint paint, Shape target);

}


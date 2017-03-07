package org.jfree.chart.ui;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Used to indicate an anchor point for a rectangle.
 */
public final class RectangleAnchor implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2457494205644416327L;
    
    /** Center. */
    public static final RectangleAnchor CENTER 
        = new RectangleAnchor("RectangleAnchor.CENTER");

    /** Top. */
    public static final RectangleAnchor TOP 
        = new RectangleAnchor("RectangleAnchor.TOP");

    /** Top-Left. */
    public static final RectangleAnchor TOP_LEFT 
        = new RectangleAnchor("RectangleAnchor.TOP_LEFT");

    /** Top-Right. */
    public static final RectangleAnchor TOP_RIGHT 
        = new RectangleAnchor("RectangleAnchor.TOP_RIGHT");

    /** Bottom. */
    public static final RectangleAnchor BOTTOM 
        = new RectangleAnchor("RectangleAnchor.BOTTOM");

    /** Bottom-Left. */
    public static final RectangleAnchor BOTTOM_LEFT 
        = new RectangleAnchor("RectangleAnchor.BOTTOM_LEFT");

    /** Bottom-Right. */
    public static final RectangleAnchor BOTTOM_RIGHT 
        = new RectangleAnchor("RectangleAnchor.BOTTOM_RIGHT");

    /** Left. */
    public static final RectangleAnchor LEFT 
        = new RectangleAnchor("RectangleAnchor.LEFT");

    /** Right. */
    public static final RectangleAnchor RIGHT 
        = new RectangleAnchor("RectangleAnchor.RIGHT");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private RectangleAnchor(String name) {
        this.name = name;
    }

    /**
     * Returns a string representing the object.
     *
     * @return The string.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Returns <code>true</code> if this object is equal to the specified 
     * object, and <code>false</code> otherwise.
     *
     * @param obj  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RectangleAnchor)) {
            return false;
        }

        final RectangleAnchor order = (RectangleAnchor) obj;
        if (!this.name.equals(order.name)) {
            return false;
        }

        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hashcode
     */
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Returns the (x, y) coordinates of the specified anchor.
     * 
     * @param rectangle  the rectangle.
     * @param anchor  the anchor.
     * 
     * @return The (x, y) coordinates.
     */
    public static Point2D coordinates(final Rectangle2D rectangle, 
                                      final RectangleAnchor anchor) {
        Point2D result = new Point2D.Double();
        if (anchor == RectangleAnchor.CENTER) {
            result.setLocation(rectangle.getCenterX(), rectangle.getCenterY());
        }
        else if (anchor == RectangleAnchor.TOP) {
            result.setLocation(rectangle.getCenterX(), rectangle.getMinY());
        }
        else if (anchor == RectangleAnchor.BOTTOM) {
            result.setLocation(rectangle.getCenterX(), rectangle.getMaxY());
        }
        else if (anchor == RectangleAnchor.LEFT) {
            result.setLocation(rectangle.getMinX(), rectangle.getCenterY());
        }
        else if (anchor == RectangleAnchor.RIGHT) {
            result.setLocation(rectangle.getMaxX(), rectangle.getCenterY());
        }
        else if (anchor == RectangleAnchor.TOP_LEFT) {
            result.setLocation(rectangle.getMinX(), rectangle.getMinY());
        }
        else if (anchor == RectangleAnchor.TOP_RIGHT) {
            result.setLocation(rectangle.getMaxX(), rectangle.getMinY());
        }
        else if (anchor == RectangleAnchor.BOTTOM_LEFT) {
            result.setLocation(rectangle.getMinX(), rectangle.getMaxY());
        }
        else if (anchor == RectangleAnchor.BOTTOM_RIGHT) {
            result.setLocation(rectangle.getMaxX(), rectangle.getMaxY());
        }
        return result;
    }
    
    /**
     * Creates a new rectangle with the specified dimensions that is aligned to
     * the given anchor point {@code (anchorX, anchorY)}.
     * 
     * @param dimensions  the dimensions ({@code null} not permitted).
     * @param anchorX  the x-anchor.
     * @param anchorY  the y-anchor.
     * @param anchor  the anchor ({@code null} not permitted).
     * 
     * @return A rectangle.
     */
    public static Rectangle2D createRectangle(Size2D dimensions, 
            double anchorX, double anchorY, RectangleAnchor anchor) {
        Rectangle2D result = null;
        final double w = dimensions.getWidth();
        final double h = dimensions.getHeight();
        if (anchor == RectangleAnchor.CENTER) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, 
                    anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.TOP) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, anchorY, w, h);
        } else if (anchor == RectangleAnchor.BOTTOM) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, anchorY - h, 
                    w, h);
        } else if (anchor == RectangleAnchor.LEFT) {
            result = new Rectangle2D.Double(anchorX, anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.RIGHT) {
            result = new Rectangle2D.Double(anchorX - w, anchorY - h / 2.0, 
                    w, h);
        } else if (anchor == RectangleAnchor.TOP_LEFT) {
            result = new Rectangle2D.Double(anchorX, anchorY, w, h);
        } else if (anchor == RectangleAnchor.TOP_RIGHT) {
            result = new Rectangle2D.Double(anchorX - w, anchorY, w, h);
        } else if (anchor == RectangleAnchor.BOTTOM_LEFT) {
            result = new Rectangle2D.Double(anchorX, anchorY - h, w, h);
        } else if (anchor == RectangleAnchor.BOTTOM_RIGHT) {
            result = new Rectangle2D.Double(anchorX - w, anchorY - h, w, h);
        }
        return result;
    }
    
    /**
     * Ensures that serialization returns the unique instances.
     * 
     * @return The object.
     * 
     * @throws ObjectStreamException if there is a problem.
     */
    private Object readResolve() throws ObjectStreamException {
        RectangleAnchor result = null;
        if (this.equals(RectangleAnchor.CENTER)) {
            result = RectangleAnchor.CENTER;
        }
        else if (this.equals(RectangleAnchor.TOP)) {
            result = RectangleAnchor.TOP;
        }
        else if (this.equals(RectangleAnchor.BOTTOM)) {
            result = RectangleAnchor.BOTTOM;
        }
        else if (this.equals(RectangleAnchor.LEFT)) {
            result = RectangleAnchor.LEFT;
        }
        else if (this.equals(RectangleAnchor.RIGHT)) {
            result = RectangleAnchor.RIGHT;
        }
        else if (this.equals(RectangleAnchor.TOP_LEFT)) {
            result = RectangleAnchor.TOP_LEFT;
        }
        else if (this.equals(RectangleAnchor.TOP_RIGHT)) {
            result = RectangleAnchor.TOP_RIGHT;
        }
        else if (this.equals(RectangleAnchor.BOTTOM_LEFT)) {
            result = RectangleAnchor.BOTTOM_LEFT;
        }
        else if (this.equals(RectangleAnchor.BOTTOM_RIGHT)) {
            result = RectangleAnchor.BOTTOM_RIGHT;
        }
        return result;
    }
    
}


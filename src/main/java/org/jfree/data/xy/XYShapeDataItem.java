package org.jfree.data.xy;

import java.awt.Shape;

public class XYShapeDataItem extends XYDataItem {

	
	private static final long serialVersionUID = 2477972108001149458L;

	private Shape shape;
	private Boolean hasShape = false;
	
	public XYShapeDataItem(double x, double y) {
		super(x, y);
	}
	
	public XYShapeDataItem(Number x, Number y) {
        super(x, y);
    }
	
	public XYShapeDataItem(double x, double y, Shape shape) {
		super(x, y);

		this.shape = shape;
		this.hasShape = true;
	}
	
	public XYShapeDataItem(Number x, Number y, Shape shape) {
        super(x, y);
        this.shape = shape;
        this.hasShape = true;
    }

	public void setShape(Shape shape) {
		this.shape = shape;
		this.hasShape = true;
	}
	
	public Shape getShape() {
		return this.shape;
	}
	
	public Boolean getHasShape() {
		return this.hasShape;
	}
	
    @Override
    public Object clone() {
        Object clone = null;
        clone = super.clone();
        return clone;
    }
    
    /**
     * Returns a string representing this instance, primarily for debugging
     * use.
     *
     * @return A string.
     */
    @Override
    public String toString() {
        return "[" + getXValue() + ", " + getYValue() + ", " + getShape().toString() + "]";
    }


}

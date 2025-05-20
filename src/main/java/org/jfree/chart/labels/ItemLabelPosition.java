/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * ----------------------
 * ItemLabelPosition.java
 * ----------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Yuri Blankenstein;
 *                   Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.util.Objects;
import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.internal.Args;

/**
 * The attributes that control the position of the label for each data item on
 * a chart.  Instances of this class are immutable.
 */
public class ItemLabelPosition implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 5845390630157034499L;

    /** The item label anchor point. */
    private final ItemLabelAnchor itemLabelAnchor;

    /** The text anchor. */
    private final TextAnchor textAnchor;

    /** The rotation anchor. */
    private final TextAnchor rotationAnchor;

    /** The rotation angle. */
    private final double angle;

    /** The item label clip type. */
    private final ItemLabelClip itemLabelClip;

    /**
     * Creates a new position record with default settings.
     */
    public ItemLabelPosition() {
        this(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER,
                TextAnchor.CENTER, 0.0);
    }

    /**
     * Creates a new position record (with zero rotation).
     *
     * @param itemLabelAnchor  the item label anchor ({@code null} not
     *                         permitted).
     * @param textAnchor  the text anchor ({@code null} not permitted).
     */
    public ItemLabelPosition(ItemLabelAnchor itemLabelAnchor,
                             TextAnchor textAnchor) {
        this(itemLabelAnchor, textAnchor, TextAnchor.CENTER, 0.0);
    }

    /**
     * Creates a new position record. The item label anchor is a point relative
     * to the data item (dot, bar or other visual item) on a chart. The item
     * label is aligned by aligning the text anchor with the item label anchor.
     *
     * @param itemLabelAnchor the item label anchor ({@code null} not
     *                        permitted).
     * @param textAnchor      the text anchor ({@code null} not permitted).
     * @param itemLabelClip   The clip type for the label ({@code null} not
     *                        permitted. Only used when
     *                        {@link ItemLabelAnchor#isInternal()} returns
     *                        {@code true}, if {@code false} {@code labelClip}
     *                        is always considered to be
     *                        {@link ItemLabelClip#NONE})
     */
    public ItemLabelPosition(ItemLabelAnchor itemLabelAnchor,
            TextAnchor textAnchor, ItemLabelClip itemLabelClip) {
        this(itemLabelAnchor, textAnchor, TextAnchor.CENTER, 0.0,
                itemLabelClip);
    }

    /**
     * Creates a new position record.  The item label anchor is a point
     * relative to the data item (dot, bar or other visual item) on a chart.
     * The item label is aligned by aligning the text anchor with the
     * item label anchor.
     *
     * @param itemLabelAnchor  the item label anchor ({@code null} not
     *                         permitted).
     * @param textAnchor  the text anchor ({@code null} not permitted).
     * @param rotationAnchor  the rotation anchor ({@code null} not
     *                        permitted).
     * @param angle  the rotation angle (in radians).
     */
    public ItemLabelPosition(ItemLabelAnchor itemLabelAnchor,
            TextAnchor textAnchor, TextAnchor rotationAnchor, double angle) {
        this(itemLabelAnchor, textAnchor, rotationAnchor, angle,
                ItemLabelClip.FIT);

    }

    /**
     * Creates a new position record. The item label anchor is a point relative
     * to the data item (dot, bar or other visual item) on a chart. The item
     * label is aligned by aligning the text anchor with the item label anchor.
     *
     * @param itemLabelAnchor the item label anchor ({@code null} not
     *                        permitted).
     * @param textAnchor      the text anchor ({@code null} not permitted).
     * @param rotationAnchor  the rotation anchor ({@code null} not permitted).
     * @param angle           the rotation angle (in radians).
     * @param itemLabelClip   The clip type for the label ({@code null} not
     *                        permitted. Only used when
     *                        {@link ItemLabelAnchor#isInternal()} returns
     *                        {@code true}, if {@code false} {@code labelClip}
     *                        is always considered to be
     *                        {@link ItemLabelClip#NONE})
     */
    public ItemLabelPosition(ItemLabelAnchor itemLabelAnchor,
            TextAnchor textAnchor, TextAnchor rotationAnchor, double angle,
            ItemLabelClip itemLabelClip) {

        Args.nullNotPermitted(itemLabelAnchor, "itemLabelAnchor");
        Args.nullNotPermitted(textAnchor, "textAnchor");
        Args.nullNotPermitted(rotationAnchor, "rotationAnchor");
        Args.nullNotPermitted(itemLabelClip, "labelClip");
        this.itemLabelAnchor = itemLabelAnchor;
        this.textAnchor = textAnchor;
        this.rotationAnchor = rotationAnchor;
        this.angle = angle;
        this.itemLabelClip = itemLabelClip;
    }

    /**
     * Returns the item label anchor.
     *
     * @return The item label anchor (never {@code null}).
     */
    public ItemLabelAnchor getItemLabelAnchor() {
        return this.itemLabelAnchor;
    }

    /**
     * Returns the text anchor.
     *
     * @return The text anchor (never {@code null}).
     */
    public TextAnchor getTextAnchor() {
        return this.textAnchor;
    }

    /**
     * Returns the rotation anchor point.
     *
     * @return The rotation anchor point (never {@code null}).
     */
    public TextAnchor getRotationAnchor() {
        return this.rotationAnchor;
    }

    /**
     * Returns the angle of rotation for the label.
     *
     * @return The angle (in radians).
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Returns the clip type for the label.
     *
     * @return The clip type for the label.
     */
    public ItemLabelClip getItemLabelClip() {
		return this.itemLabelClip;
	}

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ItemLabelPosition)) {
            return false;
        }
        ItemLabelPosition that = (ItemLabelPosition) obj;
        if (!Objects.equals(this.itemLabelAnchor, that.itemLabelAnchor)) {
            return false;
        }
        if (!Objects.equals(this.textAnchor, that.textAnchor)) {
            return false;
        }
        if (!Objects.equals(this.rotationAnchor, that.rotationAnchor)) {
            return false;
        }
        if (Double.doubleToLongBits(this.angle) != Double.doubleToLongBits(that.angle)) {
            return false;
        }
        if (!Objects.equals(this.itemLabelClip, that.itemLabelClip)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.itemLabelAnchor);
        hash = 97 * hash + Objects.hashCode(this.textAnchor);
        hash = 97 * hash + Objects.hashCode(this.rotationAnchor);
        hash = 97 * hash + Long.hashCode(Double.doubleToLongBits(this.angle));
        hash = 97 * hash + Objects.hashCode(this.itemLabelClip);
		return hash;
    }
}

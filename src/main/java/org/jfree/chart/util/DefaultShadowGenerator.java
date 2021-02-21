/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ---------------------------
 * DefaultShadowGenerator.java
 * ---------------------------
 * (C) Copyright 2009-2021 by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Serializable;
import org.jfree.chart.HashUtils;

/**
 * A default implementation of the {@link ShadowGenerator} interface, based on
 * code in a 
 * <a href="http://www.jroller.com/gfx/entry/fast_or_good_drop_shadows">blog
 * post by Romain Guy</a>.
 */
public class DefaultShadowGenerator implements ShadowGenerator, Serializable {

    private static final long serialVersionUID = 2732993885591386064L;

    /** The shadow size. */
    private int shadowSize;

    /** The shadow color. */
    private Color shadowColor;

    /** The shadow opacity. */
    private float shadowOpacity;

    /** The shadow offset angle (in radians). */
    private double angle;

    /** The shadow offset distance (in Java2D units). */
    private int distance;

    /**
     * Creates a new instance with default attributes.
     */
    public DefaultShadowGenerator() {
        this(5, Color.BLACK, 0.5f, 5, -Math.PI / 4);
    }

    /**
     * Creates a new instance with the specified attributes.
     *
     * @param size  the shadow size.
     * @param color  the shadow color.
     * @param opacity  the shadow opacity.
     * @param distance  the shadow offset distance.
     * @param angle  the shadow offset angle (in radians).
     */
    public DefaultShadowGenerator(int size, Color color, float opacity,
            int distance, double angle) {
        Args.nullNotPermitted(color, "color");
        this.shadowSize = size;
        this.shadowColor = color;
        this.shadowOpacity = opacity;
        this.distance = distance;
        this.angle = angle;
    }

    /**
     * Returns the shadow size.
     *
     * @return The shadow size.
     */
    public int getShadowSize() {
        return this.shadowSize;
    }

    /**
     * Returns the shadow color.
     *
     * @return The shadow color (never {@code null}).
     */
    public Color getShadowColor() {
        return this.shadowColor;
    }

    /**
     * Returns the shadow opacity.
     *
     * @return The shadow opacity.
     */
    public float getShadowOpacity() {
        return this.shadowOpacity;
    }

    /**
     * Returns the shadow offset distance.
     *
     * @return The shadow offset distance (in Java2D units).
     */
    public int getDistance() {
        return this.distance;
    }

    /**
     * Returns the shadow offset angle (in radians).
     *
     * @return The angle (in radians).
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Calculates the x-offset for drawing the shadow image relative to the
     * source.
     *
     * @return The x-offset.
     */
    @Override
    public int calculateOffsetX() {
        return (int) (Math.cos(this.angle) * this.distance) - this.shadowSize;
    }

    /**
     * Calculates the y-offset for drawing the shadow image relative to the
     * source.
     *
     * @return The y-offset.
     */
    @Override
    public int calculateOffsetY() {
        return -(int) (Math.sin(this.angle) * this.distance) - this.shadowSize;
    }

    /**
     * Creates and returns an image containing the drop shadow for the
     * specified source image.
     *
     * @param source  the source image.
     *
     * @return A new image containing the shadow.
     */
    @Override
    public BufferedImage createDropShadow(BufferedImage source) {
        BufferedImage subject = new BufferedImage(
                source.getWidth() + this.shadowSize * 2,
                source.getHeight() + this.shadowSize * 2,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = subject.createGraphics();
        g2.drawImage(source, null, this.shadowSize, this.shadowSize);
        g2.dispose();
        applyShadow(subject);
        return subject;
    }

    /**
     * Applies a shadow to the image.
     *
     * @param image  the image.
     */
    protected void applyShadow(BufferedImage image) {
        int dstWidth = image.getWidth();
        int dstHeight = image.getHeight();

        int left = (this.shadowSize - 1) >> 1;
        int right = this.shadowSize - left;
        int xStart = left;
        int xStop = dstWidth - right;
        int yStart = left;
        int yStop = dstHeight - right;

        int shadowRgb = this.shadowColor.getRGB() & 0x00FFFFFF;

        int[] aHistory = new int[this.shadowSize];
        int historyIdx;

        int aSum;

        int[] dataBuffer = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int lastPixelOffset = right * dstWidth;
        float sumDivider = this.shadowOpacity / this.shadowSize;

        // horizontal pass

        for (int y = 0, bufferOffset = 0; y < dstHeight; y++, bufferOffset = y * dstWidth) {
            aSum = 0;
            historyIdx = 0;
            for (int x = 0; x < this.shadowSize; x++, bufferOffset++) {
                int a = dataBuffer[bufferOffset] >>> 24;
                aHistory[x] = a;
                aSum += a;
            }

            bufferOffset -= right;

            for (int x = xStart; x < xStop; x++, bufferOffset++) {
                int a = (int) (aSum * sumDivider);
                dataBuffer[bufferOffset] = a << 24 | shadowRgb;

                // substract the oldest pixel from the sum
                aSum -= aHistory[historyIdx];

                // get the lastest pixel
                a = dataBuffer[bufferOffset + right] >>> 24;
                aHistory[historyIdx] = a;
                aSum += a;

                if (++historyIdx >= this.shadowSize) {
                    historyIdx -= this.shadowSize;
                }
            }
        }

        // vertical pass
        for (int x = 0, bufferOffset = 0; x < dstWidth; x++, bufferOffset = x) {
            aSum = 0;
            historyIdx = 0;
            for (int y = 0; y < this.shadowSize; y++,
                    bufferOffset += dstWidth) {
                int a = dataBuffer[bufferOffset] >>> 24;
                aHistory[y] = a;
                aSum += a;
            }

            bufferOffset -= lastPixelOffset;

            for (int y = yStart; y < yStop; y++, bufferOffset += dstWidth) {
                int a = (int) (aSum * sumDivider);
                dataBuffer[bufferOffset] = a << 24 | shadowRgb;

                // substract the oldest pixel from the sum
                aSum -= aHistory[historyIdx];

                // get the lastest pixel
                a = dataBuffer[bufferOffset + lastPixelOffset] >>> 24;
                aHistory[historyIdx] = a;
                aSum += a;

                if (++historyIdx >= this.shadowSize) {
                    historyIdx -= this.shadowSize;
                }
            }
        }
    }

    /**
     * Tests this object for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return The object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultShadowGenerator)) {
            return false;
        }
        DefaultShadowGenerator that = (DefaultShadowGenerator) obj;
        if (this.shadowSize != that.shadowSize) {
            return false;
        }
        if (!this.shadowColor.equals(that.shadowColor)) {
            return false;
        }
        if (this.shadowOpacity != that.shadowOpacity) {
            return false;
        }
        if (this.distance != that.distance) {
            return false;
        }
        if (this.angle != that.angle) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     * 
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        int hash = HashUtils.hashCode(17, this.shadowSize);
        hash = HashUtils.hashCode(hash, this.shadowColor);
        hash = HashUtils.hashCode(hash, this.shadowOpacity);
        hash = HashUtils.hashCode(hash, this.distance);
        hash = HashUtils.hashCode(hash, this.angle);
        return hash;
    }

}

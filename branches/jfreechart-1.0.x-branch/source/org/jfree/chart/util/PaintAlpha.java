/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * ---------------
 * PaintAlpha.java
 * ---------------
 * (C) Copyright 2011 by DaveLaw and Contributors.
 *
 * Original Author:  DaveLaw (dave ATT davelaw D0TT de);
 * Contributor(s):   could this be you?;
 *
 * Changes
 * -------
 * 09-Mar-2011 : Written (DaveLaw)
 * 03-Jul-2012 : JDK 1.6 References made reflective for JDK 1.3 compatibility (
 *               DaveLaw)
 *
 */

package org.jfree.chart.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

/**
 * This class contains static methods for the manipulation
 * of objects of type <code>Paint</code>
 * <p>
 * The intention is to honour the alpha-channel in the process.
 * <code>PaintAlpha</code> was originally conceived to improve the
 * rendering of 3D Shapes with transparent colours and to allow
 * invisible bars by making them completely transparent.
 * <p>
 * In, for example
 * {@link org.jfree.chart.renderer.category.StackedBarRenderer3D StackedBarRenderer3D},
 * bars are rendered with 6 faces. The front face is rendered with
 * the <code>Paint</code> requested. The other 5 faces are rendered
 * darker to achieve the 3D effect.
 * <p>
 * Previously {@link Color#darker()} was used for this,
 * which always returns an opaque colour.
 * <p>
 * Additionally there are methods to control the behaviour and
 * in particular a {@link PaintAlpha#cloneImage(BufferedImage) cloneImage(..)}
 * method which is needed to darken objects of type {@link TexturePaint}.
 *
 * @author  DaveLaw
 */
public class PaintAlpha {
    // TODO Revert to SVN revision 2469 in JFreeChart 1.0.16
    //      (MultipleGradientPaint's / JDK issues)
    // TODO THEN: change visibility of ALL darker(...) Methods EXCEPT
    //      darker(Paint) to private!

    /**
     * Multiplier for the <code>darker</code> Methods.<br>
     * (taken from {@link java.awt.Color}.FACTOR)
     */
    private static final double FACTOR = 0.7;

    private static boolean legacyAlpha = false;

    /**
     * Per default <code>PaintAlpha</code> will try to honour alpha-channel
     * information.  In the past this was not the case.
     * If you wish legacy functionality for your application you can request
     * this here.
     *
     * @param legacyAlpha boolean
     *
     * @return the previous setting
     */
    public static boolean setLegacyAlpha(boolean legacyAlpha) {
        boolean old = PaintAlpha.legacyAlpha;
        PaintAlpha.legacyAlpha = legacyAlpha;
        return old;
    }

    /**
     * Create a new (if possible, darker) <code>Paint</code> of the same Type.
     * If the Type is not supported, the original <code>Paint</code> is returned.
     * <p>
     * @param paint a <code>Paint</code> implementation
     * (e.g. {@link Color}, {@link GradientPaint}, {@link TexturePaint},..)
     * <p>
     * @return a (usually new, see above) <code>Paint</code>
     */
    public static Paint darker(Paint paint) {

        if (paint instanceof Color) {
            return darker((Color) paint);
        }
        if (legacyAlpha == true) {
            /*
             * Legacy? Just return the original Paint.
             * (this corresponds EXACTLY to how Paints used to be darkened)
             */
            return paint;
        }
        if (paint instanceof GradientPaint) {
            return darker((GradientPaint) paint);
        }
        if (paint.getClass().getName().equals("java.awt.LinearGradientPaint")) {
            // TODO -> instanceof
            return darkerLinearGradientPaint(paint);
        }
        if (paint.getClass().getName().equals("java.awt.RadialGradientPaint")) {
            // TODO -> instanceof
            return darkerRadialGradientPaint(paint);
        }
        if (paint instanceof TexturePaint) {
            return darker((TexturePaint) paint, true);
        }
        return paint;
    }

    /**
     * Similar to {@link Color#darker()}.
     * <p>
     * The essential difference is that this method
     * maintains the alpha-channel unchanged<br>
     *
     * @param paint a <code>Color</code>
     *
     * @return a darker version of the <code>Color</code>
     */
    private static Color darker(Color paint) {
        return new Color(
                (int)(paint.getRed  () * FACTOR),
                (int)(paint.getGreen() * FACTOR),
                (int)(paint.getBlue () * FACTOR), paint.getAlpha());
    }

    /**
     * Create a new Gradient with its colours darkened.
     *
     * @param paint a <code>GradientPaint</code>
     *
     * @return a darker version of the <code>GradientPaint</code>
     */
    private static GradientPaint darker(GradientPaint paint) {
        return new GradientPaint(
                paint.getPoint1(), darker(paint.getColor1()),
                paint.getPoint2(), darker(paint.getColor2()),
                paint.isCyclic());
    }

    /**
     * Create a new Gradient with its colours darkened.
     *
     * @param paint a <code>LinearGradientPaint</code>
     *
     * @return a darker version of the <code>LinearGradientPaint</code>
     */
    private static final Paint darkerLinearGradientPaint(Paint paint) {
        // TODO Rename->darker & change Paint->LinearGradientPaint
        try {
            final Color[] paintColours = (Color[]) invokeZeroArgumentMethod(
                    paint, "getColors");

            for (int i = 0; i < paintColours.length; i++) {
                paintColours[i] = darker(paintColours[i]);
            }

            final Constructor[] constructors
                    = paint.getClass().getConstructors();

            for (int i = 0; i < constructors.length; i++) {

                final Class[] args = constructors[i].getParameterTypes();

                if (args.length == 7
                        &&  args[args.length-1].equals(AffineTransform.class)) {

                    return (Paint) constructors[i].newInstance(new Object[] {
                        (Point2D) invokeZeroArgumentMethod(paint, "getStartPoint"),
                        (Point2D) invokeZeroArgumentMethod(paint, "getEndPoint"),
                        (float[]) invokeZeroArgumentMethod(paint, "getFractions"),
                        paintColours,
                        (Object) invokeZeroArgumentMethod(paint, "getCycleMethod"),
                        (Object) invokeZeroArgumentMethod(paint, "getColorSpace"),
                        (AffineTransform) invokeZeroArgumentMethod(paint,
                            "getTransform") });
                    }
                }
            } catch (IllegalArgumentException e) {
            } catch (SecurityException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            } catch (InstantiationException e) {
        }
        throw new UnsupportedOperationException(
                "Probably new Constructor signatures in newer JDK");
    }

    /**
     * Create a new Gradient with its colours darkened.
     *
     * @param paint a <code>RadialGradientPaint</code>
     *
     * @return a darker version of the <code>RadialGradientPaint</code>
     */
    private static final Paint darkerRadialGradientPaint(Paint paint) {
        // TODO Rename->darker & change Paint->RadialGradientPaint
        try {
            final Color[] paintColours
                    = (Color[]) invokeZeroArgumentMethod(paint, "getColors");

            for (int i = 0; i < paintColours.length; i++) {
                paintColours[i] = darker(paintColours[i]);
            }

            final Constructor[] constructors
                    = paint.getClass().getConstructors();

            for (int i = 0; i < constructors.length; i++) {

                final Class[] args = constructors[i].getParameterTypes();

                if (     args.length == 8
                &&  args[args.length-1].equals(AffineTransform.class)) {

                    return (Paint) constructors[i].newInstance(new Object[] {
                        (Point2D) invokeZeroArgumentMethod(paint, "getCenterPoint"),
                        (Float) invokeZeroArgumentMethod(paint, "getRadius"),
                        (Point2D) invokeZeroArgumentMethod(paint, "getFocusPoint"),
                        (float[]) invokeZeroArgumentMethod(paint, "getFractions"),
                        paintColours,
                        (Object) invokeZeroArgumentMethod(paint, "getCycleMethod"),
                        (Object) invokeZeroArgumentMethod(paint, "getColorSpace"),
                        (AffineTransform) invokeZeroArgumentMethod(paint,
                            "getTransform") });
                }
            }
        } catch (IllegalArgumentException e) {
        } catch (SecurityException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        } catch (InstantiationException e) {
        }
        throw new UnsupportedOperationException(
                "Probably new Constructor signatures in newer JDK");
    }

    /**
     * Convenience method to invoke the zero argument <code>methodName</code>
     * method of <code>object</code> via Reflection.
     *
     * @param object
     * @param methodName
     *
     * @return the result
     *
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private static final Object invokeZeroArgumentMethod(Object object,
            String methodName) throws IllegalArgumentException,
            SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        return object.getClass().getMethod(methodName,
                new Class[] {}).invoke(object, new Object[] {});
    }

    /**
     * Create a new <code>TexturePaint</code> with its colours darkened.
     * <p>
     * This entails cloning the underlying <code>BufferedImage</code>,
     * then darkening each colour-pixel individually!
     *
     * @param paint a <code>TexturePaint</code>
     *
     * @param ignoreThisDummyArgument which is just to guarantee a unique
     *        method signature
     *
     * @return a darker version of the <code>TexturePaint</code>
     */
    private static TexturePaint darker(TexturePaint paint,
            boolean ignoreThisDummyArgument) {
        try {
            return darker(paint);
        }
        catch (Exception e) {
            /*
             * Lots can go wrong while fiddling with Images, Colour Models
             * & such!  If anything at all goes awry, just return the original
             * TexturePaint.  (TexturePaint's are immutable anyway, so no harm
             * done)
             */
            return paint;
        }
    }

    /**
     * Create a new <code>TexturePaint</code> with its colours darkened.
     * <p>
     * This entails cloning the underlying <code>BufferedImage</code>,
     * then darkening each colour-pixel individually!
     *
     * @param paint a <code>TexturePaint</code>
     *
     * @return a darker version of the <code>TexturePaint</code>
     */
    private static TexturePaint darker(TexturePaint paint) {
        /**
         * Colour Models with pre-multiplied Alpha tested OK without any
         * special logic
         *
         * BufferedImage.TYPE_INT_ARGB_PRE:    // Type 03: tested OK 2011.02.27
         * BufferedImage.TYPE_4BYTE_ABGR_PRE:  // Type 07: tested OK 2011.02.27
         */
        if (paint.getImage().getColorModel().isAlphaPremultiplied()) {
            /* Placeholder */
        }

        BufferedImage img = cloneImage(paint.getImage());

        WritableRaster ras = img.copyData(null);

        final int miX = ras.getMinX();
        final int miY = ras.getMinY();
        final int maY = ras.getMinY() + ras.getHeight();

        final int   wid = ras.getWidth();

        /**/  int[] pix = new int[wid * img.getSampleModel().getNumBands()];
        /* (pix-buffer is large enough for all pixels of one row) */

        /**
         * Indexed Colour Models (sort of a Palette) CANNOT be simply
         * multiplied (the pixel-value is just an index into the Palette).
         *
         * Fortunately, IndexColorModel.getComponents(..) resolves the colours.
         * The resolved colours can then be multiplied by our FACTOR.
         * IndexColorModel.getDataElement(..) then tries to map the computed
         * colour to the "nearest" in the Palette.
         *
         * It is quite possible that the "nearest" colour is the ORIGINAL
         * colour!  In the worst case, the returned Image will be identical to
         * the original.
         *
         * Applies to following Image Types:
         *
         * BufferedImage.TYPE_BYTE_BINARY:    // Type 12: tested OK 2011.02.27
         * BufferedImage.TYPE_BYTE_INDEXED:   // Type 13: tested OK 2011.02.27
         */
        if (img.getColorModel() instanceof IndexColorModel) {

            int[] nco = new int[4]; // RGB (+ optional Alpha which we leave
                                    // unchanged)

            for (int y = miY; y < maY; y++)  {

                pix = ras.getPixels(miX, y, wid, 1, pix);

                for (int p = 0; p < pix.length; p++) {
                    nco    =  img.getColorModel().getComponents(pix[p], nco, 0);
                    nco[0] *= FACTOR; // Red
                    nco[1] *= FACTOR; // Green
                    nco[2] *= FACTOR; // Blue. Now map computed colour to
                                      // nearest in Palette...
                    pix[p] = img.getColorModel().getDataElement(nco, 0);
                }
                /**/ ras.setPixels(miX, y, wid, 1, pix);
            }
            img.setData(ras);

            return new TexturePaint(img, paint.getAnchorRect());
        }

        /**
         * For the other 2 Colour Models, java.awt.image.ComponentColorModel and
         * java.awt.image.DirectColorModel, the order of subpixels returned by
         * ras.getPixels(..) was observed to correspond to the following...
         */
        if (img.getSampleModel().getNumBands() == 4) {
            /**
             * The following Image Types have an Alpha-channel which we will
             * leave unchanged:
             *
             * BufferedImage.TYPE_INT_ARGB:   // Type 02: tested OK 2011.02.27
             * BufferedImage.TYPE_4BYTE_ABGR: // Type 06: tested OK 2011.02.27
             */
            for (int y = miY; y < maY; y++)  {

                pix = ras.getPixels(miX, y, wid, 1, pix);

                for (int p = 0; p < pix.length;) {
                    pix[p] = (int)(pix[p++] * FACTOR); // Red
                    pix[p] = (int)(pix[p++] * FACTOR); // Green
                    pix[p] = (int)(pix[p++] * FACTOR); // Blue
                    /* Ignore alpha-channel -> */p++;
                }
                /**/  ras.setPixels(miX, y, wid, 1, pix);
            }
            img.setData(ras);
            return new TexturePaint(img, paint.getAnchorRect());
        } else {
            for (int y = miY; y < maY; y++)  {

                pix = ras.getPixels(miX, y, wid, 1, pix);

                for (int p = 0; p < pix.length; p++) {
                    pix[p] = (int)(pix[p] * FACTOR);
                }
                /**/  ras.setPixels(miX, y, wid, 1, pix);
            }
            img.setData(ras);
            return new TexturePaint(img, paint.getAnchorRect());
            /**
             * Above, we multiplied every pixel by our FACTOR because the
             * applicable Image Types consist only of colour or grey channels:
             *
             * BufferedImage.TYPE_INT_RGB:        // Type 01: tested OK 2011.02.27
             * BufferedImage.TYPE_INT_BGR:        // Type 04: tested OK 2011.02.27
             * BufferedImage.TYPE_3BYTE_BGR:      // Type 05: tested OK 2011.02.27
             * BufferedImage.TYPE_BYTE_GRAY:      // Type 10: tested OK 2011.02.27
             * BufferedImage.TYPE_USHORT_GRAY:    // Type 11: tested OK 2011.02.27
             * BufferedImage.TYPE_USHORT_565_RGB: // Type 08: tested OK 2011.02.27
             * BufferedImage.TYPE_USHORT_555_RGB: // Type 09: tested OK 2011.02.27
             *
             * Note: as ras.getPixels(..) returned colours in the order R, G, B, A (optional)
             * for both TYPE_4BYTE_ABGR & TYPE_3BYTE_BGR,
             * it is assumed that TYPE_INT_BGR will behave similarly.
             */
        }
    }

    /**
     * Clone a {@link BufferedImage}.
     * <p>
     * Note: when constructing the clone, the original Colour Model Object is
     * reused.<br>  That keeps things simple & should not be a problem, as all
     * known Colour Models<br>
     * ({@link java.awt.image.IndexColorModel     IndexColorModel},
     *  {@link java.awt.image.DirectColorModel    DirectColorModel},
     *  {@link java.awt.image.ComponentColorModel ComponentColorModel}) are
     * immutable.
     *
     * @param image original BufferedImage to clone
     *
     * @return a new BufferedImage reusing the original's Colour Model &
     *         containing a clone of its pixels
     */
    public static BufferedImage cloneImage(BufferedImage image) {

        WritableRaster rin = image.getRaster();
        WritableRaster ras = rin.createCompatibleWritableRaster();
        /**/ ras.setRect(rin); // <- this is the code that actually COPIES the pixels

        /*
         * Buffered Images may have properties, but NEVER disclose them!
         * Nevertheless, just in case someone implements getPropertyNames()
         * one day...
         */
        Hashtable props = null;
        String[] propNames = image.getPropertyNames();
        if (propNames != null) { // ALWAYS null
            props = new Hashtable();
            for (int i = 0; i < propNames.length; i++) {
                props.put(propNames[i], image.getProperty(propNames[i]));
            }
        }
        return new BufferedImage(image.getColorModel(), ras,
                image.isAlphaPremultiplied(), props);
    }
}

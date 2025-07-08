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
 * --------------
 * TestUtils.java
 * --------------
 * (C) Copyright 2007-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.AttributedString;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.general.DefaultValueDataset;

/**
 * Some utility methods for use by the testing code.
 */
public class TestUtils {

    public static Rectangle2D createR2D(boolean isRed) {
        if (isRed) {
            return new Rectangle2D.Double(0, 0, 1, 1);
        } else {
            return new Rectangle2D.Double(1, 1, 2, 2);
        }
    }
    
    public static ResourceBundle createRB(boolean isRed) {
        if (isRed) {
            String baseName = "org.jfree.data.resources.DataPackageResources";
            return ResourceBundleWrapper.getBundle(baseName);
        } else {
            String baseName = "org.jfree.chart.LocalizationBundle";
            return ResourceBundleWrapper.getBundle(baseName);
        }
    }
    
    public static AttributedString createAS(boolean isRed) {
        if (isRed) {
            AttributedString as = new AttributedString("aaa");
            return as;
        } else {
            AttributedString as = new AttributedString("bbb");
            return as;
        }
    }

    public static Font createFont(boolean isRed) {
        if (isRed) {
            return new Font("SansSerif", Font.PLAIN, 12);
        } else {
            return new Font("Dialog", Font.BOLD, 10);
        }
    }

    public static JFreeChart createJFC(boolean isRed) {
        if (isRed) {
            return new JFreeChart("abc", new MeterPlot(new DefaultValueDataset(44)));
        } else {
            return new JFreeChart("xyz", new MeterPlot(new DefaultValueDataset(55)));
        }
    }

    public static Plot createPlot(boolean isRed) {
        FakePlot plot = new FakePlot();
        plot.setNotify(isRed);
        return plot;
    }

    public static ValueAxis createValueAxis(boolean isRed) {
        if (isRed) {
            return new FakeValueAxis("Fake1", null);
        } else {
            return new FakeValueAxis("Fake2", null);
        }
    }

    /**
     * Returns {@code true} if the collections contains any object that
     * is an instance of the specified class, and {@code false} otherwise.
     *
     * @param collection  the collection.
     * @param c  the class.
     *
     * @return A boolean.
     */
    public static boolean containsInstanceOf(Collection collection, Class c) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj != null && obj.getClass().equals(c)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Serialises an object, deserialises it and returns the deserialised 
     * version.
     * 
     * @param original  the original object.
     * 
     * @return A serialised and deserialised version of the original.
     */
    public static <K> K serialised(K original) {
        K result = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(buffer);
            out.writeObject(original);
            out.close();
            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            result = (K) in.readObject();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    
}

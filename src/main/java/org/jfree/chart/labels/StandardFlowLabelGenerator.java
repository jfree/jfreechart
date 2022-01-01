/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * -------------------------------
 * StandardFlowLabelGenerator.java
 * -------------------------------
 * (C) Copyright 2021-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.util.Formatter;
import java.util.Objects;
import org.jfree.chart.internal.Args;
import org.jfree.data.flow.FlowDataset;
import org.jfree.data.flow.FlowKey;

/**
 * Standard flow label generator.  Instances of this class are immutable.
 * 
 * @since 1.5.3
 */
public class StandardFlowLabelGenerator implements FlowLabelGenerator, Serializable {
    
    /** The default template for formatting the label. */
    public static final String DEFAULT_TEMPLATE = "%2$s to %3$s = %4$,.2f";
    
    /** The template. */
    private String template;
    
    /**
     * Creates a new instance with the default template.
     */
    public StandardFlowLabelGenerator() {
        this(DEFAULT_TEMPLATE);    
    }
    
    /**
     * Creates a new generator with the specified template.  The template
     * is passed to a Java Formatter instance along with four arguments, the
     * stage (an integer), the source (a String), the destination (a String)
     * and the flow value (a Number).
     * 
     * @param template  the template ({@code null} not permitted). 
     */
    public StandardFlowLabelGenerator(String template) {
        Args.nullNotPermitted(template, "template");
        this.template = template;
    }

    /**
     * Returns a label for the specified flow.
     * 
     * @param dataset  the flow dataset ({@code null} not permitted).
     * @param key  the flow key ({@code null} not permitted).
     * 
     * @return The label (possibly {@code null}). 
     */
    @Override
    public String generateLabel(FlowDataset dataset, FlowKey key) {
        Args.nullNotPermitted(dataset, "dataset");
        Args.nullNotPermitted(key, "key");
        String result;
        try (Formatter formatter = new Formatter(new StringBuilder())) {
            Number value = dataset.getFlow(key.getStage(), key.getSource(), key.getDestination());
            formatter.format(this.template, key.getStage(), key.getSource(), key.getDestination(), value);
            result = formatter.toString();
        }
        return result;
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StandardFlowLabelGenerator)) {
            return false;
        }
        StandardFlowLabelGenerator that = (StandardFlowLabelGenerator) obj;
        if (!this.template.equals(that.template)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.template);
        return hash;
    }

}



/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2015, by Object Refinery Limited and Contributors.
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
 * ---------------------
 * FXGraphicsDevice.java
 * ---------------------
 * (C) Copyright 2014, 2015 by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;

 * Changes:
 * --------
 * 11-Sep-2015 : Added to JFreeChart (DG);
 *
 */

package org.jfree.chart.fx;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;

/**
 * A graphics device for FXGraphics2D.
 */
public class FXGraphicsDevice extends GraphicsDevice {

    private final String id;
    
    GraphicsConfiguration defaultConfig;
    
    /**
     * Creates a new instance.
     * 
     * @param id  the id.
     * @param defaultConfig  the default configuration.
     */
    public FXGraphicsDevice(String id, GraphicsConfiguration defaultConfig) {
        this.id = id;
        this.defaultConfig = defaultConfig;
    }
    
    /**
     * Returns the device type.  
     * 
     * @return The device type.
     */
    @Override
    public int getType() {
        return GraphicsDevice.TYPE_RASTER_SCREEN;
    }

    /**
     * Returns the id string.
     * 
     * @return The id string. 
     */
    @Override
    public String getIDstring() {
        return this.id;
    }

    /**
     * Returns all configurations for this device.
     * 
     * @return All configurations for this device.
     */
    @Override
    public GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] { getDefaultConfiguration() };
    }

    /**
     * Returns the default configuration for this device.
     * 
     * @return The default configuration for this device. 
     */
    @Override
    public GraphicsConfiguration getDefaultConfiguration() {
        return this.defaultConfig;
    }
    
}

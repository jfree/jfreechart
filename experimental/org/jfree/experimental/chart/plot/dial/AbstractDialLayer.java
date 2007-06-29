/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
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
 * ----------------------
 * AbstractDialLayer.java
 * ----------------------
 * (C) Copyright 2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: AbstractDialLayer.java,v 1.1.2.2 2006/11/17 11:06:44 mungady Exp $
 *
 * Changes
 * -------
 * 06-Nov-2006 : Version 1 (DG);
 * 17-Nov-2007 : Added visible flag (DG);
 * 
 */

package org.jfree.experimental.chart.plot.dial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.EventListenerList;

/**
 * A base class that can be used to implement a {@link DialLayer}.  It includes
 * an event notification mechanism.
 */
public abstract class AbstractDialLayer implements DialLayer {

    /** A flag that controls whether or not the layer is visible. */
    private boolean visible;
    
    /** Storage for registered listeners. */
    private transient EventListenerList listenerList;

    /**
     * Creates a new instance.
     */
    protected AbstractDialLayer() {
        this.visible = true;
        this.listenerList = new EventListenerList();
    }
    
    /**
     * Returns <code>true</code> if this layer is visible (should be displayed),
     * and <code>false</code> otherwise.
     * 
     * @return A boolean.
     */
    public boolean isVisible() {
        return this.visible;
    }
    
    /**
     * Sets the flag that determines whether or not this layer is drawn by
     * the plot, and sends a {@link DialLayerChangeEvent} to all registered
     * listeners.
     * 
     * @param visible  the flag.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        notifyListeners(new DialLayerChangeEvent(this));
    }
    
    /**
     * Registers an object for notification of changes to the dial layer.
     *
     * @param listener  the object that is being registered.
     * 
     * @see #removeChangeListener(DialLayerChangeListener)
     */
    public void addChangeListener(DialLayerChangeListener listener) {
        this.listenerList.add(DialLayerChangeListener.class, listener);
    }

    /**
     * Deregisters an object for notification of changes to the dial layer.
     *
     * @param listener  the object to deregister.
     * 
     * @see #addChangeListener(DialLayerChangeListener)
     */
    public void removeChangeListener(DialLayerChangeListener listener) {
        this.listenerList.remove(DialLayerChangeListener.class, listener);
    }

    /**
     * Returns <code>true</code> if the specified object is registered with
     * the dataset as a listener.  Most applications won't need to call this 
     * method, it exists mainly for use by unit testing code.
     * 
     * @param listener  the listener.
     * 
     * @return A boolean.
     */
    public boolean hasListener(EventListener listener) {
        List list = Arrays.asList(this.listenerList.getListenerList());
        return list.contains(listener);
    }
    
    /**
     * Notifies all registered listeners that the dial layer has changed.
     * The {@link DialLayerChangeEvent} provides information about the change.
     *
     * @param event  information about the change to the axis.
     */
    protected void notifyListeners(DialLayerChangeEvent event) {
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DialLayerChangeListener.class) {
                ((DialLayerChangeListener) listeners[i + 1]).dialLayerChanged(
                        event);
            }
        }
    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     */
    private void readObject(ObjectInputStream stream) 
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.listenerList = new EventListenerList();
    }
    
}

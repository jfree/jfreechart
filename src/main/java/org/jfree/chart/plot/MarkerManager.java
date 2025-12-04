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
 */

package org.jfree.chart.plot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.api.Layer;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.internal.Args;

/**
 * A manager class for storing and managing markers in a plot. This class
 * encapsulates the logic for adding, removing, and retrieving markers
 * organized by index and layer (foreground/background).
 *
 * @param <M> the marker type (e.g., CategoryMarker, Marker)
 *
 * @since 2.1.0
 */
public class MarkerManager<M extends Marker> implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 1L;

    /** Storage for foreground markers. */
    private final Map<Integer, Collection<M>> foregroundMarkers = new HashMap<>();

    /** Storage for background markers. */
    private final Map<Integer, Collection<M>> backgroundMarkers = new HashMap<>();

    /** The listener to notify when markers change. */
    private final MarkerChangeListener listener;

    /**
     * Creates a new marker manager.
     *
     * @param listener the listener to notify of marker changes ({@code null} not permitted).
     */
    public MarkerManager(MarkerChangeListener listener) {
        Args.nullNotPermitted(listener, "listener");
        this.listener = listener;
    }

    /**
     * Adds a marker to this manager.
     *
     * @param index the index for the marker (must be >= 0).
     * @param marker the marker ({@code null} not permitted).
     * @param layer the layer ({@code null} not permitted).
     *
     * @throws IllegalArgumentException if index < 0 or marker/layer is null
     */
    public void addMarker(int index, M marker, Layer layer) {
        Args.nullNotPermitted(marker, "marker");
        Args.nullNotPermitted(layer, "layer");
        if (index < 0) {
            throw new IllegalArgumentException("index must be >= 0");
        }

        Collection<M> markers = getOrCreateCollection(index, layer);
        markers.add(marker);
        marker.addChangeListener(this.listener);
    }

    /**
     * Removes a marker from this manager.
     *
     * @param index the index of the marker (must be >= 0).
     * @param marker the marker ({@code null} not permitted).
     * @param layer the layer ({@code null} not permitted).
     *
     * @throws IllegalArgumentException if index < 0 or marker/layer is null
     */
    public void removeMarker(int index, M marker, Layer layer) {
        Args.nullNotPermitted(marker, "marker");
        Args.nullNotPermitted(layer, "layer");
        if (index < 0) {
            throw new IllegalArgumentException("index must be >= 0");
        }

        Collection<M> markers = getCollection(index, layer);
        if (markers != null && markers.remove(marker)) {
            marker.removeChangeListener(this.listener);
        }
    }

    /**
     * Returns the markers at the specified index and layer.
     *
     * @param index the index (must be >= 0).
     * @param layer the layer ({@code null} not permitted).
     *
     * @return The markers (possibly {@code null} if none exist).
     *
     * @throws IllegalArgumentException if index < 0 or layer is null
     */
    public Collection<M> getMarkers(int index, Layer layer) {
        Args.nullNotPermitted(layer, "layer");
        if (index < 0) {
            throw new IllegalArgumentException("index must be >= 0");
        }
        return getCollection(index, layer);
    }

    /**
     * Clears the markers at the specified index and layer.
     *
     * @param index the index (must be >= 0).
     * @param layer the layer ({@code null} not permitted).
     *
     * @throws IllegalArgumentException if index < 0 or layer is null
     */
    public void clearMarkers(int index, Layer layer) {
        Args.nullNotPermitted(layer, "layer");
        if (index < 0) {
            throw new IllegalArgumentException("index must be >= 0");
        }

        Collection<M> markers = getCollection(index, layer);
        if (markers != null) {
            for (M marker : markers) {
                marker.removeChangeListener(this.listener);
            }
            markers.clear();
        }
    }

    /**
     * Clears all markers from this manager.
     */
    public void clearAll() {
        for (Collection<M> markers : this.foregroundMarkers.values()) {
            for (M marker : markers) {
                marker.removeChangeListener(this.listener);
            }
            markers.clear();
        }
        for (Collection<M> markers : this.backgroundMarkers.values()) {
            for (M marker : markers) {
                marker.removeChangeListener(this.listener);
            }
            markers.clear();
        }
        this.foregroundMarkers.clear();
        this.backgroundMarkers.clear();
    }

    /**
     * Returns the collection at the specified index and layer, creating it if necessary.
     *
     * @param index the index.
     * @param layer the layer.
     *
     * @return The collection (never {@code null}).
     */
    private Collection<M> getOrCreateCollection(int index, Layer layer) {
        Map<Integer, Collection<M>> map = layer == Layer.FOREGROUND
                ? this.foregroundMarkers : this.backgroundMarkers;
        return map.computeIfAbsent(index, k -> new ArrayList<>());
    }

    /**
     * Returns the collection at the specified index and layer.
     *
     * @param index the index.
     * @param layer the layer.
     *
     * @return The collection ({@code null} if it doesn't exist).
     */
    private Collection<M> getCollection(int index, Layer layer) {
        Map<Integer, Collection<M>> map = layer == Layer.FOREGROUND
                ? this.foregroundMarkers : this.backgroundMarkers;
        return map.get(index);
    }
}

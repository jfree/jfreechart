/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
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
 * OfflineRenderingChartPanel.java
 * -------------------------------
 * (C) Copyright 2000-present, by Yuri Blankenstein and Contributors.
 *
 * Original Author:  Yuri Blankenstein;
 */

package org.jfree.chart;

import java.awt.AlphaComposite;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingWorker;

import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.PlotRenderingInfo;

/**
 * A {@link ChartPanel} that applies offline rendering, for better performance
 * when navigating (i.e. panning / zooming) {@link JFreeChart charts} with lots
 * of data.
 * <P>
 * This chart panel uses a {@link SwingWorker} to perform the actual
 * {@link JFreeChart} rendering. While rendering, a {@link Cursor#WAIT_CURSOR
 * wait cursor} is visible and the current buffered image of the chart will be
 * scaled and drawn to the screen. When - while rendering - another
 * {@link #setRefreshBuffer(boolean) refresh} is requested, this will be either
 * postponed until the current rendering is done or ignored when another refresh
 * is requested.
 */
public class OfflineRenderingChartPanel extends ChartPanel {
    private static final long serialVersionUID = -724633596883320084L;

    /**
     * Using enum state pattern to control the 'offline' rendering
     */
    protected enum State {
        IDLE {
            @Override
            protected State renderOffline(OfflineRenderingChartPanel panel,
                    OfflineChartRenderer renderer) {
                // Start rendering offline
                renderer.execute();
                panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                return RENDERING;
            }

            @Override
            protected State offlineRenderingDone(
                    OfflineRenderingChartPanel panel,
                    OfflineChartRenderer renderer) {
                throw new IllegalStateException(
                        "offlineRenderingDone not expected in IDLE state");
            }
        },
        RENDERING {
            @Override
            protected State renderOffline(OfflineRenderingChartPanel panel,
                    OfflineChartRenderer renderer) {
                // We're already rendering, we'll start this renderer when we're
                // finished. If another rendering is requested, this one will be
                // ignored, see RE_RENDERING_PENDING. This gains a lot of speed
                // as not all requested (intermediate) renderings are executed
                // for large plots.
                panel.pendingOfflineRenderer = renderer;
                return RE_RENDERING_PENDING;
            }

            @Override
            protected State offlineRenderingDone(
                    OfflineRenderingChartPanel panel,
                    OfflineChartRenderer renderer) {
                // Offline rendering done, prepare the buffer and info for the
                // next repaint and request it.
                panel.currentChartBuffer = renderer.buffer;
                panel.currentChartRenderingInfo = renderer.info;
                panel.repaint();
                panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                return IDLE;
            }
        },
        RE_RENDERING_PENDING {
            @Override
            protected State renderOffline(OfflineRenderingChartPanel panel,
                    OfflineChartRenderer renderer) {
                // We're already rendering, we'll start this renderer when we're
                // finished.
                panel.pendingOfflineRenderer = renderer;
                return RE_RENDERING_PENDING;
            }

            @Override
            protected State offlineRenderingDone(
                    OfflineRenderingChartPanel panel,
                    OfflineChartRenderer renderer) {
                // Store the intermediate result, but do not actively repaint
                // as this could trigger another RE_RENDERING_PENDING if i.e.
                // the buffer-image-size of the pending renderer differs from
                // the current buffer-image-size.
                panel.currentChartBuffer = renderer.buffer;
                panel.currentChartRenderingInfo = renderer.info;
                // Immediately start rendering again to update the chart to the
                // latest requested state.
                panel.pendingOfflineRenderer.execute();
                panel.pendingOfflineRenderer = null;
                return RENDERING;
            }
        };

        protected abstract State renderOffline(
                final OfflineRenderingChartPanel panel,
                final OfflineChartRenderer renderer);

        protected abstract State offlineRenderingDone(
                final OfflineRenderingChartPanel panel,
                final OfflineChartRenderer renderer);
    }

    /** A buffer for the rendered chart. */
    private transient BufferedImage currentChartBuffer = null;
    private transient ChartRenderingInfo currentChartRenderingInfo = null;

    /** A pending rendering for the chart. */
    private transient OfflineChartRenderer pendingOfflineRenderer = null;

    private State state = State.IDLE;

    /**
     * Constructs a double buffered JFreeChart panel that displays the specified
     * chart.
     *
     * @param chart the chart.
     */
    public OfflineRenderingChartPanel(JFreeChart chart) {
        this(chart, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_MINIMUM_DRAW_WIDTH,
                DEFAULT_MINIMUM_DRAW_HEIGHT, DEFAULT_MAXIMUM_DRAW_WIDTH,
                DEFAULT_MAXIMUM_DRAW_HEIGHT, true, // properties
                true, // save
                true, // print
                true, // zoom
                true // tooltips
        );

    }

    /**
     * Constructs a double buffered JFreeChart panel.
     *
     * @param chart      the chart.
     * @param properties a flag indicating whether or not the chart property
     *                   editor should be available via the popup menu.
     * @param save       a flag indicating whether or not save options should be
     *                   available via the popup menu.
     * @param print      a flag indicating whether or not the print option
     *                   should be available via the popup menu.
     * @param zoom       a flag indicating whether or not zoom options should be
     *                   added to the popup menu.
     * @param tooltips   a flag indicating whether or not tooltips should be
     *                   enabled for the chart.
     */
    public OfflineRenderingChartPanel(JFreeChart chart, boolean properties,
            boolean save, boolean print, boolean zoom, boolean tooltips) {

        this(chart, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_MINIMUM_DRAW_WIDTH,
                DEFAULT_MINIMUM_DRAW_HEIGHT, DEFAULT_MAXIMUM_DRAW_WIDTH,
                DEFAULT_MAXIMUM_DRAW_HEIGHT, properties, save, print, zoom,
                tooltips);

    }

    /**
     * Constructs a double buffered JFreeChart panel.
     *
     * @param chart             the chart.
     * @param width             the preferred width of the panel.
     * @param height            the preferred height of the panel.
     * @param minimumDrawWidth  the minimum drawing width.
     * @param minimumDrawHeight the minimum drawing height.
     * @param maximumDrawWidth  the maximum drawing width.
     * @param maximumDrawHeight the maximum drawing height.
     * @param properties        a flag indicating whether or not the chart
     *                          property editor should be available via the
     *                          popup menu.
     * @param save              a flag indicating whether or not save options
     *                          should be available via the popup menu.
     * @param print             a flag indicating whether or not the print
     *                          option should be available via the popup menu.
     * @param zoom              a flag indicating whether or not zoom options
     *                          should be added to the popup menu.
     * @param tooltips          a flag indicating whether or not tooltips should
     *                          be enabled for the chart.
     */
    public OfflineRenderingChartPanel(JFreeChart chart, int width, int height,
            int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth,
            int maximumDrawHeight, boolean properties, boolean save,
            boolean print, boolean zoom, boolean tooltips) {

        this(chart, width, height, minimumDrawWidth, minimumDrawHeight,
                maximumDrawWidth, maximumDrawHeight, properties, true, save,
                print, zoom, tooltips);
    }

    /**
     * Constructs a double buffered JFreeChart panel.
     *
     * @param chart             the chart.
     * @param width             the preferred width of the panel.
     * @param height            the preferred height of the panel.
     * @param minimumDrawWidth  the minimum drawing width.
     * @param minimumDrawHeight the minimum drawing height.
     * @param maximumDrawWidth  the maximum drawing width.
     * @param maximumDrawHeight the maximum drawing height.
     * @param properties        a flag indicating whether or not the chart
     *                          property editor should be available via the
     *                          popup menu.
     * @param copy              a flag indicating whether or not a copy option
     *                          should be available via the popup menu.
     * @param save              a flag indicating whether or not save options
     *                          should be available via the popup menu.
     * @param print             a flag indicating whether or not the print
     *                          option should be available via the popup menu.
     * @param zoom              a flag indicating whether or not zoom options
     *                          should be added to the popup menu.
     * @param tooltips          a flag indicating whether or not tooltips should
     *                          be enabled for the chart.
     */
    public OfflineRenderingChartPanel(JFreeChart chart, int width, int height,
            int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth,
            int maximumDrawHeight, boolean properties, boolean copy,
            boolean save, boolean print, boolean zoom, boolean tooltips) {
        super(chart, width, height, minimumDrawWidth, minimumDrawHeight,
                maximumDrawWidth, maximumDrawHeight, true, properties, copy,
                save, print, zoom, tooltips);
    }

    @Override
    protected BufferedImage paintChartToBuffer(Graphics2D g2,
            Dimension bufferSize, Dimension chartSize, Point2D anchor,
            ChartRenderingInfo info) {
        synchronized (state) {
            if (this.currentChartBuffer == null) {
                // Rendering the first time, prepare an empty buffer and
                // start rendering, no need for an additional state
                this.currentChartBuffer = createChartBuffer(g2, bufferSize);
                clearChartBuffer(currentChartBuffer);
                setRefreshBuffer(true);
            } else if ((this.currentChartBuffer.getWidth() != bufferSize.width)
                    || (this.currentChartBuffer
                            .getHeight() != bufferSize.height)) {
                setRefreshBuffer(true);
            }

            // do we need to redraw the buffer?
            if (getRefreshBuffer()) {
                setRefreshBuffer(false); // clear the flag

                // Rendering is done offline, hence it requires a fresh buffer
                // and rendering info
                BufferedImage rendererBuffer = createChartBuffer(g2,
                        bufferSize);
                ChartRenderingInfo rendererInfo = info;
                if (rendererInfo != null) {
                    // As the chart will be re-rendered, the current chart
                    // entities cannot be trusted
                    final EntityCollection entityCollection = 
                            rendererInfo.getEntityCollection();
                    if (entityCollection != null) {
                        entityCollection.clear();
                    }

                    // Offline rendering requires its own instance of
                    // ChartRenderingInfo, using clone if possible
                    try {
                        rendererInfo = rendererInfo.clone();
                    } catch (CloneNotSupportedException e) {
                        // Not expected
                        e.printStackTrace();
                        rendererInfo = new ChartRenderingInfo();
                    }
                }

                OfflineChartRenderer offlineRenderer = new OfflineChartRenderer(
                        getChart(), rendererBuffer, chartSize, anchor,
                        rendererInfo);
                state = state.renderOffline(this, offlineRenderer);
            }

            // Copy the rendered ChartRenderingInfo into the passed info
            // argument and mark that we have done so.
            copyChartRenderingInfo(this.currentChartRenderingInfo, info);
            this.currentChartRenderingInfo = info;

            return this.currentChartBuffer;
        }
    }

    private class OfflineChartRenderer extends SwingWorker<Object, Object> {
        private final JFreeChart chart;
        private final BufferedImage buffer;
        private final Dimension chartSize;
        private final Point2D anchor;
        private final ChartRenderingInfo info;

        public OfflineChartRenderer(JFreeChart chart, BufferedImage image,
                Dimension chartSize, Point2D anchor, ChartRenderingInfo info) {
            this.chart = chart;
            this.buffer = image;
            this.chartSize = chartSize;
            this.anchor = anchor;
            this.info = info;
        }

        @Override
        protected Object doInBackground() throws Exception {
            clearChartBuffer(buffer);

            Graphics2D bufferG2 = buffer.createGraphics();
            if ((this.buffer.getWidth() != this.chartSize.width)
                    || (this.buffer.getHeight() != this.chartSize.height)) {
                // Scale the chart to fit the buffer
                bufferG2.scale(
                        this.buffer.getWidth() / this.chartSize.getWidth(),
                        this.buffer.getHeight() / this.chartSize.getHeight());
            }
            Rectangle chartArea = new Rectangle(this.chartSize);

            this.chart.draw(bufferG2, chartArea, this.anchor, this.info);
            bufferG2.dispose();

            // Return type is not used
            return null;
        }

        @Override
        protected void done() {
            synchronized (state) {
                state = state.offlineRenderingDone(
                        OfflineRenderingChartPanel.this, this);
            }
        }
    }

    @Override
    public void setCursor(Cursor cursor) {
        super.setCursor(cursor);

        // Buggy mouse cursor: setting both behaves as expected
        Container root = getTopLevelAncestor();
        if (null != root) {
            root.setCursor(cursor);
        }
    }

    private static void copyChartRenderingInfo(ChartRenderingInfo source,
            ChartRenderingInfo target) {
        if (source == null || target == null || source == target) {
            // Nothing to do
            return;
        }
        target.clear();
        target.setChartArea(source.getChartArea());
        target.setEntityCollection(source.getEntityCollection());
        copyPlotRenderingInfo(source.getPlotInfo(), target.getPlotInfo());
    }

    private static void copyPlotRenderingInfo(PlotRenderingInfo source,
            PlotRenderingInfo target) {
        target.setDataArea(source.getDataArea());
        target.setPlotArea(source.getPlotArea());
        for (int i = 0; i < target.getSubplotCount(); i++) {
            PlotRenderingInfo subSource = source.getSubplotInfo(i);
            PlotRenderingInfo subTarget = new PlotRenderingInfo(
                    target.getOwner());
            copyPlotRenderingInfo(subSource, subTarget);
            target.addSubplotInfo(subTarget);
        }
    }

    private static BufferedImage createChartBuffer(Graphics2D g2,
            Dimension bufferSize) {
        GraphicsConfiguration gc = g2.getDeviceConfiguration();
        return gc.createCompatibleImage(bufferSize.width, bufferSize.height,
                Transparency.TRANSLUCENT);
    }

    private static void clearChartBuffer(BufferedImage buffer) {
        Graphics2D bufferG2 = buffer.createGraphics();
        // make the background of the buffer clear and transparent
        bufferG2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
        bufferG2.fill(new Rectangle(buffer.getWidth(), buffer.getHeight()));
        bufferG2.dispose();
    }
}

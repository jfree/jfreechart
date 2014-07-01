/* ====================
 * BarChartFXDemo1.java
 * ====================
 * 
 * Copyright (c) 2014, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.jfree.org/jfreechart/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */

package org.jfree.chart.fx.demo;

import static javafx.application.Application.launch;

import java.awt.Color;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * A demo showing the display of JFreeChart within a JavaFX application.
 * For more information about the JFreeSVG vs Batik performance test, see
 * this link: http://www.object-refinery.com/blog/blog-20140423.html
 */
public class BarChartFXDemo1 extends Application implements ChartMouseListenerFX {

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(7445, "JFreeSVG", "Warm-up");
        dataset.addValue(24448, "Batik", "Warm-up");
        dataset.addValue(4297, "JFreeSVG", "Test");
        dataset.addValue(21022, "Batik", "Test");
        return dataset;
    }
    
    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
            "Performance: JFreeSVG vs Batik", null /* x-axis label*/, 
                "Milliseconds" /* y-axis label */, dataset);
        chart.addSubtitle(new TextTitle("Time to generate 1000 charts in SVG " 
                + "format (lower bars = better performance)"));
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    /**
     * Adds a chart viewer to the stage and displays it.
     * 
     * @param stage  the stage.
     * @throws Exception if something goes wrong.
     */
    @Override 
    public void start(Stage stage) throws Exception {
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset); 
        ChartViewer viewer = new ChartViewer(chart);
        viewer.addChartMouseListener(this);
        stage.setScene(new Scene(viewer)); 
        stage.setTitle("JFreeChart: BarChartFXDemo1.java"); 
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show(); 
    }
    
    /**
     * Entry point.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Write the event to the console, just to illustrate.
     * 
     * @param event  event info. 
     */
    @Override
    public void chartMouseClicked(ChartMouseEventFX event) {
        System.out.println(event);
    }

    /**
     * Write the event to the console, just to illustrate.
     * 
     * @param event  event info. 
     */
    @Override
    public void chartMouseMoved(ChartMouseEventFX event) {
        System.out.println(event);
    }
  
}

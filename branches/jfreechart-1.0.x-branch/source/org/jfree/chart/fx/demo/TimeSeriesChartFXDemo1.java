/* ===========================
 * TimeSeriesChartFXDemo1.java
 * ===========================
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.HorizontalAlignment;

/**
 * A demo showing the display of JFreeChart within a JavaFX application.
 * 
 * The ChartCanvas code is based on: 
 * http://dlemmermann.wordpress.com/2014/04/10/javafx-tip-1-resizable-canvas/
 * 
 */
public class TimeSeriesChartFXDemo1 extends Application {

    /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "International Coffee Organisation : Coffee Prices",    // title
            null,             // x-axis label
            "US cents/lb",      // y-axis label
            dataset);

        String fontName = "Palatino";
        chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));
        chart.addSubtitle(new TextTitle("Source: http://www.ico.org/historical/2010-19/PDF/HIST-PRICES.pdf", 
                new Font(fontName, Font.PLAIN, 14)));

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        chart.getLegend().setItemFont(new Font(fontName, Font.PLAIN, 14));
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
            renderer.setDrawSeriesLineAsPath(true);
            // set the default stroke for all series
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setBaseStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, 
                    BasicStroke.JOIN_BEVEL), false);
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, new Color(24, 123, 58));
            renderer.setSeriesPaint(2, new Color(149, 201, 136));
            renderer.setSeriesPaint(3, new Color(1, 62, 29));
            renderer.setSeriesPaint(4, new Color(81, 176, 86));
            renderer.setSeriesPaint(5, new Color(0, 55, 122));
            renderer.setSeriesPaint(6, new Color(0, 92, 165));
        }

        return chart;

    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return the dataset.
     */
    private static XYDataset createDataset() {

        TimeSeries s1 = new TimeSeries("Indicator Price");
        s1.add(new Month(1, 2010), 126.80);
        s1.add(new Month(2, 2010), 123.37);
        s1.add(new Month(3, 2010), 125.30);
        s1.add(new Month(4, 2010), 126.89);
        s1.add(new Month(5, 2010), 128.10);
        s1.add(new Month(6, 2010), 142.20);
        s1.add(new Month(7, 2010), 153.41);
        s1.add(new Month(8, 2010), 157.46);
        s1.add(new Month(9, 2010), 163.61);
        s1.add(new Month(10, 2010), 161.56);
        s1.add(new Month(11, 2010), 173.90);
        s1.add(new Month(12, 2010), 184.26);
        s1.add(new Month(1, 2011), 197.35);
        s1.add(new Month(2, 2011), 216.03);
        s1.add(new Month(3, 2011), 224.33);
        s1.add(new Month(4, 2011), 231.24);
        s1.add(new Month(5, 2011), 227.97);
        s1.add(new Month(6, 2011), 215.58);
        s1.add(new Month(7, 2011), 210.36);
        s1.add(new Month(8, 2011), 212.19);
        s1.add(new Month(9, 2011), 213.04);
        s1.add(new Month(10, 2011), 193.90);
        s1.add(new Month(11, 2011), 193.66);
        s1.add(new Month(12, 2011), 189.02);
        s1.add(new Month(1, 2012), 188.90);
        s1.add(new Month(2, 2012), 182.29);
        s1.add(new Month(3, 2012), 167.77);
        s1.add(new Month(4, 2012), 160.46);
        s1.add(new Month(5, 2012), 157.68);
        s1.add(new Month(6, 2012), 145.31);
        s1.add(new Month(7, 2012), 159.07);
        s1.add(new Month(8, 2012), 148.50);
        s1.add(new Month(9, 2012), 151.28);
        s1.add(new Month(10, 2012), 147.12);
        s1.add(new Month(11, 2012), 136.35);
        s1.add(new Month(12, 2012), 131.31);
        s1.add(new Month(1, 2013), 135.38);
        s1.add(new Month(2, 2013), 131.51);
        s1.add(new Month(3, 2013), 131.38);

        TimeSeries s2 = new TimeSeries("Columbian Milds");
        s2.add(new Month(1, 2010), 207.51);
        s2.add(new Month(2, 2010), 204.71);
        s2.add(new Month(3, 2010), 205.71);
        s2.add(new Month(4, 2010), 200.00);
        s2.add(new Month(5, 2010), 200.54);
        s2.add(new Month(6, 2010), 224.49);
        s2.add(new Month(7, 2010), 235.52);
        s2.add(new Month(8, 2010), 243.98);
        s2.add(new Month(9, 2010), 247.77);
        s2.add(new Month(10, 2010), 230.02);
        s2.add(new Month(11, 2010), 244.02);
        s2.add(new Month(12, 2010), 261.97);
        s2.add(new Month(1, 2011), 279.88);
        s2.add(new Month(2, 2011), 296.44);
        s2.add(new Month(3, 2011), 300.68);
        s2.add(new Month(4, 2011), 312.95);
        s2.add(new Month(5, 2011), 302.17);
        s2.add(new Month(6, 2011), 287.95);
        s2.add(new Month(7, 2011), 285.21);
        s2.add(new Month(8, 2011), 286.97);
        s2.add(new Month(9, 2011), 287.54);
        s2.add(new Month(10, 2011), 257.66);
        s2.add(new Month(11, 2011), 256.99);
        s2.add(new Month(12, 2011), 251.60);
        s2.add(new Month(1, 2012), 255.91);
        s2.add(new Month(2, 2012), 244.14);
        s2.add(new Month(3, 2012), 222.84);
        s2.add(new Month(4, 2012), 214.46);
        s2.add(new Month(5, 2012), 207.32);
        s2.add(new Month(6, 2012), 184.67);
        s2.add(new Month(7, 2012), 202.56);
        s2.add(new Month(8, 2012), 187.14);
        s2.add(new Month(9, 2012), 190.10);
        s2.add(new Month(10, 2012), 181.39);
        s2.add(new Month(11, 2012), 170.08);
        s2.add(new Month(12, 2012), 164.40);
        s2.add(new Month(1, 2013), 169.19);
        s2.add(new Month(2, 2013), 161.70);
        s2.add(new Month(3, 2013), 161.53);
        
        TimeSeries s3 = new TimeSeries("Other Milds");
        s3.add(new Month(1, 2010), 158.90);
        s3.add(new Month(2, 2010), 157.86);
        s3.add(new Month(3, 2010), 164.50);
        s3.add(new Month(4, 2010), 169.55);
        s3.add(new Month(5, 2010), 173.38);
        s3.add(new Month(6, 2010), 190.90);
        s3.add(new Month(7, 2010), 203.21);
        s3.add(new Month(8, 2010), 211.59);
        s3.add(new Month(9, 2010), 222.71);
        s3.add(new Month(10, 2010), 217.64);
        s3.add(new Month(11, 2010), 233.48);
        s3.add(new Month(12, 2010), 248.17);
        s3.add(new Month(1, 2011), 263.77);
        s3.add(new Month(2, 2011), 287.89);
        s3.add(new Month(3, 2011), 292.07);
        s3.add(new Month(4, 2011), 300.12);
        s3.add(new Month(5, 2011), 291.09);
        s3.add(new Month(6, 2011), 274.98);
        s3.add(new Month(7, 2011), 268.02);
        s3.add(new Month(8, 2011), 270.44);
        s3.add(new Month(9, 2011), 274.88);
        s3.add(new Month(10, 2011), 247.82);
        s3.add(new Month(11, 2011), 245.09);
        s3.add(new Month(12, 2011), 236.71);
        s3.add(new Month(1, 2012), 237.21);
        s3.add(new Month(2, 2012), 224.16);
        s3.add(new Month(3, 2012), 201.26);
        s3.add(new Month(4, 2012), 191.45);
        s3.add(new Month(5, 2012), 184.65);
        s3.add(new Month(6, 2012), 168.69);
        s3.add(new Month(7, 2012), 190.45);
        s3.add(new Month(8, 2012), 174.82);
        s3.add(new Month(9, 2012), 178.98);
        s3.add(new Month(10, 2012), 173.32);
        s3.add(new Month(11, 2012), 159.91);
        s3.add(new Month(12, 2012), 152.74);
        s3.add(new Month(1, 2013), 157.29);
        s3.add(new Month(2, 2013), 149.46);
        s3.add(new Month(3, 2013), 149.78);
        
        TimeSeries s4 = new TimeSeries("Brazilian Naturals");
        s4.add(new Month(1, 2010), 131.67);
        s4.add(new Month(2, 2010), 124.57);
        s4.add(new Month(3, 2010), 126.21);
        s4.add(new Month(4, 2010), 126.07);
        s4.add(new Month(5, 2010), 127.45);
        s4.add(new Month(6, 2010), 143.20);
        s4.add(new Month(7, 2010), 156.87);
        s4.add(new Month(8, 2010), 163.21);
        s4.add(new Month(9, 2010), 175.15);
        s4.add(new Month(10, 2010), 175.38);
        s4.add(new Month(11, 2010), 190.62);
        s4.add(new Month(12, 2010), 204.25);
        s4.add(new Month(1, 2011), 219.77);
        s4.add(new Month(2, 2011), 247.00);
        s4.add(new Month(3, 2011), 260.98);
        s4.add(new Month(4, 2011), 273.40);
        s4.add(new Month(5, 2011), 268.66);
        s4.add(new Month(6, 2011), 250.59);
        s4.add(new Month(7, 2011), 245.69);
        s4.add(new Month(8, 2011), 249.83);
        s4.add(new Month(9, 2011), 255.64);
        s4.add(new Month(10, 2011), 234.28);
        s4.add(new Month(11, 2011), 236.75);
        s4.add(new Month(12, 2011), 228.79);
        s4.add(new Month(1, 2012), 228.21);
        s4.add(new Month(2, 2012), 215.40);
        s4.add(new Month(3, 2012), 192.03);
        s4.add(new Month(4, 2012), 180.90);
        s4.add(new Month(5, 2012), 174.17);
        s4.add(new Month(6, 2012), 156.17);
        s4.add(new Month(7, 2012), 175.98);
        s4.add(new Month(8, 2012), 160.05);
        s4.add(new Month(9, 2012), 166.53);
        s4.add(new Month(10, 2012), 161.20);
        s4.add(new Month(11, 2012), 148.25);
        s4.add(new Month(12, 2012), 140.69);
        s4.add(new Month(1, 2013), 145.17);
        s4.add(new Month(2, 2013), 136.63);
        s4.add(new Month(3, 2013), 133.61);
        
        TimeSeries s5 = new TimeSeries("Robustas");
        s5.add(new Month(1, 2010), 69.92);
        s5.add(new Month(2, 2010), 67.88);
        s5.add(new Month(3, 2010), 67.25);
        s5.add(new Month(4, 2010), 71.59);
        s5.add(new Month(5, 2010), 70.70);
        s5.add(new Month(6, 2010), 76.92);
        s5.add(new Month(7, 2010), 85.27);
        s5.add(new Month(8, 2010), 82.68);
        s5.add(new Month(9, 2010), 81.28);
        s5.add(new Month(10, 2010), 85.27);
        s5.add(new Month(11, 2010), 92.04);
        s5.add(new Month(12, 2010), 94.09);
        s5.add(new Month(1, 2011), 101.09);
        s5.add(new Month(2, 2011), 109.35);
        s5.add(new Month(3, 2011), 118.13);
        s5.add(new Month(4, 2011), 117.37);
        s5.add(new Month(5, 2011), 121.98);
        s5.add(new Month(6, 2011), 117.95);
        s5.add(new Month(7, 2011), 112.73);
        s5.add(new Month(8, 2011), 112.07);
        s5.add(new Month(9, 2011), 106.06);
        s5.add(new Month(10, 2011), 98.10);
        s5.add(new Month(11, 2011), 97.24);
        s5.add(new Month(12, 2011), 98.41);
        s5.add(new Month(1, 2012), 96.72);
        s5.add(new Month(2, 2012), 101.93);
        s5.add(new Month(3, 2012), 103.57);
        s5.add(new Month(4, 2012), 101.80);
        s5.add(new Month(5, 2012), 106.88);
        s5.add(new Month(6, 2012), 105.70);
        s5.add(new Month(7, 2012), 107.06);
        s5.add(new Month(8, 2012), 106.52);
        s5.add(new Month(9, 2012), 104.95);
        s5.add(new Month(10, 2012), 104.47);
        s5.add(new Month(11, 2012), 97.67);
        s5.add(new Month(12, 2012), 96.59);
        s5.add(new Month(1, 2013), 99.69);
        s5.add(new Month(2, 2013), 104.03);
        s5.add(new Month(3, 2013), 106.26);
       
        TimeSeries s6 = new TimeSeries("Futures (London)");
        s6.add(new Month(1, 2010), 62.66);
        s6.add(new Month(2, 2010), 60.37);
        s6.add(new Month(3, 2010), 58.64);
        s6.add(new Month(4, 2010), 62.21);
        s6.add(new Month(5, 2010), 62.46);
        s6.add(new Month(6, 2010), 69.72);
        s6.add(new Month(7, 2010), 78.17);
        s6.add(new Month(8, 2010), 78.42);
        s6.add(new Month(9, 2010), 75.87);
        s6.add(new Month(10, 2010), 80.08);
        s6.add(new Month(11, 2010), 86.40);
        s6.add(new Month(12, 2010), 88.70);
        s6.add(new Month(1, 2011), 96.02);
        s6.add(new Month(2, 2011), 104.53);
        s6.add(new Month(3, 2011), 111.36);
        s6.add(new Month(4, 2011), 111.34);
        s6.add(new Month(5, 2011), 116.76);
        s6.add(new Month(6, 2011), 110.51);
        s6.add(new Month(7, 2011), 103.36);
        s6.add(new Month(8, 2011), 102.71);
        s6.add(new Month(9, 2011), 96.10);
        s6.add(new Month(10, 2011), 88.64);
        s6.add(new Month(11, 2011), 85.78);
        s6.add(new Month(12, 2011), 87.65);
        s6.add(new Month(1, 2012), 84.19);
        s6.add(new Month(2, 2012), 88.69);
        s6.add(new Month(3, 2012), 91.37);
        s6.add(new Month(4, 2012), 91.81);
        s6.add(new Month(5, 2012), 96.82);
        s6.add(new Month(6, 2012), 94.75);
        s6.add(new Month(7, 2012), 96.14);
        s6.add(new Month(8, 2012), 96.12);
        s6.add(new Month(9, 2012), 94.65);
        s6.add(new Month(10, 2012), 94.66);
        s6.add(new Month(11, 2012), 87.32);
        s6.add(new Month(12, 2012), 85.94);
        s6.add(new Month(1, 2013), 88.85);
        s6.add(new Month(2, 2013), 94.41);
        s6.add(new Month(3, 2013), 97.22);
        
        TimeSeries s7 = new TimeSeries("Futures (New York)");
        s7.add(new Month(1, 2010), 142.76);
        s7.add(new Month(2, 2010), 134.35);
        s7.add(new Month(3, 2010), 134.97);
        s7.add(new Month(4, 2010), 135.12);
        s7.add(new Month(5, 2010), 135.81);
        s7.add(new Month(6, 2010), 152.36);
        s7.add(new Month(7, 2010), 165.23);
        s7.add(new Month(8, 2010), 175.10);
        s7.add(new Month(9, 2010), 187.80);
        s7.add(new Month(10, 2010), 190.43);
        s7.add(new Month(11, 2010), 206.92);
        s7.add(new Month(12, 2010), 221.51);
        s7.add(new Month(1, 2011), 238.05);
        s7.add(new Month(2, 2011), 261.41);
        s7.add(new Month(3, 2011), 274.10);
        s7.add(new Month(4, 2011), 285.58);
        s7.add(new Month(5, 2011), 277.72);
        s7.add(new Month(6, 2011), 262.52);
        s7.add(new Month(7, 2011), 255.90);
        s7.add(new Month(8, 2011), 260.39);
        s7.add(new Month(9, 2011), 261.39);
        s7.add(new Month(10, 2011), 236.74);
        s7.add(new Month(11, 2011), 235.25);
        s7.add(new Month(12, 2011), 227.23);
        s7.add(new Month(1, 2012), 227.50);
        s7.add(new Month(2, 2012), 212.09);
        s7.add(new Month(3, 2012), 188.78);
        s7.add(new Month(4, 2012), 181.75);
        s7.add(new Month(5, 2012), 176.50);
        s7.add(new Month(6, 2012), 159.93);
        s7.add(new Month(7, 2012), 183.20);
        s7.add(new Month(8, 2012), 169.77);
        s7.add(new Month(9, 2012), 175.36);
        s7.add(new Month(10, 2012), 170.43);
        s7.add(new Month(11, 2012), 155.72);
        s7.add(new Month(12, 2012), 149.58);
        s7.add(new Month(1, 2013), 154.28);
        s7.add(new Month(2, 2013), 144.89);
        s7.add(new Month(3, 2013), 141.43);
       
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        dataset.addSeries(s3);        
        dataset.addSeries(s4);
        dataset.addSeries(s5);
        dataset.addSeries(s6);
        dataset.addSeries(s7);
        return dataset;
    }

    @Override 
    public void start(Stage stage) throws Exception {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset); 
        ChartViewer viewer = new ChartViewer(chart);  
        
        stage.setScene(new Scene(viewer)); 
        stage.setTitle("JFreeChart: TimeSeriesFXDemo1.java"); 
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
  
}

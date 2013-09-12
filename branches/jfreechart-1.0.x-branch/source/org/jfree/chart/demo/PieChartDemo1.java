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
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ------------------
 * PieChartDemo1.java
 * ------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   ;
 *
 * Changes
 * -------
 * 09-Mar-2005 : Version 1, copied from the demo collection that ships with
 *               the JFreeChart Developer Guide (DG);
 *
 */

package org.jfree.chart.demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a pie chart using
 * data from a {@link DefaultPieDataset}.
 */
public class PieChartDemo1 extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
                true));
    }

    /**
     * Default constructor.
     *
     * @param title  the frame title.
     */
    public PieChartDemo1(String title) {
        super(title);
        setContentPane(createDemoPanel());
    }

    /**
     * Creates a sample dataset.
     * 
     * Source: http://www.bbc.co.uk/news/business-15489523
     *
     * @return A sample dataset.
     */
    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Samsung", new Double(27.8));
        dataset.setValue("Others", new Double(55.3));
        dataset.setValue("Nokia", new Double(16.8));
        dataset.setValue("Apple", new Double(17.1));
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset  the dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createChart(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createPieChart(
            "Smart Phones Manufactured / Q3 2011",  // chart title
            dataset,            // data
            false,              // no legend
            true,               // tooltips
            false               // no URL generation
        );

        // set a custom background for the chart
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0), 
                new Color(20, 20, 20), new Point(400, 200), Color.DARK_GRAY));

        // customise the title position and font
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);

        // use gradients and white borders for the section colours
        plot.setSectionPaint("Others", createGradientPaint(new Color(200, 200, 255), Color.BLUE));
        plot.setSectionPaint("Samsung", createGradientPaint(new Color(255, 200, 200), Color.RED));
        plot.setSectionPaint("Apple", createGradientPaint(new Color(200, 255, 200), Color.GREEN));
        plot.setSectionPaint("Nokia", createGradientPaint(new Color(200, 255, 200), Color.YELLOW));
        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        // customise the section label appearance
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);
        
        // add a subtitle giving the data source
        TextTitle source = new TextTitle("Source: http://www.bbc.co.uk/news/business-15489523", 
                new Font("Courier New", Font.PLAIN, 12));
        source.setPaint(Color.WHITE);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);
        return chart;

    }

    /**
     * A utility method for creating gradient paints.
     * 
     * @param c1  color 1.
     * @param c2  color 2.
     * 
     * @return A radial gradient paint.
     */
    private static RadialGradientPaint createGradientPaint(Color c1, Color c2) {
        Point2D center = new Point2D.Float(0, 0);
        float radius = 200;
        float[] dist = {0.0f, 1.0f};
        return new RadialGradientPaint(center, radius, dist,
                new Color[] {c1, c2});
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600, 300));
        return panel;
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

        // ******************************************************************
        //  More than 150 demo applications are included with the JFreeChart
        //  Developer Guide...for more information, see:
        //
        //  >   http://www.object-refinery.com/jfreechart/guide.html
        //
        // ******************************************************************

        PieChartDemo1 demo = new PieChartDemo1("JFreeChart: Pie Chart Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}


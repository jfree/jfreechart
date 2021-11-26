package org.jfree.issues.issues167;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.chart.swing.ChartFrame;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.Test;

/**

 */
public class Issues167 {

    @Test
    public void test1() throws InterruptedException {
        //create the default pie chart
        DefaultPieDataset ds = new DefaultPieDataset();
        ds.setValue("test01", 2);
        ds.setValue("test02", 3);
        ds.setValue("test03", 40);
        ds.setValue("test04", 15);
        ds.setValue("test05", 20);
        ds.setValue("test06", 20);

        //
        JFreeChart chart = ChartFactory.createPieChart("", ds, true, true, false);
        //
        PiePlot pieplot = (PiePlot) chart.getPlot();
        pieplot.setSimpleLabels(true);

        //
        ChartFrame chartFrame = new ChartFrame("", chart);
        chartFrame.pack();
        chartFrame.setVisible(true);
        Thread.sleep(5000);
    }

    @Test
    public void test2() throws InterruptedException {
        //create the default pie chart
        DefaultPieDataset ds = new DefaultPieDataset();
        ds.setValue("test01", 2);
        ds.setValue("test02", 3);
        ds.setValue("test03", 80);
        ds.setValue("test04", 15);


        //
        JFreeChart chart = ChartFactory.createPieChart("", ds, true, true, false);
        //
        PiePlot pieplot = (PiePlot) chart.getPlot();
        pieplot.setSimpleLabels(true);

        //
        ChartFrame chartFrame = new ChartFrame("", chart);
        chartFrame.pack();
        chartFrame.setVisible(true);
        Thread.sleep(5000);
    }
}

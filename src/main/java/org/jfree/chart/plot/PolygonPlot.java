package org.jfree.chart.plot;
import java.awt.*;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.swing.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPolygonAnnotation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.swing.UIUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.swing.ApplicationFrame;
import org.jfree.chart.axis.NumberAxis;

/**
 * This demo illustrates the solution to issue #234.
 */
public class PolygonPlot extends ApplicationFrame{
    private static XYDataset dataset;
    private static XYPlot xyplot;

    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public PolygonPlot(String title) {
        super(title);
        JPanel chartPanel = createDemoPanel(title);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 500));
        setContentPane(chartPanel);
    }

    /**
     * Creates a chart.
     *
     *
     * @return The chart.
     */

    private static JFreeChart createChart(String title) {
        JFreeChart chart = ChartFactory.createScatterPlot(
                title, "Distance(m)", "Depth(m)", dataset,
                PlotOrientation.VERTICAL, true, true, false);


        xyplot = (XYPlot) chart.getPlot();
        xyplot.setDomainPannable(true);
        xyplot.setRangePannable(true);
        xyplot.setDomainAxisLocation(0, AxisLocation.TOP_OR_LEFT);

        NumberAxis domain = (NumberAxis) xyplot.getDomainAxis();
        domain.setRange(60.0, 800.0);


        NumberAxis range = (NumberAxis) xyplot.getRangeAxis();
        range.setRange(0.0, 150.0);
        range.setInverted(true);
        return  chart;
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        XYLineAndShapeRenderer renderer
                = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xyplot.setBackgroundPaint(Color.white);

        Color color1 = new Color(138, 43, 226);
        Color color2 = new Color(18, 10, 255);
        Color color3 = new Color(18, 255, 0);
        Color color4 = new Color(255, 255, 0);


        XYPolygonAnnotation polygon1 = new XYPolygonAnnotation(new double[] {70.0,
                10.0,80.0, 21.0, 570.0, 21.0,560.0, 10.0}, null, null,color1);
        renderer.addAnnotation(polygon1);

        XYPolygonAnnotation polygon2 = new XYPolygonAnnotation(new double[] {80.0,
                20.0, 110.0, 50.0, 600.0, 50.0, 570.0, 20.0}, null, null,color2);
        renderer.addAnnotation(polygon2);

        XYPolygonAnnotation polygon3 = new XYPolygonAnnotation(new double[] {110.0,
                50.0, 130.0, 70.0, 620.0, 70.0, 600.0, 50.0}, null, null,color3);
        renderer.addAnnotation(polygon3);


        XYPolygonAnnotation polygon4 = new XYPolygonAnnotation(new double[] {130.0,
                70.0, 150.0, 90.0, 640.0, 90.0,620.0, 70.0}, null, null,color4);
        renderer.addAnnotation(polygon4);

        XYPolygonAnnotation polygon5 = new XYPolygonAnnotation(new double[] {150.0,
                90.0, 180.0, 120.0, 670.0, 120.0, 640.0, 90.0}, null, null,new Color(255,165,0));
        renderer.addAnnotation(polygon5);
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel(String title) {
        JFreeChart chart = createChart(title);
        ChartPanel panel = new ChartPanel(chart);
       // panel.setMouseWheelEnabled(true);
        return panel;
    }

    public static void main(String[] args) {
        PolygonPlot demo = new PolygonPlot("PolygonPlot Demo");
        demo.pack();
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}

package org.jfree.chart.plot;

import java.awt.*;

import javax.swing.JPanel;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.swing.ChartPanel;
import org.jfree.chart.swing.ApplicationFrame;
import org.jfree.chart.swing.UIUtils;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.*;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.urls.XYURLGenerator;

import org.jfree.data.time.*;

import org.jfree.data.xy.XYDataset;

/**
 *  CS427: Issue link: https://github.com/jfree/jfreechart/issues/210
 * A demo showing a time series line chart drawn using spline curves.
 *  Demonstrate the solution to Issue #210
 */
public class TimeSeriesChart extends ApplicationFrame {

    /**
     * Creates a new instance of the demo application.
     *
     * @param title  the frame title.
     */

    private static ChartTheme currentTheme = new StandardChartTheme("JFree");

    public TimeSeriesChart(final String title) {
        super(title);
        final JPanel content = createDemoPanel();
        content.setPreferredSize(new Dimension(500, 270));
        getContentPane().add(content);
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {

        final TimeSeriesCollection dataset =createSampleData();
        // create and return the chart panel...
        final JFreeChart chart = createTimeSeriesChartXYSpline(
                "Evolution single line: Highest Top 10 Total Bytes",
                "Time",
                "MBytes",
                dataset,
                5); // precision (Default = 5)

        ChartUtils.applyCurrentTheme(chart);
        return new ChartPanel(chart);
    }

    /**
     * Creates and returns a sample dataset.  The data was randomly
     * generated.
     *
     * @return a sample dataset.
     */
    private static TimeSeriesCollection createSampleData() {
//        XYSeries series = new XYSeries("Series 1");
        final TimeSeries network = new TimeSeries("Network Protocols");
        final Day day = new Day(29, Month.JANUARY,2021);
        final Hour hour = new Hour(8,day);
        network.add(new Minute(24,hour),390.95);
        network.add(new Minute(25, hour), 371.80);
        network.add(new Minute(26, hour), 413.92);
        network.add(new Minute(27, hour), 321.47);
        network.add(new Minute(28, hour), 258.98);
        network.add(new Minute(29, hour), 197.32);
        network.add(new Minute(30, hour), 173.98);
        network.add(new Minute(31, hour), 488.99);
        network.add(new Minute(32, hour), 247.73);
        network.add(new Minute(33, hour), 454.94);
        network.add(new Minute(34, hour), 483.03);

        return new TimeSeriesCollection(network);
    }

    /**
     * Creates and returns a time series chart.  A time series chart is an
     * {@link XYPlot} with a {@link DateAxis} for the x-axis and a
     * {@link NumberAxis} for the y-axis.  The default renderer is an
     * {@link XYSplineRenderer}.
     * <P>
     * A convenient dataset to use with this chart is a
     * {@link org.jfree.data.time.TimeSeriesCollection}.
     *
     * @param title  the chart title ({@code null} permitted).
     * @param timeAxisLabel  a label for the time axis ({@code null}
     *                       permitted).
     * @param valueAxisLabel  a label for the value axis ({@code null}
     *                        permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     *
     * @param precision precision used in spline interpolation
     * @return A time series chart.
     */
    public static JFreeChart createTimeSeriesChartXYSpline(final String title,
                                                           final String timeAxisLabel,
                                                           final String valueAxisLabel,
                                                           final XYDataset dataset,
                                                           final int precision) {

        final ValueAxis timeAxis = new DateAxis(timeAxisLabel);
        timeAxis.setLowerMargin(0.02);  // reduce the default margins
        timeAxis.setUpperMargin(0.02);
        final NumberAxis valueAxis = new NumberAxis(valueAxisLabel);
        valueAxis.setAutoRangeIncludesZero(true);  // override default

        final XYPlot plot = new XYPlot(dataset, timeAxis, valueAxis, null);
        final NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(150.0, 500.0);

        XYToolTipGenerator toolTipGenerator = null;
        toolTipGenerator = StandardXYToolTipGenerator.getTimeSeriesInstance();

        XYURLGenerator urlGenerator = null;

        final XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setPrecision(precision);
        renderer.setDefaultToolTipGenerator(toolTipGenerator);
        renderer.setURLGenerator(urlGenerator);
        plot.setRenderer(renderer);

        final JFreeChart chart = new JFreeChart(title, new Font("SansSerif",Font.PLAIN,3),
                plot, true);

        currentTheme.apply(chart);
        return chart;

    }

    /**
     * CS 427: Tests this plot for equality with another object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} or {@code false}.
     */
    @Override
    public final boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimeSeriesChart)) {
            return false;
        }
        final TimeSeriesChart that = (TimeSeriesChart) obj;
        if (this.getTitle().equals(that.getTitle())) {
            return true;
        }
        return super.equals(obj);
    }


    /**
     * The starting point for the regression demo.
     *
     * @param args  ignored.
     */
    public static void main(final String args[]) {
        final TimeSeriesChart appFrame = new TimeSeriesChart("Issue #210");
        appFrame.pack();
        UIUtils.centerFrameOnScreen(appFrame);
        appFrame.setVisible(true);
    }

}
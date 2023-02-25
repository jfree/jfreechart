package org.jfree.chart;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.charts.BarChart;
import org.jfree.chart.charts.PieChart;
import org.jfree.chart.charts.TimeSeriesChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class ChartFactoryTest {
    private JFreeChart barChart;
    private JFreeChart pieChart;
    private JFreeChart timeSeriesChart;
    private Map<String, List<Object>> params = new HashMap<String, List<Object>>();
    private ChartFactoryReflection reflectionFactory;

    /**
     * Common test setup.
     */
    // @BeforeEach
    // public void setUp() {

    // }

    public void setUpBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(7445, "JFreeSVG", "Warm-up");
        dataset.addValue(24448, "Batik", "Warm-up");
        dataset.addValue(4297, "JFreeSVG", "Test");
        dataset.addValue(21022, "Batik", "Test");
        dataset.addValue(7445, "JFreeSVG", "Warm-up");
        dataset.addValue(24448, "Batik", "Warm-up");
        dataset.addValue(4297, "JFreeSVG", "Test");
        dataset.addValue(21022, "Batik", "Test");

        List<Object> parameters = new ArrayList<Object>();
        parameters.add("Performance: JFreeSVG vs Batik");
        parameters.add("Miliseconds");
        parameters.add("Miliseconds");
        parameters.add(dataset);

        this.params.put("BarChart", parameters);
        this.barChart = ChartFactory.getChartRegular("BarChart", "Performance: JFreeSVG vs Batik", "Miliseconds",
                "Miliseconds",
                dataset);
    }

    public void setUpPieChart() {
        String title = "Smart Phones Manufactured / Q3 2011";
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Samsung", new Double(27.8));
        dataset.setValue("Others", new Double(55.3));
        dataset.setValue("Nokia", new Double(16.8));
        dataset.setValue("Apple", new Double(17.1));

        List<Object> parameters = new ArrayList<Object>();
        parameters.add(title);
        parameters.add(null);
        parameters.add(null);
        parameters.add(dataset);

        this.params.put("PieChart", parameters);

        this.pieChart = ChartFactory.getChartRegular("PieChart", title, null, null, dataset);
    }

    public void setUpTimeSeriesChart() {
        String title = "Legal & General Unit Trust Prices";
        String timeAxisLabel = "Date";
        String valueAxisLabel = "Price per Unit";

        TimeSeries s1 = new TimeSeries("L&G European Index Trust");
        s1.add(new Month(2, 2001), 181.8);
        s1.add(new Month(3, 2001), 167.3);
        s1.add(new Month(4, 2001), 153.8);
        s1.add(new Month(5, 2001), 167.6);
        s1.add(new Month(6, 2001), 158.8);
        s1.add(new Month(7, 2001), 148.3);
        s1.add(new Month(8, 2001), 153.9);
        s1.add(new Month(9, 2001), 142.7);
        s1.add(new Month(10, 2001), 123.2);
        s1.add(new Month(11, 2001), 131.8);
        s1.add(new Month(12, 2001), 139.6);
        s1.add(new Month(1, 2002), 142.9);
        s1.add(new Month(2, 2002), 138.7);
        s1.add(new Month(3, 2002), 137.3);
        s1.add(new Month(4, 2002), 143.9);
        s1.add(new Month(5, 2002), 139.8);
        s1.add(new Month(6, 2002), 137.0);
        s1.add(new Month(7, 2002), 132.8);

        TimeSeries s2 = new TimeSeries("L&G UK Index Trust");
        s2.add(new Month(2, 2001), 129.6);
        s2.add(new Month(3, 2001), 123.2);
        s2.add(new Month(4, 2001), 117.2);
        s2.add(new Month(5, 2001), 124.1);
        s2.add(new Month(6, 2001), 122.6);
        s2.add(new Month(7, 2001), 119.2);
        s2.add(new Month(8, 2001), 116.5);
        s2.add(new Month(9, 2001), 112.7);
        s2.add(new Month(10, 2001), 101.5);
        s2.add(new Month(11, 2001), 106.1);
        s2.add(new Month(12, 2001), 110.3);
        s2.add(new Month(1, 2002), 111.7);
        s2.add(new Month(2, 2002), 111.0);
        s2.add(new Month(3, 2002), 109.6);
        s2.add(new Month(4, 2002), 113.2);
        s2.add(new Month(5, 2002), 111.6);
        s2.add(new Month(6, 2002), 108.8);
        s2.add(new Month(7, 2002), 101.6);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        List<Object> parameters = new ArrayList<Object>();
        parameters.add(title);
        parameters.add(timeAxisLabel);
        parameters.add(valueAxisLabel);
        parameters.add(dataset);

        this.params.put("TimeSeriesChart", parameters);

        this.timeSeriesChart = ChartFactory.getChartRegular("TimeSeriesChart", title, timeAxisLabel, valueAxisLabel,
                dataset);
    }

    @Test
    public void testDynamicLoadingOfBarChartIsCorrect()
            throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setUpBarChart();
        this.reflectionFactory = new ChartFactoryReflection();
        var chart = this.reflectionFactory.getChartReflection("org.jfree.chart.charts.BarChart", this.params);

        assertTrue(chart instanceof JFreeChart);
        assertTrue(chart instanceof BarChart);
        assertEquals(this.barChart, chart);
    }

    @Test
    public void testDynamicLoadingOfPieCharttIsCorrect()
            throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setUpPieChart();
        this.reflectionFactory = new ChartFactoryReflection();
        var chart = this.reflectionFactory.getChartReflection("org.jfree.chart.charts.PieChart", this.params);

        assertTrue(chart instanceof JFreeChart);
        assertTrue(chart instanceof PieChart);
        assertEquals(this.pieChart, chart);
    }

    @Test
    public void testDynamicLoadingOfTimeSeriesCharttIsCorrect()
            throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setUpTimeSeriesChart();
        this.reflectionFactory = new ChartFactoryReflection();
        var chart = this.reflectionFactory.getChartReflection("org.jfree.chart.charts.TimeSeriesChart", this.params);

        assertTrue(chart instanceof JFreeChart);
        assertTrue(chart instanceof TimeSeriesChart);
        assertEquals(this.timeSeriesChart, chart);
    }

    @Test
    public void testDynamicLoadingOfBarChartFailsWithInvalidStringInput()
            throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.reflectionFactory = new ChartFactoryReflection();
        assertThrows(ClassNotFoundException.class, () -> {
            this.reflectionFactory.getChartReflection("org.jfree.chart.charts.Bar", this.params);
        });
    }
}

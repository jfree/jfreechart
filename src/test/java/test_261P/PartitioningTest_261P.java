package test_261P;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;

public class PartitioningTest_261P {

    /** A chart. */
private JFreeChart pieChart;

    /**
     * Common test setup.
     */
    @BeforeEach
    public void setUp() {
        this.pieChart = createPieChart();
    }


    /**
     * Creates a pie chart.
     *
     * @return The pie chart.
     */
    private static JFreeChart createPieChart() {
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", 43);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        return ChartFactory.createPieChart("Pie Chart", data);
    }

    /**
     * A chart change listener.
     */
    static class LocalListener implements ChartChangeListener {

        /** Set to true when the listener is triggered. */
        private boolean flag;

        /**
         * Event handler.
         *
         * @param event  the event.
         */
        @Override
        public void chartChanged(ChartChangeEvent event) {
            this.flag = true;
        }

    }

    /*
      Integer values in dataset - all positive, no applicable flags
     */
    @Test
    public void testAllPositiveIntValues(){
       assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /*
    Integer values in dataset one value is negative, no applicable flags
     */
    @Test
    public void testContainsNegativeIntValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset containing negative
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", 43);
        data.setValue("Visual Basic", -12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);

        assertEquals(true, l.flag);

        assertEquals(2,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /*
     Integer values in dataset - one value is zero, toggle IgnoreZeroFlag
 */
    @Test
    public void testContainsZeroValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset containing 0
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", 43);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 0);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);


        assertEquals(false,((PiePlot)this.pieChart.getPlot()).getIgnoreZeroValues());
        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
        ((PiePlot)this.pieChart.getPlot()).setIgnoreZeroValues(true);

       assertEquals(true,((PiePlot)this.pieChart.getPlot()).getIgnoreZeroValues());
        assertEquals(2,this.pieChart.getPlot().getLegendItems().getItemCount());

    }

    /*
    Integer values in dataset one of the values is null - toggle IgnoreNull flag
     */
    @Test
    public void testContainsNullValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", 43);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", null);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(false,((PiePlot)this.pieChart.getPlot()).getIgnoreNullValues());
        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
        ((PiePlot)this.pieChart.getPlot()).setIgnoreNullValues(true);

        assertEquals(true,((PiePlot)this.pieChart.getPlot()).getIgnoreNullValues());
        assertEquals(2,this.pieChart.getPlot().getLegendItems().getItemCount());

    }

    /**
     * Test Integer.MAX_VALUE
     */
    @Test
    public void testMaxIntegerValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", Integer.MAX_VALUE);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test Integer.MIN_VALUE
     */
    @Test
    public void testMinIntegerValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", Integer.MIN_VALUE);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(2,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test on Long type values
     */
    @Test
    public void testLongValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", new Long(274877906988L));
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test Long.MAX_VALUE
     */
    @Test
    public void testMaxLongValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", Long.MAX_VALUE);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test Long.MIN_VALUE
     */
    @Test
    public void testMinLongValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", Long.MIN_VALUE);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(2,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test on Float type values
     */
    @Test
    public void testFloatValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", new Float(1.5555555555));
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test Float.MAX_VALUE
     */
    @Test
    public void testMaxFloatValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", Float.MAX_VALUE);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test Float.MIN_VALUE
     */
    @Test
    public void testMinFloatValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", Float.MIN_VALUE);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test on Double type values
     */
    @Test
    public void testDoubleValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", new Double(9.99));
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test Double.MAX_VALUE
     */
    @Test
    public void testMaxDoubleValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", Double.MAX_VALUE);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

    /**
     * Test Double.MIN_VALUE
     */
    @Test
    public void testMinDoubleValue(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", Double.MIN_VALUE);
        data.setValue("Visual Basic", 12);
        data.setValue("C/C++", 17);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
    }

}

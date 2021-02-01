package test_261P;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    public void testContainsFloatData(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", 43);
        data.setValue("Visual Basic", 17f);
        data.setValue("C/C++", null);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(false,((PiePlot)this.pieChart.getPlot()).getIgnoreNullValues());
        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
        ((PiePlot)this.pieChart.getPlot()).setIgnoreNullValues(true);

        assertEquals(true,((PiePlot)this.pieChart.getPlot()).getIgnoreNullValues());
        assertEquals(2,this.pieChart.getPlot().getLegendItems().getItemCount());

    }
    @Test
    public void testContainsFloatLongData(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        //replacing existing dataset with new dataset
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", 43L);
        data.setValue("Visual Basic", 12f);
        data.setValue("C/C++", null);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(true, l.flag);

        assertEquals(false,((PiePlot)this.pieChart.getPlot()).getIgnoreNullValues());
        assertEquals(3,this.pieChart.getPlot().getLegendItems().getItemCount());
        ((PiePlot)this.pieChart.getPlot()).setIgnoreNullValues(true);

        assertEquals(true,((PiePlot)this.pieChart.getPlot()).getIgnoreNullValues());
        assertEquals(2,this.pieChart.getPlot().getLegendItems().getItemCount());

    }
    @Test
    public void testReplaceKeysWithNull(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        boolean pass = false;
        try{
            data.setValue(null , 43.2);
            data.setValue("Visual Basic", 0.0);
            data.setValue("C/C++", 17.5);
            ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        }catch (IllegalArgumentException ex) {
            pass = true;
        }
        assertTrue(pass);
        assertFalse(l.flag);

    }
    @Test
    public void testReplaceKeysWithSpace(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("" , 43.2);
        data.setValue("Visual Basic", 0.0);
        data.setValue("C/C++", 17.5);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(0), "");
        assertTrue(l.flag);
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(1), "Visual Basic");
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(2), "C/C++");
    }
    @Test
    public void testReplaceKeysWithOtherString(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("\n" , 43.2);
        data.setValue("Visual Basic", 0.0);
        data.setValue("C/C++", 17.5);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(0), "\n");
        assertTrue(l.flag);
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(1), "Visual Basic");
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(2), "C/C++");
    }
    @Test
    public void testReplaceKeysWithInteger(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("\n" , 43.2);
        data.setValue("15", 0.0);
        data.setValue("C/C++", 17.5);
        ((PiePlot)this.pieChart.getPlot()).setDataset(data);
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(0), "\n");
        assertTrue(l.flag);
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(1), "15");
        assertEquals(((PiePlot)this.pieChart.getPlot()).getDataset().getKey(2), "C/C++");
    }
    @Test
    public void testNonExistentKey(){
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        DefaultPieDataset<String> emptydata = new DefaultPieDataset<>();
        ((PiePlot)this.pieChart.getPlot()).setDataset(emptydata);
        boolean pass = false;
        try{
            ((PiePlot)this.pieChart.getPlot()).getDataset().getValue("NonexistentKey");
        }catch (UnknownKeyException ex){
            pass = true;
        }
        assertTrue(pass);
        // try to get a key at index 0 with empty dataset
        pass = false;
        try{
            ((PiePlot)this.pieChart.getPlot()).getDataset().getKey(0);
        }catch(IndexOutOfBoundsException ex){
            pass = true;
        }
        assertTrue(pass);
        // try to get value at index 0 with an empty dataset
        pass = false;
        try{
            //System.out.println(((PiePlot)this.pieChart.getPlot()).getDataset().getKeys());
            System.out.println(((PiePlot)this.pieChart.getPlot()).getDataset().getValue(0));
        }catch(IndexOutOfBoundsException ex){
            pass = true;
        }
        //this test fails. probably a bug. getValue(0) for empty dataset is returning null
        // When I think it should throw an exception. See https://github.com/jfree/jfreechart/issues/212
        assertTrue(pass);
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

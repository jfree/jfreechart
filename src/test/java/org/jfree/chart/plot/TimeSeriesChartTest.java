package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TimeSeriesChartTest {
    /**
     *  CS427: Issue link: https://github.com/jfree/jfreechart/issues/210
     *  Some checks for the equals() method
      */
    @Test
    public void testEquals(){
       TimeSeriesChart plot1 = new TimeSeriesChart("Time series plot");
       TimeSeriesChart plot2 = new TimeSeriesChart("Time series plot");
       assertTrue(plot1.equals(plot2));
       plot1.setTitle("Time series plot 1");
       assertFalse(plot1.equals(plot2));
    }
}

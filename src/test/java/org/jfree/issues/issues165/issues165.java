package org.jfree.issues.issues165;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * issues165
 *
 * Version:
 */
public class issues165 {

    @Test
    //create a test for the year before 1990
    public void testOldCode() {
        try {
            org.jfree.data.time.Day day = new org.jfree.data.time.Day(1, 1, 1890);
        }catch (Exception e){
            Assertions.assertEquals(e.getClass(),IllegalArgumentException.class);
        }
    }

    @Test
    //test the year of 1890
    public void testSucessCode() {
        Day day = new Day(1, 1, 1890);
    }
}

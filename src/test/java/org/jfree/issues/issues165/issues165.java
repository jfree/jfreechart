package org.jfree.issues.issues165;

import org.jfree.data.time.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Project: Issues165
 * Description: This file fixed the issue #165 for the jfree library.
 *
 * <p>
 * author:wm
 * Date:2021/12/8
 * Time:13:17
 * Version:
 */
public class Issues165 {

    @Test
    //create a test for the year before 1990
    public void testOldCode() {
        try {
            new org.jfree.data.time.Day(1, 1, 1890);
        }catch (Exception e){
            Assertions.assertEquals(e.getClass(),IllegalArgumentException.class);
        }
    }

    @Test
    //test the year of 1700
    public void testSucessCode() {
        new Day(1, 1, 1700);
    }
}

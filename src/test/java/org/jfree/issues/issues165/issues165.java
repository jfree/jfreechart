package org.jfree.issues.issues165;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 类名: issues165
 * 描述:
 *
 * <p>
 * author:
 * Date:2021/11/26
 * Time:9:21
 * Version:
 */
public class issues165 {

    @Test
    public void testOldCode() {
        try {
            org.jfree.data.time.Day day = new org.jfree.data.time.Day(1, 1, 1890);
        }catch (Exception e){
            Assertions.assertEquals(e.getClass(),IllegalArgumentException.class);
        }
    }

    @Test
    public void testSucessCode() {
        Day day = new Day(1, 1, 1890);
    }
}

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 */

package org.jfree.chart.text;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.Map;

/**
 * Some utility methods for working with {@code AttributedString} objects.
 */
public class AttributedStringUtils {

    /**
     * Private constructor prevents object creation.
     */
    private AttributedStringUtils() {
    }

    /**
     * Tests two attributed strings for equality.
     * 
     * @param s1  string 1 ({@code null} permitted).
     * @param s2  string 2 ({@code null} permitted).
     * 
     * @return {@code true} if {@code s1} and {@code s2} are
     *         equal or both {@code null}, and {@code false} 
     *         otherwise.
     */
    public static boolean equal(AttributedString s1, AttributedString s2) {
        if (s1 == null) {
            return (s2 == null);
        }
        if (s2 == null) {
            return false;
        }
        AttributedCharacterIterator it1 = s1.getIterator();
        AttributedCharacterIterator it2 = s2.getIterator();
        char c1 = it1.first();
        char c2 = it2.first();
        int start = 0;
        while (c1 != CharacterIterator.DONE) {
            int limit1 = it1.getRunLimit();
            int limit2 = it2.getRunLimit();
            if (limit1 != limit2) {
                return false;
            }
            // if maps aren't equivalent, return false
            Map m1 = it1.getAttributes();
            Map m2 = it2.getAttributes();
            if (!m1.equals(m2)) {
                return false;
            }
            // now check characters in the run are the same
            for (int i = start; i < limit1; i++) {
                if (c1 != c2) {
                    return false;
                }
                c1 = it1.next();
                c2 = it2.next();
            }
            start = limit1;
        }
        return c2 == CharacterIterator.DONE;
    }
    
}

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ----------------------------
 * SegmentedTimelineTests.java
 * ----------------------------
 * (C) Copyright 2003-2007, by Bill Kelemen and Contributors.
 *
 * Original Author:  Bill Kelemen;
 * Contributor(s):   ;
 *
 * $Id: SegmentedTimelineTests.java,v 1.1.2.3 2007/02/02 15:10:21 mungady Exp $
 *
 * Changes
 * -------
 * 24-May-2003 : Version 1 (BK);
 * 07-Jan-2005 : Added test for hashCode() method (DG);
 * 02-Feb-2007 : Removed author tags all over JFreeChart sources (DG);
 *
 */

package org.jfree.chart.axis.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.axis.SegmentedTimeline;

/**
 * JUnit Tests for the {@link SegmentedTimeline} class.
 */
public class SegmentedTimelineTests extends TestCase {

    /** These constants control test cycles in the validateXXXX methods. */
    private static final int TEST_CYCLE_START = 0;

    /** These constants control test cycles in the validateXXXX methods. */
    private static final int TEST_CYCLE_END   = 1000;

    /** These constants control test cycles in the validateXXXX methods. */
    private static final int TEST_CYCLE_INC   = 55;

    /** Number of ms in five years */
    private static final long FIVE_YEARS 
        = 5 * 365 * SegmentedTimeline.DAY_SEGMENT_SIZE;

    /** Number format object for ms tests. */
    private static final NumberFormat NUMBER_FORMAT 
        = NumberFormat.getNumberInstance();

    /** Date format object for Monday through Friday tests. */
    private static final SimpleDateFormat DATE_FORMAT;

    /** Date format object 9:00 AM to 4:00 PM tests. */
    private static final SimpleDateFormat DATE_TIME_FORMAT;

    /** Some ms exceptions for ms testing. */
    private static final String[] MS_EXCEPTIONS =
        {"0", "2", "4", "10", "15", "16", "17", "18", "19", "20", "21", "22", 
         "23", "24", "47", "58", "100", "101"};

     /** Some ms4 exceptions for ms testing. */
     private static final String[] MS2_BASE_TIMELINE_EXCEPTIONS =
         {"0", "8", "16", "24", "32", "40", "48", "56", "64", "72", "80", "88", 
          "96", "104", "112", "120", "128", "136"};

    /** US non-trading dates in 2000 through 2002 to test exceptions. */
    private static final String[] US_HOLIDAYS =
        {"2000-01-17", "2000-02-21", "2000-04-21", "2000-05-29", "2000-07-04",
         "2000-09-04", "2000-11-23", "2000-12-25", "2001-01-01", "2001-01-15",
         "2001-02-19", "2001-04-13", "2001-05-28", "2001-07-04", "2001-09-03",
         "2001-09-11", "2001-09-12", "2001-09-13", "2001-09-14", "2001-11-22",
         "2001-12-25", "2002-01-01", "2002-01-21", "2002-02-18", "2002-03-29",
         "2002-05-27", "2002-07-04", "2002-09-02", "2002-11-28", "2002-12-25"};

     /** Some test exceptions for the fifteen min timeline. */
     private static final String[] FIFTEEN_MIN_EXCEPTIONS =
         {"2000-01-10 09:00:00", "2000-01-10 09:15:00", "2000-01-10 09:30:00",
          "2000-01-10 09:45:00", "2000-01-10 10:00:00", "2000-01-10 10:15:00",
          "2000-02-15 09:00:00", "2000-02-15 09:15:00", "2000-02-15 09:30:00",
          "2000-02-15 09:45:00", "2000-02-15 10:00:00", "2000-02-15 10:15:00",
          "2000-02-16 11:00:00", "2000-02-16 11:15:00", "2000-02-16 11:30:00",
          "2000-02-16 11:45:00", "2000-02-16 12:00:00", "2000-02-16 12:15:00",
          "2000-02-16 12:30:00", "2000-02-16 12:45:00", "2000-02-16 01:00:00",
          "2000-02-16 01:15:00", "2000-02-16 01:30:00", "2000-02-16 01:45:00",
          "2000-05-17 11:45:00", "2000-05-17 12:00:00", "2000-05-17 12:15:00",
          "2000-05-17 12:30:00", "2000-05-17 12:45:00", "2000-05-17 01:00:00",
          "2000-05-17 01:15:00", "2000-05-17 01:30:00", "2000-05-17 01:45:00",
          "2000-05-17 02:00:00", "2000-05-17 02:15:00", "2000-05-17 02:30:00",
          "2000-05-17 02:45:00", "2000-05-17 03:00:00", "2000-05-17 03:15:00",
          "2000-05-17 03:30:00", "2000-05-17 03:45:00", "2000-05-17 04:00:00"};

    /** Our 1-ms test timeline using 5 included and 2 excluded segments. */
    private SegmentedTimeline msTimeline;

    /** 
     * Our 1-ms test timeline (with baseTimeline) using 2 included and 2 
     * excluded segments. 
     */
    private SegmentedTimeline ms2Timeline;

    /** 
     * Our 4-ms test base timeline for ms2Timeline using 1 included and 1 
     * excluded segments 
     */
    private SegmentedTimeline ms2BaseTimeline;

    /** Our test Monday through Friday test timeline. */
    private SegmentedTimeline mondayFridayTimeline;

    /** Our 9:00 AM to 4:00 PM fifteen minute timeline. */
    private SegmentedTimeline fifteenMinTimeline;

    /** ms from 1970-01-01 to first monday after 2001-01-01. */
    private Calendar monday;

    /** ms from 1970-01-01 to 9 am first monday after 2001-01-01. */
    private Calendar monday9am;

    /** Static initialization block. */
    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        DATE_FORMAT.setTimeZone(SegmentedTimeline.NO_DST_TIME_ZONE);

        DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DATE_TIME_FORMAT.setTimeZone(SegmentedTimeline.NO_DST_TIME_ZONE);
    }

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(SegmentedTimelineTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public SegmentedTimelineTests(String name) {
        super(name);
    }

    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     * 
     * @throws Exception if there is a problem.
     */
    protected void setUp() throws Exception {
        // setup our test timelines
        //
        // Legend for comments below:
        // <spaces> = Segments included in the final timeline
        // EE       = Excluded segments via timeline rules
        // xx       = Exception segments inherited from base timeline exclusions

        // 1-ms test timeline using 5 included and 2 excluded segments.
        //
        // timeline start time = 0
        //   |
        //   v
        //   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 ..
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+..
        // |  |  |  |  |  |EE|EE|  |  |  |  |  |EE|EE|  |  |  |  |  |  |EE|EE|    <-- msTimeline
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+..
        //  \_________  ________/            \_/
        //            \/                      |
        //       segment group         segment size = 1 ms
        //
        this.msTimeline = new SegmentedTimeline(1, 5, 2);
        this.msTimeline.setStartTime(0);

        // 4-ms test base timeline for ms2Timeline using 1 included and 1
        // excluded segments
        //
        // timeline start time = 0
        //   |
        //   v
        //   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 ...
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+...
        // |  |  |  |  |EE|EE|EE|EE|  |  |  |  |EE|EE|EE|EE|  |  |  |  |    <-- ms2BaseTimeline
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+...
        //  \__________  _________/            \____  _____/
        //             \/                           \/
        //        segment group              segment size = 4 ms
        //
        this.ms2BaseTimeline = new SegmentedTimeline(4, 1, 1);
        this.ms2BaseTimeline.setStartTime(0);

        // 1-ms test timeline (with a baseTimeline) using 2 included and 2 
        // excluded segments centered inside each base segment
        //
        // The ms2Timeline without a base would look like this:
        //
        //    timeline start time = 1
        //      |
        //      v
        //   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 ...
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+...
        // |EE|  |  |EE|EE|  |  |EE|EE|  |  |EE|EE|  |  |EE|EE|  |  |EE|    <-- ms2Timeline
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+...
        //    \____  _____/            \_/
        //         \/                   |
        //    segment group      segment size = 1 ms
        //
        // With the base timeline some originally included segments are now 
        // removed (see "xx" below):
        //
        //   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 ...
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+...
        // |EE|  |  |EE|EE|xx|xx|EE|EE|  |  |EE|EE|xx|xx|EE|EE|  |  |EE|    <-- ms2Timeline
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+...
        // |  |  |  |  |EE|EE|EE|EE|  |  |  |  |EE|EE|EE|EE|  |  |  |  |    <-- ms2BaseTimeline
        // +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+...
        //
        this.ms2Timeline = new SegmentedTimeline(1, 2, 2);
        this.ms2Timeline.setStartTime(1);
        this.ms2Timeline.setBaseTimeline(this.ms2BaseTimeline);

        // test monday though friday timeline
        this.mondayFridayTimeline 
            = SegmentedTimeline.newMondayThroughFridayTimeline();

        // test 9am-4pm Monday through Friday timeline
        this.fifteenMinTimeline 
            = SegmentedTimeline.newFifteenMinuteTimeline();

        // find first Monday after 2001-01-01
        Calendar cal = new GregorianCalendar(
            SegmentedTimeline.NO_DST_TIME_ZONE
        );
        cal.set(2001, 0, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            cal.add(Calendar.DATE, 1);
        }
        this.monday = (Calendar) cal.clone();

        // calculate 9am on the first Monday after 2001-01-01
        cal.add(Calendar.HOUR, 9);
        this.monday9am = (Calendar) cal.clone();
    }

    /**
     * Tears down the fixture, for example, close a network connection.
     * This method is called after a test is executed.
     * 
     * @throws Exception if there is a problem.
     */
    protected void tearDown() throws Exception {
        // does nothing
    }

    //////////////////////////////////////////////////////////////////////////
    // test construction process
    //////////////////////////////////////////////////////////////////////////

    /**
     * Tests that the new method that created the msTimeline segmented
     * timeline did so correctly.
     */
    public void testMsSegmentedTimeline() {
        // verify attributes set during object construction
        assertEquals(1, this.msTimeline.getSegmentSize());
        assertEquals(0, this.msTimeline.getStartTime());
        assertEquals(5, this.msTimeline.getSegmentsIncluded());
        assertEquals(2, this.msTimeline.getSegmentsExcluded());
    }

    /**
     * Tests that the new method that created the ms2Timeline segmented
     * timeline did so correctly.
     */
    public void testMs2SegmentedTimeline() {
        // verify attributes set during object construction
        assertEquals(1, this.ms2Timeline.getSegmentSize());
        assertEquals(1, this.ms2Timeline.getStartTime());
        assertEquals(2, this.ms2Timeline.getSegmentsIncluded());
        assertEquals(2, this.ms2Timeline.getSegmentsExcluded());
        assertEquals(this.ms2BaseTimeline, this.ms2Timeline.getBaseTimeline());
    }

    /**
     * Tests that the factory method that creates Monday through Friday 
     * segmented timeline does so correctly.
     */
    public void testMondayThroughFridaySegmentedTimeline() {
        // verify attributes set during object construction
        assertEquals(
            SegmentedTimeline.DAY_SEGMENT_SIZE, 
            this.mondayFridayTimeline.getSegmentSize()
        );
        assertEquals(
            SegmentedTimeline.FIRST_MONDAY_AFTER_1900, 
            this.mondayFridayTimeline.getStartTime()
        );
        assertEquals(5, this.mondayFridayTimeline.getSegmentsIncluded());
        assertEquals(2, this.mondayFridayTimeline.getSegmentsExcluded());
    }

    /**
     * Tests that the factory method that creates a 15-min 9:00 AM  4:00 PM
     * segmented axis does so correctly.
     */
    public void testFifteenMinSegmentedTimeline() {
        assertEquals(SegmentedTimeline.FIFTEEN_MINUTE_SEGMENT_SIZE,
                this.fifteenMinTimeline.getSegmentSize());
        assertEquals(SegmentedTimeline.FIRST_MONDAY_AFTER_1900 + 36 
                     * this.fifteenMinTimeline.getSegmentSize(),
                     this.fifteenMinTimeline.getStartTime());
        assertEquals(28, this.fifteenMinTimeline.getSegmentsIncluded());
        assertEquals(68, this.fifteenMinTimeline.getSegmentsExcluded());
    }

    //////////////////////////////////////////////////////////////////////////
    // test one-segment and adjacent segments
    //////////////////////////////////////////////////////////////////////////

    /**
     * Tests one segment of the ms timeline. Internal indices
     * inside one segment as well as adjacent segments are verified.
     */
    public void testMsSegment() {
        verifyOneSegment(this.msTimeline);
    }

    /**
     * Tests one segment of the ms timeline. Internal indices
     * inside one segment as well as adjacent segments are verified.
     */
    public void testMs2Segment() {
        verifyOneSegment(this.ms2Timeline);
    }

    /**
     * Tests one segment of the Monday through Friday timeline. Internal indices
     * inside one segment as well as adjacent segments are verified.
     */
    public void testMondayThroughFridaySegment() {
        verifyOneSegment(this.mondayFridayTimeline);
    }

    /**
     * Tests one segment of the Fifteen timeline. Internal indices
     * inside one segment as well as adjacent segments are verified.
     */
    public void testFifteenMinSegment() {
        verifyOneSegment(this.fifteenMinTimeline);
    }

    /**
     * Tests one segment of the Monday through Friday timeline. Internal indices
     * inside one segment as well as adjacent segments are verified.
     * @param timeline the timeline to use for verifications.
     */
    public void verifyOneSegment(SegmentedTimeline timeline) {
        
        for (long testCycle = TEST_CYCLE_START; testCycle < TEST_CYCLE_END;
             testCycle += TEST_CYCLE_INC) {

            // get two consecutive segments for various tests
            SegmentedTimeline.Segment segment1 = timeline.getSegment(
                this.monday.getTime().getTime() + testCycle
            );
            SegmentedTimeline.Segment segment2 =
                timeline.getSegment(segment1.getSegmentEnd() + 1);

            // verify segments are consecutive and correct
            assertEquals(
                segment1.getSegmentNumber() + 1, segment2.getSegmentNumber()
            );
            assertEquals(
                segment1.getSegmentEnd() + 1, segment2.getSegmentStart()
            );
            assertEquals(
                segment1.getSegmentStart() + timeline.getSegmentSize() - 1,
                segment1.getSegmentEnd()
            );
            assertEquals(
                segment1.getSegmentStart() + timeline.getSegmentSize(),
                segment2.getSegmentStart()
            );
            assertEquals(
                segment1.getSegmentEnd() + timeline.getSegmentSize(),
                segment2.getSegmentEnd()
            );

            // verify various indices inside a segment are the same segment
            long delta;
            if (timeline.getSegmentSize() > 1000000) {
                delta = timeline.getSegmentSize() / 10000;
            } 
            else if (timeline.getSegmentSize() > 100000) {
                delta = timeline.getSegmentSize() / 1000;
            } 
            else if (timeline.getSegmentSize() > 10000) {
                delta = timeline.getSegmentSize() / 100;
            }
            else if (timeline.getSegmentSize() > 1000) {
                delta = timeline.getSegmentSize() / 10;
            }
            else if (timeline.getSegmentSize() > 100) {
                delta = timeline.getSegmentSize() / 5;
            }
            else {
                delta = 1;
            }

            long start = segment1.getSegmentStart() + delta;
            long end = segment1.getSegmentStart() 
                       + timeline.getSegmentSize() - 1;
            SegmentedTimeline.Segment lastSeg = timeline.getSegment(
                segment1.getSegmentStart()
            );
            SegmentedTimeline.Segment seg;
            for (long i = start; i < end; i += delta) {
                seg = timeline.getSegment(i);
                assertEquals(
                    lastSeg.getSegmentNumber(), seg.getSegmentNumber()
                );
                assertEquals(lastSeg.getSegmentStart(), seg.getSegmentStart());
                assertEquals(lastSeg.getSegmentEnd(), seg.getSegmentEnd());
                assertTrue(lastSeg.getMillisecond() < seg.getMillisecond());
                lastSeg = seg;
            }

            // try next segment
            seg = timeline.getSegment(end + 1);
            assertEquals(segment2.getSegmentNumber(), seg.getSegmentNumber());
            assertEquals(segment2.getSegmentStart(), seg.getSegmentStart());
            assertEquals(segment2.getSegmentEnd(), seg.getSegmentEnd());
        }
    }

    //////////////////////////////////////////////////////////////////////////
    // test inc methods
    //////////////////////////////////////////////////////////////////////////

    /**
     * Tests the inc methods on the msTimeline.
     */
    public void testMsInc() {
        verifyInc(this.msTimeline);
    }

    /**
     * Tests the inc methods on the msTimeline.
     */
    public void testMs2Inc() {
        verifyInc(this.ms2Timeline);
    }

    /**
     * Tests the inc methods on the Monday through Friday timeline.
     */
    public void testMondayThroughFridayInc() {
        verifyInc(this.mondayFridayTimeline);
    }

    /**
     * Tests the inc methods on the Fifteen minute timeline.
     */
    public void testFifteenMinInc() {
        verifyInc(this.fifteenMinTimeline);
    }

    /**
     * Tests the inc methods.
     * @param timeline the timeline to use for verifications.
     */
    public void verifyInc(SegmentedTimeline timeline) {
        for (long testCycle = TEST_CYCLE_START; testCycle < TEST_CYCLE_END;
             testCycle += TEST_CYCLE_INC) {

            long m = timeline.getSegmentSize();
            SegmentedTimeline.Segment segment = timeline.getSegment(testCycle);
            SegmentedTimeline.Segment seg1 = segment.copy();
            for (int i = 0; i < 1000; i++) {

                // test inc() method
                SegmentedTimeline.Segment seg2 = seg1.copy();
                seg2.inc();

                if ((seg1.getSegmentEnd() + 1) != seg2.getSegmentStart()) {
                    // logically consecutive segments non-physically consecutive
                    // (with non-contained time in between)
                    assertTrue(
                        !timeline.containsDomainRange(
                            seg1.getSegmentEnd() + 1, seg2.getSegmentStart() - 1
                        )
                    );
                    assertEquals(
                        0, (seg2.getSegmentStart() - seg1.getSegmentStart()) % m
                    );
                    assertEquals(
                        0, (seg2.getSegmentEnd() - seg1.getSegmentEnd()) % m
                    );
                    assertEquals(
                        0, (seg2.getMillisecond() - seg1.getMillisecond()) % m
                    );
                } 
                else {
                    // physically consecutive
                    assertEquals(
                        seg1.getSegmentStart() + m, seg2.getSegmentStart()
                    );
                    assertEquals(
                        seg1.getSegmentEnd() + m, seg2.getSegmentEnd()
                    );
                    assertEquals(
                        seg1.getMillisecond() + m, seg2.getMillisecond()
                    );
                }

                // test inc(n) method
                SegmentedTimeline.Segment seg3 = seg1.copy();
                SegmentedTimeline.Segment seg4 = seg1.copy();

                for (int j = 0; j < i; j++) {
                    seg3.inc();
                }
                seg4.inc(i);

                assertEquals(seg3.getSegmentStart(), seg4.getSegmentStart());
                assertEquals(seg3.getSegmentEnd(), seg4.getSegmentEnd());
                assertEquals(seg3.getMillisecond(), seg4.getMillisecond());

                // go to another segment to continue test
                seg1.inc();
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////
    // main include and excluded segments
    //////////////////////////////////////////////////////////////////////////

    /**
     * Tests that the msTimeline's included and excluded
     * segments are being calculated correctly.
     */
    public void testMsIncludedAndExcludedSegments() {
        verifyIncludedAndExcludedSegments(this.msTimeline, 0);
    }

    /**
     * Tests that the ms2Timeline's included and excluded
     * segments are being calculated correctly.
     */
    public void testMs2IncludedAndExcludedSegments() {
        verifyIncludedAndExcludedSegments(this.ms2Timeline, 1);
    }

    /**
     * Tests that the Monday through Friday timeline's included and excluded
     * segments are being calculated correctly. The test is performed starting
     * on the first monday after 1/1/2000 and for five years.
     */
    public void testMondayThroughFridayIncludedAndExcludedSegments() {
        verifyIncludedAndExcludedSegments(
            this.mondayFridayTimeline, this.monday.getTime().getTime()
        );
    }

    /**
     * Tests that the Fifteen-Min timeline's included and excluded
     * segments are being calculated correctly. The test is performed starting
     * on the first monday after 1/1/2000 and for five years.
     */
    public void testFifteenMinIncludedAndExcludedSegments() {
        verifyIncludedAndExcludedSegments(
            this.fifteenMinTimeline, this.monday9am.getTime().getTime()
        );
    }

    /**
     * Tests that a timeline's included and excluded segments are being 
     * calculated correctly.
     * 
     * @param timeline the timeline to verify
     * @param n the first segment number to start verifying
     */
    public void verifyIncludedAndExcludedSegments(SegmentedTimeline timeline, 
                                                  long n) {
        // clear any exceptions in this timeline
        timeline.setExceptionSegments(new java.util.ArrayList());

        // test some included and excluded segments
        SegmentedTimeline.Segment segment = timeline.getSegment(n);
        for (int i = 0; i < 1000; i++) {
            int d = (i % timeline.getGroupSegmentCount());
            if (d < timeline.getSegmentsIncluded()) {
                // should be an included segment
                assertTrue(segment.inIncludeSegments());
                assertTrue(!segment.inExcludeSegments());
                assertTrue(!segment.inExceptionSegments());
            } 
            else {
                // should be an excluded segment
                assertTrue(!segment.inIncludeSegments());
                assertTrue(segment.inExcludeSegments());
                assertTrue(!segment.inExceptionSegments());
            }
            segment.inc();
        }
    }

    //////////////////////////////////////////////////////////////////////////
    // test exception segments
    //////////////////////////////////////////////////////////////////////////

    /**
     * Tests methods related to exceptions methods in the msTimeline.
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testMsExceptionSegments() throws ParseException {
        verifyExceptionSegments(this.msTimeline, MS_EXCEPTIONS, NUMBER_FORMAT);
    }

    /**
     * Tests methods related to exceptions methods in the ms2BaseTimeline.
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testMs2BaseTimelineExceptionSegments() throws ParseException {
        verifyExceptionSegments(
            this.ms2BaseTimeline, MS2_BASE_TIMELINE_EXCEPTIONS, NUMBER_FORMAT
        );
    }

    /**
     * Tests methods related to exceptions methods in the mondayFridayTimeline.
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testMondayThoughFridayExceptionSegments() 
        throws ParseException {
        verifyExceptionSegments(
            this.mondayFridayTimeline, US_HOLIDAYS, DATE_FORMAT
        );
    }

    /**
     * Tests methods related to exceptions methods in the fifteenMinTimeline.
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testFifteenMinExceptionSegments() throws ParseException {
        verifyExceptionSegments(
            this.fifteenMinTimeline, FIFTEEN_MIN_EXCEPTIONS, DATE_TIME_FORMAT
        );
    }

    /**
     * Tests methods related to adding exceptions.
     * 
     * @param timeline the timeline to verify
     * @param exceptionString array of Strings that represent the exceptions
     * @param fmt Format object that can parse the exceptionString strings
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void verifyExceptionSegments(SegmentedTimeline timeline,
                                        String[] exceptionString,
                                        Format fmt)
        throws ParseException {

        // fill in the exceptions
        long[] exception = verifyFillInExceptions(
            timeline, exceptionString, fmt
        );

        int m = exception.length;

        // verify list of exceptions
        assertEquals(exception.length, timeline.getExceptionSegments().size());
        SegmentedTimeline.Segment lastSegment 
            = timeline.getSegment(exception[m - 1]);
        for (int i = 0; i < m; i++) {
            SegmentedTimeline.Segment segment 
                = timeline.getSegment(exception[i]);
            assertTrue(segment.inExceptionSegments());
            // include current exception and last one
            assertEquals(m - i, timeline.getExceptionSegmentCount(
                segment.getSegmentStart(), lastSegment.getSegmentEnd()));
            // exclude current exception and last one
            assertEquals(
                Math.max(0, m - i - 2), timeline.getExceptionSegmentCount(
                exception[i] + 1, exception[m - 1] - 1)
            );
        }

    }

    //////////////////////////////////////////////////////////////////////////
    // test timeline translations
    //////////////////////////////////////////////////////////////////////////

    /**
     * Tests translations for 1-ms timeline
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testMsTranslations() throws ParseException {
        verifyFillInExceptions(this.msTimeline, MS_EXCEPTIONS, NUMBER_FORMAT);
        verifyTranslations(this.msTimeline, 0);
    }

    /**
     * Tests translations for the base timeline used for the ms2Timeline
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testMs2BaseTimelineTranslations() throws ParseException {
        verifyFillInExceptions(
            this.ms2BaseTimeline, MS2_BASE_TIMELINE_EXCEPTIONS, NUMBER_FORMAT
        );
        verifyTranslations(this.ms2BaseTimeline, 0);
    }

    /**
     * Tests translations for the Monday through Friday timeline
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testMs2Translations() throws ParseException {
        fillInBaseTimelineExceptions(
            this.ms2Timeline, MS2_BASE_TIMELINE_EXCEPTIONS, NUMBER_FORMAT
        );
        fillInBaseTimelineExclusionsAsExceptions(this.ms2Timeline, 0, 5000);
        verifyTranslations(this.ms2Timeline, 1);
    }

    /**
     * Tests translations for the Monday through Friday timeline
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testMondayThroughFridayTranslations() throws ParseException {
        verifyFillInExceptions(
            this.mondayFridayTimeline, US_HOLIDAYS, DATE_FORMAT
        );
        verifyTranslations(
            this.mondayFridayTimeline, this.monday.getTime().getTime()
        );
    }

    /**
     * Tests translations for the Fifteen Min timeline
     * 
     * @throws ParseException if there is a parsing error.
     */
    public void testFifteenMinTranslations() throws ParseException {
        verifyFillInExceptions(
            this.fifteenMinTimeline, FIFTEEN_MIN_EXCEPTIONS, DATE_TIME_FORMAT
        );
        fillInBaseTimelineExceptions(
            this.fifteenMinTimeline, US_HOLIDAYS, DATE_FORMAT
        );
        fillInBaseTimelineExclusionsAsExceptions(
            this.fifteenMinTimeline,
            this.monday9am.getTime().getTime(),
            this.monday9am.getTime().getTime() + FIVE_YEARS
        );
        verifyTranslations(
            this.fifteenMinTimeline, this.monday9am.getTime().getTime()
        );
    }

    /**
     * Tests translations between timelines.
     * 
     * @param timeline the timeline to use for verifications.
     * @param startTest  ??.
     */
    public void verifyTranslations(SegmentedTimeline timeline, long startTest) {
        for (long testCycle = TEST_CYCLE_START; testCycle < TEST_CYCLE_END;
             testCycle += TEST_CYCLE_INC) {

            long millisecond = startTest + testCycle 
                               * timeline.getSegmentSize();
            SegmentedTimeline.Segment segment 
                = timeline.getSegment(millisecond);
            
            for (int i = 0; i < 1000; i++) {
                long translatedValue 
                    = timeline.toTimelineValue(segment.getMillisecond());
                long newValue = timeline.toMillisecond(translatedValue);

                if (segment.inExcludeSegments() 
                        || segment.inExceptionSegments()) {
                    // the reverse transformed value will be in the start of the
                    // next non-excluded and non-exception segment
                    SegmentedTimeline.Segment tempSegment = segment.copy();
                    tempSegment.moveIndexToStart();
                    do {
                        tempSegment.inc();
                    }
                    while (!tempSegment.inIncludeSegments());
                    assertEquals(tempSegment.getMillisecond(), newValue);
                }

                else {
                    assertEquals(segment.getMillisecond(), newValue);
                }
                segment.inc();
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////
    // test serialization
    //////////////////////////////////////////////////////////////////////////

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        verifySerialization(this.msTimeline);
        verifySerialization(this.ms2Timeline);
        verifySerialization(this.ms2BaseTimeline);
        verifySerialization(SegmentedTimeline.newMondayThroughFridayTimeline());
        verifySerialization(SegmentedTimeline.newFifteenMinuteTimeline());
    }

    /**
     * Tests serialization of an instance.
     * @param a1 The timeline to verify the serialization
     */
    private void verifySerialization(SegmentedTimeline a1) {
        SegmentedTimeline a2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            a2 = (SegmentedTimeline) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(a1, a2);
    }

    /**
     * Adds an array of exceptions to the timeline. The timeline exception list
     * is first cleared.
     * @param timeline The timeline where the exceptions will be stored
     * @param exceptionString The exceptions to load
     * @param fmt The date formatter to use to parse each exceptions[i] value
     * @throws ParseException If there is any exception parsing each 
     *         exceptions[i] value.
     * @return An array of Dates[] containing each exception date.
     */
    private long[] verifyFillInExceptions(SegmentedTimeline timeline,
                                         String[] exceptionString,
                                         Format fmt) throws ParseException {
        // make sure there are no exceptions
        timeline.setExceptionSegments(new java.util.ArrayList());
        assertEquals(0, timeline.getExceptionSegments().size());

        // add our exceptions and store locally in ArrayList of Longs
        ArrayList exceptionList = new ArrayList();
        for (int i = 0; i < exceptionString.length; i++) {
            long e;
            if (fmt instanceof NumberFormat) {
                e = ((NumberFormat) fmt).parse(exceptionString[i]).longValue();
            }
            else {
                e = timeline.getTime(
                    ((SimpleDateFormat) fmt).parse(exceptionString[i])
                );
            }
            // only add an exception if it is currently an included segment
            SegmentedTimeline.Segment segment = timeline.getSegment(e);
            if (segment.inIncludeSegments()) {
                timeline.addException(e);
                exceptionList.add(new Long(e));
                assertEquals(
                    exceptionList.size(), timeline.getExceptionSegments().size()
                );
                assertTrue(segment.inExceptionSegments());
            }
        }

        // make array of exceptions
        long[] exception = new long[exceptionList.size()];
        int i = 0;
        for (Iterator iter = exceptionList.iterator(); iter.hasNext();) {
            Long l = (Long) iter.next();
            exception[i++] = l.longValue();
        }

        return (exception);

    }

    /**
     * Adds an array of exceptions relative to the base timeline.
     *
     * @param timeline The timeline where the exceptions will be stored
     * @param exceptionString The exceptions to load
     * @param fmt The date formatter to use to parse each exceptions[i] value
     * @throws ParseException If there is any exception parsing each 
     *                        exceptions[i] value.
     */
    private void fillInBaseTimelineExceptions(SegmentedTimeline timeline,
                                             String[] exceptionString,
                                             Format fmt) throws ParseException {
        SegmentedTimeline baseTimeline = timeline.getBaseTimeline();
        for (int i = 0; i < exceptionString.length; i++) {
            long e;
            if (fmt instanceof NumberFormat) {
                e = ((NumberFormat) fmt).parse(exceptionString[i]).longValue();
            }
            else {
                e = timeline.getTime(
                    ((SimpleDateFormat) fmt).parse(exceptionString[i])
                );
            }
            timeline.addBaseTimelineException(e);

            // verify all timeline segments included in the 
            // baseTimeline.segment are now exceptions
            SegmentedTimeline.Segment segment1 = baseTimeline.getSegment(e);
            for (SegmentedTimeline.Segment segment2 
                = timeline.getSegment(segment1.getSegmentStart());
                 segment2.getSegmentStart() <= segment1.getSegmentEnd();
                 segment2.inc()) {
                if (!segment2.inExcludeSegments()) {
                    assertTrue(segment2.inExceptionSegments());
                }
            }

        }
    }

    /**
     * Adds new exceptions to a timeline. The exceptions are the excluded 
     * segments from its base timeline.
     *
     * @param timeline  the timeline.
     * @param from  the start.
     * @param to  the end.
     */
    private void fillInBaseTimelineExclusionsAsExceptions(
            SegmentedTimeline timeline, long from, long to) {

        // add the base timeline exclusions as timeline's esceptions
        timeline.addBaseTimelineExclusions(from, to);

        // validate base timeline exclusions added as timeline's esceptions
        for (SegmentedTimeline.Segment segment1 
                = timeline.getBaseTimeline().getSegment(from);
             segment1.getSegmentStart() <= to;
             segment1.inc()) {

            if (segment1.inExcludeSegments()) {

                // verify all timeline segments included in the 
                // baseTimeline.segment are now exceptions
                for (SegmentedTimeline.Segment segment2 
                     = timeline.getSegment(segment1.getSegmentStart());
                     segment2.getSegmentStart() <= segment1.getSegmentEnd();
                     segment2.inc()) {
                    if (!segment2.inExcludeSegments()) {
                        assertTrue(segment2.inExceptionSegments());
                    }
                }
            }
        }
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        SegmentedTimeline l1 = new SegmentedTimeline(1000, 5, 2);
        SegmentedTimeline l2 = null;
        try {
            l2 = (SegmentedTimeline) l1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(l1 != l2);
        assertTrue(l1.getClass() == l2.getClass());
        assertTrue(l1.equals(l2));
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        
        SegmentedTimeline l1 = new SegmentedTimeline(1000, 5, 2);
        SegmentedTimeline l2 = new SegmentedTimeline(1000, 5, 2);
        assertTrue(l1.equals(l2));
        
        l1 = new SegmentedTimeline(1000, 5, 2);
        l2 = new SegmentedTimeline(1001, 5, 2);
        assertFalse(l1.equals(l2));
        
        l1 = new SegmentedTimeline(1000, 5, 2);
        l2 = new SegmentedTimeline(1000, 4, 2);
        assertFalse(l1.equals(l2));
        
        l1 = new SegmentedTimeline(1000, 5, 2);
        l2 = new SegmentedTimeline(1000, 5, 1);
        assertFalse(l1.equals(l2));
        
        l1 = new SegmentedTimeline(1000, 5, 2);
        l2 = new SegmentedTimeline(1000, 5, 2);
        
        // start time...
        l1.setStartTime(1234L);
        assertFalse(l1.equals(l2));
        l2.setStartTime(1234L);
        assertTrue(l1.equals(l2));

    }
    
    /**
     * Two objects that are equal are required to return the same hashCode. 
     */
    public void testHashCode() {
        SegmentedTimeline l1 = new SegmentedTimeline(1000, 5, 2);
        SegmentedTimeline l2 = new SegmentedTimeline(1000, 5, 2);
        assertTrue(l1.equals(l2));
        int h1 = l1.hashCode();
        int h2 = l2.hashCode();
        assertEquals(h1, h2);
    }    
    
    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization2() {

        SegmentedTimeline l1 = new SegmentedTimeline(1000, 5, 2);
        SegmentedTimeline l2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(l1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            l2 = (SegmentedTimeline) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        boolean b = l1.equals(l2);
        assertTrue(b);

    }

    //////////////////////////////////////////////////////////////////////////
    // utility methods
    //////////////////////////////////////////////////////////////////////////

    /**
     * Tests a basic segmented timeline.
     */
    public void testBasicSegmentedTimeline() {
        SegmentedTimeline stl = new SegmentedTimeline(10, 2, 3);
        stl.setStartTime(946684800000L);  // 1-Jan-2000
        assertFalse(stl.containsDomainValue(946684799999L));
        assertTrue(stl.containsDomainValue(946684800000L));
        assertTrue(stl.containsDomainValue(946684800019L));
        assertFalse(stl.containsDomainValue(946684800020L));
        assertFalse(stl.containsDomainValue(946684800049L));
        assertTrue(stl.containsDomainValue(946684800050L));
        assertTrue(stl.containsDomainValue(946684800069L));
        assertFalse(stl.containsDomainValue(946684800070L));
        assertFalse(stl.containsDomainValue(946684800099L));
        assertTrue(stl.containsDomainValue(946684800100L));
        
        assertEquals(0, stl.toTimelineValue(946684800000L));
        assertEquals(19, stl.toTimelineValue(946684800019L));
        assertEquals(20, stl.toTimelineValue(946684800020L));
        assertEquals(20, stl.toTimelineValue(946684800049L));
        assertEquals(20, stl.toTimelineValue(946684800050L));
        assertEquals(39, stl.toTimelineValue(946684800069L));
        assertEquals(40, stl.toTimelineValue(946684800070L));
        assertEquals(40, stl.toTimelineValue(946684800099L));
        assertEquals(40, stl.toTimelineValue(946684800100L));
        
        assertEquals(946684800000L, stl.toMillisecond(0));
        assertEquals(946684800019L, stl.toMillisecond(19));
        assertEquals(946684800050L, stl.toMillisecond(20));
        assertEquals(946684800069L, stl.toMillisecond(39));
        assertEquals(946684800100L, stl.toMillisecond(40));
        
    }
    
    /**
     * Tests a basic time line with one exception.
     */
    public void testSegmentedTimelineWithException1() {
        SegmentedTimeline stl = new SegmentedTimeline(10, 2, 3);
        stl.setStartTime(946684800000L);  // 1-Jan-2000
        stl.addException(946684800050L);        
        assertFalse(stl.containsDomainValue(946684799999L));
        assertTrue(stl.containsDomainValue(946684800000L));
        assertTrue(stl.containsDomainValue(946684800019L));
        assertFalse(stl.containsDomainValue(946684800020L));
        assertFalse(stl.containsDomainValue(946684800049L));
        assertFalse(stl.containsDomainValue(946684800050L));
        assertFalse(stl.containsDomainValue(946684800059L));
        assertTrue(stl.containsDomainValue(946684800060L));
        assertTrue(stl.containsDomainValue(946684800069L));
        assertFalse(stl.containsDomainValue(946684800070L));
        assertFalse(stl.containsDomainValue(946684800099L));
        assertTrue(stl.containsDomainValue(946684800100L));

        //long v = stl.toTimelineValue(946684800020L);
        assertEquals(0, stl.toTimelineValue(946684800000L));
        assertEquals(19, stl.toTimelineValue(946684800019L));
        assertEquals(20, stl.toTimelineValue(946684800020L));
        assertEquals(20, stl.toTimelineValue(946684800049L));
        assertEquals(20, stl.toTimelineValue(946684800050L));
        assertEquals(29, stl.toTimelineValue(946684800069L));
        assertEquals(30, stl.toTimelineValue(946684800070L));
        assertEquals(30, stl.toTimelineValue(946684800099L));
        assertEquals(30, stl.toTimelineValue(946684800100L));

        assertEquals(946684800000L, stl.toMillisecond(0));
        assertEquals(946684800019L, stl.toMillisecond(19));
        assertEquals(946684800060L, stl.toMillisecond(20));
        assertEquals(946684800069L, stl.toMillisecond(29));
        assertEquals(946684800100L, stl.toMillisecond(30));

    }    

    //////////////////////////////////////////////////////////////////////////
    // main method only for debug
    //////////////////////////////////////////////////////////////////////////

    /**
     * Only use to debug JUnit suite.
     * 
     * @param args  ignored.
     * 
     * @throws Exception if there is some problem.
     */
    public static void main(String[] args) throws Exception {
        SegmentedTimelineTests test = new SegmentedTimelineTests("Test");
        test.setUp();
        test.testMondayThoughFridayExceptionSegments();
        test.tearDown();
    }

}

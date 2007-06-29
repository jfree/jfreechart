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
 * -----------------------
 * RelativeDateFormat.java
 * -----------------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: RelativeDateFormat.java,v 1.1.2.3 2007/03/05 13:47:40 mungady Exp $
 *
 * Changes:
 * --------
 * 01-Nov-2006 : Version 1 (DG);
 * 23-Nov-2006 : Added argument checks, updated equals(), added clone() and 
 *               hashCode() (DG);
 *
 */
package org.jfree.chart.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A formatter that formats dates to show the elapsed time relative to some
 * base date.
 *
 * @since 1.0.3
 */
public class RelativeDateFormat extends DateFormat {
    
    /** The base milliseconds for the elapsed time calculation. */
    private long baseMillis;
    
    /**
     * A flag that controls whether or not a zero day count is displayed.
     */
    private boolean showZeroDays;
    
    /** 
     * A formatter for the day count (most likely not critical until the
     * day count exceeds 999). 
     */
    private NumberFormat dayFormatter;
    
    /**
     * A string appended after the day count.
     */
    private String daySuffix;
    
    /**
     * A string appended after the hours.
     */
    private String hourSuffix;
    
    /**
     * A string appended after the minutes.
     */
    private String minuteSuffix;
    
    /**
     * A formatter for the seconds (and milliseconds).
     */
    private NumberFormat secondFormatter;
    
    /**
     * A string appended after the seconds.
     */
    private String secondSuffix;

    /**
     * A constant for the number of milliseconds in one hour.
     */
    private static long MILLISECONDS_IN_ONE_HOUR = 60 * 60 * 1000L;

    /**
     * A constant for the number of milliseconds in one day.
     */
    private static long MILLISECONDS_IN_ONE_DAY = 24 * MILLISECONDS_IN_ONE_HOUR;
    
    /**
     * Creates a new instance.
     */
    public RelativeDateFormat() {
        this(0L);  
    }
    
    /**
     * Creates a new instance.
     * 
     * @param time  the date/time (<code>null</code> not permitted).
     */
    public RelativeDateFormat(Date time) {
        this(time.getTime());
    }
    
    /**
     * Creates a new instance.
     * 
     * @param baseMillis  the time zone (<code>null</code> not permitted).
     */
    public RelativeDateFormat(long baseMillis) {
        super();        
        this.baseMillis = baseMillis;
        this.showZeroDays = false;
        this.dayFormatter = NumberFormat.getInstance();
        this.daySuffix = "d";
        this.hourSuffix = "h";
        this.minuteSuffix = "m";
        this.secondFormatter = NumberFormat.getNumberInstance();
        this.secondFormatter.setMaximumFractionDigits(3);
        this.secondFormatter.setMinimumFractionDigits(3);
        this.secondSuffix = "s";

        // we don't use the calendar or numberFormat fields, but equals(Object) 
        // is failing without them being non-null
        this.calendar = new GregorianCalendar();
        this.numberFormat = new DecimalFormat("0");    
    }
    
    /**
     * Returns the base date/time used to calculate the elapsed time for 
     * display.
     * 
     * @return The base date/time in milliseconds since 1-Jan-1970.
     * 
     * @see #setBaseMillis(long)
     */
    public long getBaseMillis() {
        return this.baseMillis;
    }
    
    /**
     * Sets the base date/time used to calculate the elapsed time for display.  
     * This should be specified in milliseconds using the same encoding as
     * <code>java.util.Date</code>.
     * 
     * @param baseMillis  the base date/time in milliseconds.
     * 
     * @see #getBaseMillis()
     */
    public void setBaseMillis(long baseMillis) {
        this.baseMillis = baseMillis;
    }
    
    /**
     * Returns the flag that controls whether or not zero day counts are 
     * shown in the formatted output.
     * 
     * @return The flag.
     * 
     * @see #setShowZeroDays(boolean)
     */
    public boolean getShowZeroDays() {
        return this.showZeroDays;
    }
    
    /**
     * Sets the flag that controls whether or not zero day counts are shown
     * in the formatted output.
     * 
     * @param show  the flag.
     * 
     * @see #getShowZeroDays()
     */
    public void setShowZeroDays(boolean show) {
        this.showZeroDays = show;
    }
    
    /**
     * Returns the string that is appended to the day count.
     * 
     * @return The string.
     * 
     * @see #setDaySuffix(String)
     */
    public String getDaySuffix() {
        return this.daySuffix;
    }
    
    /**
     * Sets the string that is appended to the day count.
     * 
     * @param suffix  the suffix (<code>null</code> not permitted).
     * 
     * @see #getDaySuffix()
     */
    public void setDaySuffix(String suffix) {
        if (suffix == null) {
            throw new IllegalArgumentException("Null 'suffix' argument.");
        }
        this.daySuffix = suffix;
    }

    /**
     * Returns the string that is appended to the hour count.
     * 
     * @return The string.
     * 
     * @see #setHourSuffix(String)
     */
    public String getHourSuffix() {
        return this.hourSuffix;
    }
    
    /**
     * Sets the string that is appended to the hour count.
     * 
     * @param suffix  the suffix (<code>null</code> not permitted).
     * 
     * @see #getHourSuffix()
     */
    public void setHourSuffix(String suffix) {
        if (suffix == null) {
            throw new IllegalArgumentException("Null 'suffix' argument.");
        }
        this.hourSuffix = suffix;
    }

    /**
     * Returns the string that is appended to the minute count.
     * 
     * @return The string.
     * 
     * @see #setMinuteSuffix(String)
     */
    public String getMinuteSuffix() {
        return this.minuteSuffix;
    }
    
    /**
     * Sets the string that is appended to the minute count.
     * 
     * @param suffix  the suffix (<code>null</code> not permitted).
     * 
     * @see #getMinuteSuffix()
     */
    public void setMinuteSuffix(String suffix) {
        if (suffix == null) {
            throw new IllegalArgumentException("Null 'suffix' argument.");
        }
        this.minuteSuffix = suffix;
    }

    /**
     * Returns the string that is appended to the second count.
     * 
     * @return The string.
     * 
     * @see #setSecondSuffix(String)
     */
    public String getSecondSuffix() {
        return this.secondSuffix;
    }
    
    /**
     * Sets the string that is appended to the second count.
     * 
     * @param suffix  the suffix (<code>null</code> not permitted).
     * 
     * @see #getSecondSuffix()
     */
    public void setSecondSuffix(String suffix) {
        if (suffix == null) {
            throw new IllegalArgumentException("Null 'suffix' argument.");
        }
        this.secondSuffix = suffix;
    }
    
    /**
     * Sets the formatter for the seconds and milliseconds.
     * 
     * @param formatter  the formatter (<code>null</code> not permitted).
     */
    public void setSecondFormatter(NumberFormat formatter) {
        if (formatter == null) {
            throw new IllegalArgumentException("Null 'formatter' argument.");
        }
        this.secondFormatter = formatter;
    }

    /**
     * Formats the given date as the amount of elapsed time (relative to the
     * base date specified in the constructor).
     * 
     * @param date  the date.
     * @param toAppendTo  the string buffer.
     * @param fieldPosition  the field position.
     * 
     * @return The formatted date.
     */
    public StringBuffer format(Date date, StringBuffer toAppendTo,
                               FieldPosition fieldPosition) {
        long currentMillis = date.getTime();
        long elapsed = currentMillis - this.baseMillis;
        
        long days = elapsed / MILLISECONDS_IN_ONE_DAY;
        elapsed = elapsed - (days * MILLISECONDS_IN_ONE_DAY);
        long hours = elapsed / MILLISECONDS_IN_ONE_HOUR;
        elapsed = elapsed - (hours * MILLISECONDS_IN_ONE_HOUR);
        long minutes = elapsed / 60000L;
        elapsed = elapsed - (minutes * 60000L);
        double seconds = elapsed / 1000.0;
        if (days != 0 || this.showZeroDays) {
            toAppendTo.append(this.dayFormatter.format(days) + getDaySuffix());
        }
        toAppendTo.append(String.valueOf(hours) + getHourSuffix());
        toAppendTo.append(String.valueOf(minutes) + getMinuteSuffix());
        toAppendTo.append(this.secondFormatter.format(seconds) 
                + getSecondSuffix());
        return toAppendTo;   
    }

    /**
     * Parses the given string (not implemented).
     * 
     * @param source  the date string.
     * @param pos  the parse position.
     * 
     * @return <code>null</code>, as this method has not been implemented.
     */
    public Date parse(String source, ParsePosition pos) {
        return null;   
    }

    /**
     * Tests this formatter for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RelativeDateFormat)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        RelativeDateFormat that = (RelativeDateFormat) obj;
        if (this.baseMillis != that.baseMillis) {
            return false;
        }
        if (this.showZeroDays != that.showZeroDays) {
            return false;
        }
        if (!this.daySuffix.equals(that.daySuffix)) {
            return false;
        }
        if (!this.hourSuffix.equals(that.hourSuffix)) {
            return false;
        }
        if (!this.minuteSuffix.equals(that.minuteSuffix)) {
            return false;
        }
        if (!this.secondSuffix.equals(that.secondSuffix)) {
            return false;
        }
        if (!this.secondFormatter.equals(that.secondFormatter)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a hash code for this instance.
     * 
     * @return A hash code.
     */
    public int hashCode() {
        int result = 193;
        result = 37 * result 
                + (int) (this.baseMillis ^ (this.baseMillis >>> 32));
        result = 37 * result + this.daySuffix.hashCode();
        result = 37 * result + this.hourSuffix.hashCode();
        result = 37 * result + this.minuteSuffix.hashCode();
        result = 37 * result + this.secondSuffix.hashCode();
        result = 37 * result + this.secondFormatter.hashCode();
        return result;
    }

    /**
     * Returns a clone of this instance.
     * 
     * @return A clone.
     */
    public Object clone() {
        RelativeDateFormat clone = (RelativeDateFormat) super.clone();
        clone.dayFormatter = (NumberFormat) this.dayFormatter.clone();
        clone.secondFormatter = (NumberFormat) this.secondFormatter.clone();
        return clone;
    }
    
    /**
     * Some test code.
     * 
     * @param args  ignored.
     */
    public static void main(String[] args) {
        GregorianCalendar c0 = new GregorianCalendar(2006, 10, 1, 0, 0, 0);
        GregorianCalendar c1 = new GregorianCalendar(2006, 10, 1, 11, 37, 43);
        c1.set(Calendar.MILLISECOND, 123);
        
        System.out.println("Default: ");
        RelativeDateFormat rdf = new RelativeDateFormat(c0.getTimeInMillis());
        System.out.println(rdf.format(c1.getTime()));
        System.out.println();
        
        System.out.println("Hide milliseconds: ");
        rdf.setSecondFormatter(new DecimalFormat("0"));
        System.out.println(rdf.format(c1.getTime()));        
        System.out.println();

        System.out.println("Show zero day output: ");
        rdf.setShowZeroDays(true);
        System.out.println(rdf.format(c1.getTime()));
        System.out.println();
        
        System.out.println("Alternative suffixes: ");
        rdf.setShowZeroDays(false);
        rdf.setDaySuffix(":");
        rdf.setHourSuffix(":");
        rdf.setMinuteSuffix(":");
        rdf.setSecondSuffix("");
        System.out.println(rdf.format(c1.getTime()));
        System.out.println();
    }
}

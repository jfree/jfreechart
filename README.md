JFreeChart
==========

Version 1.5.1, 29 October 2020.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.jfree/jfreechart/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.jfree/jfreechart)

Overview
--------
JFreeChart is a comprehensive free chart library for the Java(tm) platform that 
can be used on the client-side (JavaFX and Swing) or the server side (with
export to multiple formats including SVG, PNG and PDF).

![JFreeChart sample](http://jfree.org/jfreechart/images/coffee_prices.png)

The home page for the project is:

http://www.jfree.org/jfreechart

JFreeChart requires JDK 8 or later.  If JavaFX support is required, you
need to also include the JFreeChart-FX extensions:

https://github.com/jfree/jfreechart-fx

The library is licensed under the terms of the GNU Lesser General Public 
License (LGPL) version 2.1 or later.


Using JFreeChart
----------------
To use JFreeChart in your projects, add the following dependency to your build tool:

    <dependency>
        <groupId>org.jfree</groupId>
        <artifactId>jfreechart</artifactId>
        <version>1.5.1</version>
    </dependency>


Building JFreeChart
-------------------
You can build JFreeChart using Maven.  The build requires JDK 8 or later.

#### Maven
Using Maven you can build JFreeChart using the following command (issued from the root directory of the project):

    mvn clean install


Demos
-----
A small set of demo applications can be found in the following projects here
at GitHub:

* [JFree-Demos](https://github.com/jfree/jfree-demos "JFree-Demos Project Page at GitHub")
* [JFree-FXDemos](https://github.com/jfree/jfree-fxdemos "JFree-FXDemos Project Page at GitHub")


History
-------

##### Version 1.5.1 (29 October 2020)
- add DeviationStepRenderer (PR #173)
- modify buffer in ChartPanel to handle high DPI displays (bug #170);
- fix for exception on extreme zoom on NumberAxis (bug #64);
- fix for LayeredBarRenderer (bug #169);
- add Catalan translations (PR #117);
- add automatic module name `org.jfree.jfreechart`;
- migrate to JUnit 5;
- raise minimum requirement to Java 8 or later.

##### Version 1.5.0 (5 November 2017)
- all JavaFX classes moved to a separate project;
- added cleaner method to create histograms in `ChartFactory`;
- JFreeSVG updated to version 3.2;
- OrsonPDF updated to version 1.7;
- JCommon removed as a dependency, and required classes incorporated directly;
- pull request #4 improvements to `XYStepRenderer`;
- bug #36 fix for crosshairs with multiple datasets / axes;
- bug #25 fix for `DateAxis.previousStandardDate()` method;
- bug #19 fix for default time zone in `SegmentedDateAxis`;
- SourceForge #1147 improve performance of `CategoryPlot` mapping datasets to axes;
- moved SWT code out into separate projects;
- moved demo programs to a separate project;
- dropped the Ant build;

##### Version 1.0.19 (31-Jul-2014)
- fixed clipping issues for combined plots in JavaFX;
- fixed a memory leak in the new JavaFX `ChartCanvas` class;
- `CombinedDomainXYPlot` and `CombinedRangeXYPlot` now take into account the pannable flags in the subplots;
- `FastScatterPlot` panning direction is corrected;
- added rendering hints to sharpen gridlines and borders in most output formats;        
- JFreeSVG updated to version 2.0;
- included a preview of JSFreeChart, a 2D chart library written in JavaScript that is conceptually similar to JFreeChart but runs directly in a web browser.

##### Version 1.0.18 (3-Jul-2014)
- added JavaFX support via `FXGraphics2D`;
- improved `LogAxis` labelling;
- improved numeric tick labelling;
- center text support in `RingPlot`;
- `stepPoint` attribute in the `XYStepAreaRenderer`;
- other minor improvements and bug fixes.

##### Version 1.0.17 (22-Nov-2013)
- Enhanced `XYSplineRenderer` with new area fill (contributed by Klaus Rheinwald);
- added a notify flag to all datasets that extend `AbstractDataset`;
- extended `TimeSeriesCollection` to validate `TimeSeries` keys for uniqueness;
- added a new `DirectionalGradientPaintTransformer` (by Peter Kolb);
- updated `OHLCSeries`;
- added `HMSNumberFormat`;
- updated JCommon to version 1.0.21 (includes rotated text improvements) and fixed some minor bugs.

###### Bug Fixes
- 977  : Multithreading issue with `DateAxis`;
- 1084 : `BorderArrangement.add()` possible `ClassCastException`;
- 1099 : `XYSeriesCollection.removeSeries(int)` does not deregister listener;
- 1109 : `WaterfallBarRenderer` uses wrong color for diff 0.


##### Version 1.0.16 (13-Sep-2013)

*** THIS RELEASE REQUIRES JDK/JRE 1.6.0 OR LATER. ***

- Provided subscript/superscript support for axis labels (via `AttributedString`);
- new axis label positioning options;
- simplified `ChartFactory` methods;
- added new methods to `DatasetUtilities` to interpolate y-values in `XYDatasets`;
- added URLs to labels on `CategoryAxis`;
- seamless integration with JFreeSVG (http://www.jfree.org/jfreesvg/) and OrsonPDF 
(http://www.object-refinery.com/pdf/);
- improved the consistency of the `SWTGraphics2D` implementation;  

All the JUnit tests have been upgraded to JUnit 4.

###### Bug Fixes

- 1107 : Fixed TimeZone issue in `PeriodAxis`;

Also fixed a line drawing issue with the `StackedXYAreaRenderer`, and a memory 
leak in the SWT `ChartComposite` class.


##### Version 1.0.15 (4-Jul-2013)
- Added support for non-visible series in `XYBarRenderer`;
- minor gridlines in `PolarPlot`;
- legend item ordering;
- chart editor enhancements;
- updates to `StandardDialScale`;
- localisation files for Japanese;
- refactored parameter checks.  

This release also fixes a minor security flaw in the `DisplayChart` class, detected and reported by OSI Security:

http://www.osisecurity.com.au/advisories/jfreechart-path-disclosure

###### Patches

- 3500621 : `LegendTitle` order attribute (by Simon Kaczor);
- 3463807 : `ChartComposite` does not dispose popup (by Sebastiao Correia);
- 3204823 : `PaintAlpha` for 3D effects (by Dave Law);

###### Bug Fixes

- 3561093 : Rendering anomaly for `XYPlots`;
- 3555275 : `ValueAxis.reserveSpace()` problem for axes with fixed dimension;
- 3521736 : `DeviationRenderer` optimisation (by Milan Ramaiya);
- 3514487 : `SWTGraphics2D` `get/setStroke()` problem;
- 3508799 : `DefaultPolarItemRenderer` does not populate `seriesKey` in `LegendItem`;
- 3482106 : Missing text in `SWTGraphics2D` (by Kevin Xu);
- 3484408 : Maven fixes (Martin Hoeller);
- 3484403 : `DateAxis` endless loop (by Martin Hoeller);
- 3446965 : `TimeSeries` calculates range incorrectly in `addOrUpdate()`;
- 3445507 : `TimeSeriesCollection.findRangeBounds()` regression;
- 3425881 : `XYDifferenceRenderer` fix (by Patrick Schlott/Christoph Schroeder);
- 2963199 : SWT print job (by Jonas Rüttimann);
- 2879650 : Path disclosure vulnerability in `DisplayChart` servlet;

Also fixed a rendering issue for polar charts using an inverted axis.

##### Version 1.0.14 (20-Nov-2011)
This release contains:

- support for multiple and logarithmic axes with `PolarPlot`;
- optional drop-shadows in plot rendering;
- fitting polynomial functions to a data series;
- some performance improvements in the `TimeSeriesCollection` class;
- mouse wheel rotation of pie charts;
- improved Maven support.

###### Patches

- 3435734 : Fix lines overlapping item labels (by Martin Hoeller);
- 3421088 : Bugfix for misalignment in `BoxAndWhiskerRenderer`;
- 2952086 : Enhancement for finding bounds in `XYZDatasets`;
- 2954302 : `CategoryPointerAnnotation` line calculation;
- 2902842 : `HistogramDataset.addSeries()` fires change change event (by Thomas A Caswell);
- 2868608 : Whisker width attribute for `BoxAndWhiskerRenderer` (by Peter Becker);
- 2868585 : Added `useOutlinePaint` flag for `BoxAndWhiskerRenderer` (by Peter Becker);
- 2850344 : `PolarPlot` enhancements (by Martin Hoeller);
- 2795746 : Support for polynomial regression;
- 2791407 : Fixes for `findRangeBounds()` in various renderers.

###### Bug Fixes

- 3440237 : Shadows always visible;
- 3432721 : `PolarPlot` doesn't work with logarithmic axis;
- 3433405 : `LineChart3D` - Problem with Item Labels;
- 3429707 : `LogAxis` endless loop;
- 3428870 : Missing argument check in `TextAnnotation`;
- 3418287 : `RelativeDateFormatTest.java` is locale dependent;
- 3353913 : Localisation fixes for `ChartPanel`, `CompassPlot` and `PiePlot3D`;
- 3237879 : `RingPlot` should respect `getSectionOutlineVisible()`;
- 3190615 : Added missing `clear()` method in `CategoryTableXYDataset`;
- 3165708 : `PolarChartPanel` localisation fix;
- 3072674 : Bad handling of `NaN` in `DefaultStatisticalCategoryDataset`;
- 3035289 : `StackedXYBarRenderer` should respect series/item visible flags;
- 3026341 : Check for null in `getDomain/RangeBounds()` for `XYShapeRenderer`;
- 2947660 : `AbstractCategoryRenderer` fix null check in `getLegendItems()`;
- 2946521 : `StandardDialScale` check `majorTickIncrement` argument;
- 2876406 : `TimeTableXYDataset` should support `Comparable` for series keys;
- 2868557 : `BoxAndWhiskerRenderer` should fire change event in `setMedianVisible()`;
- 2849731 : For `IntervalCategoryDataset` and `IntervalXYDataset`, fix `iterateRangeBounds()` in `DatasetUtilities`;
- 2840132 : `AbstractXYItemRenderer` `drawAnnotations` doesn't set renderer index;
- 2810220 : Offset problem in `StatisticalBarRenderer`;
- 2802014 : Dial value border too small;
- 2781844 : `XYPointerAnnotation` arrow drawing;
- 1937486 : `AreaRenderer` doesn't respect `AreaRendererEndType.LEVEL`;

Also fixed:
- use of simple label offset in `PiePlot`;
- cached `minY` and `maxY` in `TimeSeries.createCopy()`;
- scaling issues for charts copied to the clipboard;
- use of timezone in `TimeTableXYDataset` constructor;
- duplicate series names in `XYSeriesCollection`;
- `HistogramDataset` fires a change event in `addSeries()`;
- check visibility of main chart title before drawing it;
- fixed serialization of `PowerFunction2D`, `NormalDistributionFunction2D`, and `LineFunction2D`;
- item label positioning for the `AreaRenderer` class when the plot has an horizontal orientation.

##### Version 1.0.13 (17-Apr-2009)

> SPECIAL NOTICE:  There will be a birds-of-a-feather session for JFreeChart at this year's JavaOne conference in San Francisco.  The session is scheduled for 6.45pm to 7.35pm on Wednesday 3 June.                        

This release contains:

- updates to the `ChartPanel` class to support copying charts to the clipboard, 
  panning and mouse-wheel zooming, and an overlay mechanism that supports
  crosshairs;
- enhancements to the auto-range calculation for axes, providing the ability
  to use subranges only and also to skip hidden series;
- updates for many of the `CategoryItemRenderer` implementations to ensure that
  they respect the `seriesVisible` flags;
- an improvement to the `TimeSeries` class so that it is no longer necessary to
  specify the time period type in the constructor;
- a new `SamplingXYLineRenderer` for improving the performance of time series
  charts with large datasets;
- the `XYSeries/XYSeriesCollection` classes now cache the minimum and maximum
  data values to improve the performance of charts with large datasets;
- entities are now created for the chart, data area and axes, allowing mouse
  clicks to be detected for these regions;
- added a bar alignment factor to the `XYBarRenderer` class;
- a new `errorIndicatorStroke` field for the `StatisticalLineAndShapeRenderer` and `XYErrorRenderer` classes;
- added a new `HeatMapDataset` interface, `DefaultHeatMapDataset` implementation,
  and a `HeatMapUtilities` class to make it easier to create heat map charts;
- there is a new flag to allow an `XYDataImageAnnotation` to be included in the
  automatic range calculation for the axes;
- additional attributes in the `XYTextAnnotation` class;
- added a `sampleFunction2DToSeries()` method to the `DatasetUtilities` class;
- some changes to the `ChartPanel` class that help to work around a regression in
  JRE 1.6.0_10 relating to drawing in XOR mode.  Regarding this final point:
     * the default value for the `useBuffer` flag has changed to true, which means
       that all charts will, by default, be rendered into an off-screen image
       before being displayed in the `ChartPanel`;
     * the zoom rectangle is drawn using XOR mode *only* when the `useBuffer`
       flag has been set to false.
  For most usage, this should improve performance (but at the cost of using more
  memory for each `ChartPanel` used in your application);

###### Bug Fixes

- 2690293 : Problem with Javascript escape characters;
- 2617557 : `StandardDialScale` ignored `tickLabelPaint`;
- 2612649 : `Stroke` selection in plot property editor;
- 2583891 : `SWTGraphics2D.fillPolygon()` not implemented;
- 2564636 : `Month` constructor ignores Locale;
- 2502355 : `ChartPanel` sending multiple events;
- 2490803 : `PeriodAxis.setRange()` method doesn't take into account that the axis
          displays whole periods;

In addition, a bug in the `SlidingCategoryDataset` class has been fixed, the
correct outline paint is now used by `GradientXYBarPainter`, a new method
has been added to the `ImageMapUtilities` class to escape special characters
in Javascript strings to avoid problems with the OverLIB and DynamicDrive
tooltips, and there are some important fixes in the `LogAxis` class.

This release passes 2110 JUnit tests (0 failures) on JRE 1.6.0_12.


##### Version 1.0.12 (31-Dec-2008)

This release adds 
- support for minor tick marks;
- mapping datasets to more than one axis;
- an important fix for the `XYSeries` class (relating to the `addOrUpdate()` method);
- plus numerous other bug fixes.

This release passes 1996 JUnit test (0 failures) on JRE 1.6.0_10.

###### API Adjustments
- `CategoryPlot` : added `mapDatasetToDomainAxes()` and `mapDatasetToRangeAxes()` methods;
- `Month` : added a new constructor `Month(Date, TimeZone, Locale)` and deprecated `Month(Date, TimeZone)`;
- `Quarter` : added a new constructor `Quarter(Date, TimeZone, Locale)` and deprecated `Quarter(Date, TimeZone)`;
- `XYPlot` : added `mapDatasetToDomainAxes()` and `mapDatasetToRangeAxes()` methods;
- `Year` : added a new constructor `Year(Date, TimeZone, Locale)` and deprecated `Year(Date, TimeZone)`;

###### Bug Fixes
- 2471906 : `XYAreaRenderer` with dashed outline - performance problem;
- 2452078 : `StackedAreaChart` has gaps;
- 2275695 : `NullPointerException` for `SubCategoryAxis` on plot with null dataset;
- 2221495 : `XYLineAnnotation` with dashed stroke;
- 2216511 : `SWTBarChartDemo1` throws `RuntimeException`;
- 2201869 : `DateAxis` tick label position error;
- 2121818 : Label link lines for very thin `RingPlot`;
- 2113627 : `XYStepRenderer` item labels;
- 1955483 : `XYSeries.addOrUpdate()` problem.

Also fixed `StackedXYBarRenderer` which was ignoring the `shadowsVisible` attribute.


##### Version 1.0.11 (18-Sep-2008)

This release features:
- a new chart theming mechanism to allow charts to be restyled conveniently;
- a new `BarPainter` mechanism to enhance the appearance of bar charts;
- a new `XYShapeRenderer` class;
- a scaling facility for the `XYDrawableAnnotation` for drawing images within specific data coordinates;
- some new classes (`XYTaskDataset`, `XYDataImageAnnotation` and `XYTitleAnnotation`);
- a modification to the `Year` class to support an extended range; 
- various bug fixes and API improvements.  

There is an important bug fix for the `StackedBarRenderer3D` class (see bug 2031407).

This release passes 1,961 JUnit tests (0 failures) on JRE 1.6.0_07.

###### API Adjustments
- `AbstractRenderer` - added `clearSeriesPaints()` and `clearSeriesStrokes()` methods;
- `BarRenderer` - added `shadowPaint` attribute;
- `CategoryAxis` - added `getCategoryMiddle()` method;
- `CategoryPlot` - added `getRendererCount()` method;
- `ChartFactory` - added `get/setChartTheme()` methods;
- `ChartPanel` - increased default maximum drawing width and height;
- `ChartTheme` - new interface;
- `ChartUtilities` - added `applyCurrentTheme()` method;
- `CompositeTitle` - added `backgroundPaint` attribute;
- `GradientBarPainter` - new class;
- `LegendTitle` - added `getWrapper()` method;
- `OHLCSeriesCollection` - added `xPosition` attribute;
- `PaintScaleLegend` - new subdivisions field;
- `PiePlot` - added `autoPopulate` flags, and methods to clear section attributes;
- `Plot` - added `setDrawingSupplier()` method;
- `RegularTimePeriod` - the `DEFAULT_TIME_ZONE` field has been deprecated in this release;
- `RelativeDateFormat` - added methods to control formatting of hours and minutes - see patch 2033092;
- `StandardChartTheme` - new class;
- `XYItemRendererState` - new methods;
- `XYPlot` - added `getRendererCount()` method;
- `XYShapeRenderer` - new class;
- `XYTaskDataset` - new class.

###### Patches
- 1997549 : Status calls to `XYItemRendererState` [Ulrich Voigt];
- 2006826 : `CompositeTitle` drawing fix;
- 2033092 : Additional formatters for `RelativeDateFormat` [Cole Markham];

###### Bug Fixes
- 1994355 : `ChartComposite` listener type;
- 2031407 : Incorrect rendering in `StackedBarRenderer3D`;
- 2033721 : `WaferMapRenderer`;
- 2051168 : No key in `LegendItemEntity` for pie charts;

Also fixed drawing of alternate grid bands in `SymbolAxis`, the `totalWeight` 
calculation in the `CombinedXXXPlot` classes, a `NullPointerException` in the
`XYPlot` class when drawing quadrants, outline visibility in the 
`CategoryPlot` class, and auto-range calculations with `XYBarRenderer`.

##### Version 1.0.10 (8-Jun-2008)

This release contains various bug fixes and minor enhancements to JFreeChart.

- PiePlot labelling has been enhanced (new curve options, and more robust bounds checking);
- The BoxAndWhiskerRenderer now has a maximumBarWidth attribute;
- the XYStepRenderer has a new stepPoint attribute;
- The RelativeDateFormat class has new options;
- There are new dataset classes (SlidingCategoryDataset and SlidingGanttDataset) that permit a subset of categories to be plotted, and allow charts based on these datasets to simulate scrolling.  
- There is a new ShortTextTitle class.

This release passes 1,929 JUnit tests (0 failures) on JRE 1.6.0_03.

###### API Adjustments:

- BoxAndWhiskerRenderer - added maximumBarWidth attribute (see patch 1866446);
- ChartPanel - the zoomPoint attribute has been changed from Point to Point2D;
- DatasetUtilities - iterateCategoryRangeBounds() is deprecated, the method has been renamed iterateRangeBounds(CategoryDataset) for consistency;
- DefaultKeyedValue - the constructor now prevents a null key;
- LogFormat - now has a 'powerLabel' attribute;
- ShortTextTitle - a new title class;
- SlidingCategoryDataset - new class;
- SlidingGanttDataset - new class;
- TimeSeriesCollection - getSeries(String) changed to getSeries(Comparable);
- XIntervalSeriesCollection - added series removal methods;
- YIntervalSeriesCollection - added series removal methods;
- XYIntervalSeriesCollection - added series removal methods;

`PublicCloneable` is now implemented by a number of classes that didn't previously implement the interface - this should improve the reliability of chart cloning.

###### Patches

- 1943021 : Fix for MultiplePiePlot [Brian Cabana];
- 1925366 : Speed improvement for DatasetUtilities [Rafal Skalny];
- 1918209 : LogAxis createTickLabel() changed from private to protected [Andrew Mickish];
- 1914411 : Simplification of plot event notification [Richard West];
- 1913751 : XYPlot and CategoryPlot addMarker() methods with optional notification [Richard West];
- 1902418 : Bug fix for LogAxis vertical labels [Andrew Mickish];
- 1901599 : Fixes for XYTitleAnnotation [Andrew Mickish];
- 1891849 : New curve option for pie chart label links [Martin Hilpert];
- 1874890 : Added step point to XYStepRenderer [Ulrich Voigt];
- 1873328 : Enhancements to RelativeDateFormat [Michael Siemer];
- 1871902 : PolarPlot now has angleTickUnit attribute [Martin Hoeller];
- 1868745 : Fix label anchor points on LogAxis [Andrew Mickish];
- 1866446 : Added maximumBarWidth to BoxAndWhiskerRenderer [Rob Van der Sanden];

###### Bug Fixes

- 1932146 - PeriodAxis.setRange() doesn't notify listeners;
- 1927239 - Fix calculation of cumulative range;
- 1926517 - Bugs in data range calculation for combined plots;
- 1920854 - PiePlot3D labels drawn multiple times;
- 1897580 - Fix for DefaultIntervalCategoryDataset;
- 1892419 - Wrong default for minor tick count in LogAxis;
- 1880114 - VectorRenderer doesn't work for horizontal plot orientation;
- 1873160 - DialPlot clipping issues;
- 1868521 - Problem saving charts to JPEG format;
- 1864222 - Error on TimeSeries createCopy() method;

The `DatasetUtilities.sampleFunction2D()` has been changed to sample the correct 
number of points - you should check any code that calls this method.  The
`XYBlockRenderer` class now generates entities.  Bugs in the `removeDomainMarker()`
and `removeRangeMarker()` methods in the `CategoryPlot` and `XYPlot` classes have 
been fixed.  A bug in the `TimePeriodValues` range calculation has been fixed.
Fixes were applied to the `clone()` methods in the `TaskSeries` and 
`TaskSeriesCollection` classes.

###### New Experimental Features

Two new classes `CombinedCategoryPlot` and `CombinedXYPlot` have been added to the
'experimental' source tree - these were contributed by Richard West (see
patch 1924543).

##### Version 1.0.9 (4-Jan-2008)

This release contains an important security patch as well as various bug fixes 
and minor enhancements.  Regarding the security patch, please see the 
following advisory:

http://www.rapid7.com/advisories/R7-0031.jsp

Note that the fix incorporated in the special JFreeChart 1.0.8a release was
flawed in that it broke the URLs in the HTML image maps generated by 
JFreeChart.  Further amendments have been made in this release to fix this 
problem.

###### API Adjustments

A number of classes have new methods.  Nothing has been removed or deprecated:

- HashUtilities - added hashCode() methods for BooleanList, PaintList and StrokeList;
- ImageMapUtilities - added htmlEscape(String);
- IntervalMarker - added new constructor;
- Range - added intersects(Range) and scale(Range, double);
- TextTitle - added protected methods arrangeNN(), arrangeFN() and arrangeRN();
- XYDataItem - added getXValue() and getYValue() methods;
- XYPlot - added setFixedDomainAxisSpace(AxisSpace, boolean) and setFixedRangeAxisSpace(AxisSpace, boolean);
- XYSeriesCollection - added getSeries(Comparable) method.

###### Bug Fixes

- 1852525 - CandlestickChart.getCategoryPlot() - ClassCastException;
- 1851416 - testGetFirstMillisecondWithTimeZone fails in 1.0.8a;
- 1849333 - 1.0.8a breaks URLs in HTML image maps;
- 1848961 - GroupedStackedBarRenderer works only for primary dataset;
- 1846063 - Endless loop in paint of XYPlot;
- 1840139 - Cross-site scripting vulnerabilities in image map code;
- 1837979 - Background image not shown with SWT;
- 1460195 - ChartEntity.getImageMapAreaTag() needs nohref;
- 1400917 - OverLIBToolTipTagFragmentGenerator not escaping single quote;
- 1363043 - Escape Image Map Data;
- 1178601 - AbstractRenderer.hashcode() method returns the same value;

In addition, a bug in the constructor for the Week class has been fixed.  

##### Version 1.0.8 (23-Nov-2007)
This release is primarily a bug fix release:
- a problem with pie chart labeling;
- a regression in the `DefaultCategoryDataset` class (and underlying `KeyedValues2D` class);
- a cloning bug in the `TimeSeries` class.  

In addition:
- the `StatisticalBarRenderer` class has a new `errorIndicatorStroke` field and has been updated to support gradients;
- the `StandardDialScale` has had some missing accessor methods implemented;
- an override field in the `StandardXYItemRenderer` class has been deprecated;
- some warnings reported by FindBugs 1.3.0 have been addressed.

##### Version 1.0.7 (14-Nov-2007)
This release features
- new classes `DialPlot` and `LogAxis` (previously in experimental);
- initial support for minor tick units;
- a new anchored zooming option for the `ChartPanel` class;
- optional simple labeling on pie charts;
- improvements to the "statistical" datasets and underlying data structures;
- and numerous bug fixes.

###### API Adjustments

- `CategoryAxis` - added `getCategorySeriesMiddle()` method;
- `CategoryPlot` - added methods to remove markers;
- `ChartPanel` - added `defaultDirectoryForSaveAs` attribute;
- `DialPlot` - new class, an alternative to `MeterPlot`;
- `LogAxis` - new class, an alternative to `LogarithmicAxis`;
- `NumberTick` - new constructor that allows specification of the tick type;
- `NumberTickUnit` - new constructor to specify the minor tick count;
- `SymbolAxis` - new methods `get/setGridBandAlternatePaint()`;
- `TickType` - new class;
- `TickUnit` - added `minorTickCount` attribute;
- `ValueTick` - added `tickType` attribute;
- `StandardPieSectionLabelGenerator` - new constructors accepting a Locale;
- `StandardPieToolTipGenerator` - likewise;
- `CategoryPlot` - added `getRangeAxisIndex()`, `zoomDomainAxes()` and `zoomRangeAxes()` methods;
- `FastScatterPlot` - added new zooming methods;
- `PiePlot` - new attributes to support simple labeling;
- `PlotUtilities` - new class;
- `PolarPlot` - added new zooming methods;
- `ThermometerPlot` - likewise;
- `XYPlot` - added methods to remove markers (patch 1823697--same as for `CategoryPlot`), and added new zooming methods; 
- `Zoomable` - added new zooming methods to this interface;
- `LineAndShapeRenderer` - added `useSeriesOffset` and `itemMargin` attributes;
- `MinMaxCategoryRenderer` - implemented `equals()`;
- `XYSplineAndShapeRenderer` - new class;
- `LogFormat` - new class;
- `ChartFactory` - new pie and ring chart creation methods that accept a `Locale`;
- `ChartPanel` - added `zoomAroundAnchor` attribute;
- `Series` - added `isEmpty()` method;
- `BoxAndWhiskerItem` - new convenience constructor;
- `DefaultBoxAndWhiskerCategoryDataset` - new remove methods;
- `DefaultStatisticalCategoryDataset` - likewise;
- `MeanAndStandardDeviation` - added new value accessor methods;
- `TimeTableXYDataset` - added `clear()` method;
- `Week` - added new constructor;
- `KeyedObjects` - added `insertValue()` and `clear()` methods;
- `KeyedObjects2D` - added `clear()` method.

###### Patches

- 1823724 - updated `XYDifferenceRenderer` to support item labels;
- 1827829 - fixed possible `NullPointerException` in `XYBarRenderer`;

###### Bug Fixes

- 1767315 - `GrayPaintScale.getPaint()` uses wrong value;
- 1775452 - Inverted `XYBarRenderer` does not render margins correctly;
- 1802195 - `Marker.listenerList` serializable;
- 1779941 - `StatisticalBarRenderer` NPE;
- 1766646 - `XYBlockRenderer` can't handle empty datasets;
- 1763413 - `PeriodAxis` labels fail to display with setInverted;
- 1737953 - Zoom doesn't work on `LogAxis` (Demo1);
- 1749124 - `JFreeChart` not added as `TitleChangeListener`; 

##### Version 1.0.6 (15-Jun-2007)
This release features:
- a new `VectorRenderer` (previously in experimental);
- a generalised `XYDifferenceRenderer`;
- better support for hotspots on legend items;
- improved performance for time series charts displaying subsets of data;
- support for `GradientPaint` in plot backgrounds;
- plus the usual slew of bug fixes and minor feature additions.

###### API Adjustments

- `CategoryItemEntity` - replaced row and column index attributes with row and column key attributes;
- `CategoryItemRenderer` - numerous series override settings have been deprecated;
- `DefaultPieDataset` - added `insertValues()` method;
- `HexNumberFormat` - new class;
- `LegendItem` - added dataset and seriesKey attributes;
- `Plot` - added new `fillBackground()` method to support `GradientPaint`, and added `is/setOutlineVisible()` methods;
- `QuarterDateFormat` - added `GREEK_QUARTERS` field plus a new constructor;
- `SimpleHistogramDataset` - added `clearObservations()` and `removeAllBins()` methods;
- `TimeSeriesCollection` - added `indexOf()` method;
- `URLUtilities` - new class;
- `XYItemRenderer` - numerous series override settings have been deprecated;
- `XYSeriesCollection` - added indexOf() method.

###### Bug Fixes

- 1735508 - `ClusteredXYBarRenderer` fails with inverted x-axis;
- 1726404 - `ChartComposite` tooltips;
- 1713474 - `StackedBarRenderer3D` doesn't fill shadows;
- 1713401 - `StackedBarRenderer3D` doesn't check `drawBarOutline` flag;
- 1701822 - `DefaultBoxAndWhiskerCategoryDataset` doesn't follow contracts;
- 1698965 - NPE in `CombinedDomainXYPlot`;
- 1690994 - `HideSeriesDemo1` does not work;
- 1690654 - Bug in `removeValue()` of `DefaultKeyedValues2D`;
- 1562701 - `LegendItemEntity` needs dataset index;
- 1486299 - Use `URLEncoder.encode()` for URL generators;

Plus the following bugs that didn't have entries in the database:

- `BarRenderer` - check for series visibility in `getLegendItem()`;
- `ChartPanel` - use correct insets for painting chart buffer to screen, update UI for popup menu if LookAndFeel changes;
- `DateAxis` - fixed boundary cases for `previousStandardDate()` method;
- `LineBorder` - only draw border if area has positive dimensions;
- `JFreeChart` - should register as a listener with the default legend;
- `StandardXYItemRenderer` - fixed a problem where chart entities are created for non-visible items;
- `TimePeriodValuesCollection.getDomainBounds()` now computes the bounds correctly;
- `XYLineAndShapeRenderer` - fixed a problem where chart entities are created for non-visible items;
- `XYLine3DRenderer` - `equals()` implemented, and serialization fixed;
- `XYTitleAnnotation` - fixed `equals()` method;
- various resource usage bugs in the experimental `ChartComposite` class;

##### Version 1.0.5 (23-Mar-2007)
This release features:
- a new `DeviationRenderer` class;
- support for item labels in `StackedXYBarRenderer`;
- tooltips and URLs in the `CategoryStepRenderer`; and 
- many bug fixes.

###### API Adjustments

- `AbstractCategoryItemRenderer` - added `createState()` method;
- `StackedXYBarRenderer` - added `get/setRenderAsPercentages()` methods;
- `XYIntervalSeries` - added `getXLowValue()`, `getXHighValue()`, `getYLowValue()` and `getYHighValue()`;
- `YIntervalSeries` - added `getYLowValue()` and `getYHighValue()` methods;
    
###### Bug Fixes

- 1681777 - `DefaultCategoryDataset` does not clone data;
- 1672552 - Zoom rectangle is lost when the chart is repainted;
- 1671645 - `Crosshair` incorrectly positioned in horizontal orientation;
- 1669302 - Tick labels in vertical symbol axis;
- 1669218 - `CategoryPlot.setDomainAxisLocation()` ignores parameter;
- 1667750 - Clip region not restored in `Spider` and `MeterPlot`;
- 1663380 - OutputStream not closed;
- 1659627 - `IntervalMarker` with `Double.POSITIVE_INFINITY` bound;
- 1647269 - `IntervalMarker` with `Double.MAX_VALUE as upper` bound;
- 1594477 - `XYBarRenderer` does not render bars on `LogarithmicAxis`;
- 1459958 - Log axis zoom function problem;
- 880597 - Zooming `ChartPanel` with log axes;
- 764561 - Dynamic chart zoom buggy.
 
Also fixed numerous bugs in equals(), cloning and serialization implementations.  

##### Version 1.0.4 (9-Feb-2007)

This release features:
- a new `XYBlockRenderer` class;
- URLs for pie chart labels in HTML image maps;
- a new dataset implementation for open-high-low-close charts;
- support for gradient paint in `ClusteredXYBarRenderer`, `StackedXYBarRenderer` and legend graphics;
- a new anchor attribute for `XYImageAnnotation`;
- improvements to the experimental SWT support; plus
- a number of additions to the API for usability; and 
- many bug fixes.

###### API Adjustments

- `DateAxis` - added `get/setTimeZone()` methods;
- `DefaultXYDataset` - now implements `PublicCloneable`;
- `StackedXYAreaRenderer2` - added `get/setRoundXValues()` methods;
- `StandardXYItemLabelGenerator` - added new constructor;
- `StandardXYToolTipGenerator` - added new constructor;
- `XYBarDataset` - added `getUnderlyingDataset()` and `get/setBarWidth()` methods;
- `XYDifferenceRenderer` - added `roundXCoordinates` attribute;
- `XYImageAnnotation` - added an image anchor attribute, a new constructor, and several accessor methods;
- `XYSeries` - added `toArray()` method;

###### Bug Fixes

- 1654215 - `XYPlot` renderer with no corresponding dataset;
- 1652640 - `RangeMarkers` do not update properly;
- 1649686 - `Crosshairs` for `StackedXYAreaRenderer`;
- 1647749 - `IllegalArgumentException` in `SWTAxisEditor`;
- 1644877 - Replacing series data in `DefaultXYDataset`;
- 1644010 - `DateAxis.nextStandardDate()` ignores timezone;
- 1638678 - `DateAxis` code uses the default calendar;
- 1629382 - Tests fail for jfreechart-1.0.3;
- 1624067 - `StandardXYToolTipGenerator` missing constructor;
- 1616583 - Serialize `ChartDeleter`;
- 1612770 - Popup menu in wrong position for SWT `ChartComposite`;
- 1611872 - `Minute.previous()` returns null for minute == 0;
- 1608371 - Tick labels overlap with custom `NumberFormat`;
- 1606205 - Draw shared axis last on combined plots;
- 1605207 - `IntervalMarker` exceeds bounds of data area;
- 1605202 - `SpiderWebPlot` method access;
- 1599652 - Inverted `StackedBar3D` problem;
- 1598394 - `XYBarDataset` hiding its proxied object;
- 1564967 - Crosshairs on `XYDifferenceRenderer`;
- 1245305 - `NullPointerException` in `writeImageMap()`;
- 1086307 - Crosshairs on plots with multiple axes.

Also fixed numerous bugs in `equals()` and `clone()` methods throughout the API.

##### Version 1.0.3 (17-Nov-2006)

This release features:
- several new `IntervalXYDataset` implementations;
- some significant refactoring of the time period classes (to improve performance and correctness);
- modifications to the `PiePlot` class to support reordering of dataset items;
- a new event mechanism to allow updating of markers, plus
- many other enhancements, bug fixes and documentation updates.

A new `DialPlot` implementation has been added to the 'experimental' sources. 
We are looking for people to test this code and provide feedback, so that we
can stabilize the API and add this code to the main JFreeChart API.

###### API adjustments

The following adjustments have been made to the API:

- `CategoryLabelEntity` - new class;
- `CategoryPointerAnnotation` - new class;
- `ChartPanel`: added new public method `doEditChartProperties()`;
- `ComparableObjectItem`, `ComparableObjectSeries` - new classes;
- `CrosshairState`: added several new accessor methods;
- `DefaultPieDataset`: added `sortByKeys()` and `sortByValues()` methods;
- `Markers`: a change event mechanism has been added to the `Marker` class and its subclasses;
- `StackedAreaRenderer`: added `get/setRenderAsPercentages()` methods;
- `XIntervalDataItem`, `XIntervalSeries` and `XIntervalSeriesCollection` - new classes;
- `XYErrorRenderer`: new class;
- `XYInterval`, `XYIntervalDataItem`, `XYIntervalSeries` and `XYIntervalSeriesCollection` - new classes;
- `YInterval`, `YIntervalDataItem`, `YIntervalSeries`, `YIntervalSeriesCollection` and `YWithXInterval` - new classes.

###### Bug Fixes

- 1578293 - Unused methods in `JDBCXYDataset`;
- 1572478 - `BoxAndWhiskerRenderer` potential `NullPointerException`;
- 1569094 - `XYStepRenderer` with horizontal orientation;
- 1565168 - Crosshair position incorrect;
- 1564977 - `DateAxis` missing initial tick label;
- 1562759 - `StatisticalLineAndShapeRenderer` constructor ignores arguments;
- 1557141 - Bad locale in `ServletUtilities`;
- 1550045 - `TimeSeries.removeAgedItems()` method problems;
- 1549218 - Chart not displaying when all data values are the same and large;
- 1450447 - `Marker.setAlpha()` ignored;

Also fixed URL generation for legend items, tick mark positioning on the 
`DateAxis`, the `equals()` method in the `AreaRenderer` class, hardcoded outline 
attributes in the `XYBubbleRenderer`, and potential `NullPointerExceptions` in the
`ChartPanel` class.

##### Version 1.0.2 (25-Aug-2006)

###### API adjustments

The following adjustments have been made to the API (there should be no breakage of applications coded to the 1.0.0 or 1.0.1 API):

- `CategoryToPieDataset`: added accessor methods for underlying dataset, extract type and index (feature request 1477915);
- `DefaultXYDataset`:  New dataset implementation that uses double[] arrays;
- `DefaultXYZDataset`:  New dataset implementation that uses double[] arrays;
- `LegendItemBlockContainer`: New container used in legends (enables legend item entities again);
- `MultiplePiePlot`:  Added new fields `aggregatedItemsKey` and `aggregatedItemsPaint`, plus accessor methods - see bug 1190647;
- `SpiderWebPlot`:  Added new fields `toolTipGenerator` and `urlGenerator`, plus accessor methods (see patch 1463455);
- `StackedBarRenderer3D`:  Added new flag (`renderAsPercentages`), plus accessor methods, that controls whether the data items are displayed as values or percentages.  Two new constructors are also added (see patch 1459313);
- `XYPolygonAnnotation`: Added new accessor methods.

###### Patches

- 1459313 - Add `renderAsPercentages` option to `StackedBarRenderer3D`;
- 1462727 - Modify `SpiderWebPlot` to support zero values;
- 1463455 - Modify `SpiderWebPlot` to support mouse clicks, tool tips and URLs;

###### Bug Fixes

- 1514904 - Background image alpha in `Plot` class;
- 1499140 - `ClusteredXYBarRenderer` with margin not drawing correctly;
- 1494936 - `LineAndShapeRenderer` generates entity for non-visible item;
- 1493199 - NPE drawing `SpiderWebPlot` with null info;
- 1480978 - `AbstractPieItemLabelGenerator.clone()` doesn't clone `percentFormat`;
- 1472942 - `DateAxis.equals()` broken;
- 1468794 - `StatisticalLineAndShapeRenderer` doesn't draw error bars correctly when the plot has a horizontal orientation;
- `AbstractCategoryItemRenderer` doesn't check `seriesVisibleInLegend` flag before creating new item;
- 1440415 - Bad distribution of pie chart section labels;
- 1440346 - Bad manifest entry for JCommon in JFreeChart jar file;
- 1435461 - `NumberAxis.equals()` ignores `rangeType` field;
- 1435160 - `XYPointerAnnotation.equals()` ignores x and y fields;
- 1398672 - `LegendItemEntities` not working;
- 1380480 - `StandardXYItemRenderer` problems with `Double.NaN`;
- 1190647 - Legend and section color mismatch for `MultiplePiePlot`.

###### Miscellaneous Changes

- Updated `CandlestickRenderer`, `CyclicXYItemRenderer`, `HighLowRenderer`, `XYStepAreaRenderer` and `TimeSeriesURLGenerator` to call dataset methods that return double primitive only;
- Updated `XYPolygonAnnotation`, adding new accessor methods and fixing problems in the `equals()/hashCode()` methods;
- `ChartFactory.createStackedXYAreaChart()` now uses `StackedXYAreaRenderer2`, for better handling of negative values;
- Added crosshair support for `XYBarRenderer`.

###### Experimental Code

In this release, some new (incomplete) classes have been included in the 
`org.jfree.experimental.*` namespace.  These classes are not part of the 
standard API, but are included for developers to experiment with and provide
feedback on.  Hopefully in the future, refined versions of these classes will
be incorporated into the main library.  PLEASE NOTE THAT THE API FOR THESE
CLASSES IS SUBJECT TO CHANGE. 

##### Version 1.0.1 (27-Jan-2006)
This is primarily a bug fix release.  In addition, there are some API 
adjustments (there should be no breakage of applications coded to the 1.0.0 API).

###### API adjustments
- `BarRenderer`: added a new flag (`includeBaseInRange`), plus accessor 
    methods, that controls whether or not the base value for the bar is 
    included in the range calculated by the `findRangeBounds()` method;
- `BubbleXYItemLabelGenerator`: new class;
- `Range`: added a new method `expandToInclude(Range, double)`, this is used by 
    the `BarRenderer` class;
- `TaskSeriesCollection`: added two new methods, `getSeries(int)` and 
    `getSeries(Comparable)`;
- `TimeSeriesCollection`:  the `domainIsPointsInTime` flag has been deprecated. 
  The flag serves no function now that renderers are used to calculate the domain 
  bounds, so you can safely delete any calls to the `setDomainIsPointsInTime()`
  method;
- `XYPlot`: added a new `getAnnotations()` method;
- `XYSeries`: the `update(int, Number)` method has been deprecated and a new 
  method `updateByIndex(int, Number)` has been added;

###### Bug fixes

- 1243050 - `XYBarRenderer` doesn't show entire range of values for a `TimeSeriesCollection`;
- 1373371 - `XYBubbleRenderer` doesn't support item labels;
- 1374222 - `BarRenderer` contains JDK 1.4 specific code;
- 1374328 - `LegendItem` serialization problem;
- 1377239 - Bad argument checking in `Quarter` constructor;
- 1379331 - Incorrect drawing of `TextTitle` at LEFT or RIGHT position;
- 1386328 - `RingPlot` entity incorrect;
- 1400442 - Inconsistent treatment of null and zero values in `PiePlot`;
- 1401856 - Bad rendering for non-zero base values in `BarRenderer`;
- 1403043 - `NullPointerException` in `CategoryAxis`;
- 1408904 - `NumberAxis3D` assumes `CategoryPlot`;
- 1415480 - `XYTextAnnotation` equals() method doesn't check (x, y);

##### Version 1.0.0 (2-Dec-2005) 
- the first stable release of the JFreeChart class library, all future releases in the 1.0.x series will aim to maintain backward compatibility with this release;

##### Version 1.0.0-rc3 (28-Nov-2005)
- the third "release candidate" for version 1.0.0, this release fixes some issues with the 1.0.0-rc2 release (mainly concerning packaging of resource bundles for localisation).
- if no significant problems are reported in the next few days, the 1.0.0 "final" release will be posted on 2-Dec-2005.

##### Version 1.0.0-rc2 (25-Nov-2005)
- the second "release candidate" for version 1.0.0.  If no problems are reported, 1.0.0 "final" will be released on 2-Dec-2005.

##### Version 1.0.0-rc1 (2-Jun-2005)
- this is a "release candidate" for version 1.0.0.  If no significant API problems are reported, this release will be re-released as version 1.0.0.

##### Version 1.0.0-pre2 (10-Mar-2005)

##### Version 1.0.0-pre1 (29-Nov-2004)

##### Version 0.9.21 (9-Sep-2004)
- added new axes: `PeriodAxis` and `ModuloAxis`.
- split `org.jfree.data` and `org.jfree.chart.renderer` into subpackages for 'category' and 'xy' charts.
- Sun PNG encoder is now used, if available.
- a new demo application makes it easier to preview the chart types that JFreeChart can create.
- added a new series visibility flag to the `AbstractRenderer` class.
- added support for `GradientPaint` in interval markers.

##### Version 0.9.20 (7-Jun-2004)
- primarily bug fixes.

##### Version 0.9.19 (28-May-2004)
- added methods to `XYDataset` that return double primitives;
- removed distinction between "primary" and "secondary" datasets, renderers and axes;
- added fixed legend item options to `CategoryPlot` and `XYPlot`;
- legend changes by Barek Naveh;
- removed Log4j dependency;
- many, many bug fixes;

##### Version 0.9.18 (15-Apr-2004)
- new legend anchor options;
- fixed broken JPEG export;
- fixed title size problems;
- various other bug fixes;

##### Version 0.9.17 (26-Mar-2004)
- pie chart enhancements for labelling, shading and multiple pie charts (2D or 3D) on a single plot;
- new `PolarPlot` class added;
- `XYSeries` can now be sorted or unsorted;
- `createBufferedImage()` method can now scale charts;
- domain and range markers now support intervals;
- item labels are now supported by some `XYItemRenderers`;
- tooltip and item label generators now use `MessageFormat` class;
- added new `XYBarDataset` class;
- added transparency support to PNG export;
- numerous other small enhancements and bug fixes;

##### Version 0.9.16 (09-Jan-2004)
- this release contains bug fixes and some minor feature enhancements (title and category label wrapping, legend shape scaling, enhanced performance for the `DefaultTableXYDataset` class);
- added Spanish localisation files;

##### Version 0.9.15 (28-Nov-2003)
- the focus of this release is bug fixes - quite a number of issues have been resolved, please check the bug database for details;
- added a new Wafer Map chart type;
- added a cyclic axis;
- added localisation files for _ru;

##### Version 0.9.14 (17-Nov-2003)
- implemented zooming for the `FastScatterPlot` class;
- added item label support for stacked bar charts, and new fall back options for item labels that don't fit within bars;
- modified the `CategoryAxis` class to allow additional options for the alignment and rotation of category labels;
- addition of the `AxisState` class, used in the drawing of axes to eliminate a bug when multiple threads draw the same axis simultaneously;
- provided additional attributes in the `DateTickUnit` class to improve labelling on a segmented `DateAxis`;
- added support for `GradientPaint` in bar charts;
- updated the `PNGEncoder`;
- fixes for tick label positioning on axes;
- various Javadoc updates;
- numerous bug fixes;

##### Version 0.9.13 (26-Sep-2003)
         - various enhancements to the stacked area XY charts;
         - added a completion indicator for the Gantt chart;
         - range and domain markers can now be placed in the foreground or the
           background;
         - more fixes for cloning and serialization;
         - fixed mouse event bug for combined charts;
         - fixed bugs in the PngEncoder class;
         - incorporated .properties files that were missing from the 0.9.12
           distribution;

##### Version 0.9.12 (11-Sep-2003)
         - extended box-and-whisker plots to work with the CategoryPlot class
           as well as the XYPlot class (based on work by David Browning);
         - added a new LayeredBarRenderer (by Arnaud Lelievre);
         - added support for stacked area charts with the XYPlot class (thanks
           to Richard Atkinson);
         - improved HTML image map support (thanks to Richard Atkinson);
         - added localized resources for the chart property editors (thanks to
           Arnaud Lelievre).  Current translations include French and Portugese
           (thanks to Eduardo Ramalho);
         - added facility for setting all rendering hints;
         - improved support for cloning and serialization;
         - fixed a bug in the XYSeries class that prevented the TableXYDataset
           from functioning correctly;
         - improved date axis labelling with segmented time lines;
         - fixed several bugs in the secondary dataset/axis/renderer code;
         - fixed bugs in the JDBCCategoryDataset class;
         - numerous other bug fixes;

##### Version 0.9.11 (8-Aug-2003)
         - added support for box-and-whisker plots, thanks to David Browning;
         - lots of bug fixes;

##### Version 0.9.10 (25-Jul-2003)
         - added support for multiple secondary axes, datasets and
           renderers;
         - minor feature enhancements and bug fixes;

##### Version 0.9.9 (10-Jul-2003) 

PLEASE NOTE THAT MAJOR CHANGES HAVE BEEN MADE IN THIS
RELEASE AND ONE OR TWO FEATURES MAY BE BROKEN.  PLEASE REPORT BUGS SO THEY CAN
BE FIXED FOR THE NEXT RELEASE.

        - merged the HorizontalCategoryPlot and VerticalCategoryPlot classes,
          into the CategoryPlot class;
        - merged the horizontal and vertical axis classes;
        - merged the horizontal and vertical renderer classes;
        - CategoryPlot and XYPlot now support both horizontal and vertical
          orientation via the setOrientation(...) method;
        - merged horizontal and vertical methods in the ChartFactory class;
        - created new combined plot classes: CombinedDomainCategoryPlot,
          CombinedRangeCategoryPlot, CombinedDomainXYPlot and
          CombinedRangeXYPlot (these can all be drawn with a horizontal or
          vertical orientation);
        - Bill Kelemen has enhanced the DateAxis class to handle segmented
          timelines.  This can be used, for example, to skip weekends for
          daily stock price charts;
        - Richard Atkinson has updated the ServletUtilities class;
        - Bryan Scott has added an XYDatasetTableModel class for presenting
          datasets in a JTable;
        - modified XYPlot to allow renderers to use multiple passes through
          the dataset;
        - added new XYDifferenceRenderer;
        - added support for colored bands between gridlines in XYPlot;
        - added new XYDrawableAnnotation class;
        - added a new attribute to control the order of dataset rendering in
          a CategoryPlot;
        - extended the value label mechanism for the renderers, to allow
          better (per series) control over label generation, positioning and
          visibility;
        - CategoryItemTooltipGenerator has been renamed
          CategoryItemLabelGenerator, since it is now being used to generated
          item labels as well as tooltips;
        - there is now support for horizontal stacked 3D bar charts;
        - added support for range markers against secondary axis in a
          CategoryPlot;
        - added labels to domain and range markers;
        - added a new HistogramDataset class (contributed by Jelai Wang) to
          make it easier to create histograms with JFreeChart;
        - moved the DrawingSupplier into the plot class, renderers now
          reference the supplier from the plot (parent plot for combined and
          overlaid charts).  This means that renderers now share a single
          DrawingSupplier by default, which simplifies the creation of
          combined charts;
        - changed the ColorBarAxis classes that extended the NumberAxis class,
          to a single ColorBar class that wraps a ValueAxis (may have broken
          one or two things in the process);
        - Barak Naveh has contributed new classes MatrixSeries and
          MatrixSeriesCollection, along with demos:  BubblyBubblesDemo.java
          and BubblyBubblesDemo2.java;
        - the TextTitle class now has a background paint attribute;
        - the StandardLegend class now generates LegendEntity objects if a
          ChartRenderingInfo instance is supplied to the draw(...) method;
        - extended the CategoryTextAnnotation class to take into account a
          category anchor point.  See the SurveyResultsDemo.java application
          for an example;
        - included numerous bug fixes;

##### Version 0.9.8 (24-Apr-2003)
        - changed package naming from com.jrefinery.* to org.jfree.*;
        - added new TimePeriodValuesCollection class;
        - added MIME type code to ServletUtilities class;
        - reversed the order of PieDataset and KeyedValuesDataset in
          the class hierarchy;
        - reversed the order of CategoryDataset and KeyedValues2DDataset
          in the class hierarchy;
        - minor bug fixes;

##### Version 0.9.7 (11-Apr-2003)
        - added a new ValueDataset interface and DefaultValueDataset
          class, and changed the CompassPlot class to use this instead
          of MeterDataset;
        - added DataUtilities class, to support creation of Pareto
          charts (new demo included);
        - updated writeImageMap method as suggested by Xavier Poinsard
          (see Feature Request 688079);
        - implemented Serializable for most classes (this is likely to
          require further testing);
        - incorporated contour plot updates from David M. O'Donnell;
        - added new CategoryTextAnnotation and XYLineAnnotation
          classes;
        - added new HorizontalCategoryAxis3D class contributed by
          Klaus Rheinwald;

        Bug fixes:
        - added a workaround for JVM crash (a JDK bug) in pie charts
          with small sections (see bug report 620031);
        - fixed minor bug in HorizontalCategoryPlot constructor (see
          bug report 702248);
        - added code to ensure HorizontalNumberAxis3D is not drawn if
          it is not visible (see bug report 702466);
        - added small fix for suppressed chart change events (see bug
          report 690865);
        - added pieIndex parameter to tooltip and URL generators for
          pie charts;
        - fixed bug in getLastMillisecond() method for the Second
          class and the getFirstMillisecond() method for the Year
          class (picked up in JUnit tests);
        - in TextTitle, changed width used for relative spacing to fix
          bug 703050;

##### Version 0.9.6 (17-Feb-2003) Bug fixes:
        - fixed null pointer exception in DefaultCategoryDataset;
        - fixed update problem for PaintTable, StrokeTable and
          ShapeTable objects;
        - added methods to control colors in PiePlot (these were
          inadvertantly removed in the changes made for 0.9.5);
        - fixed auto-range update problem for secondary axis;
        - fixed missing category labels in the overlaid category plot;
        - fixed constructors for symbolic axes;
        - corrected error in Javadoc generation (Ant script);

##### Version 0.9.5 (6-Feb-2003) 

PLEASE NOTE THAT MAJOR CHANGES TO THE
        JFREECHART API HAVE BEEN MADE IN THIS RELEASE!

        - added support for secondary axes, datasets and renderers;
        - added new data interfaces (Value, Values, Values2D,
          KeyedValues and KeyedValues2D) and incorporated these into
          the existing PieDataset and CategoryDataset interfaces.
        - modified the CategoryDataset interface to be more
          symmetrical, data is organised in rows and columns (as
          before) but can now be accessed by row/column index or
          row/column key.
        - added support for reading PieDatasets and CategoryDatasets
          from XML files.
        - created separate packages for the axes
          (com.jrefinery.chart.axis), plots (com.jrefinery.chart.plot)
          and renderers (com.jrefinery.chart.renderer).
        - series attributes (paint, outline paint, stroke and shape)
          are now controlled by the renderer classes using lookup
          tables.  Introduced the DrawingSupplier interface (and
          DefaultDrawingSupplier class) which is used to populate the
          lookup tables from a common source (necessary to coordinate
          complex combined charts).
        - the chart legend can now display shapes corresponding to
          series.
        - moved responsibility for category distribution to the
          CategoryAxis class, which tidies up the code in the
          CategoryPlot classes.
        - gridlines are now controlled by the CategoryPlot and XYPlot
          classes, not the axes (included in this change is the
          addition of gridlines for the CategoryPlot domain values).
        - changed the list of titles in the JFreeChart class to a
          title and a list of subtitles.
        - added new renderers for XYPlot (XYBubbleRenderer and
          YIntervalRenderer).
        - modified Gantt chart to display sub-tasks.
        - added ContourPlot class (still experimental) by David
          M. O'Donnell.
        - introduced new MovingAverage class.
        - ChartMouseEvent now includes source chart.
        - numerous bug fixes.
        - lots of Javadoc updates.

##### Version 0.9.4 (18-Oct-2002)  

Added a new stacked area chart (contributed by Dan
        Rivett) and a compass plot (contributed by Bryan Scott).  Updated
        the ThermometerPlot class. Added a new XYDotRenderer for scatter
        plots. Modified combined and overlaid plots to use the series colors
        specified in the sub plot rather than the parent plot (this makes it
        easier to align the colors in the legend).  Added Regression class
        for linear and power regressions.  BasicTimeSeries can now
        automatically drop "old" data.  Some clean-up work in the code for
        tooltips and the event listener mechanism.  Richard Atkinson has
        incorporated some useful extensions for servlets/JSP developers.

        Ran Checkstyle and corrected issues reported for most classes.
        Checkstyle is a free utility that you can download from:

            http://checkstyle.sourceforge.net

        Fixed bugs and updated documentation.

        API changes include:
        - added tickMarkPaint to Axis constructor (also affects
          subclasses);
        - added getLegendItems() to Plot, and deprecated
          getLegendItemLabels();
        - added getLegendItem(int) to XYItemRenderer and
          CategoryItemRenderer.
        - most 'protected' member variables have been changed to
          'private'.

##### Version 0.9.3 (4-Sep-2002) 

- Added multiple pie charts based on `CategoryDataset`;
- Updated logarithmic axes;
- Improved URL support for image map generation;
- Moved the `com.jrefinery.data` package from JCommon to JFreeChart. 
- Added simple framework for chart annotations;
- Improved control over renderers;
- Duplicate x-values now allowed in `XYSeries`; 
- Optional category label skipping in category axes; 
- Added `CategoriesPaint` attribute to `AbstractCategoryItemRenderer`;
- Added new attributes to `MeterPlot` class;
- Updated 3D pie chart to observe start angle and direction, and also foreground alpha < 1.0;
- Improved Javadoc comments;
- New demo applications, including: `AnnotationDemo1`, `EventFrequencyDemo`, `JDBCCategoryChartDemo`, `JDBCPieChartDemo`, `JDBCXYChartDemo` and `MinMaxCategoryPlotDemo`.

Bug fixes:
- negative percentages on `PiePlot`;
- added listener notification to `setXXXAxis(...)` methods;
- fixed `DomainInfo` method name clash;
- added `DomainIsPointsInTime` flag to `TimeSeriesCollection` to give better control over auto range on axis for time series charts;
- axis margins for date axes are no longer hard-coded;
- fix for ordering of categories in `JdbcCategoryDataset`;
- added check for `null` axis in mouse click handler.

The CVS repository at SourceForge has also been restructured to match the distribution directory layout.


##### Version 0.9.2 (28-Jun-2002) 

- `PiePlot` now has `startAngle` and `direction` attributes;
- added support for image map generation;
- added a new `Pie3DPlot` class;
- added label drawing code to bar renderers;
- added optional range markers to horizontal number axis;
- added bar clipping to avoid PRExceptions in bar charts; 
- `JFreeChartDemo` has been modified and now includes examples of the dial and thermometer plots.

######Bug fixes

- auto range for `VerticalNumberAxis` when zero is forced to be included in the range;
- fixed null pointer exception in `StackedVerticalBarRenderer3D`;
- added get/set methods for min/max chart drawing dimensions in `ChartPanel`;
- `HorizontalIntervalBarRenderer` now handles single category;
- `verticalTickLabels` now possible in `HorizontalNumberAxis3D`;
- removed unnecessary imports;


##### Version 0.9.1 (14-Jun-2002) 

Bug fixes and Javadoc updates.

- fixed auto range calculation for category plots;
- fixed event notification for `XYPlot`;
- fixed auto axis range for Gantt charts;
- check for null popup menu in `ChartPanel.mouseDragged`;
- new checks for null info in renderers;
- range markers now drawn only if in visible axis range;


##### Version 0.9.0 (7-Jun-2002) 

- new plots including an area chart, a horizontal 3D bar chart, a Gantt chart 
  and a thermometer chart;
- combination plots have been reworked to provide a simpler framework, and 
  extends to allow category plots to be combined;
- there is now a facility to add a `ChartMouseListener` to the `ChartPanel` 
  (formerly `JFreeChartPanel`);
- an interactive zooming feature (experimental at this point) is now available 
  for `XYPlots`;
- a new Polish translation has been added;
- several fixes have been applied to the default tool tip generators;
- a workaround has been added to fix the alignment between time series charts 
  and the date axis;  
- there are some improvements to the `VerticalLogarithmicAxis` class, and now a 
  corresponding `HorizontalLogarithmicAxis` class;  
- additional demonstration applications have been added;
- fixed the popup menu bug.


##### Version 0.8.1 (5-Apr-2002) 

- Localised resource bundles for French, German and Spanish languages (thanks to 
Anthony Boulestreau, Thomas Meier and Hans-Jurgen Greiner for the translations);  
- an area XY plot and meter chart contributed by Hari;
- symbol charts contributed by Anthony Boulestreau;
- an improved `CandleStickRenderer` class from Sylvain Vieujot;
- updated servlet code from Bryan Scott;
- `XYItemRenderers` now have a change listener mechanism and therefore do not 
have to be immutable;
- additional demonstration applications for individual chart types;
- minor bug fixes.


##### Version 0.8.0 (22-Mar-2002) 

- all the category plots are now controlled through the one class (`CategoryPlot`) with plug-in renderers;
- added a `ResourceBundle` for user interface items that require localisation;
- added a logarithmic axis class contributed by Mike Duffy and some new JDBC and servlet code contributed by Bryan Scott;
- updated the JCommon class library to improve handling of time periods in different time zones.


##### Version 0.7.4 (6-Mar-2002) 

- bug fixes in the JCommon Class Library; 
- various Javadoc comment updates;  
- some minor changes to the code; 
- added new domain name (http://www.object-refinery.com) in the source headers.


##### Version 0.7.3 (14-Feb-2002) 

Bug fixes.


##### Version 0.7.2 (8-Feb-2002) 

- integrated the `WindPlot` code from Achilleus Mantzios;
- added an optional background image for the `JFreeChart` class;
- added an optional background image for the `Plot` class;
- added alpha-transparency for the plot foreground and background;
- added new pie chart label types that show values;
- fixed a bug with the legend that results in a loop at small chart sizes;
- added some tooltip methods that were missing from the previous version;
- changed the `Insets` class on chart titles to a new `Spacer` class that will 
allow for relative or absolute insets (the plan is to eventually replace all 
`Insets` in the `JFreeChart` classes);  
- fixed a bug in the `setAutoRangeIncludesZero` method of the `NumberAxis` class;
- added the instructions that were missing from the copies of the GNU Lesser General Public Licence included with JFreeChart.


##### Version 0.7.1 (25-Jan-2002) 

- added tooltips, crosshairs and zooming functions, thanks to Jonathan Nash and Hans-Jurgen Greiner
  for contributing the code that these features are based on;
- moved the combination charts into the package `com.jrefinery.chart.combination`;
- made a number of other small API changes and fixed some bugs;
- removed the Javadoc HTML from the download to save space (you can regenerate it from the source code if you need it).


##### Version 0.7.0 (11-Dec-2001) 

- new combination plots developed by Bill Kelemen;
- added Wolfgang Irler's servlet demo to the standard download;
- the About window in the demo application now includes a list of developers that have contributed to the project.


##### Version 0.6.0 (27-Nov-2001) 

- new plots including scatter plot, stacked bar charts and 3D bar charts; 
- improved pie chart;
- data interfaces and classes moved to the JCommon class library;
- new properties to control spacing on bar charts;
- new auto-tick mechanism;
- `JFreeChartPanel` now incorporates buffering, and popup menu;
- Javadocs revised;
- fixed numerous bugs from version 0.5.6;  
- demo application updated.


CONTRIBUTORS
------------
JFreeChart wouldn't be half the library that it is today without the contributions (large and small) that have been made by the developers listed below:

- Eric Alexander
- Richard Atkinson
- David Basten
- David Berry
- Chris Boek
- Zoheb Borbora
- Anthony Boulestreau
- Jeremy Bowman
- Nicolas Brodu
- Jody Brownell
- David Browning
- Soren Caspersen
- Thomas A Caswell
- Chuanhao Chiu
- Brian Cole
- Pascal Collet
- Martin Cordova
- Paolo Cova
- Greg Darke
- Mike Duffy
- Don Elliott
- Rune Fauske
- Jonathan Gabbai
- Serge V. Grachov
- Daniel Gredler
- Joao Guilherme Del Valle
- Hans-Jurgen Greiner
- Nick Guenther
- Aiman Han
- Cameron Hayne
- Martin Hoeller (xS+S)
- Jon Iles
- Wolfgang Irler
- Sergei Ivanov
- Nina Jeliazkova
- Adriaan Joubert
- Darren Jung
- Xun Kang
- Bill Kelemen
- Norbert Kiesel
- Petr Kopac
- Gideon Krause
- Dave Law;
- Pierre-Marie Le Biot
- Simon Legner
- Arnaud Lelievre
- Wolfgang Lenhard
- Leo Leung
- David Li
- Yan Liu
- Tin Luu
- Craig MacFarlane
- Achilleus Mantzios
- John Matthews
- Thomas Meier
- Jim Moore
- Jonathan Nash
- Barak Naveh
- David M. O'Donnell
- Krzysztof Paz
- Eric Penfold
- Tomer Peretz
- Xavier Poinsard
- Andrzej Porebski
- Viktor Rajewski
- Eduardo Ramalho
- Michael Rauch
- Klaus Rheinwald
- Cameron Riley
- Dan Rivett
- Lukasz Rzeszotarski
- Scott Sams
- Michel Santos
- Thierry Saura
- Patrick Schlott
- Andreas Schneider
- Christoph Schroeder
- Jean-Luc SCHWAB
- Bryan Scott
- Tobias Selb
- Darshan Shah
- Mofeed Shahin
- Michael Siemer
- Pady Srinivasan
- Greg Steckman
- Roger Studner
- Gerald Struck
- Irv Thomae
- Eric Thomas
- Rich Unger
- Daniel van Enckevort
- Laurence Vanhelsuwe
- Sylvain Vieujot
- Jelai Wang
- Mark Watson
- Alex Weber
- Richard West
- Matthew Wright
- Benoit Xhenseval
- Christian W. Zuckschwerdt
- Hari
- Sam (oldman)

It is possible that I have missed someone on this list, if that applies to you, please e-mail me.

Dave Gilbert (david.gilbert@object-refinery.com)

JFreeChart Project Leader

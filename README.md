JFreeChart
==========

Version 1.5.0 (and 1.0.20), not yet released.


Overview
--------
JFreeChart is a comprehensive free chart library for the Java(tm) platform that 
can be used on the client-side (JavaFX and Swing) or the server side (with
export to multiple formats including SVG, PNG and PDF).

The home page for the project is:

http://www.jfree.org/jfreechart

JFreeChart requires JDK 1.8.0_40 or later and is licensed under the terms of the
GNU Lesser General Public License (LGPL) version 2.1 or later.


History
-------

##### Version 1.0.19 (31-Jul-2014)
- fixed clipping issues for combined plots in JavaFX;
- fixed a memory leak in the new JavaFX ChartCanvas class;
- CombinedDomainXYPlot and CombinedRangeXYPlot now take into account the pannable flags in the subplots;
- FastScatterPlot panning direction is corrected;
- added rendering hints to sharpen gridlines and borders in most output formats;        
- JFreeSVG updated to version 2.0;
- included a preview of JSFreeChart, a 2D chart library written in JavaScript that is conceptually similar to JFreeChart but runs directly in a web browser.

##### Version 1.0.18 (3-Jul-2014)
- added JavaFX support via FXGraphics2D;
- improved LogAxis labelling;
- improved numeric tick labelling;
- center text support in RingPlot;
- 'stepPoint' attribute in the XYStepAreaRenderer;
- other minor improvements and bug fixes.

##### Version 1.0.17 (22-Nov-2013)
- Enhanced XYSplineRenderer with new area fill (contributed by Klaus Rheinwald);
- added a notify flag to all datasets that extend AbstractDataset;
- extended TimeSeriesCollection to validate TimeSeries keys for uniqueness;
- added a new DirectionalGradientPaintTransformer (by Peter Kolb);
- updated OHLCSeries;
- added HMSNumberFormat;
- updated JCommon to version 1.0.21 (includes rotated text improvements) and fixed some minor bugs.

###### Bug Fixes
- 977  : Multithreading issue with DateAxis;
- 1084 : BorderArrangement.add() possible ClassCastException;
- 1099 : XYSeriesCollection.removeSeries(int) does not deregister listener;
- 1109 : WaterfallBarRenderer uses wrong color for diff 0.


##### Version 1.0.16 (13-Sep-2013)

*** THIS RELEASE REQUIRES JDK/JRE 1.6.0 OR LATER. ***

- Provided subscript/superscript support for axis labels (via AttributedString);
- new axis label positioning options;
- simplified ChartFactory methods;
- added new methods to DatasetUtilities to interpolate y-values in XYDatasets;
- added URLs to labels on CategoryAxis;
- seamless integration with JFreeSVG (http://www.jfree.org/jfreesvg/) and OrsonPDF 
(http://www.object-refinery.com/pdf/);
- improved the consistency of the SWTGraphics2D implementatio;  

All the JUnit tests have been upgraded to JUnit 4.

###### Bug Fixes

- 1107 : Fixed TimeZone issue in PeriodAxis;

Also fixed a line drawing issue with the StackedXYAreaRenderer, and a memory 
leak in the SWT ChartComposite class.


##### Version 1.0.15 (4-Jul-2013)
- Added support for non-visible series in XYBarRenderer;
- minor gridlines in PolarPlot;
- legend item ordering;
- chart editor enhancements;
- updates to StandardDialScale;
- localisation files for Japanese;
- refactored parameter checks.  

This release also fixes a minor security flaw in the DisplayChart class, detected and reported by OSI Security:

http://www.osisecurity.com.au/advisories/jfreechart-path-disclosure

###### Patches

- 3500621 : LegendTitle order attribute (by Simon Kaczor);
- 3463807 : ChartComposite does not dispose popup (by Sebastiao Correia);
- 3204823 : PaintAlpha for 3D effects (by Dave Law);

###### Bug Fixes

- 3561093 : Rendering anomaly for XYPlots;
- 3555275 : ValueAxis.reserveSpace() problem for axes with fixed dimension;
- 3521736 : DeviationRenderer optimisation (by Milan Ramaiya);
- 3514487 : SWTGraphics2D get/setStroke() problem;
- 3508799 : DefaultPolarItemRenderer does not populate seriesKey in LegendItem;
- 3482106 : Missing text in SWTGraphics2D (by Kevin Xu);
- 3484408 : Maven fixes (Martin Hoeller);
- 3484403 : DateAxis endless loop (by Martin Hoeller);
- 3446965 : TimeSeries calculates range incorrectly in addOrUpdate();
- 3445507 : TimeSeriesCollection.findRangeBounds() regression;
- 3425881 : XYDifferenceRenderer fix (by Patrick Schlott/Christoph Schroeder);
- 2963199 : SWT print job (by Jonas Rüttimann);
- 2879650 : Path disclosure vulnerability in DisplayChart servlet;

Also fixed a rendering issue for polar charts using an inverted axis.

##### Version 1.0.14 (20-Nov-2011)
This release contains:

- support for multiple and logarithmic axes with PolarPlot;
- optional drop-shadows in plot rendering;
- fitting polynomial functions to a data series;
- some performance improvements in the TimeSeriesCollection class;
- mouse wheel rotation of pie charts;
- improved Maven support.

###### Patches

- 3435734 : Fix lines overlapping item labels (by Martin Hoeller);
- 3421088 : Bugfix for misalignment in BoxAndWhiskerRenderer;
- 2952086 : Enhancement for finding bounds in XYZDatasets;
- 2954302 : CategoryPointerAnnotation line calculation;
- 2902842 : HistogramDataset.addSeries() fires change change event (by Thomas A Caswell);
- 2868608 : Whisker width attribute for BoxAndWhiskerRenderer (by Peter Becker);
- 2868585 : Added useOutlinePaint flag for BoxAndWhiskerRenderer (by Peter Becker);
- 2850344 : PolarPlot enhancements (by Martin Hoeller);
- 2795746 : Support for polynomial regression;
- 2791407 : Fixes for findRangeBounds() in various renderers.

###### Bug Fixes

- 3440237 : Shadows always visible;
- 3432721 : PolarPlot doesn't work with logarithmic axis;
- 3433405 : LineChart3D - Problem with Item Labels;
- 3429707 : LogAxis endless loop;
- 3428870 : Missing argument check in TextAnnotation;
- 3418287 : RelativeDateFormatTest.java is locale dependent;
- 3353913 : Localisation fixes for ChartPanel, CompassPlot and PiePlot3D;
- 3237879 : RingPlot should respect getSectionOutlineVisible();
- 3190615 : Added missing clear() method in CategoryTableXYDataset;
- 3165708 : PolarChartPanel localisation fix;
- 3072674 : Bad handling of NaN in DefaultStatisticalCategoryDataset;
- 3035289 : StackedXYBarRenderer should respect series/item visible flags;
- 3026341 : Check for null in getDomain/RangeBounds() for XYShapeRenderer;
- 2947660 : AbstractCategoryRenderer fix null check in getLegendItems();
- 2946521 : StandardDialScale check majorTickIncrement argument;
- 2876406 : TimeTableXYDataset should support Comparable for series keys;
- 2868557 : BoxAndWhiskerRenderer should fire change event in setMedianVisible();
- 2849731 : For IntervalCategoryDataset and IntervalXYDataset, fix iterateRangeBounds() in DatasetUtilities;
- 2840132 : AbstractXYItemRenderer drawAnnotations doesn't set renderer index;
- 2810220 : Offset problem in StatisticalBarRenderer;
- 2802014 : Dial value border too small;
- 2781844 : XYPointerAnnotation arrow drawing;
- 1937486 : AreaRenderer doesn't respect AreaRendererEndType.LEVEL;

Also fixed:
- use of simple label offset in PiePlot;
- cached minY and maxY in TimeSeries.createCopy();
- scaling issues for charts copied to the clipboard;
- use of timezone in TimeTableXYDataset constructor;
- duplicate series names in XYSeriesCollection;
- HistogramDataset fires a change event in addSeries();
- check visibility of main chart title before drawing it;
- fixed serialization of PowerFunction2D, NormalDistributionFunction2D, and LineFunction2D;
- item label positioning for the AreaRenderer class when the plot has an horizontal orientation.

##### Version 1.0.13 (17-Apr-2009)

> SPECIAL NOTICE:  There will be a birds-of-a-feather session for JFreeChart
> at this year's JavaOne conference in San Francisco.  The session is        
> scheduled for 6.45pm to 7.35pm on Wednesday 3 June.                        

This release contains:

- updates to the ChartPanel class to support copying charts to the clipboard, 
  panning and mouse-wheel zooming, and an overlay mechanism that supports
  crosshairs;
- enhancements to the auto-range calculation for axes, providing the ability
  to use subranges only and also to skip hidden series;
- updates for many of the CategoryItemRenderer implementations to ensure that
  they respect the 'seriesVisible' flags;
- an improvement to the TimeSeries class so that it is no longer necessary to
  specify the time period type in the constructor;
- a new SamplingXYLineRenderer for improving the performance of time series
  charts with large datasets;
- the XYSeries/XYSeriesCollection classes now cache the minimum and maximum
  data values to improve the performance of charts with large datasets;
- entities are now created for the chart, data area and axes, allowing mouse
  clicks to be detected for these regions;
- added a bar alignment factor to the XYBarRenderer class;
- a new 'errorIndicatorStroke' field for the StatisticalLineAndShapeRenderer 
  and XYErrorRenderer classes;
- added a new HeatMapDataset interface, DefaultHeatMapDataset implementation,
  and a HeatMapUtilities class to make it easier to create heat map charts;
- there is a new flag to allow an XYDataImageAnnotation to be included in the
  automatic range calculation for the axes;
- additional attributes in the XYTextAnnotation class;
- added a sampleFunction2DToSeries() method to the DatasetUtilities class;
- some changes to the ChartPanel class that help to work around a regression in
  JRE 1.6.0_10 relating to drawing in XOR mode.  Regarding this final point:
     * the default value for the useBuffer flag has changed to true, which means
       that all charts will, by default, be rendered into an off-screen image
       before being displayed in the ChartPanel;
     * the zoom rectangle is drawn using XOR mode *only* when the useBuffer
       flag has been set to false.
  For most usage, this should improve performance (but at the cost of using more
  memory for each ChartPanel used in your application);

###### Bug Fixes

- 2690293 : Problem with Javascript escape characters;
- 2617557 : StandardDialScale ignored tickLabelPaint;
- 2612649 : Stroke selection in plot property editor;
- 2583891 : SWTGraphics2D.fillPolygon() not implemented;
- 2564636 : Month constructor ignores Locale;
- 2502355 : ChartPanel sending multiple events;
- 2490803 : PeriodAxis.setRange() method doesn't take into account that the axis
          displays whole periods;

In addition, a bug in the SlidingCategoryDataset class has been fixed, the
correct outline paint is now used by GradientXYBarPainter, a new method
has been added to the ImageMapUtilities class to escape special characters
in Javascript strings to avoid problems with the OverLIB and DynamicDrive
tooltips, and there are some important fixes in the LogAxis class.

This release passes 2110 JUnit tests (0 failures) on JRE 1.6.0_12.


##### Version 1.0.12 (31-Dec-2008)
        - added support for minor tick marks, mapping datasets to more than
          one axis, and numerous bug fixes - see the NEWS and ChangeLog files
          for details.

##### Version 1.0.11 (18-Sep-2008)
        - this release includes a new chart theming mechanism, and numerous
          other feature enhancements and bug fixes - see the NEWS and ChangeLog
          files for details.

##### Version 1.0.10 (8-Jun-2008)
        - another general maintenance release - see the NEWS and ChangeLog
          files for more details.

##### Version 1.0.9 (4-Jan-2008)
        - this release fixes a security advisory with respect to the HTML
          image maps generated by JFreeChart - see the NEWS file for more
          information.  In addition, a number of bugs have been fixed.

##### Version 1.0.8 (23-Nov-2007)
        - primarily a bug fix release.  See the NEWS and ChangeLog files for a
          more detailed description of the changes in this release.

##### Version 1.0.7 (14-Nov-2007)
        - See the NEWS and ChangeLog files for a more detailed description of
          the changes in this release.

##### Version 1.0.6 (15-Jun-2007)
        - the VectorRenderer and associated dataset classes have been promoted
          to the standard API from the 'experimental' source tree.  See the
          NEWS and ChangeLog files for a more detailed description of the
          changes in this release.

##### Version 1.0.5 (23-Mar-2007)
        - this release contains a new DeviationRenderer, enhancements to a
          number of existing classes and numerous bug fixes, see the NEWS
          and ChangeLog files for details.

##### Version 1.0.4 (9-Feb-2007)
        - this release contains both new features and bug fixes, see the NEWS
          and ChangeLog files for details.

##### Version 1.0.3 (17-Nov-2006)
        - this release contains a new DialPlot class (in 'experimental') that
          will hopefully replace the MeterPlot class, once the API has been
          polished a little, plus many other new features and bug fixes.  See
          the NEWS and ChangeLog files for details.

##### Version 1.0.2 (25-Aug-2006)
        - this release contains both new features and bug fixes, see the NEWS
          and ChangeLog files for details.

##### Version 1.0.1 (27-Jan-2006)
        - primarily a bug fix release, see the NEWS and ChangeLog files for
          details.

##### Version 1.0.0 (2-Dec-2005)
        - the first stable release of the JFreeChart class library, all future
          releases in the 1.0.x series will aim to maintain backward
          compatibility with this release;
        - see the ChangeLog file for a detailed list of changes.

##### Version 1.0.0-rc3 (28-Nov-2005)
        - the third "release candidate" for version 1.0.0, this release
          fixes some issues with the 1.0.0-rc2 release (mainly concerning
          packaging of resource bundles for localisation).
        - if no significant problems are reported in the next few days,
          the 1.0.0 "final" release will be posted on 2-Dec-2005.

##### Version 1.0.0-rc2 (25-Nov-2005)
            - the second "release candidate" for version 1.0.0.  If no problems
              are reported, 1.0.0 "final" will be released on 2-Dec-2005.
            - see the ChangeLog file for a detailed list of changes in this
              release.

##### Version 1.0.0-rc1 (2-Jun-2005)
            - this is a "release candidate" for version 1.0.0.  If no
              significant API problems are reported, this release will be
              re-released as version 1.0.0.
            - see CHANGELOG.txt for further details.

##### Version 1.0.0-pre2 (10-Mar-2005)
         - see CHANGELOG.txt for further details.

##### Version 1.0.0-pre1 (29-Nov-2004)
         - see CHANGELOG.txt for further details.

##### Version 0.9.21 (9-Sep-2004)
         - added new axes: PeriodAxis and ModuloAxis.
         - split org.jfree.data and org.jfree.chart.renderer into
           subpackages for 'category' and 'xy' charts.
         - Sun PNG encoder is now used, if available.
         - a new demo application makes it easier to preview the
           chart types that JFreeChart can create.
         - added a new series visibility flag to the AbstractRenderer
           class.
         - added support for GradientPaint in interval markers.
         - see CHANGELOG.txt for further details.

##### Version 0.9.20 (7-Jun-2004)
         - primarily bug fixes, see CHANGELOG.txt for further details.

##### Version 0.9.19 (28-May-2004)
         - added methods to XYDataset that return double primitives;
         - removed distinction between "primary" and "secondary" datasets,
           renderers and axes;
         - added fixed legend item options to CategoryPlot and XYPlot;
         - legend changes by Barek Naveh;
         - removed Log4j dependency;
         - many, many bug fixes;
         - see CHANGELOG.txt for further details.

##### Version 0.9.18 (15-Apr-2004)
         - new legend anchor options;
         - fixed broken JPEG export;
         - fixed title size problems;
         - various other bug fixes;

##### Version 0.9.17 (26-Mar-2004)
         - pie chart enhancements for labelling, shading and multiple pie
           charts (2D or 3D) on a single plot;
         - new PolarPlot class added;
         - XYSeries can now be sorted or unsorted;
         - createBufferedImage() method can now scale charts;
         - domain and range markers now support intervals;
         - item labels are now supported by some XYItemRenderers;
         - tooltip and item label generators now use MessageFormat class;
         - added new XYBarDataset class;
         - added transparency support to PNG export;
         - numerous other small enhancements and bug fixes, see the
           CHANGELOG.txt file for more details;

##### Version 0.9.16 (09-Jan-2004)
         - this release contains bug fixes and some minor feature
           enhancements (title and category label wrapping, legend shape
           scaling, enhanced performance for the DefaultTableXYDataset class);
         - added Spanish localisation files;

##### Version 0.9.15 (28-Nov-2003)
         - the focus of this release is bug fixes - quite a number of issues
           have been resolved, please check the bug database for details;
         - added a new Wafer Map chart type;
         - added a cyclic axis;
         - added localisation files for _ru;

##### Version 0.9.14 (17-Nov-2003)
         - implemented zooming for the FastScatterPlot class;
         - added item label support for stacked bar charts, and new fall back
           options for item labels that don't fit within bars;
         - modified the CategoryAxis class to allow additional options for the
           alignment and rotation of category labels;
         - addition of the AxisState class, used in the drawing of axes to
           eliminate a bug when multiple threads draw the same axis
           simultaneously;
         - provided additional attributes in the DateTickUnit class to improve
           labelling on a segmented DateAxis;
         - added support for GradientPaint in bar charts;
         - updated the PNGEncoder;
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

Added multiple pie charts based on
        CategoryDataset.  Updated logarithmic axes.  Improved URL
        support for image map generation. Moved the com.jrefinery.data
        package from JCommon to JFreeChart. Added simple framework for
        chart annotations. Improved control over renderers. Duplicate
        x-values now allowed in XYSeries. Optional category label
        skipping in category axes. Added CategoriesPaint attribute to
        AbstractCategoryItemRenderer.  Added new attributes to
        MeterPlot class. Updated 3D pie chart to observe start angle
        and direction, and also foreground alpha < 1.0. Improved
        Javadoc comments. New demo applications, including:
        AnnotationDemo1, EventFrequencyDemo, JDBCCategoryChartDemo,
        JDBCPieChartDemo, JDBCXYChartDemo and MinMaxCategoryPlotDemo.
        Bug fixes:
        - negative percentages on PiePlot.
        - added listener notification to setXXXAxis(...) methods.
        - fixed DomainInfo method name clash.
        - added DomainIsPointsInTime flag to TimeSeriesCollection to
          give better control over auto range on axis for time series
          charts.
        - axis margins for date axes are no longer hard-coded.
        - fix for ordering of categories in JdbcCategoryDataset.
        - added check for null axis in mouse click handler.

        The CVS repository at SourceForge has also been restructured
        to match the distribution directory layout.

##### Version 0.9.2 (28-Jun-2002) 

PiePlot now has startAngle and direction
        attributes.  Added support for image map generation.  Added a
        new Pie3DPlot class. Added label drawing code to bar
        renderers. Added optional range markers to horizontal number
        axis.  Added bar clipping to avoid PRExceptions in bar
        charts.  JFreeChartDemo has been modified and now includes
        examples of the dial and thermometer plots.
        Bug fixes:
        - auto range for VerticalNumberAxis when zero is forced to be
          included in the range.
        - fixed null pointer exception in StackedVerticalBarRenderer3D;
        - Added get/set methods for min/max chart drawing dimensions
          in ChartPanel;
        - HorizontalIntervalBarRenderer now handles single category;
        - verticalTickLabels now possible in HorizontalNumberAxis3D;
        - removed unnecessary imports;

##### Version 0.9.1 (14-Jun-2002) 

Bug fixes and Javadoc updates.
        - fixed auto range calculation for category plots;
        - fixed event notification for XYPlot;
        - fixed auto axis range for Gantt charts;
        - check for null popup menu in ChartPanel.mouseDragged;
        - new checks for null info in renderers;
        - range markers now drawn only if in visible axis range;

##### Version 0.9.0 (7-Jun-2002) 

New plots including an area chart, a horizontal
        3D bar chart, a Gantt chart and a thermometer chart.
        Combination plots have been reworked to provide a
        simpler framework, and extends to allow category plots to be
        combined. There is now a facility to add a ChartMouseListener
        to the ChartPanel (formerly JFreeChartPanel).  An interactive
        zooming feature (experimental at this point) is now available
        for XYPlots.  A new Polish translation has been added. Several
        fixes have been applied to the default tool tip generators.  A
        workaround has been added to fix the alignment between time
        series charts and the date axis.  There are some improvements
        to the VerticalLogarithmicAxis class, and now a corresponding
        HorizontalLogarithmicAxis class.  Additional demonstration
        applications have been added.  Fixed the popup menu bug.

##### Version 0.8.1 (5-Apr-2002) 

Localised resource bundles for French, German and
        Spanish languages (thanks to Anthony Boulestreau, Thomas Meier
        and Hans-Jurgen Greiner for the translations).  An area XY
        plot and meter chart contributed by Hari.  Symbol charts
        contributed by Anthony Boulestreau. An improved
        CandleStickRenderer class from Sylvain Vieujot.  Updated
        servlet code from Bryan Scott.  XYItemRenderers now have a
        change listener mechanism and therefore do not have to be
        immutable.  Additional demonstration applications for
        individual chart types. Minor bug fixes.

##### Version 0.8.0 (22-Mar-2002) 

All the category plots are now controlled
        through the one class (CategoryPlot) with plug-in renderers.
        Added a ResourceBundle for user interface items that require
        localisation. Added a logarithmic axis class contributed by
        Mike Duffy and some new JDBC and servlet code contributed by
        Bryan Scott.  Updated the JCommon class library to improve
        handling of time periods in different time zones.

##### Version 0.7.4 (6-Mar-2002) 

Bug fixes in the JCommon Class Library. Various
        Javadoc comment updates.  Some minor changes to the
        code. Added new domain name (http://www.object-refinery.com)
        in the source headers.

##### Version 0.7.3 (14-Feb-2002) 

Bug fixes.

##### Version 0.7.2 (8-Feb-2002) 

Integrated the WindPlot code from Achilleus
        Mantzios. Added an optional background image for the
        JFreeChart class, and another optional background image for
        the Plot class.  Added alpha-transparency for the plot
        foreground and background.  Added new pie chart label types
        that show values.  Fixed a bug with the legend that results in
        a loop at small chart sizes. Added some tooltip methods that
        were missing from the previous version. Changed the Insets
        class on chart titles to a new Spacer class that will allow
        for relative or absolute insets (the plan is to eventually
        replace all Insets in the JFreeChart classes).  Fixed a bug in
        the setAutoRangeIncludesZero method of the NumberAxis class.
        Added the instructions that were missing from the copies of
        the GNU Lesser General Public Licence included with JFreeChart.

##### Version 0.7.1 (25-Jan-2002) 

Added tooltips, crosshairs and zooming
        functions, thanks to Jonathan Nash and Hans-Jurgen Greiner
        for contributing the code that these features are based on.
        Moved the combination charts into the package
        com.jrefinery.chart.combination, made a number of other small
        API changes and fixed some bugs.  Removed the Javadoc HTML
        from the download to save space (you can regenerate it from
        the source code if you need it).

##### Version 0.7.0 (11-Dec-2001) 

New combination plots developed by Bill
        Kelemen.  Added Wolfgang Irler's servlet demo to the standard
        download.  The About window in the demo application now
        includes a list of developers that have contributed to the
        project.

##### Version 0.6.0 (27-Nov-2001) 

New plots including scatter plot, stacked bar
        charts and 3D bar charts.  Improved pie chart.  Data
        interfaces and classes moved to the JCommon class library.
        New properties to control spacing on bar charts.  New
        auto-tick mechanism.  JFreeChartPanel now incorporates
        buffering, and popup menu.  Javadocs revised.  Fixed numerous
        bugs from version 0.5.6.  Demo application updated.
 
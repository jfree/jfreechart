/** 
 * JFreeChart module.
 */
module org.jfree.chart {
    requires java.desktop;
    exports org.jfree.chart;
    exports org.jfree.chart.annotations;
    exports org.jfree.chart.api;
    exports org.jfree.chart.axis;
    exports org.jfree.chart.date;
    exports org.jfree.chart.entity;
    exports org.jfree.chart.event;
    exports org.jfree.chart.imagemap;
    exports org.jfree.chart.labels;
    exports org.jfree.chart.plot.compass;
    exports org.jfree.chart.plot;
    exports org.jfree.chart.plot.dial;
    exports org.jfree.chart.plot.flow;
    exports org.jfree.chart.plot.pie;
    exports org.jfree.chart.renderer;
    exports org.jfree.chart.renderer.category;
    exports org.jfree.chart.renderer.xy;
    exports org.jfree.chart.swing;
    exports org.jfree.chart.swing.editor;
    exports org.jfree.chart.text;
    exports org.jfree.chart.text.format;
    exports org.jfree.chart.title;
    exports org.jfree.chart.urls;
    exports org.jfree.chart.util;
    exports org.jfree.data;
    exports org.jfree.data.category;
    exports org.jfree.data.flow;    
    exports org.jfree.data.function;    
    exports org.jfree.data.gantt;
    exports org.jfree.data.general;
    exports org.jfree.data.io;
    exports org.jfree.data.json;
    exports org.jfree.data.statistics;
    exports org.jfree.data.time;
    exports org.jfree.data.time.ohlc;
    exports org.jfree.data.xml;
    exports org.jfree.data.xy;
}
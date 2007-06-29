/* --------------
 * DialDemo1.java
 * --------------
 * (C) Copyright 2006, by Object Refinery Limited.
 */

package org.jfree.experimental.chart.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.plot.dial.DialPlot;
import org.jfree.experimental.chart.plot.dial.SimpleDialFrame;
import org.jfree.experimental.chart.plot.dial.DialTextAnnotation;
import org.jfree.experimental.chart.plot.dial.StandardDialRange;
import org.jfree.experimental.chart.plot.dial.StandardDialScale;
import org.jfree.experimental.chart.plot.dial.DialBackground;
import org.jfree.experimental.chart.plot.dial.DialCap;
import org.jfree.experimental.chart.plot.dial.DialPointer;
import org.jfree.experimental.chart.plot.dial.DialValueIndicator;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 * A sample application showing the use of a {@link DialPlot}.
 */
public class DialDemo1 extends JFrame implements ChangeListener {
    
    /** A slider to update the dataset value. */
    JSlider slider;
    
    /** The dataset. */
    DefaultValueDataset dataset;
    
    /** 
     * Creates a new instance.
     *
     * @param title  the frame title.
     */
    public DialDemo1(String title) {
        super(title);
        
        this.dataset = new DefaultValueDataset(10.0);
        
        // get data for diagrams
        DialPlot plot = new DialPlot();
        plot.setView(0.0, 0.0, 1.0, 1.0);
        plot.setDataset(this.dataset);
        SimpleDialFrame dialFrame = new SimpleDialFrame();
        dialFrame.setBackgroundPaint(Color.lightGray);
        dialFrame.setForegroundPaint(Color.darkGray);
        plot.setDialFrame(dialFrame);
        
        GradientPaint gp = new GradientPaint(new Point(), 
                new Color(255, 255, 255), new Point(), 
                new Color(170, 170, 220));
        DialBackground db = new DialBackground(gp);
        db.setGradientPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.VERTICAL));
        plot.setBackground(db);
        
        DialTextAnnotation annotation1 = new DialTextAnnotation("Temperature");
        annotation1.setFont(new Font("Dialog", Font.BOLD, 14));
        annotation1.setRadius(0.7);
        
        plot.addLayer(annotation1);

        DialValueIndicator dvi = new DialValueIndicator(0, "c");
        plot.addLayer(dvi);
        
        StandardDialScale scale = new StandardDialScale(-40, 60, -120, -300);
        scale.setTickRadius(0.88);
        scale.setTickLabelOffset(0.15);
        scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 14));
        plot.addScale(0, scale);
        
        StandardDialRange range = new StandardDialRange(40.0, 60.0, Color.red);
        range.setInnerRadius(0.52);
        range.setOuterRadius(0.55);
        plot.addLayer(range);
        
        StandardDialRange range2 = new StandardDialRange(10.0, 40.0, 
                Color.orange);
        range2.setInnerRadius(0.52);
        range2.setOuterRadius(0.55);
        plot.addLayer(range2);

        StandardDialRange range3 = new StandardDialRange(-40.0, 10.0, 
                Color.green);
        range3.setInnerRadius(0.52);
        range3.setOuterRadius(0.55);
        plot.addLayer(range3);

        DialPointer needle = new DialPointer.Pointer();
        plot.addLayer(needle);
        
        DialCap cap = new DialCap();
        cap.setRadius(0.10);
        plot.setCap(cap);
        
        JFreeChart chart1 = new JFreeChart(plot);
        chart1.setTitle("Demo Dial 1");
        ChartPanel cp1 = new ChartPanel(chart1);
        cp1.setPreferredSize(new Dimension(400, 400));
        this.slider = new JSlider(-40, 60);
        this.slider.setMajorTickSpacing(10);
        this.slider.setPaintLabels(true);
        this.slider.addChangeListener(this);
        JPanel content = new JPanel(new BorderLayout());
        content.add(cp1);
        content.add(this.slider, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(content);
    }
    
    /**
     * Handle a change in the slider by updating the dataset value.  This
     * automatically triggers a chart repaint.
     * 
     * @param e  the event.
     */
    public void stateChanged(ChangeEvent e) {
        this.dataset.setValue(new Integer(this.slider.getValue()));
    }

    /**
     * Starting point for the demo application.
     * 
     * @param args  ignored.
     */
    public static void main(String[] args) {
        DialDemo1 app = new DialDemo1("JFreeChart - Demo Dial 1");
        app.pack();
        app.setVisible(true);
    }

}

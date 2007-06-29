/* --------------
 * DialDemo2.java
 * --------------
 * (C) Copyright 2006, by Object Refinery Limited.
 */

package org.jfree.experimental.chart.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.experimental.chart.plot.dial.DialBackground;
import org.jfree.experimental.chart.plot.dial.DialCap;
import org.jfree.experimental.chart.plot.dial.DialPlot;
import org.jfree.experimental.chart.plot.dial.DialPointer;
import org.jfree.experimental.chart.plot.dial.DialTextAnnotation;
import org.jfree.experimental.chart.plot.dial.DialValueIndicator;
import org.jfree.experimental.chart.plot.dial.SimpleDialFrame;
import org.jfree.experimental.chart.plot.dial.StandardDialScale;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 * A sample application showing the use of a {@link DialPlot}.
 */
public class DialDemo2 extends JFrame implements ChangeListener {
    
    /** The first dataset. */
    DefaultValueDataset dataset1;
    
    /** The second dataset. */
    DefaultValueDataset dataset2;
    
    /** A slider to update the first dataset value. */
    JSlider slider1;
    
    /** A slider to update the second dataset value. */
    JSlider slider2;
    
    /** 
     * Creates a new instance.
     *
     * @param title  the frame title.
     */
    public DialDemo2(String title) {
        super(title);
        
        this.dataset1 = new DefaultValueDataset(10.0);
        this.dataset2 = new DefaultValueDataset(50.0);
        
        // get data for diagrams
        DialPlot plot = new DialPlot();
        plot.setView(0.0, 0.0, 1.0, 1.0);
        plot.setDataset(0, this.dataset1);
        plot.setDataset(1, this.dataset2);
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
        dvi.setFont(new Font("Dialog", Font.PLAIN, 10));
        dvi.setOutlinePaint(Color.darkGray);
        dvi.setRadius(0.60);
        dvi.setAngle(-103.0);
        plot.addLayer(dvi);
        
        DialValueIndicator dvi2 = new DialValueIndicator(1, "c");
        dvi2.setFont(new Font("Dialog", Font.PLAIN, 10));
        dvi2.setOutlinePaint(Color.red);
        dvi2.setRadius(0.60);
        dvi2.setAngle(-77.0);
        plot.addLayer(dvi2);
        
        StandardDialScale scale = new StandardDialScale(-40, 60, -120, -300);
        scale.setTickRadius(0.88);
        scale.setTickLabelOffset(0.15);
        scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 14));
        plot.addScale(0, scale);

        StandardDialScale scale2 = new StandardDialScale(0, 100, -120, -300);
        scale2.setTickRadius(0.50);
        scale2.setTickLabelOffset(0.15);
        scale2.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        scale2.setMajorTickPaint(Color.red);
        plot.addScale(1, scale2);
        plot.mapDatasetToScale(1, 1);
        
        DialPointer needle2 = new DialPointer.Pin(1);
        needle2.setRadius(0.55);
        plot.addLayer(needle2);

        DialPointer needle = new DialPointer.Pointer(0);
        plot.addLayer(needle);
        
        DialCap cap = new DialCap();
        cap.setRadius(0.10);
        plot.setCap(cap);
        
        JFreeChart chart1 = new JFreeChart(plot);
        chart1.setTitle("Dial Demo 2");
        ChartPanel cp1 = new ChartPanel(chart1);
        cp1.setPreferredSize(new Dimension(400, 400));
        
        JPanel sliderPanel = new JPanel(new GridLayout(2, 2));
        sliderPanel.add(new JLabel("Outer Needle:"));
        sliderPanel.add(new JLabel("Inner Needle:"));
        this.slider1 = new JSlider(-40, 60);
        this.slider1.setMajorTickSpacing(20);
        this.slider1.setPaintTicks(true);
        this.slider1.setPaintLabels(true);
        this.slider1.addChangeListener(this);
        sliderPanel.add(this.slider1);
        sliderPanel.add(this.slider1);
        this.slider2 = new JSlider(0, 100);
        this.slider2.setMajorTickSpacing(20);
        this.slider2.setPaintTicks(true);
        this.slider2.setPaintLabels(true);
        this.slider2.addChangeListener(this);
        sliderPanel.add(this.slider2);
        JPanel content = new JPanel(new BorderLayout());
        content.add(cp1);
        content.add(sliderPanel, BorderLayout.SOUTH);
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
        this.dataset1.setValue(new Integer(this.slider1.getValue()));
        this.dataset2.setValue(new Integer(this.slider2.getValue()));
    }

    /**
     * Starting point for the demo application.
     * 
     * @param args  ignored.
     */
    public static void main(String[] args) {
        DialDemo2 app = new DialDemo2("JFreeChart - Dial Demo 2");
        app.pack();
        app.setVisible(true);
    }

}

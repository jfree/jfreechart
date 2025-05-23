package org.jfree.chart.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jfree.chart.plot.PolarPlot;

import static org.jfree.chart.swing.ChartPanel.localizationResources;

public class PolarChartPopupMenu extends JPopupMenu {
    private static final String POLAR_ZOOM_IN_ACTION_COMMAND = "Polar Zoom In";
    private static final String POLAR_ZOOM_OUT_ACTION_COMMAND
            = "Polar Zoom Out";

    /** Auto range command string. */
    private static final String POLAR_AUTO_RANGE_ACTION_COMMAND
            = "Polar Auto Range";


    protected JPopupMenu createPopupMenu(boolean properties, boolean copy,
            boolean save, boolean print, boolean zoom, JPopupMenu result) {

       int zoomInIndex = getPopupMenuItem(result,
               localizationResources.getString("Zoom_In"));
       int zoomOutIndex = getPopupMenuItem(result,
               localizationResources.getString("Zoom_Out"));
       int autoIndex = getPopupMenuItem(result,
               localizationResources.getString("Auto_Range"));
       if (zoom) {
           JMenuItem zoomIn = new JMenuItem(
                   localizationResources.getString("Zoom_In"));
           zoomIn.setActionCommand(POLAR_ZOOM_IN_ACTION_COMMAND);
           zoomIn.addActionListener((ActionListener) this);

           JMenuItem zoomOut = new JMenuItem(
                   localizationResources.getString("Zoom_Out"));
           zoomOut.setActionCommand(POLAR_ZOOM_OUT_ACTION_COMMAND);
           zoomOut.addActionListener((ActionListener) this);

           JMenuItem auto = new JMenuItem(
                   localizationResources.getString("Auto_Range"));
           auto.setActionCommand(POLAR_AUTO_RANGE_ACTION_COMMAND);
           auto.addActionListener((ActionListener) this);

           if (zoomInIndex != -1) {
               result.remove(zoomInIndex);
           }
           else {
               zoomInIndex = result.getComponentCount() - 1;
           }
           result.add(zoomIn, zoomInIndex);
           if (zoomOutIndex != -1) {
               result.remove(zoomOutIndex);
           }
           else {
               zoomOutIndex = zoomInIndex + 1;
           }
           result.add(zoomOut, zoomOutIndex);
           if (autoIndex != -1) {
               result.remove(autoIndex);
           }
           else {
               autoIndex = zoomOutIndex + 1;
           }
           result.add(auto, autoIndex);
       }
       return result;
    }
    private int getPopupMenuItem(JPopupMenu menu, String text) {
        int index = -1;
        for (int i = 0; (index == -1) && (i < menu.getComponentCount()); i++) {
            Component comp = menu.getComponent(i);
            if (comp instanceof JMenuItem) {
                JMenuItem item = (JMenuItem) comp;
                if (text.equals(item.getText())) {
                    index = i;
                }
            }
        }
        return index;
    }
}

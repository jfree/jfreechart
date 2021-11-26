package org.jfree.issues.issues167;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.chart.swing.ChartFrame;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.Test;

/**
 * 类名: Issues167
 * 描述:
 *
 * <p>
 * author:
 * Date:2021/11/18
 * Time:17:17
 * Version:
 */
public class Issues167 {

    @Test
    public void test1() throws InterruptedException {
        //建立默认的饼图
        DefaultPieDataset ds = new DefaultPieDataset();
        ds.setValue("test01", 2);
        ds.setValue("test02", 3);
        ds.setValue("test03", 40);
        ds.setValue("test04", 15);
        ds.setValue("test05", 20);
        ds.setValue("test06", 20);

        //参数：1->标题 2-> 数据集 3->是否显示legend(在图的下方显示颜色块标注) 4->是否显示提示 5->图中是否存在url
        JFreeChart chart = ChartFactory.createPieChart("", ds, true, true, false);
        //PiePlot是图中饼图的上一级区域
        PiePlot pieplot = (PiePlot) chart.getPlot();
        pieplot.setSimpleLabels(true);

        //下面的标题是Frame的标题
        ChartFrame chartFrame = new ChartFrame("", chart);
        chartFrame.pack();
        chartFrame.setVisible(true);
        Thread.sleep(5000);
    }

    @Test
    public void test2() throws InterruptedException {
        //建立默认的饼图
        DefaultPieDataset ds = new DefaultPieDataset();
        ds.setValue("test01", 2);
        ds.setValue("test02", 3);
        ds.setValue("test03", 80);
        ds.setValue("test04", 15);


        //参数：1->标题 2-> 数据集 3->是否显示legend(在图的下方显示颜色块标注) 4->是否显示提示 5->图中是否存在url
        JFreeChart chart = ChartFactory.createPieChart("", ds, true, true, false);
        //PiePlot是图中饼图的上一级区域
        PiePlot pieplot = (PiePlot) chart.getPlot();
        pieplot.setSimpleLabels(true);

        //下面的标题是Frame的标题
        ChartFrame chartFrame = new ChartFrame("", chart);
        chartFrame.pack();
        chartFrame.setVisible(true);
        Thread.sleep(5000);
    }
}

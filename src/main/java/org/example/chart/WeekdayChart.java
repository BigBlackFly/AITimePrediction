package org.example.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;

public class WeekdayChart extends JFrame {

    public WeekdayChart() {
        // 创建示例数据
        double[][] data1 = {{0, 1, 2, 3, 4}, {17.1, 17.2, 19.3, 16.7, 18.1}};
        double[][] data2 = {{0, 1, 2, 3, 4}, {18.1, 18.2, 18.3, 17.8, 19.3}};
        double[][] data3 = {{0, 1, 2, 3, 4}, {19.1, 19.1, 18.7, 18.8, 20.4}};
        double[][] data4 = {{0, 1, 2, 3, 4}, {16.5, 17.1, 17.1, 16.8, 18.1}};
        double[][] data5 = {{0, 1, 2, 3, 4}, {17.5, 18.3, 18.3, 17.9, 19.3}};
        double[][] data6 = {{0, 1, 2, 3, 4}, {19.3, 19.2, 18.1, 18.8, 20.4}};
        double[][] data7 = {{0, 1, 2, 3, 4}, {17.3, 16.7, 17.0, 17.2, 17.9}};
        double[][] data8 = {{0, 1, 2, 3, 4}, {18.2, 17.8, 18.0, 17.7, 19.3}};
        double[][] data9 = {{0, 1, 2, 3, 4}, {19.4, 19.3, 18.7, 18.9, 19.7}};

        // 创建数据集
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("1", data1);
        dataset.addSeries("2", data2);
        dataset.addSeries("3", data3);
        dataset.addSeries("4", data4);
        dataset.addSeries("5", data5);
        dataset.addSeries("6", data6);
        dataset.addSeries("7", data7);
        dataset.addSeries("8", data8);
        dataset.addSeries("9", data9);

        // 创建散点图
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Weekday vs Home Time",
                "Weekday",
                "Home Time",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        // 设置X轴为特定字符串刻度
        String[] symbols = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        SymbolAxis domainAxis = new SymbolAxis("Weekday", symbols);
        plot.setDomainAxis(domainAxis);

        // 设置Y轴起始值
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(12, 24);

        // 创建图表面板
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);

        // 设置窗口属性
        setTitle("Multi-Series Pair Plot Chart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeekdayChart example = new WeekdayChart();
            example.setVisible(true);
        });
    }
}

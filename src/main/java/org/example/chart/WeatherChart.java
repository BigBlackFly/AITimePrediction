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

public class WeatherChart extends JFrame {

    public WeatherChart() {
        // 创建示例数据
        double[][] data1 = {{0, 1, 2}, {17.1, 18.1, 19.1}};
        double[][] data2 = {{0, 1, 2}, {17.2, 18.2, 19.1}};
        double[][] data3 = {{0, 1, 2}, {17.3, 18.3, 18.7}};
        double[][] data4 = {{0, 1, 2}, {16.7, 17.8, 18.8}};
        double[][] data5 = {{0, 1, 2}, {18.1, 19.3, 20.4}};
        double[][] data6 = {{0, 1, 2}, {17.5, 17.5, 19.1}};
        double[][] data7 = {{0, 1, 2}, {17.2, 18.2, 19.1}};
        double[][] data8 = {{0, 1, 2}, {17.3, 18.3, 18.1}};
        double[][] data9 = {{0, 1, 2}, {16.7, 17.8, 18.8}};
        double[][] data10 = {{0, 1, 2}, {18.1, 19.3, 20.4}};

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
        dataset.addSeries("10", data10);

        // 创建散点图
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Weather vs Home Time",
                "Weather",
                "Home Time",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        // 设置X轴为特定字符串刻度
        String[] symbols = {"Sunny", "Rainy", "Snowy"};
        SymbolAxis domainAxis = new SymbolAxis("Weather", symbols);
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
            WeatherChart example = new WeatherChart();
            example.setVisible(true);
        });
    }
}

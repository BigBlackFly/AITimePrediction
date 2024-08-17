package org.example.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;

public class EpochsEffectChart extends JFrame {

    public EpochsEffectChart() {
        // 创建示例数据
        double[][] data0 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, {20.9300, 23.1344, 20.3166, 21.8605, 22.4493, 21.5542, 19.6988, 22.7515, 23.5867, 23.3488, 20.6313, 20.3037, 20.4639, 22.2744}};
        double[][] data1 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, {19.8466, 21.8463, 20.6591, 20.9111, 20.3685, 20.3969, 22.8151, 21.3268, 21.7153, 20.4450, 20.1288, 21.3455, 20.7371, 21.0411}};
        double[][] data2 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, {19.5290, 19.5370, 19.5252, 19.3290, 20.1559, 19.4431, 20.0649, 19.4430, 19.5414, 19.3072, 20.0474, 19.9133, 19.2824, 19.2764}};
        double[][] data3 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, {20.0528, 19.4368, 19.5248, 20.0535, 19.5467, 20.0475, 19.2588, 19.5252, 19.7340, 20.0379, 20.0374, 19.5374, 19.7163, 19.4952}};
        double[][] data4 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, {19.5093, 19.8482, 19.9827, 19.5242, 20.0334, 19.5248, 20.0498, 19.8377, 20.0055, 19.5485, 20.0500, 19.4487, 20.1391, 19.4347}};


        // 创建数据集
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("EPOCHS = 200", data0);
        dataset.addSeries("EPOCHS = 250", data1);
        dataset.addSeries("EPOCHS = 500", data2);
        dataset.addSeries("EPOCHS = 750", data3);
        dataset.addSeries("EPOCHS = 1000", data4);

        // 创建散点图
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Home Time Ai Prediction",
                "Weekday && Weather",
                "Home Time",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        // 设置X轴为特定字符串刻度
        String[] symbols = {"Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy", "Fri, Snowy"};
        SymbolAxis domainAxis = new SymbolAxis("Weekday && Weather", symbols);
        plot.setDomainAxis(domainAxis);

        // 设置Y轴起始值
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(12, 24);

        // 设置渲染器以显示线条和数据点
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesLinesVisible(3, true);
        renderer.setSeriesShapesVisible(3, true);
        renderer.setSeriesLinesVisible(4, true);
        renderer.setSeriesShapesVisible(4, true);
        plot.setRenderer(renderer);

        // 创建图表面板
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);

        // 设置窗口属性
        setTitle("Multi-Series Pair Plot Chart");
        setSize(1400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EpochsEffectChart example = new EpochsEffectChart();
            example.setVisible(true);
        });
    }
}

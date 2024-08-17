package org.example.demo;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * 简单线性回归
 */
public class SimpleLinearRegression {

    public static void main(String[] args) {
        // 示例数据：正整数序列
        double[][] featuresArray = {{1}, {2}, {3}, {4}, {5}}; // 自变量
        double[][] labelsArray = {{2}, {4}, {6}, {8}, {10}}; // 因变量

        // 创建INDArray
        INDArray features = Nd4j.create(featuresArray);
        INDArray labels = Nd4j.create(labelsArray);

        // 创建数据集
        DataSet dataSet = new DataSet(features, labels);

        // 创建线性回归模型
        MultiLayerNetwork model = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .updater(new Sgd(0.01)) // 学习率
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(1)  // 输入特征数量
                        .nOut(1) // 隐藏层神经元数量
                        .activation(Activation.IDENTITY) // 激活函数
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(1)
                        .nOut(1)
                        .build())
                .build());

        model.init();
        model.setListeners(new ScoreIterationListener(10));

        // 训练模型
        for (int i = 0; i < 1000; i++) {
            model.fit(dataSet);
        }

        // 预测新数据
        double[] newInput = {6}; // 需要预测的新数据
        INDArray input = Nd4j.create(new double[][]{newInput});
        INDArray prediction = model.output(input);

        System.out.println("Predicted value for input " + newInput[0] + " is: " + prediction.getDouble(0));
    }
}

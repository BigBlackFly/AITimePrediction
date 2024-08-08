package org.example;

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

public class OffWorkTimePrediction {

    public static void main(String[] args) {
        // 示例数据：前面的数字和目标值
        double[][] featuresArray = {{1}, {1}, {1}, {1}, {1}, {1}, {1}}; // 相同的输入，因为是同一个人。后续考虑按照星期几分组
        double[][] labelsArray = {{18.0}, {19.0}, {18.5}, {20.5}, {18.0}, {18.5}, {18.5}}; // 输出为这个人的下班时间

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
                        .nOut(5) // 隐藏层神经元数量（可以调整）
                        .activation(Activation.RELU) // 激活函数
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(5) // 与隐藏层的神经元数量相同
                        .nOut(1) // 输出特征数量
                        .build())
                .build());

        model.init();
        model.setListeners(new ScoreIterationListener(10));

        // 训练模型
        for (int i = 0; i < 1000; i++) {
            model.fit(dataSet);
        }

        // 预测下一个数字
        double[] newInput = {1}; // 预测这个人的下班时间
        INDArray input = Nd4j.create(new double[][]{newInput});
        INDArray prediction = model.output(input);

        System.out.println("Predicted value for input " + newInput[0] + " is: " + prediction.getDouble(0));
    }
}

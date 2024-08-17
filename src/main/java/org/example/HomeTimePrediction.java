package org.example;

import org.deeplearning4j.datasets.iterator.utilty.ListDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.example.data.DataBean;
import org.example.data.DataProvider;
import org.example.data.Weather;
import org.example.data.Weekday;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;
import java.util.List;

public class HomeTimePrediction {

    private static final int SEED = 123;
    private static final double LEARNING_RATE = 0.001;
    private static final int BATCH_SIZE = 10;
    private static final int EPOCHS = 1000;

    private static DataSetIterator getTrainingData() {
        List<DataSet> dataList = new ArrayList<>();
        List<DataBean> dataset = DataProvider.provideData();

        for (DataBean data : dataset) {
            INDArray featureMatrix = createInputArray(data.weather, data.weekday);
            INDArray labelMatrix = createOutputArray(data.homeTime);
            dataList.add(new DataSet(featureMatrix, labelMatrix));
        }

        return new ListDataSetIterator<>(dataList, BATCH_SIZE);
    }

    private static MultiLayerNetwork buildTrainingModel() {
        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
//                .seed(SEED)
                .updater(new Adam(LEARNING_RATE))
                .list()
                // a layer, to extract the simple patterns between the data.
                .layer(new DenseLayer.Builder().nIn(8).nOut(20)
                        // RELU activation method is suitable for linear regression.
                        .activation(Activation.RELU)
                        .build())
                // one more layer, to extract the deeply-hidden patterns between the data.
                .layer(new DenseLayer.Builder().nIn(20).nOut(20)
                        .activation(Activation.RELU)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(20).nOut(1).build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(config);
        model.init();
        return model;
    }

    public static INDArray createInputArray(Weather weather, Weekday weekday) {
        double[] features = new double[8];

        // put the weather into the features array.
        double[] weatherOneHot = weather.getOneHotEncoding();
        System.arraycopy(weatherOneHot, 0, features, 0, 3);

        // put the weekday into the features array.
        double[] weekdayOneHot = weekday.getOneHotEncoding();
        System.arraycopy(weekdayOneHot, 0, features, 3, 5);

        // 2D array for features.
        return Nd4j.create(features, new int[]{1, 8});
    }

    public static INDArray createOutputArray(double homeTime) {
        // 2D array for the label.
        return Nd4j.create(new double[]{homeTime}, new int[]{1, 1});
    }

    public static void main(String[] args) {
        DataSetIterator trainData = getTrainingData();

        // train the model
        MultiLayerNetwork model = buildTrainingModel();
        model.setListeners(new ScoreIterationListener(10));
        for (int i = 0; i < EPOCHS; i++) {
            model.fit(trainData);
        }

        // Test the model with a sample input.
        INDArray testInput = createInputArray(Weather.Snowy, Weekday.Friday);
        INDArray prediction = model.output(testInput);
        System.out.println("Predicted home time: " + prediction);
    }
}

package org.example;

import org.deeplearning4j.datasets.iterator.utilty.ListDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;
import java.util.List;

public class MultiFeatureOffWorkTimePrediction {

    private static final int SEED = 123;
    private static final double LEARNING_RATE = 0.001;
    private static final int BATCH_SIZE = 10;
    private static final int EPOCHS = 1000;

    // Generate fixed data
    private static DataSetIterator generateFixedData() {
        List<DataSet> dataList = new ArrayList<>();

        String[] weatherOptions = {"Sunny", "Rainy", "Snowy"};
        double[][] weatherEncodings = {
                {1.0, 0.0, 0.0}, // Sunny
                {0.0, 1.0, 0.0}, // Rainy
                {0.0, 0.0, 1.0}  // Snowy
        };

        int[][] dataset = {
                {0, 1, 17}, // Sunny, Monday, 17:00
                {0, 2, 17}, // Sunny, Tuesday, 17:00
                {0, 3, 17}, // Sunny, Wednesday, 17:00
                {0, 4, 17}, // Sunny, Thursday, 17:00
                {0, 5, 18}, // Sunny, Friday, 18:00

                {1, 1, 18}, // Rainy, Monday, 18:00
                {1, 2, 18}, // Rainy, Tuesday, 18:00
                {1, 3, 18}, // Rainy, Wednesday, 18:00
                {1, 4, 18}, // Rainy, Thursday, 18:00
                {1, 5, 19}, // Rainy, Friday, 19:00

                {2, 1, 19}, // Snowy, Monday, 19:00
                {2, 2, 19}, // Snowy, Tuesday, 19:00
                {2, 3, 19}, // Snowy, Wednesday, 19:00
                {2, 4, 19}, // Snowy, Thursday, 19:00
                {2, 5, 20}, // Snowy, Friday, 20:00


                {0, 1, 17}, // Sunny, Monday, 17:00
                {0, 2, 17}, // Sunny, Tuesday, 17:00
                {0, 3, 17}, // Sunny, Wednesday, 17:00
                {0, 4, 17}, // Sunny, Thursday, 17:00
                {0, 5, 18}, // Sunny, Friday, 18:00

                {1, 1, 18}, // Rainy, Monday, 18:00
                {1, 2, 18}, // Rainy, Tuesday, 18:00
                {1, 3, 18}, // Rainy, Wednesday, 18:00
                {1, 4, 18}, // Rainy, Thursday, 18:00
                {1, 5, 19}, // Rainy, Friday, 19:00

                {2, 1, 19}, // Snowy, Monday, 19:00
                {2, 2, 19}, // Snowy, Tuesday, 19:00
                {2, 3, 19}, // Snowy, Wednesday, 19:00
                {2, 4, 19}, // Snowy, Thursday, 19:00
                {2, 5, 20}, // Snowy, Friday, 20:00

                {0, 1, 17}, // Sunny, Monday, 17:00
                {0, 2, 17}, // Sunny, Tuesday, 17:00
                {0, 3, 17}, // Sunny, Wednesday, 17:00
                {0, 4, 17}, // Sunny, Thursday, 17:00
                {0, 5, 18}, // Sunny, Friday, 18:00

                {1, 1, 18}, // Rainy, Monday, 18:00
                {1, 2, 18}, // Rainy, Tuesday, 18:00
                {1, 3, 18}, // Rainy, Wednesday, 18:00
                {1, 4, 18}, // Rainy, Thursday, 18:00
                {1, 5, 19}, // Rainy, Friday, 19:00

                {2, 1, 19}, // Snowy, Monday, 19:00
                {2, 2, 19}, // Snowy, Tuesday, 19:00
                {2, 3, 19}, // Snowy, Wednesday, 19:00
                {2, 4, 19}, // Snowy, Thursday, 19:00
                {2, 5, 20}, // Snowy, Friday, 20:00


                {0, 1, 17}, // Sunny, Monday, 17:00
                {0, 2, 17}, // Sunny, Tuesday, 17:00
                {0, 3, 17}, // Sunny, Wednesday, 17:00
                {0, 4, 17}, // Sunny, Thursday, 17:00
                {0, 5, 18}, // Sunny, Friday, 18:00

                {1, 1, 18}, // Rainy, Monday, 18:00
                {1, 2, 18}, // Rainy, Tuesday, 18:00
                {1, 3, 18}, // Rainy, Wednesday, 18:00
                {1, 4, 18}, // Rainy, Thursday, 18:00
                {1, 5, 19}, // Rainy, Friday, 19:00

                {2, 1, 19}, // Snowy, Monday, 19:00
                {2, 2, 19}, // Snowy, Tuesday, 19:00
                {2, 3, 19}, // Snowy, Wednesday, 19:00
                {2, 4, 19}, // Snowy, Thursday, 19:00
                {2, 5, 20}, // Snowy, Friday, 20:00
        };

        for (int[] data : dataset) {
            int weatherIndex = data[0];
            int weekday = data[1];
            int workHour = data[2];

            double[] features = new double[4];
            double[] weatherEncoding = weatherEncodings[weatherIndex];
            System.arraycopy(weatherEncoding, 0, features, 0, 3);
            features[3] = weekday;

            INDArray featureMatrix = Nd4j.create(features, new int[]{1, 4}); // 2D array for features
            INDArray labelMatrix = Nd4j.create(new double[]{workHour}, new int[]{1, 1}); // 2D array for label

            dataList.add(new DataSet(featureMatrix, labelMatrix));
        }

        return new ListDataSetIterator<>(dataList, BATCH_SIZE);
    }

    // Build model
    private static MultiLayerNetwork buildModel() {
        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .seed(SEED)
                .updater(new Adam(LEARNING_RATE))
                .list()
                .layer(new DenseLayer.Builder().nIn(4).nOut(10)
                        .activation(Activation.RELU)
                        .build())
                .layer(new DenseLayer.Builder().nIn(10).nOut(10)
                        .activation(Activation.RELU)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(10).nOut(1).build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(config);
        model.init();
        return model;
    }

    public static void main(String[] args) {
        DataSetIterator trainData = generateFixedData();

        MultiLayerNetwork model = buildModel();
        model.setListeners(new ScoreIterationListener(10));

        for (int i = 0; i < EPOCHS; i++) {
            model.fit(trainData);
        }

        // Test model with a sample input
        INDArray testInput = Nd4j.create(new double[]{1.0, 0.0, 0.0, 5}, new int[]{1, 4}); // Example: Sunny, Friday
        INDArray prediction = model.output(testInput);
        System.out.println("Predicted work hour: " + prediction);
    }
}

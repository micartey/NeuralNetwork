package me.micartey.neuralnetwork;

import me.micartey.neuralnetwork.functions.FunctionType;
import me.micartey.neuralnetwork.layer.Layer;
import me.micartey.neuralnetwork.layer.Neuron;
import me.micartey.neuralnetwork.train.DataRow;
import me.micartey.neuralnetwork.train.DataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class NeuralNetworkTest {

    @Test
    public void createNormal() {
        NeuralNetwork network = new NeuralNetwork(FunctionType.SIGMOID, 2, 3, 1);
        System.out.println(Arrays.toString(network.calculate(3.1, 0)));
    }

    @Test
    public void createAbstract() {
        NeuralNetwork network = new NeuralNetwork(new Layer(
                new Neuron(FunctionType.SIGMOID),
                new Neuron(FunctionType.SIGMOID)
        ), new Layer(
                new Neuron(FunctionType.SIGMOID),
                new Neuron(FunctionType.SIGMOID),
                new Neuron(FunctionType.SIGMOID)
        ), new Layer(
                new Neuron(FunctionType.BINARY)
        ));

        System.out.println(Arrays.toString(network.calculate(3.1, 11.2)));
    }

    @Test
    public void trainNetwork() {
//        NeuralNetwork network = new NeuralNetwork(FunctionType.SIGMOID, 2, 3, 1);

        NeuralNetwork network = new NeuralNetwork(new Layer(
                new Neuron(FunctionType.SIGMOID),
                new Neuron(FunctionType.SIGMOID)
        ), new Layer(
                new Neuron(FunctionType.SIGMOID),
                new Neuron(FunctionType.SIGMOID),
                new Neuron(FunctionType.SIGMOID)
        ), new Layer(
                new Neuron(FunctionType.SIGMOID)
        ));

        DataSet dataSet = new DataSet(
                new DataRow(new double[]{1, 0}, new double[]{1}),
                new DataRow(new double[]{1, 1}, new double[]{1}),
                new DataRow(new double[]{0, 1}, new double[]{1}),
                new DataRow(new double[]{0, 0}, new double[]{0})
        );

        network.train(dataSet, 10000, .03);

        System.out.println(Arrays.toString(network.calculate(0, 0)));
        System.out.println(Arrays.toString(network.calculate(1, 0)));
        System.out.println(Arrays.toString(network.calculate(0, 1)));
        System.out.println(Arrays.toString(network.calculate(1, 1)));
    }
}
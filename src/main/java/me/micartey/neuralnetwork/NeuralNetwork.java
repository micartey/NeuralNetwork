package me.micartey.neuralnetwork;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.var;
import me.micartey.neuralnetwork.layer.Layer;
import me.micartey.neuralnetwork.layer.Neuron;
import me.micartey.neuralnetwork.functions.FunctionType;
import me.micartey.neuralnetwork.functions.Random;
import me.micartey.neuralnetwork.train.DataSet;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
public class NeuralNetwork {

    private int[] layerSize;

    @Getter private CopyOnWriteArrayList<Layer> layers;

    public NeuralNetwork(FunctionType function, int... neuronsInLayers) {
        this.layers = new CopyOnWriteArrayList<>();
        this.layerSize = neuronsInLayers;

        /* CREATE LAYERS AND FILL WITH NEURONS */
        for (int state : neuronsInLayers) {
            List<Neuron> neurons = new LinkedList<>();
            for(int index = 0; index < state; index++)
                neurons.add(new Neuron(function));

            layers.add(new Layer(
                    neurons.toArray(new Neuron[state])
            ));
        }

        /* RANDOMIZE AND SKIP INPUT NEURONS */
        randomizeBiases(-2, 2);
        randomizeWeights(-2, 2);
    }

    public NeuralNetwork(Layer... layer) {
        this.layerSize = Arrays.stream(layer)
                .map(Layer::getNeurons)
                .mapToInt(List::size)
                .toArray();

        this.layers = new CopyOnWriteArrayList<>(layer);

        /* RANDOMIZE AND SKIP INPUT NEURONS */
        randomizeBiases(-2, 2);
        randomizeWeights(-2, 2);
    }

    private synchronized void randomizeBiases(double lowerBound, double upperBound) {
        layers.stream().skip(1).forEach(layer ->
                layer.getNeurons().forEach(neuron ->
                        neuron.setBias(new Random().randomValue(lowerBound, upperBound))
                )
        );
    }

    private synchronized void randomizeWeights(double lowerBound, double upperBound) {
        AtomicInteger circle = new AtomicInteger();
        layers.stream().forEach(layer -> {
            int value = circle.getAndIncrement();
            if(value > 0) {
                layer.getNeurons().forEach(neuron ->
                        neuron.setWeights(new Random().createRandomArray(layerSize[value - 1], lowerBound, upperBound))
                );
            }
        });
    }

    private synchronized void fill(int layer, double... input) {
        if(layers.get(layer).getNeurons().size() != input.length)
            throw new IllegalArgumentException("Input vector must be equal to network layer size");

        AtomicInteger count = new AtomicInteger();
        layers.get(layer).getNeurons().forEach(neuron -> {
            neuron.setOutput(input[count.getAndIncrement()]);
        });
    }

    public synchronized double[] calculate(double... input) {
        if(input.length != layers.get(0).getNeurons().size())
            throw new IllegalArgumentException("Input vector must be equal to network input size.");

        fill(0, input);

        AtomicReference<Layer> lastLayer = new AtomicReference<>(layers.get(0));

        layers.stream().skip(1).forEach(layer -> {
            layer.getNeurons().forEach(neuron -> {
                var sum = neuron.getBias();

                sum += lastLayer.get().getNeurons().stream().mapToDouble(prevNeuron -> prevNeuron.getOutput() * neuron.getWeights()[prevNeuron.getIndex()]).sum();

                neuron.setOutput(neuron.getFunction().calculate(sum));
                neuron.setDerivatOutput(neuron.getOutput() * (1 - neuron.getOutput()));
            });
            lastLayer.set(layer);
        });

        return layers.get(layers.size() - 1).getNeurons().stream().mapToDouble(Neuron::getOutput).toArray();
    }

    private synchronized void updateWeights(double eta) {
        AtomicReference<Layer> lastLayer = new AtomicReference<>(layers.get(0));

        layers.stream().skip(1).forEach(layer -> {
            layer.getNeurons().forEach(neuron -> {
                var delta = -eta * neuron.getError();
                neuron.setBias(neuron.getBias() + delta);

                lastLayer.get().getNeurons().forEach(prevNeuron -> {
                    neuron.getWeights()[prevNeuron.getIndex()] += delta * prevNeuron.getOutput();
                });
            });
            lastLayer.set(layer);
        });
    }

    private synchronized void backpropError(double[] target) {
        layers.get(layers.size() - 1).getNeurons().forEach(neuron -> neuron.setError(
                (neuron.getOutput() - target[neuron.getIndex()]) * neuron.getDerivatOutput()
        ));

        for(int layer = layers.size() - 2; layer > 0; layer--) {
            for(int neuron = 0; neuron < layers.get(layer).getNeurons().size(); neuron++) {
                double sum = 0;

                for(int nextNeuron = 0; nextNeuron < layers.get(layer + 1).getNeurons().size(); nextNeuron++)
                    sum += layers.get(layer + 1).getNeurons().get(nextNeuron).getWeights()[neuron] * layers.get(layer + 1).getNeurons().get(nextNeuron).getError();

                layers.get(layer).getNeurons().get(neuron).setError(sum * layers.get(layer).getNeurons().get(neuron).getDerivatOutput());
            }
        }
    }

    public synchronized void train(double[] input, double[] target, double eta) {
        if(input.length != layers.get(0).getNeurons().size() || target.length != layers.get(layers.size() - 1).getNeurons().size())
            throw new IllegalArgumentException("Input or output vector doesn't match with the network.");

        calculate(input);
        backpropError(target);
        updateWeights(eta);
    }

    public synchronized void train(DataSet dataSet, int loops, double eta) {
        for(int i = 0; i < loops; i++) {
            dataSet.getRows().forEach(dataRow -> {
                train(dataRow.getInput(), dataRow.getDestination(), eta);
            });
        }
    }

    public synchronized double[] getError(DataSet dataSet) {
        double[] output = new double[layers.get(layers.size() - 1).getNeurons().size()];

        dataSet.getRows().forEach(dataRow -> {
            AtomicInteger count = new AtomicInteger();
            Arrays.stream(calculate(dataRow.getInput())).forEach(guess ->
                    output[count.get()] += dataRow.getDestination()[count.getAndIncrement()] - guess
            );
        });

        for(int i = 0; i < output.length; i++)
            output[i] = Math.abs(output[i] / dataSet.getRows().size());

        return output;
    }

}

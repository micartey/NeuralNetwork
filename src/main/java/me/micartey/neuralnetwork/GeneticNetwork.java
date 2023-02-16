package me.micartey.neuralnetwork;

import me.micartey.neuralnetwork.functions.FunctionType;
import me.micartey.neuralnetwork.functions.Random;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class GeneticNetwork extends NeuralNetwork {

    private final AtomicReference<Double> score = new AtomicReference<>(0D);

    public GeneticNetwork(FunctionType function, double score, int... neuronsInLayers) {
        super(function, neuronsInLayers);
        this.score.set(score);
    }

    public synchronized void mutate(double rate, double strength) {
        Random random = new Random();
        getLayers().stream().skip(1).forEach(layer -> {
            layer.getNeurons().forEach(neuron -> {
                /* MUTATE BIAS */
                if(Math.random() < rate)
                    neuron.setBias(neuron.getBias() + (random.nextGaussian() * strength));

                /* MUTATE WEIGHTS */
                Arrays.stream(neuron.getWeights()).forEach(weight -> {
                    if(Math.random() < rate)
                        weight += random.nextGaussian() * strength;
                });
            });
        });
    }

    public double getScore() {
        return score.get();
    }

    public void reward(double value) {
        score.set(score.get() + value);
    }
}

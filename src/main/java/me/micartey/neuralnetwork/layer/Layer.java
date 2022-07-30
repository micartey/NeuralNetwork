package me.micartey.neuralnetwork.layer;

import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class Layer {

    @Getter private final CopyOnWriteArrayList<Neuron> neurons;

    public Layer(Neuron... neuron) {
        this.neurons = new CopyOnWriteArrayList<>();
        neurons.addAll(Arrays.asList(neuron));

        for (int index = 0; index < this.neurons.size(); index++)
            this.neurons.get(index).setIndex(index);
    }
}

package me.clientastisch.network.neural.layer;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("ALL")
public class Layer implements Serializable, Cloneable {

    @Getter private CopyOnWriteArrayList<Neuron> neurons;

    public Layer(Neuron... neuron) {
        this.neurons = new CopyOnWriteArrayList<>();
        Arrays.stream(neuron).forEach(neurons::add);
    }
}

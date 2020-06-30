package me.clientastisch.network.neural.layer;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class Neuron implements Serializable, Cloneable {

    @Setter private double[] weights;
    @Setter private double bias;

    @Setter private double error;
    @Setter private double output;
    @Setter private double derivatOutput;

    private int index;

    public Neuron(int index) {
        this.index = index;
    }
}

package me.clientastisch.network.neural.layer;

import lombok.Getter;
import lombok.Setter;
import me.clientastisch.network.neural.functions.FunctionType;

import java.io.Serializable;

@Getter
public class Neuron implements Serializable, Cloneable {

    @Setter private double[] weights;
    @Setter private double bias;

    @Setter private double error, output, derivatOutput;
    private int index;

    private final FunctionType function;

    public Neuron(FunctionType function, int index) {
        this.function = function;
        this.index = index;
    }
}

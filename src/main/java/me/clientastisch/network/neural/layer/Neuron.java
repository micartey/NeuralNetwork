package me.clientastisch.network.neural.layer;

import lombok.Data;
import me.clientastisch.network.neural.functions.FunctionType;

import java.io.Serializable;

@Data
public class Neuron implements Serializable, Cloneable {

    private double[] weights;
    private double bias;

    private double error, output, derivatOutput;

    private final FunctionType function;
    private int index;

    public Neuron(FunctionType function) {
        this.function = function;
    }
}

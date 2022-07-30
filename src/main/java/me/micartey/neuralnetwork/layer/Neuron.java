package me.micartey.neuralnetwork.layer;

import lombok.Data;
import me.micartey.neuralnetwork.functions.FunctionType;

@Data
public class Neuron {

    private double[] weights;
    private double bias;

    private double error, output, derivatOutput;

    private final FunctionType function;
    private int index;

    public Neuron(FunctionType function) {
        this.function = function;
    }
}

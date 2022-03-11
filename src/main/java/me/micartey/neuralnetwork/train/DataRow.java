package me.micartey.neuralnetwork.train;

import lombok.Getter;

import java.io.Serializable;

public class DataRow implements Serializable {

    @Getter private final double[] input;
    @Getter private final double[] destination;

    public DataRow(double[] input, double[] destination) {
        this.destination = destination;
        this.input = input;
    }
}

package me.clientastisch.network.neural.train;

import lombok.Getter;

import java.io.Serializable;

@SuppressWarnings("ALL")
public class DataRow implements Serializable {

    @Getter private double[] input;
    @Getter private double[] destination;

    public DataRow(double[] input, double[] destination) {
        this.destination = destination;
        this.input = input;
    }
}

package me.clientastisch.network.neural.functions;

import lombok.val;

@SuppressWarnings("ALL")
public class Random extends java.util.Random {

    public double[] createRandomArray(int size, double lower, double upper){
        val array = new double[size];
        for(int i = 0; i < size; i++)
            array[i] = randomValue(lower, upper);
        return array;
    }

    public double[][] createRandomArray(int sizeX, int sizeY, double lower, double upper){
        val array = new double[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++)
            array[i] = createRandomArray(sizeY, lower, upper);
        return array;
    }

    public double randomValue(double lower, double upper){
        return Math.random() * (upper - lower) + lower;
    }
}

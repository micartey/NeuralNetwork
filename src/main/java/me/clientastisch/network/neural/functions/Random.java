package me.clientastisch.network.neural.functions;

import lombok.val;

public class Random extends java.util.Random {

    public double[] createRandomArray(int size, double lower, double upper){
        val array = new double[size];
        for(int index = 0; index < size; index++)
            array[index] = randomValue(lower, upper);
        return array;
    }

    public double[][] createRandomArray(int sizeX, int sizeY, double lower, double upper){
        val array = new double[sizeX][sizeY];
        for(int index = 0; index < sizeX; index++)
            array[index] = createRandomArray(sizeY, lower, upper);
        return array;
    }

    public double randomValue(double lower, double upper){
        return Math.random() * (upper - lower) + lower;
    }
}

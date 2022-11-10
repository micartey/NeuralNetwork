# NeuralNetwork

<div align="center">
  <a href="https://www.oracle.com/java/" target="_blank">
    <img
      src="https://img.shields.io/badge/Written%20in-java-%23EF4041?style=for-the-badge"
      height="30"
    />
  </a>
  <a href="https://github.com/micartey/NeuralNetwork/actions/workflows/maven-publish.yml" target="_blank">
    <img
      src="https://img.shields.io/badge/actions-build-%27a147?style=for-the-badge"
      height="30"
    />
  </a>
</div>

<br />

<p align="center">
  <a href="#-introduction">Introduction</a> •
  <a href="#-create-a-neural-network">Getting started</a> •
  <a href="https://github.com/micartey/NeuralNetwork/issues">Troubleshooting</a>
</p>

## 📚 Introduction

This is a object oriented neural network where each `Layer` has a n-amount of `Neurons` and each `Neuron` is capable of having it's own activation function. The structure of the `Neurons` and their connections to other `Layers` doesn't sepperate itself from other neural networks where each `Neuron` is connected to each `Neuron` in the next `Layer`

<div align="center">
    <img
      height="200px"
      src="images/Network.png"
    />
</div>

## 🧱 Create a neural network

You can create a neural network with a single activation function for each `Neuron` which also indexes the `Neurons` automatically

```java
    NeuralNetwork network = new NeuralNetwork(FunctionType.SIGMOID, 2, 3, 1);
```

Alternatively, you can create each `Layer` individually which also requires each `Neuron` to be created individually with its own activation function

```java
    NeuralNetwork network = new NeuralNetwork(
        new Layer(
            new Neuron(FunctionType.SIGMOID),
            new Neuron(FunctionType.SIGMOID)
        ), new Layer(
            new Neuron(FunctionType.SIGMOID),
            new Neuron(FunctionType.SIGMOID),
            new Neuron(FunctionType.SIGMOID)
        ), new Layer(
            new Neuron(FunctionType.SIGMOID)
        )
    );
```

## 🏋🏽‍♂️ Training a neural network

To train your neural network you can use either `DataSets` or normal `double` arrays. The following example trains the OR-Gate to a neural network.

```java
    NeuralNetwork network = new NeuralNetwork(FunctionType.SIGMOID, 2, 3, 1);

    DataSet dataSet = new DataSet(
            new DataRow(new double[]{1, 0}, new double[]{1}),
            new DataRow(new double[]{1, 1}, new double[]{1}),
            new DataRow(new double[]{0, 1}, new double[]{1}),
            new DataRow(new double[]{0, 0}, new double[]{0})
    );

    /* TRAIN ON THE DATASET FOR 1000 CICLES */
    network.train(dataSet, 1000, .03);
```
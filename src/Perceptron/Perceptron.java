package Perceptron;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Vector.Vector;

public class Perceptron {
	private double learningRate;
	private int theta = 0;
	private List<Vector> trainSet = new ArrayList<>();
	private List<Vector> testSet = new ArrayList<>();

	public static void main(String args[]) throws FileNotFoundException {
		Perceptron p = new Perceptron(Perceptron.class.getResource("/Perceptron/train.txt").getFile(),
				Perceptron.class.getResource("/Perceptron/test.txt").getFile());
		//print1Normal(p);
		print2SearchBestRate(p);
	}

	public static void print1Normal(Perceptron p) {
		for (double i = 0.01; i <= 0.50; i += 0.01) {
			double a = p.accuracy(i, 1) * 100;
			System.out.println("Learning rate=" + new DecimalFormat("0.00").format(i) + ", accuracy is: " + a + "%");
		}
	}

	public static void print2SearchBestRate(Perceptron p) {
		List<Double> d = new ArrayList<>();
		int maxIterationTimes = 1;
		for (double i = 0.01; i <= 0.50; i += 0.01) {
			if (p.accuracy(i, maxIterationTimes) == 1) // add the learning rate which could output weights that makes the testSet get
										// 100% accuracy
				d.add(Double.parseDouble(new DecimalFormat("0.00").format(i)));
			System.out.println("Best Learning Rate for not exceeding "+maxIterationTimes+" iteration: "+d.stream().collect(Collectors.groupingBy(q -> q, Collectors.counting())).entrySet()
					.stream().max(Comparator.comparing(Map.Entry<Double, Long>::getValue)).orElse(null)+" times makes testSet 100% accurate");
			if (i > 0.49)
				i = 0.01;
		}
	}

	public Perceptron(String train, String test) throws FileNotFoundException {
		trainSet = Vector.vectorParser(train);
		testSet = Vector.vectorParser(test);
	}

	public double accuracy(double rate, int maxIterationTimes) {
		learningRate = rate;
		double[] weights = new double[5];
		double error, totalError;
		int output, iteration;
		for (int i = 0; i < weights.length; i++)
			weights[i] = Math.random();

		iteration = 0;
		do {
			iteration++;
			totalError = 0;
			for (Vector v : trainSet) {
				output = calculateOutput(theta, weights, v.x, v.y, v.z, v.t);
				error = (v.className.equals("Iris-versicolor") ? 1 : 0) - output;
				weights[0] += learningRate * error * v.x;
				weights[1] += learningRate * error * v.y;
				weights[2] += learningRate * error * v.z;
				weights[3] += learningRate * error * v.t;
				weights[4] += learningRate * error;
				totalError += (error * error);
			}
		} while (totalError != 0 && iteration < maxIterationTimes);

		// System.out.println("Decision boundary equation: " + weights[0] + "*x + " +
		// weights[1] + "*y + " + weights[2]
		// + "*z + " + weights[3] + "*t + " + weights[4] + " = 0");
		int errorCount = 0;
		for (Vector v : testSet) {
			output = calculateOutput(theta, weights, v.x, v.y, v.z, v.t);
			errorCount += (output == 1 ? "Iris-versicolor" : "Iris-virginica").equals(v.className) ? 0 : 1;
		}
		return (double) (testSet.size() - errorCount) / testSet.size();

	}

	static int calculateOutput(int theta, double weights[], double x, double y, double z, double t) {
		double sum = x * weights[0] + y * weights[1] + z * weights[2] + t * weights[3] + weights[4];
		return (sum >= theta) ? 1 : 0;
	}

}
package Perceptron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerceptronWithUI {
	double LEARNING_RATE = 0.00001;
	int numOfPoints = 100000, iteration = 0,xLimit=800,yLimit=480;
	List<Double> x1 = new ArrayList<>(), x2 = new ArrayList<>(), y1 = new ArrayList<>(), y2 = new ArrayList<>();
	double w[] = { 0, 0, 0 }, x, y;
	static Random r = new Random();
	coordinateSystemUI xy = new coordinateSystemUI(this);

	public PerceptronWithUI() {
		double[] ww = { r.nextDouble(), r.nextDouble(), range(-yLimit, yLimit) };
		for (int i = 0; i < numOfPoints; i++) {
			x = range(-xLimit, xLimit);
			y = range(-yLimit, yLimit);
			if (outputFunction(0, ww, x, y) == 1) {
				x1.add(x);
				y1.add(y);
			} else {
				x2.add(x);
				y2.add(y);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new PerceptronWithUI().a();

	}

	public void a() {
		double totalError = 0;
		do {
			iteration++;
			totalError = 0;
			for (int i = 0; i < numOfPoints; i++) {
				if (x1.size() > i) {
					int output = outputFunction(0, w, x1.get(i), y1.get(i));
					int error = 1 - output;
					w[0] += LEARNING_RATE * error * x1.get(i);
					w[1] += LEARNING_RATE * error * y1.get(i);
					w[2] += LEARNING_RATE * error;
					totalError += (error * error);
					xy.repaint();
				}
				if (x2.size() > i) {
					int output = outputFunction(0, w, x2.get(i), y2.get(i));
					int error = 0 - output;
					w[0] += LEARNING_RATE * error * x2.get(i);
					w[1] += LEARNING_RATE * error * y2.get(i);
					w[2] += LEARNING_RATE * error;
					totalError += (error * error);
					xy.repaint();
				}
			}
		} while (totalError != 0);
	}

	static int outputFunction(int theta, double weights[], double x, double y) {
		double sum = x * weights[0] + y * weights[1] + weights[2];
		return (sum >= theta) ? 1 : 0;
	}

	public static double range(double a, double b) {
		return r.nextDouble() * (b - a) + a;
	}

}

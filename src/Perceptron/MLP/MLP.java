package Perceptron.MLP;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class MLP {
	public double w1[][], w2[][], b1[], b2[], loss, learningRate = 0.0001, x, y;
	public int numNeurons, numOfPoints = 10000, epoch = 0, xLimit = 200, yLimit = 120;
	public Random r = new Random();
	public List<Double> x1 = new ArrayList<>(), x2 = new ArrayList<>(), y1 = new ArrayList<>(), y2 = new ArrayList<>(),
			ox1 = new ArrayList<>(), ox2 = new ArrayList<>(), oy1 = new ArrayList<>(), oy2 = new ArrayList<>();
	CoordinateSystemUI panelUp, panelDown;

	public MLP(int numNeurons) throws IOException {
		initializeParameters(numNeurons);
	}

	public static void main(String[] args) throws IOException {
		MLP p = new MLP(50);
		JFrame f = new JFrame("timmmGZ¡ª¡ªMLP(2 layers)");
		f.setLayout(new GridLayout(0, 1, 0, 0));
		f.add(p.panelUp);
		f.add(p.panelDown);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		p.train();
	}

	public void initializeParameters(int n) {
		numNeurons = n;
		w1 = new double[2][numNeurons];
		w2 = new double[numNeurons][1];
		b1 = new double[numNeurons];
		b2 = new double[1];
		for (int i = 0; i < w1.length; i++)
			for (int j = 0; j < w1[i].length; j++)
				w1[i][j] = r.nextDouble() - 0.5;
		for (int i = 0; i < w2.length; i++)
			for (int j = 0; j < w2[i].length; j++)
				w2[i][j] = r.nextDouble() - 0.5;
		for (int i = 0; i < b1.length; i++)
			b1[i] = (r.nextDouble() - 0.5) * xLimit * 2 * w1[1][i];// my algorithm for optimizing
		for (int i = 0; i < b2.length; i++)
			b2[i] = r.nextDouble() - 0.5;
		// createCircle(50, 100, -30);
		// createTwoCircle(30, 100, -30, 50, -100, 20);
		// createRectangle();
		createHeart(2.3, -80, -20);
		panelUp = new CoordinateSystemUI(this, false);
		panelDown = new CoordinateSystemUI(this, true);
	}

	public void train() {
		do {
			loss = 0;
			if (!panelDown.drawing && epoch % 1 == 0) {
				ox1.clear();
				oy1.clear();
				ox2.clear();
				oy2.clear();
				for (x = -xLimit; x < xLimit; x += 1)
					for (y = -yLimit; y < yLimit; y += 1)
						if (output() > 0.9) {
							ox1.add(x);
							oy1.add(y);
						} else {
							ox2.add(x);
							oy2.add(y);
						}
				panelDown.drawing = true;
				panelDown.repaint();
			}
			epoch++;
			for (int i = 0; i < numOfPoints; i++)
				if (r.nextDouble() > 0.5) {
					int ii = r.nextInt(x1.size());
					double input[] = { x1.get(ii), y1.get(ii) };
					double label[] = { 1 };
					firstLayer(input, label);
				} else {
					int ii = r.nextInt(x2.size());
					double input[] = { x2.get(ii), y2.get(ii) };
					double label[] = { 0 };
					firstLayer(input, label);
				}
			panelUp.repaint();
			panelUp.loss = loss / numOfPoints;
		} while (loss > 0.001);
	}

	private double output() {
		double input[] = { x, y };
		return calculateOutput(calculateOutput(input, w1, b1), w2, b2)[0];
	}

	public void firstLayer(double[] input, double[] label) {
		SecondLayer(calculateOutput(input, w1, b1), label, input);
	}

	private void SecondLayer(double[] hiddenOutput, double[] label, double[] input) {
		double output[] = calculateOutput(hiddenOutput, w2, b2);
		double error[] = new double[output.length];
		double forOutputBP[] = new double[w2[0].length];
		for (int i = 0; i < error.length; i++) {
			error[i] = label[i] - output[i];
			forOutputBP[i] = -error[i] * output[i] * (1 - output[i]);
			loss += error[i] * error[i];
			for (int prevLayer = 0; prevLayer < w2.length; prevLayer++)
				w2[prevLayer][i] += learningRate * error[i] * hiddenOutput[prevLayer];
		}
		backPropagationForBothLayer(hiddenOutput, error, forOutputBP, input);
	}

	public void backPropagationForBothLayer(double[] hiddenOutput, double[] error, double[] forOutputBP,
			double[] input) {
		for (int prevLayer = 0; prevLayer < w2.length; prevLayer++)
			for (int currLayer = 0; currLayer < w2[prevLayer].length; currLayer++)
				w2[prevLayer][currLayer] -= learningRate * hiddenOutput[prevLayer] * forOutputBP[currLayer];
		for (int currLayer = 0; currLayer < w2[0].length; currLayer++)
			b2[currLayer] += learningRate * (error[currLayer] - forOutputBP[currLayer]);
		double forOutputBPf[] = new double[w2.length];
		for (int prevLayer = 0; prevLayer < w2.length; prevLayer++)
			for (int currLayer = 0; currLayer < w2[0].length; currLayer++)
				forOutputBPf[prevLayer] += forOutputBP[currLayer] * w2[prevLayer][currLayer];
		for (int i = 0; i < w1.length; i++)
			for (int prevLayer = 0; prevLayer < w2.length; prevLayer++)
				w1[i][prevLayer] -= learningRate * forOutputBPf[prevLayer] * hiddenOutput[prevLayer]
						* (1 - hiddenOutput[prevLayer]) * input[i];
		for (int prevLayer = 0; prevLayer < w2.length; prevLayer++)
			b1[prevLayer] -= learningRate * forOutputBPf[prevLayer] * hiddenOutput[prevLayer]
					* (1 - hiddenOutput[prevLayer]);
	}

	public double[] calculateOutput(double input[], double weights[][], double bias[]) {
		double output[] = new double[weights[0].length];
		for (int prevLayer = 0; prevLayer < weights.length; prevLayer++)
			for (int currLayer = 0; currLayer < weights[prevLayer].length; currLayer++)
				output[currLayer] += weights[prevLayer][currLayer] * input[prevLayer];
		for (int currLayer = 0; currLayer < weights[0].length; currLayer++)
			output[currLayer] = sigmoid(output[currLayer] + bias[currLayer]);
		return output;
	}

	public double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public double range(double a, double b) {
		return r.nextDouble() * (b - a) + a;
	}

	public void createCircle(int r, int cx, int cy) {
		for (int i = 0; i < numOfPoints; i++) {
			x = range(-xLimit, xLimit);
			y = range(-yLimit, yLimit);
			if ((x - cx) * (x - cx) + (y - cy) * (y - cy) < r * r) {
				x1.add(x);
				y1.add(y);
			} else {
				x2.add(x);
				y2.add(y);
			}
		}
	}

	public void createTwoCircle(int r, int cx, int cy, int r2, int cx2, int cy2) {
		for (int i = 0; i < numOfPoints; i++) {
			x = range(-xLimit, xLimit);
			y = range(-yLimit, yLimit);
			if ((x - cx) * (x - cx) + (y - cy) * (y - cy) < r * r
					|| (x - cx2) * (x - cx2) + (y - cy2) * (y - cy2) < r2 * r2) {
				x1.add(x);
				y1.add(y);
			} else {
				x2.add(x);
				y2.add(y);
			}
		}
	}

	public void createRectangle() {
		for (int i = 0; i < numOfPoints / 4; i++) {
			x = range(-xLimit, xLimit);
			y = range(yLimit / 2, yLimit);
			x1.add(x);
			y1.add(y);
		}
		for (int i = 0; i < numOfPoints / 8; i++) {
			x = range(-xLimit, -xLimit / 2);
			y = range(-yLimit, yLimit / 2);
			x1.add(x);
			y1.add(y);

		}
		for (int i = 0; i < numOfPoints / 8; i++) {
			x = range(xLimit / 2, xLimit);
			y = range(-yLimit, yLimit);
			x1.add(x);
			y1.add(y);

		}
		for (int i = 0; i < numOfPoints / 2; i++) {
			x = range(-xLimit / 2, xLimit / 2);
			y = range(-yLimit, yLimit / 2);
			x2.add(x);
			y2.add(y);
		}
	}

	public void createHeart(double size, int cx, int cy) {
		double t, yy, xx;
		for (int i = 0; i < numOfPoints; i++) {
			x = range(-xLimit, xLimit);
			y = range(-yLimit, yLimit);
			yy = y + cy - 20;
			xx = x - cx;
			t = xx * xx / 3000 + yy * yy / 2000 - size;
			if (t * t * t + xx * xx * yy * yy * yy / 100000000 <= 0) {
				x1.add(x);
				y1.add(-y);
			} else {
				x2.add(x);
				y2.add(-y);
			}
		}
	}
}

package Perceptron.MLP;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CoordinateSystemUI extends JPanel {
	DecimalFormat df = new DecimalFormat("0.0000");

	private static final long serialVersionUID = 1L;
	List<Double> x1, x2, y1, y2;
	double b1[], b2[];
	double w[][];
	int i = 0, num = 50, scale = 2;
	double learningRate, loss;
	boolean isDownPanel = false, drawing = false;
	MLP p;

	public CoordinateSystemUI(MLP p, boolean isDownP) {
		b1 = p.b1;
		b2 = p.b2;
		x1 = p.x1;
		x2 = p.x2;
		y1 = p.y1;
		y2 = p.y2;
		w = p.w1;
		learningRate = p.learningRate;
		this.p = p;
		isDownPanel = isDownP;
		if (isDownPanel) {
			setFont(new Font("Tahoma", Font.BOLD, 9));
			setPreferredSize(new Dimension(p.xLimit * 2 * scale, p.yLimit * 2 * scale));
			JPanel jp = new JPanel();
			JButton lr = new JButton("Learning Rate");
			JTextField LR = new JTextField(8);
			lr.addActionListener(e -> {
				p.learningRate = Double.parseDouble(LR.getText());
				p.panelUp.learningRate = p.learningRate;
			});
			jp.add(lr);
			jp.add(LR);
			jp.setOpaque(false);
			add(jp);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isDownPanel) {
			g.setColor(Color.RED);
			for (int i = 0; i < x1.size(); i++)
				g.drawString("Tim", getWidth() / 2 + scale * (int) (x1.get(i) - 3),
						getHeight() / 2 - scale * (int) (y1.get(i) + 0));
			g.setColor(Color.BLUE);
			for (int i = 0; i < x2.size(); i++)
				g.drawString("GZ", getWidth() / 2 + scale * (int) (x2.get(i) - 3),
						getHeight() / 2 - scale * (int) (y2.get(i) + 0));
			g.setColor(Color.BLACK);
			g.setFont(new Font("Tahoma", Font.BOLD, 20));
			g.drawString("Number of Neurons(lines): " + p.numNeurons, 10, 20);
			g.drawString("Epoch: " + p.epoch, 10, 40);
			g.drawString("Learning rate: " + learningRate, 10, 60);
			g.drawString("Loss: " + df.format(loss), 10, 80);
			for (int i = 0; i < b1.length; i++)
				g.drawLine(0, scale * (int) ((w[0][i] * -getWidth() / 4 + b1[i]) / w[1][i] + getHeight() / 4),
						getWidth(), scale * (int) ((w[0][i] * getWidth() / 4 + b1[i]) / w[1][i] + getHeight() / 4));
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(4.0f));
			g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);// x-axis
			g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());// y-axis
		} else {
			x1 = new ArrayList<>(p.ox1);
			x2 = new ArrayList<>(p.ox2);
			y1 = new ArrayList<>(p.oy1);
			y2 = new ArrayList<>(p.oy2);
			g.setColor(Color.BLUE);
			for (int i = 0; i < x2.size(); i++)
				g.drawString(".", (int) (getWidth() / 2 + scale * x2.get(i)),
						getHeight() / 2 - scale * (int) (y2.get(i) + 0));
			g.setColor(Color.RED);
			for (int i = 0; i < x1.size(); i++)
				g.drawString(".", (int) (getWidth() / 2 + scale * x1.get(i)),
						getHeight() / 2 - scale * (int) (y1.get(i) + 0));
			drawing = false;

		}
	}
}
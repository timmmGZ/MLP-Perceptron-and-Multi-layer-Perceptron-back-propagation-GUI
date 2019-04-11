package Perceptron;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class coordinateSystemUI extends JPanel {
	DecimalFormat df = new DecimalFormat("0.00");

	private static final long serialVersionUID = 1L;
	List<Double> x1, x2, y1, y2 = new ArrayList<>();
	double w[];
	int i = 0, num = 50;
	double LEARNING_RATE;
	PerceptronWithUI p;

	public coordinateSystemUI(PerceptronWithUI p) {
		x1 = p.x1;
		y1 = p.y1;
		x2 = p.x2;
		y2 = p.y2;
		w = p.w;
		LEARNING_RATE = p.LEARNING_RATE;
		this.p = p;
		this.setFocusable(true);
		setPreferredSize(new Dimension(p.xLimit*2, p.yLimit*2));
		JFrame f = new JFrame();
		f.setLayout(new BorderLayout());
		f.add(this, BorderLayout.CENTER);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		for (int i = 0; i < x1.size(); i++) {
			g.drawString("¡£", getWidth() / 2 + (int) (x1.get(i) - 3), getHeight() / 2 - (int) (y1.get(i) + 0));
		}
		g.setColor(Color.BLUE);
		for (int i = 0; i < x2.size(); i++) {
			g.drawString("¡£", getWidth() / 2 + (int) (x2.get(i) - 3), getHeight() / 2 - (int) (y2.get(i) + 0));
		}
		g.setColor(Color.BLACK);

		g.setFont(new Font("Tahoma", Font.BOLD, 20));
		g.drawString("Iteration: " + p.iteration, 10, 20);
		g.drawString("Learning rate: " + LEARNING_RATE, 10, 40);
		g.drawString(
				"y = " + df.format(-w[0] / w[1]) + "*x" + ((-w[2] / w[1]) > 0 ? "+" : " ") + df.format(-w[2] / w[1]),
				10, 60);
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);// x
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());// y
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(8.0f));
		g.drawLine(0, (int) ((w[0] * -getWidth() / 2 + w[2]) / w[1] + getHeight() / 2), getWidth(),
				(int) ((w[0] * getWidth() / 2 + w[2]) / w[1] + getHeight() / 2));

	}

}
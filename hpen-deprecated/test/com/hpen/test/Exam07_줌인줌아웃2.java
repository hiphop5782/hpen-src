package com.hpen.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PanAndZoom implements ChangeListener {
	BufferedImage image;
	JLabel label;

	public void stateChanged(ChangeEvent e) {
		int value = ((JSlider) e.getSource()).getValue();
		double scale = value / 100.0;
		BufferedImage scaled = getScaledImage(scale);
		label.setIcon(new ImageIcon(scaled));
		label.revalidate(); // signal scrollpane
	}

	private BufferedImage getScaledImage(double scale) {
		int w = (int) (scale * image.getWidth());
		int h = (int) (scale * image.getHeight());
		BufferedImage bi = new BufferedImage(w, h, image.getType());
		Graphics2D g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
		g2.drawRenderedImage(image, at);
		g2.dispose();
		return bi;
	}

	JLabel getContent() {
		createAnImage();
		label = new JLabel(new ImageIcon(image));
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}

	private void createAnImage() {
		int w = 500;
		int h = 500;
		int type = BufferedImage.TYPE_INT_RGB; // many options
		image = new BufferedImage(w, h, type);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2.setPaint(new Color(240, 200, 200));
		g2.fillRect(0, 0, w, h);
		g2.setPaint(Color.blue);
		g2.draw(new Rectangle2D.Double(w / 16, h / 16, w * 7 / 8, h * 7 / 8));
		g2.setPaint(Color.green.darker());
		g2.draw(new Line2D.Double(w / 16, h * 15 / 16, w * 15 / 16, h / 16));
		Ellipse2D e = new Ellipse2D.Double(w / 4, h / 4, w / 2, h / 2);
		g2.setPaint(new Color(240, 240, 200));
		g2.fill(e);
		g2.setPaint(Color.red);
		g2.draw(e);
		g2.dispose();
	}

	JSlider getControl() {
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 50, 200, 100);
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		return slider;
	}

}

public class Exam07_줌인줌아웃2 {
	public static void main(String[] args) {
		PanAndZoom app = new PanAndZoom();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new JScrollPane(app.getContent()));
		f.getContentPane().add(app.getControl(), "Last");
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.setVisible(true);
	}
}

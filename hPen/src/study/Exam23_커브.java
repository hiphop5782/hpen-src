package study;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.QuadCurve2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Exam23_Ä¿ºê {
	static int sx = 100, sy = 100, mx = 200, my = 200, ex = 300, ey = 100;
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel canvas = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.clearRect(0, 0, getWidth(), getHeight());
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setStroke(new BasicStroke(3f));
				g2d.setColor(Color.blue);
				QuadCurve2D quad = new QuadCurve2D.Float();
				quad.setCurve(sx, sy, mx, my, ex, ey);
				g2d.draw(quad);
			}
		};
		frame.setVisible(true);
		frame.add(canvas);
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				my += 30;
				canvas.repaint();
			}
		});
	}
}

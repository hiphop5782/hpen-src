package study;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

class ZoomBoxWindow {

	public ZoomBoxWindow() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}

				TestPane pane = new TestPane();
				ZoomPane zoomPane = new ZoomPane(pane);

				JFrame frame = new JFrame("Testing");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(pane);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public static class ZoomPane extends JPanel {

		protected static final int ZOOM_AREA = 40;

		private JComponent parent;
		private JWindow popup;

		private BufferedImage buffer;

		private float zoomLevel = 2f;

		public ZoomPane(JComponent parent) {
			this.parent = parent;
			popup = new JWindow();
			popup.setLayout(new BorderLayout());
			popup.add(this);
			popup.pack();
			MouseAdapter ma = new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					Point p = e.getPoint();
					Point pos = e.getLocationOnScreen();
					updateBuffer(p);
					popup.setLocation(pos.x + 16, pos.y + 16);
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					popup.setVisible(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					popup.setVisible(false);
				}

			};

			parent.addMouseListener(ma);
			parent.addMouseMotionListener(ma);
		}

		protected void updateBuffer(Point p) {
			int width = Math.round(ZOOM_AREA);
			int height = Math.round(ZOOM_AREA);
			buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = buffer.createGraphics();
			AffineTransform at = new AffineTransform();

			int xPos = (ZOOM_AREA / 2) - p.x;
			int yPos = (ZOOM_AREA / 2) - p.y;

			if (xPos > 0) {
				xPos = 0;
			}
			if (yPos > 0) {
				yPos = 0;
			}

			if ((xPos * -1) + ZOOM_AREA > parent.getWidth()) {
				xPos = (parent.getWidth() - ZOOM_AREA) * -1;
			}
			if ((yPos * -1) + ZOOM_AREA > parent.getHeight()) {
				yPos = (parent.getHeight() - ZOOM_AREA) * -1;
			}

			at.translate(xPos, yPos);
			g2d.setTransform(at);
			parent.paint(g2d);
			g2d.dispose();
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(Math.round(ZOOM_AREA * zoomLevel), Math.round(ZOOM_AREA * zoomLevel));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			if (buffer != null) {
				AffineTransform at = g2d.getTransform();
				g2d.setTransform(AffineTransform.getScaleInstance(zoomLevel, zoomLevel));
				g2d.drawImage(buffer, 0, 0, this);
				g2d.setTransform(at);
			}
			g2d.setColor(Color.RED);
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			g2d.dispose();
		}

	}

	public class TestPane extends JPanel {

		private BufferedImage img;

		public TestPane() {
			try {
				img = ImageIO.read(new File("image/temp/temp.jpg"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(), img.getHeight());
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (img != null) {
				Graphics2D g2d = (Graphics2D) g.create();
				int x = (getWidth() - img.getWidth()) / 2;
				int y = (getHeight() - img.getHeight()) / 2;
				g2d.drawImage(img, x, y, this);
				g2d.dispose();
			}
		}

	}

}

public class Exam07_¡‹¿Œ¡‹æ∆øÙ3 {
	public static void main(String[] args) {
		new ZoomBoxWindow();
	}
}

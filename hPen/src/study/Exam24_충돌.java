package study;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

class TestPanel extends JPanel {
	Point start = new Point(125, 25);
	Point end = new Point(300, 220);
	Point test = new Point();
	int radius = 3;
	boolean crash = false;

	@Override
	protected void paintComponent(Graphics g) {
		Image bg = createImage(getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) bg.getGraphics();
		if (crash) {
			g2d.setColor(Color.red);
		} else {
			g2d.setColor(Color.black);
		}
		// g2d.setStroke(new BasicStroke(radius));
		g2d.drawLine(start.x, start.y, end.x, end.y);
		g2d.drawOval(test.x - radius, test.y - radius, radius * 2, radius * 2);

		g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	}

	public void check() {
		crash = checkCrash(start, end, test, radius);
	}

	// 두점을 잇는 직선과 원이 충돌인지 검사
	// 원의 방정식과 직선의 방정식을 연결
	// (x-a)² + (y-b)² = r²
	// y = cx + d
	// -> (x-a)² + (cx + d - b)² = r²
	// -> (x-a)² + (cx + v)² = r²
	// (참고)
	// http://flash365.dreamx.com/game/view.php?id=flash_study&page=11&sn1=&divpage=1&sn=off&ss=on&sc=on&&select_arrange=hit&desc=desc&no=11988
	public boolean checkCrash(Point p1, Point p2, Point test, int r) {
		if (r < 0) {
			return false;
		} else if (p1.x != p2.x) {
			if (p1.y == p2.y) {
				if (Math.sqrt((test.x - p1.x) * (test.x - p1.x) + (test.y - p1.y) * (test.y - p1.y)) <= r) {
					return true;
				} else {
					return false;
				}
			} else {
				double c = (double)(p1.y - p2.y) / (p1.x - p2.x);
				double d = p1.y - c * p1.x;
				double e = d - test.y;
				double D = (c * e - test.x) * (c * e - test.x) - (test.x * test.x + e * e - r * r) * (c * c + 1);
				System.out.println("D = "+D);
				if (D >= 0) {
					if ((test.x - c * e + Math.sqrt(D)) / (c * c + 1) >= Math.min(p1.x, p2.x)
							&& (test.x - c * e - Math.sqrt(D)) / (c * c + 1) <= Math.max(p1.x, p2.x)) {
						// 닿인거다
						return true;
					} else {
						// 안닿인거다
						return false;
					}
				} else {
					// 안닿인거다
					return false;
				}
			}
		} else {
			double D = test.y * test.y - (p1.x - test.x) * (p1.x - test.x) - test.y * test.y + r * r;
			System.out.println("D = "+D);
			if (D >= 0) {
				if (test.y + Math.sqrt(D) > Math.min(p1.y, p2.y) && test.y - Math.sqrt(D) < Math.max(p1.y, p2.y)) {
					// 닿인거
					return true;
				} else {
					// 안닿인거
					return false;
				}
			} else {
				// 안닿인거
				return false;
			}
		}
	}
}

public class Exam24_충돌 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 500, 500);
		frame.setUndecorated(true);
		TestPanel panel = new TestPanel();
		frame.setContentPane(panel);

		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				System.out.println(e.getXOnScreen() + "/" + e.getYOnScreen());
				panel.test.x = e.getXOnScreen();
				panel.test.y = e.getYOnScreen();
				panel.check();
				panel.repaint();
			};
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}

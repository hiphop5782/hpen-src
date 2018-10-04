package com.hpen.draw.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import com.hakademy.utility.hook.KeyboardHook;
import com.hpen.draw.ui.component.SaveImageFileChooser;
import com.hpen.property.CaptureOption;
import com.hpen.property.DrawingOption;
import com.hpen.property.PropertyLoader;
import com.hpen.update.subutil.ScreenManager;
import com.hpen.util.CursorManager;
import com.hpen.util.ScreenData;

/**
 * 스크린 캡쳐 화면, 정확한 범위 지정을 지원하도록 한다 일단은 정지 화면으로 캡쳐하고 나중에 동영상 캡쳐 기능은 따로 구현
 * 
 * @author Hwang
 *
 */
public class CaptureFrame extends JFrame {
	/**
		 * 
		 */
	private static final long serialVersionUID = 626178305071575904L;

	private static CaptureFrame cf = new CaptureFrame();
	private static KeyboardHook hook = KeyboardHook.getInstance();
	public static void start() {
		if(cf.isVisible()) return;
		
		hook.addPreventKey(KeyboardHook.WINDOWS_LEFT);
		hook.addPreventKey(KeyboardHook.WINDOWS_RIGHT);
		cf.setWindowTransparent();
		cf.prepare();
		cf.eventbind();
		cf.setVisible(true);
	}
	
	private ScreenPainter screenPainter;
	private void prepare(){
		screenData = new ScreenData();
		setBounds(ScreenManager.getManager().getCurrentMonitorRect());
		setWindowTransparent();
		screenPainter = new ScreenPainter(this);
	}
	
	private void clear(){
		screenData = null;
		screenPainter.kill();
		screenPainter = null;
	}
	
	private void exit(){
		setVisible(false);
		PropertyLoader.save();
		eventunbind();
		clear();
		hook.removePreventKey(KeyboardHook.WINDOWS_LEFT);
		hook.removePreventKey(KeyboardHook.WINDOWS_RIGHT);
	}
	
	private MouseEvt mouseEvt = new MouseEvt();
	private MouseMotionEvt motionEvt = new MouseMotionEvt();
	private KeyEvt keyEvt = new KeyEvt();
	
	private void eventbind(){
		addMouseListener(mouseEvt);
		addMouseMotionListener(motionEvt);
		addKeyListener(keyEvt);
	}
	
	private void eventunbind(){
		removeMouseListener(mouseEvt);
		removeMouseMotionListener(motionEvt);
		removeKeyListener(keyEvt);
	}
	
	private ScreenData screenData;

	private boolean inFlag = false;
	private boolean isDragged = false;

	private CaptureFrame() {
		init();
	}
	
	private void init(){
		screen();
	}
	
	private void screen(){
		setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		getContentPane().setCursor(CursorManager.createEmptyCursor());
		
		setFocusTraversalKeysEnabled(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}

	class MouseEvt extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			inFlag = true;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			inFlag = false;
			isDragged = false;
			screenData.clearCursor();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			screenData.setStart(p);
			screenData.setEnd(p);
//			screenData.setStart(e.getX(), e.getY());
//			screenData.setEnd(e.getX(), e.getY());
			isDragged = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isDragged = false;
			saveScreen();
			if(screenData != null){
				screenData.clearStart();
				screenData.clearEnd();
			}
		}
	}

	/**
	 * 캡쳐 영역의 이미지를 버퍼에 저장하는 메소드
	 */
	private BufferedImage saveImage;

	private void saveScreen() {
		try {
			Robot robot = new Robot();
			if(screenData.getWidth() <= 0 || screenData.getHeight() <= 0) return;
			Rectangle screen = screenData.getRectangle();
			int thickness = CaptureOption.getInstance().getCaptureBorderThickness();
			screen.x += thickness/2;
			screen.y += thickness/2;
			screen.width -= thickness;
			screen.height -= thickness;
			saveImage = robot.createScreenCapture(screen);

			if(CaptureOption.getInstance().isCopytoClipboard()){
				// 버퍼 저장
				ImageSelection selection = new ImageSelection(saveImage);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, null);
				// System.out.println("이미지 버퍼 저장 완료");
			}else{
				// 임시 파일에 저장 -> JFileChooser로 선택하여 저장
				File temp = new File("image/temp/temp.jpg");
				ImageIO.write(saveImage, "jpg", temp);
				
				chooser.saveImage(saveImage, this.getClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			exit();
		}
	}
	
	private SaveImageFileChooser chooser = new SaveImageFileChooser(DrawingOption.getInstance().getSaveFolder());
	
	/**
	 * 파일 chooser의 확장자 필터링 내역을 저장하는 클래스
	 * @author Hwang
	 *
	 */
	class ChooserFileFilter extends FileFilter{
		String type; 
	    String desc; 
	    public ChooserFileFilter(String type, String desc){ 
	        this.type = type; 
	        this.desc = desc; 
	    } 
	    public boolean accept(File f){ 
	        return f.getName().endsWith(type) || f.isDirectory(); 
	    }
	    public String getDescription(){ 
	        if(type == null || type.equals(""))
	            return desc;
	        else
	            return desc + " (" + type + ")"; 
	    }
	}
	
	class MouseMotionEvt extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			screenData.setStart(p);
//			screenData.setStart(e.getX(), e.getY());
			screenData.clearEnd();
			screenData.setCursor(p);
//			screenData.setCursor(e.getX(), e.getY());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			screenData.setCursor(p);
			//screenData.setCursor(e.getX(), e.getY());
		}
	}

	class KeyEvt extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				exit();
			}
		}
	}

	private BufferedImage bg = null;

	private void setWindowTransparent() {
		bg = ScreenManager.getManager().getCurrentMonitorImage();
		repaint();
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	private Image backImg;
	private Graphics backScreen;

	@Override
	public void paint(Graphics g) {
		if (backImg == null) {
			backImg = createImage(getWidth(), getHeight());
			backScreen = backImg.getGraphics();
		} else {
			backScreen.clearRect(0, 0, getWidth(), getHeight());
		}
		drawScreen(g);
		g.drawImage(backImg, 0, 0, this.getContentPane());
	}

	private void drawScreen(Graphics g) {
		drawBackground(g);
		
		if(!inFlag) return;
		
		drawMousePoint();
		captureLivezoomImage();
		drawLivezoom();
		drawTempArea();
	}

	private void drawBackground(Graphics g) {
		if (bg == null)
			return;
//		if(ScreenCaptureOption.isLiveCapture()){
//			g.clearRect(0, 0, getWidth(), getHeight());
//			bg = ScreenUtility.getScreenCapture(this.getBounds());
//		}
		backScreen.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	}

	/**
	 * 십자가로 마우스 좌표를 표시해야 한다.
	 */
	private void drawMousePoint() {
		if (!isDragged) {
			try {
				backScreen.setColor(Color.black);
				Rectangle r = ScreenManager.getManager().getCurrentMonitorRect();
				Point p = screenData.getCursorAtFrame(CaptureFrame.this);
				backScreen.drawLine(p.x, 0, p.x, r.height);
				backScreen.drawLine(0, p.y, r.width, p.y);
			} catch (Exception e) {
				System.out.println("커서 그리기 오류 : " + e.getMessage());
			}
		}
	}

	private BufferedImage liveZoomImage;
	private int zoomRate = CaptureOption.getInstance().getZoomrate();// 5배줌

	private void captureLivezoomImage() {
		try {
			Robot robot = new Robot();
			Point p = MouseInfo.getPointerInfo().getLocation();
			int x = p.x;
			int y = p.y;
			Rectangle screenArea = new Rectangle(x - zoomsize / zoomRate, y - zoomsize / zoomRate,
					zoomsize / zoomRate * 2, zoomsize / zoomRate * 2);
			liveZoomImage = robot.createScreenCapture(screenArea);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int zoomsize = CaptureOption.getInstance().getZoomsize();
	private int zoomgap = 20;
	private Rectangle[] area = new Rectangle[] { new Rectangle(zoomgap, zoomgap, zoomsize, zoomsize),
			new Rectangle(ScreenManager.getManager().getCurrentMonitorRect().width - zoomgap - zoomsize,
					ScreenManager.getManager().getCurrentMonitorRect().height - zoomgap - zoomsize, zoomsize, zoomsize) };

	private void drawLivezoom() {
		if (liveZoomImage == null)
			return;
		if (!onFirstarea()) {// 좌상단
			backScreen.drawImage(liveZoomImage, area[0].x, area[0].y, area[0].width, area[0].height, this);
		}else{// 우하단
			if(!onLastarea()){
				backScreen.drawImage(liveZoomImage, area[1].x, area[1].y, area[1].width, area[1].height, this);
			}
		}
	}

	private void drawTempArea() {
		if (isDragged) {
			Graphics2D g2d = (Graphics2D) backScreen;
			g2d.setColor(CaptureOption.getInstance().getCaptureBorderColor());
			int thickness = CaptureOption.getInstance().getCaptureBorderThickness();
			g2d.setStroke(new BasicStroke(thickness));
			if(screenData.getWidth() > 0 && screenData.getHeight() > 0){
				Point p = screenData.getStartAtFrame(CaptureFrame.this);
				g2d.drawRect(p.x-thickness/2, p.y-thickness/2, screenData.getWidth(), screenData.getHeight());
			}
		}
	}

	private boolean onFirstarea() {
		Point start = screenData.getStartAtFrame(CaptureFrame.this);
		Point cursor = screenData.getCursorAtFrame(CaptureFrame.this);
		boolean result = 
				(start.x != -1 && start.y != -1) 
				&& (start.x < zoomsize + zoomgap * 2 && start.y < zoomsize + zoomgap * 2) 
				|| (cursor.x < zoomsize + zoomgap * 2 && cursor.y < zoomsize + zoomgap * 2);
//		System.out.println("x = "+x+", y = "+y+", sx = "+sx+", sy = "+sy+", result = "+result);
		return result;
	}
	
	private boolean onLastarea(){
		Point start = screenData.getStartAtFrame(CaptureFrame.this);
		Point cursor = screenData.getCursorAtFrame(CaptureFrame.this);
		Point end = screenData.getEndAtFrame(CaptureFrame.this);
		
		Rectangle screen = ScreenManager.getManager().getCurrentMonitorRect();
		boolean result = 
				(start.x > screen.width - (zoomsize + zoomgap * 2) && start.y > screen.height - (zoomsize + zoomgap * 2))
				|| (cursor.x > screen.width - (zoomsize + zoomgap * 2) && cursor.y > screen.height - (zoomsize + zoomgap * 2))
				|| (end.x > screen.width - (zoomsize + zoomgap * 2) && end.y > screen.height - (zoomsize + zoomgap * 2));
		
		return result;
	}

//	public static void main(String[] args) {
//		CaptureFrame.start();
//	}

	class ImageSelection implements Transferable {
		private Image image;

		public ImageSelection(Image image) {
			this.image = image;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { DataFlavor.imageFlavor };
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return DataFlavor.imageFlavor.equals(flavor);
		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (!DataFlavor.imageFlavor.equals(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			}
			return image;
		}
	}
}

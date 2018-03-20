package com.hpen.draw.ui;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hakademy.screen.ScreenSaver;
import com.hpen.draw.shapes.Curve;
import com.hpen.draw.shapes.Icon;
import com.hpen.draw.shapes.Shape;
import com.hpen.draw.shapes.ShapeFactory;
import com.hpen.draw.shapes.Text;
import com.hpen.draw.ui.component.SaveImageFileChooser;
import com.hpen.property.DrawingOption;
import com.hpen.property.PropertyLoader;
import com.hpen.update.subutil.ScreenManager;
import com.hpen.util.CursorManager;
import com.hpen.util.ScreenData;
import com.hpen.util.image.IconManager;
import com.hpen.util.key.KeyManager;

/**
 * 캡쳐 스크린 필기 화면
 * @author Hwang
 *
 */
public class DrawingFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private DrawingOption options = DrawingOption.getInstance();
	
	/**
	 * 상태 판정 플래그
	 */
	private int mode = DRAWING_MODE;
	public static final int DRAWING_MODE = 0;
	public static final int TEXT_MODE = 1;
	public static final int ICON_MODE = 2;
	public static final int CHOICE_MODE = 3;
	public boolean isDefaultMode() {
		return isDrawingMode();
	}
	public void restoreDefaultMode() {
		mode = DRAWING_MODE;
		setCursor();
	}
	public boolean isTextMode() {
		return mode == TEXT_MODE;
	}
	public boolean isIconMode() {
		return mode == ICON_MODE;
	}
	public boolean isDrawingMode() {
		return mode == DRAWING_MODE;
	}
	public boolean isChoiceMode() {
		return mode == CHOICE_MODE;
	}
	
	private static DrawingFrame df = new DrawingFrame();
	public static void start(){
		if(df.isVisible()) return;
		
		df.prepare();
		df.eventbind();
		df.setVisible(true);
		df.setWindowTransparent();
	}
	private ScreenData screenData;
	private ScreenPainter screenPainter;
	
	private SaveImageFileChooser chooser = new SaveImageFileChooser(options.getSaveFolder());
	private DrawingFrame(){
		init();
	}
	private void exit(){
		PropertyLoader.save();
		eventunbind();
		clear();
		setVisible(false);
	}
	
	private BufferedImage bg = null;
	private void setWindowTransparent(){
		try {
			bg = ScreenSaver.getMonitorScreenShotAtCursor();
			repaint();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		//스크린샷 테스트
		//try {
		//	bg = ScreenSaver.getMonitorScreenShotAtCursor();
		//	ImageIO.write(bg, "png", new File("test.png"));
		//}catch(Exception e) {e.printStackTrace();}
	}
	
	private Curve curve;
	private Text text;
	
	private void init(){
		//스크린 설정
		screen();
		keybind();
	}
	
	private void screen(){
		setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setFocusTraversalKeysEnabled(false);
		
		addWindowListener(windowAdapter);
		
		//test code
		//setBackground(Color.yellow);
		//System.out.println("size = "+this.getBounds());
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			exit();
		}
	};
	
	private MouseMotionEvt motionEvt = new MouseMotionEvt();
	private MouseEvt mouseEvt = new MouseEvt();
	private MouseWheelEvt wheelEvt = new MouseWheelEvt();
	private KeyEvt keyEvt = new KeyEvt();
	
	private void eventunbind(){
		removeMouseMotionListener(motionEvt);
		removeMouseListener(mouseEvt);
		removeMouseWheelListener(wheelEvt);
		getContentPane().removeKeyListener(keyEvt);
	}
	private void eventbind(){
		//이벤트 설정
		//WindowUtility.setWindowOpacity(this, 60/100f);
		addMouseMotionListener(motionEvt);
		addMouseListener(mouseEvt);
		addMouseWheelListener(wheelEvt);
		getContentPane().addKeyListener(keyEvt);
	}
	private void prepare(){
		setBounds(ScreenManager.getManager().getCurrentMonitorRect());
		setCursor(CursorManager.createCircleCursor());
		screenData = new ScreenData();
		curve = new Curve();
		text = new Text();
		screenPainter = new ScreenPainter(this);
	}
	private void clear(){
		screenData = null;
		curve = null;
		text = null;
		screenPainter.kill();
		screenPainter = null;
	}
	
	/**
	 * key binding method
	 */
	private void keybind(){
		ActionMap actionMap = ((JPanel)this.getContentPane()).getActionMap();
		InputMap inputMap = ((JPanel)this.getContentPane()).getInputMap();
		
		/* input map setting */
		inputMap.put(KeyManager.esc, "esc");
		inputMap.put(KeyManager.backspace, "backspace");
		inputMap.put(KeyManager.shift_space, "shift_space");
		inputMap.put(KeyManager.ctrl_s, "ctrl_s");
		inputMap.put(KeyManager.ctrl_z, "ctrl_z");
		inputMap.put(KeyManager.ctrl_r, "ctrl_r");
		inputMap.put(KeyManager.ctrl_y, "ctrl_y");
		inputMap.put(KeyManager.t, "t");
		inputMap.put(KeyManager.c, "c");
		inputMap.put(KeyManager.f1, "f1");
		inputMap.put(KeyManager.f2, "f2");
		inputMap.put(KeyManager.f3, "f3");
		inputMap.put(KeyManager.f4, "f4");
		inputMap.put(KeyManager.f5, "f5");
		inputMap.put(KeyManager.f6, "f6");
		inputMap.put(KeyManager.f7, "f7");
		inputMap.put(KeyManager.f8, "f8");
		inputMap.put(KeyManager.f9, "f9");
		inputMap.put(KeyManager.f10, "f10");
		
		
		/* action map setting */
		actionMap.put("esc", new AbstractAction(){
			private static final long serialVersionUID = 1587852735003918298L;
			public void actionPerformed(ActionEvent e) {
				if(isDefaultMode()) {
					screenData.clear();
					exit();
					return;
				}
				
				switch(mode) {
				case TEXT_MODE:
					text.finish();
					break;
				case ICON_MODE:
					selectedIcon = null;
					break;
				case CHOICE_MODE:
					screenData.clearChoice();
					break;
				}
				restoreDefaultMode();
			}
		});
				
		actionMap.put("backspace", new AbstractAction(){
			private static final long serialVersionUID = -1895194402565202987L;
			public void actionPerformed(ActionEvent e) {
				if(isTextMode()){
					text.undo();
				}
			}
		});
		actionMap.put("shift_space", new AbstractAction(){
			private static final long serialVersionUID = -2947049820846861899L;
			public void actionPerformed(ActionEvent e) {
				if(isTextMode()) {
					options.changeKorean();
				}
			}
		});
		actionMap.put("ctrl_s", new AbstractAction(){
			private static final long serialVersionUID = -1895194402565202987L;
			public void actionPerformed(ActionEvent e) {
				try {
					saveScreen();
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		actionMap.put("ctrl_z", new AbstractAction(){
			private static final long serialVersionUID = 3866328194655477517L;
			public void actionPerformed(ActionEvent e) {
				screenData.undo();
			}
		});
		actionMap.put("ctrl_r", new AbstractAction(){
			private static final long serialVersionUID = -5201116251074061469L;
			public void actionPerformed(ActionEvent e) {
				if(!isTextMode()) {
					screenData.recovery();
				}
			}
		});
		actionMap.put("ctrl_y", new AbstractAction() {
			private static final long serialVersionUID = -6354565020879453633L;
			@Override
			public void actionPerformed(ActionEvent e) {
				screenData.redo();
			}
		});
		actionMap.put("f1", new AbstractAction(){
			private static final long serialVersionUID = -5963057482921099056L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(1);
				setCursor();
			}
		});
		actionMap.put("f2", new AbstractAction(){
			private static final long serialVersionUID = -6354565020879453633L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(2);
				setCursor();
			}
		});
		actionMap.put("f3", new AbstractAction(){
			private static final long serialVersionUID = 2044616271891732949L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(3);
				setCursor();
			}
		});
		actionMap.put("f4", new AbstractAction(){
			private static final long serialVersionUID = -808999856814236056L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(4);
				setCursor();
			}
		});
		actionMap.put("f5", new AbstractAction(){
			private static final long serialVersionUID = -2554852532055970687L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(5);
				setCursor();
			}
		});
		actionMap.put("f6", new AbstractAction(){
			private static final long serialVersionUID = 3104526927899384109L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(6);
				setCursor();
			}
		});
		actionMap.put("f7", new AbstractAction(){
			private static final long serialVersionUID = 3733474738823690329L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(7);
				setCursor();
			}
		});
		actionMap.put("f8", new AbstractAction(){
			private static final long serialVersionUID = 18424280218463845L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(8);
				setCursor();
			}
		});
		actionMap.put("f9", new AbstractAction(){
			private static final long serialVersionUID = 5579777624382249446L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(9);
				setCursor();
			}
		});
		actionMap.put("f10", new AbstractAction(){
			private static final long serialVersionUID = -330567895445130724L;
			public void actionPerformed(ActionEvent e) {
				options.setPointColor(0);
				setCursor();
			}
		});
		actionMap.put("t", new AbstractAction(){
			private static final long serialVersionUID = -4139956715708260428L;
			public void actionPerformed(ActionEvent e) {
				if(isDefaultMode()){
					mode = TEXT_MODE;
					setCursor();
				}
			}
		});
		actionMap.put("c", new AbstractAction(){
			private static final long serialVersionUID = -4139956715708260428L;
			public void actionPerformed(ActionEvent e) {
				if(!isTextMode()) {
					screenData.clearShape();
				}
			}
		});
	}
	
	private void setCursor() {
		switch(mode) {
		case TEXT_MODE:
			setCursor(CursorManager.createTextCursor());			
			break;
		case DRAWING_MODE:
			setCursor(CursorManager.createCircleCursor());			
			break;
		case ICON_MODE:
			break;
		case CHOICE_MODE:
			setCursor(CursorManager.createGrabCursor());
			break;
		}
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	private Image backImg;
	private Graphics backScreen;
	
	@Override
	public void paint(Graphics g) {
		drawScreen();	
		g.drawImage(backImg, 0, 0, this.getContentPane());
	}
	
	/**
	 * backScreen에 화면을 그리는 메소드
	 */
	private void drawScreen(){
		prepareScreen();
		drawBackground();
		drawTempShape();
		drawShapelist();
	}
	
	private void prepareScreen() {
		if(backImg == null){
			backImg = createImage(getWidth(), getHeight());
			backScreen = backImg.getGraphics();
		}else{
			backScreen.clearRect(0, 0, getWidth(), getHeight());
		}
	}
	
	private void drawBackground(){
		if(bg == null) return;
		backScreen.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	}
	
	private boolean isDragged = false;
	
	private Set<Integer> pressedKey = new HashSet<>();
	/**
	 * 드래그 상태일 경우 임시 도형을 그리는 메소드
	 * 시작점 정보 : screenData.start
	 * 종료점 정보 : screenData.end
	 * 드래그 플래그 : isDragged
	 */
	private Shape tempShape;
	private void drawTempShape(){
//		System.out.println("isDragged = "+isDragged+", "+screenData.isTempShapeDrawable());
		if(isDragged && screenData.isTempShapeDrawable()){
			Graphics2D g2d = (Graphics2D)backScreen;
			
			tempShape = ShapeFactory.createShape(pressedKey, screenData.getStart(), screenData.getEnd(), options.getPointThickness(), options.getPointColor());
			if(tempShape == null){
				if(isTextMode()) 
					tempShape = text;
				else{
					tempShape = curve;
				}
			}
			
			tempShape.draw2(g2d);
		}
	}
	
	/**
	 * 임시 도형을 저장하는 메소드
	 */
	private void saveShape(){
		if(!screenData.isTempShapeDrawable()) return;
		
		Shape shape = ShapeFactory.createShape(pressedKey, screenData.getStart(), screenData.getEnd(), options.getPointThickness(), options.getPointColor());
		if(shape == null){
			switch(mode) {
			case TEXT_MODE:
				shape = text; 
				break;
			case ICON_MODE:
				shape = selectedIcon.copy(); 
				break;
			default:
				shape = curve;
				break;
			}
		}
		screenData.addShape(shape);
		tempShape = null;
		if(shape instanceof Curve){
			curve = new Curve();
		}
	}

	/**
	 * 저장된 도형을 그리는 메소드
	 */
	private void drawShapelist(){
		if(screenData.isShapeEmpty()) return;
		Graphics2D g2d = (Graphics2D)backScreen;
		for(Shape s : screenData.getShapelist()){
			s.draw2(g2d);
		}
	}
	
	
	/**
	 * 화면 저장 메소드
	 */
	private void saveScreen() throws AWTException{
		BufferedImage capture = ScreenSaver.getMonitorScreenShotAtCursor();
		ScreenSaver.sleep(0.5);
		int sel = chooser.showSaveDialog(this);
		if(sel != 0) return;

		chooser.saveImage(capture, this.getClass());
	}
	
	/**
	 * 스크린의 키이벤트를 처리하는 클래스
	 * @author Hwang
	 *
	 */
	class KeyEvt extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			
			int code = e.getKeyCode();
//			System.out.println(code);
//			System.out.println(Character.getType(e.getKeyChar()));
			switch(Character.getType(e.getKeyChar())) {
			
			/*제어 문자와 미분류에 대한 처리*/
			case 0:
			case Character.CONTROL:
				switch(code) {
				case KeyEvent.VK_CONTROL:
					if(isTextMode()) {
						options.changeKorean(); 
					}
					return;
				case KeyEvent.VK_SHIFT:
					if(isDefaultMode()) {
						mode = CHOICE_MODE;
						setCursor();
					}
					break;
				case KeyEvent.VK_ENTER:
					if(isTextMode()) {
						text.append("\n");
					}
					break;
				}
			/*공백 문자에 대한 처리*/
			case Character.SPACE_SEPARATOR:
				/* 쉬프트 스페이스는 제외 */
				if(isTextMode()) {
					if(e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK)
						return;
					
					switch(code) {
					case KeyEvent.VK_SPACE:
//					case KeyEvent.VK_ENTER:
						text.append(String.valueOf((char)code));
					}
				}
				break;
			/* 일반 글자(대/소문자, 특수문자, 숫자 등)에 대한 처리*/
			case Character.LOWERCASE_LETTER:
			case Character.UPPERCASE_LETTER:
			case Character.TITLECASE_LETTER:
			case Character.DECIMAL_DIGIT_NUMBER:
			case Character.DASH_PUNCTUATION:
			case Character.START_PUNCTUATION:
			case Character.END_PUNCTUATION:
			case Character.CONNECTOR_PUNCTUATION:
			case Character.OTHER_PUNCTUATION:
			case Character.MATH_SYMBOL:
			case Character.CURRENCY_SYMBOL:
			case Character.MODIFIER_SYMBOL:
			case Character.OTHER_SYMBOL:
			case Character.INITIAL_QUOTE_PUNCTUATION:
			case Character.FINAL_QUOTE_PUNCTUATION:
//			default:
				/* 텍스트 모드일 경우의 처리 */
				if(isTextMode()) {
					text.append(String.valueOf(e.getKeyChar()));
				}
				/* 나머지 처리 - 단축키 저장소에 등록 */
				else {
					pressedKey.add(code);
//					System.out.println(pressedKey);
				}
			}
				
		}
		@Override
		public void keyReleased(KeyEvent e) {
			pressedKey.remove(e.getKeyCode());
		}
	}
	private Icon selectedIcon;
	private List<Shape> choiceShapeList;
	class MouseEvt extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
//			System.out.println("mode = "+mode+", editable = "+screenData.isCursorDrawable()+", "+screenData.isTempShapeDrawable());
			if(e.getButton() == MouseEvent.BUTTON3){
				selectedIcon = IconManager.showIconDialog(DrawingFrame.this);
				if(selectedIcon == null) return;
				setCursor(CursorManager.createIconCursor(selectedIcon));
				mode = ICON_MODE;
				return;
			}
			
			switch(mode) {
			
			case TEXT_MODE:
				screenData.setStart(e.getX(), e.getY());
				int end_x = DrawingFrame.this.getX()+DrawingFrame.this.getWidth();
				int end_y = DrawingFrame.this.getY()+DrawingFrame.this.getHeight();
				screenData.setEnd(end_x, end_y);
				if(text != null) 
					text.finish();
				text = new Text(screenData.getStart(), screenData.getEnd(), options.getPointThickness(), options.getPointColorCopy(), "", options.getFontCopy());
				break;
				
			case ICON_MODE:
				
				break;
				
			case CHOICE_MODE:
				choiceShapeList = screenData.findShape(e.getX(), e.getY());
				break;
				
			case DRAWING_MODE:
			default:
				screenData.setStart(e.getX(), e.getY());
				screenData.setEnd(e.getX(), e.getY());
				curve = new Curve();
				isDragged = true;
				break;
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			isDragged = false;
			switch(mode) {
			case CHOICE_MODE:
				choiceShapeList.clear();
				choiceShapeList = null;
				break;
			case ICON_MODE:
				if(selectedIcon != null) {
					selectedIcon.setSx(e.getX());
					selectedIcon.setSy(e.getY());
					selectedIcon.setSize(options.getIconSize());
				}
			case DRAWING_MODE:
			case TEXT_MODE:
				saveShape();
				screenData.clearStart();
				screenData.clearEnd();
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			isDragged = false;
			screenData.clearCursor();
		}
	}
	
	class MouseWheelEvt implements MouseWheelListener{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			switch(mode) {
			case TEXT_MODE:
				if(e.getWheelRotation() > 0) {
					options.decreaseFontSize();
				}else if(e.getWheelRotation() < 0) {
					options.increaseFontSize();
				}
				setCursor(CursorManager.decreaseTextCursor());
				break;
			case ICON_MODE:
				if(e.getWheelRotation() > 0) {
					options.decreaseIconSize();
				}else if(e.getWheelRotation() < 0) {
					options.increaseIconSize();
				}
				setCursor(CursorManager.createIconCursor(selectedIcon));
				break;
			case CHOICE_MODE:
				break;
			case DRAWING_MODE:
				if(!isDragged) {
					if(e.getWheelRotation() > 0){
						options.decreasePointThickness();
					}
					else if(e.getWheelRotation() < 0){
						options.increasePointThickness();
					}
					setCursor(CursorManager.decreaseCircleCursor());
				}
			}
		}
	}
	
	class MouseMotionEvt extends MouseAdapter{
		@Override
		public void mouseMoved(MouseEvent e) {
			switch(mode) {
			case CHOICE_MODE:
				List<Shape> list = screenData.findShape(e.getX(), e.getY());
//				System.out.println(list.size());
			}
			screenData.setCursor(e.getX(), e.getY());
			screenData.setStart(e.getX(), e.getY());
			//System.out.println(screenData);
		}
		
		private int oldX = -1, oldY = -1;
		@Override
		public void mouseDragged(MouseEvent e) {
			oldX = screenData.getCursor().x;
			oldY = screenData.getCursor().y;
			screenData.setCursor(e.getX(), e.getY());
			screenData.setEnd(e.getX(), e.getY());
			switch(mode) {
			case DRAWING_MODE:
				if(pressedKey.isEmpty()) {
						curve.add(e.getX(), e.getY(), options.getPointThickness(), options.getPointColor());	
				}
				break;
			case CHOICE_MODE:
				if(choiceShapeList != null) {
					for(Shape s : choiceShapeList) {
						s.move(e.getX() - oldX, e.getY() - oldY);
					}
				}
			}
				
		}
//		빈도를 높이려 했지만 오히려 역효과 발생... 안티에얼라이싱만 적용하여 일차 테스트 후 수정할 것
//		private static final long EVENT_FREQUENCY = 10; //ms
//	    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//	    private ScheduledFuture<?> mouseDraggedFrequencyTimer;
//	    private MouseEvent lastDragEvent;
//		@Override
//		public void mouseDragged(MouseEvent e) {
//			lastDragEvent = e;
//			if (mouseDraggedFrequencyTimer == null || mouseDraggedFrequencyTimer.isCancelled() || mouseDraggedFrequencyTimer.isDone()) {
//				mouseDraggedFrequencyTimer = scheduler.schedule(()->{
//					screenData.setCursor(e.getX(), e.getY());
//					screenData.setEnd(e.getX(), e.getY());
//					if(!isText && pressedKey.isEmpty())
//						curve.add(e.getX(), e.getY(), 
//								options.getPointThickness(), 
//								options.getPointColor());
//				}, EVENT_FREQUENCY, TimeUnit.MILLISECONDS);
//			}
//		}
	}

}

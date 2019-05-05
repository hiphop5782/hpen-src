package com.hpen.draw.ui;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
import javax.swing.JPanel;

import com.hakademy.utility.hook.KeyboardHook;
import com.hakademy.utility.screen.ScreenManager;
import com.hpen.draw.shapes.Curve;
import com.hpen.draw.shapes.Icon;
import com.hpen.draw.shapes.Shape;
import com.hpen.draw.shapes.ShapeFactory;
import com.hpen.draw.shapes.Text;
import com.hpen.draw.ui.component.SaveImageFileChooser;
import com.hpen.property.DrawingOption;
import com.hpen.property.PropertyLoader;
import com.hpen.util.CursorManager;
import com.hpen.util.ScreenData;
import com.hpen.util.image.IconManager;
import com.hpen.util.key.KeyManager;

/**
 * 캡쳐 스크린 필기 화면
 * @author Hwang
 *
 */
public class DrawingFrame extends FullScreenFrame{
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


	public static final boolean TRANSPARENT = true;
	public static final boolean WHITEBOARD = false;
	
	public boolean screenState;
	
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
	public static boolean isNowDisplaying() {
		return df.isVisible();
	}
	
	private KeyboardHook hook = KeyboardHook.getInstance();
	public static void start(boolean screenState){
		if(isNowDisplaying()) return;
		
		df.screenState = screenState;
		df.setKeyboardPrevent();
		df.prepare();
		df.eventbind();
		df.setVisible(true);
	}
	private void setKeyboardPrevent() {
		hook.addPreventKey(KeyboardHook.WINDOWS_LEFT);
		hook.addPreventKey(KeyboardHook.WINDOWS_RIGHT);
		hook.addPreventKey(KeyboardHook.MENU);
		hook.addPreventKey(KeyboardHook.ALT_RIGHT, (a, b, c)->{
			if(a >= 0) {
				switch(b.intValue()) {
				case KeyboardHook.SYSKEY_RELEASE:
				case KeyboardHook.KEY_RELEASE:
					options.changeKorean();
				}
			}
			return null;
		});
	}
	private void setKeyboardUnprevent() {
		hook.removePreventKey(KeyboardHook.WINDOWS_LEFT);
		hook.removePreventKey(KeyboardHook.WINDOWS_RIGHT);
		hook.removePreventKey(KeyboardHook.MENU);
		hook.removePreventKey(KeyboardHook.ALT_RIGHT);
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
		df.setKeyboardUnprevent();
	}

	private Curve curve;
	private Text text;
	
	private void init(){
		//스크린 설정
		screen();
		keybind();
		screenData = new ScreenData();
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
		removeMouseWheelListener(wheelEvt);
		removeMouseMotionListener(motionEvt);
		removeMouseListener(mouseEvt);
		getContentPane().removeKeyListener(keyEvt);
	}
	private void eventbind(){
//		이벤트 설정
		addMouseMotionListener(motionEvt);
		addMouseListener(mouseEvt);
		addMouseWheelListener(wheelEvt);		//자바
		getContentPane().addKeyListener(keyEvt);
	}
	private void prepare(){
		ScreenManager manager = ScreenManager.getManager();
		Rectangle rect = manager.getCurrentMonitorRect();
		setBounds(rect);
		getContentPane().setCursor(CursorManager.createCircleCursor());
		if(screenState == WHITEBOARD)
			screenData.createNowImage(rect.width, rect.height);
		else
			screenData.createNowImage(rect.width, rect.height, manager.getCurrentMonitorImage());
		screenPainter = new ScreenPainter(this);
	}
	private void clear(){
//		screenData = null;
		curve = null;
		text = null;
		screenPainter.kill();
		screenPainter = null;
	}
	
	/**
	 * 키보드 바인딩
	 */
	int altnum = 0;
	int colornum = 0;
	private void keybind(){
		ActionMap actionMap = ((JPanel)this.getContentPane()).getActionMap();
		InputMap inputMap = ((JPanel)this.getContentPane()).getInputMap();
		
		/* input map setting */
		inputMap.put(KeyManager.esc, "esc");
		inputMap.put(KeyManager.backspace, "backspace");
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
		
		inputMap.put(KeyManager.numpad1, "numpad1");
		inputMap.put(KeyManager.numpad2, "numpad2");
		inputMap.put(KeyManager.numpad3, "numpad3");
		inputMap.put(KeyManager.numpad4, "numpad4");
		inputMap.put(KeyManager.numpad5, "numpad5");
		inputMap.put(KeyManager.numpad6, "numpad6");
		inputMap.put(KeyManager.numpad7, "numpad7");
		inputMap.put(KeyManager.numpad8, "numpad8");
		inputMap.put(KeyManager.numpad9, "numpad9");
		inputMap.put(KeyManager.numpad0, "numpad0");
		inputMap.put(KeyManager.altnumpad1, "altnumpad1");
		inputMap.put(KeyManager.altnumpad2, "altnumpad2");
		inputMap.put(KeyManager.altnumpad3, "altnumpad3");
		inputMap.put(KeyManager.altnumpad4, "altnumpad4");
		inputMap.put(KeyManager.altnumpad5, "altnumpad5");
		inputMap.put(KeyManager.altnumpad6, "altnumpad6");
		inputMap.put(KeyManager.altnumpad7, "altnumpad7");
		inputMap.put(KeyManager.altnumpad8, "altnumpad8");
		inputMap.put(KeyManager.altnumpad9, "altnumpad9");
		inputMap.put(KeyManager.altnumpad0, "altnumpad0");
		
		
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
					saveTextShape();
					screenData.setEnd(-1, -1);
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
		actionMap.put("ctrl_r", new AbstractAction() {
			private static final long serialVersionUID = -5201116251074061469L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isTextMode()) {
					screenData.recoverLastScreen();
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
		for(colornum = 1; colornum <= 10; colornum++) {
			actionMap.put("f"+colornum, new AbstractAction(){
				public void actionPerformed(ActionEvent e) {
					options.setPointColor(colornum);
					setCursor();
				}
			});
		}
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
					screenData.clear();
				}
			}
		});
		
		for(altnum = 0; altnum < 10; altnum++) {
			actionMap.put("altnumpad"+altnum, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!isTextMode()) {
						screenData.addMemory(altnum);
					}
				}
			});
			
			actionMap.put("numpad"+altnum, new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					if(!isTextMode()) {
						screenData.loadMemory(altnum);
					}
				}
			});
		}
	}
	
	private void setCursor() {
		switch(mode) {
		case TEXT_MODE:
			this.getContentPane().setCursor(CursorManager.createTextCursor());			
			break;
		case DRAWING_MODE:
			this.getContentPane().setCursor(CursorManager.createCircleCursor());			
			break;
		case ICON_MODE:
			break;
		case CHOICE_MODE:
			this.getContentPane().setCursor(CursorManager.createGrabCursor());
			break;
		}
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public boolean isCurveMode() {
		return isDragged && pressedKey.isEmpty() && !isTextMode();
	}
	@Override
	public void paint(Graphics g) {
		if(screenData.hasNowImage()) {
			if(isCurveMode()) {
				BufferedImage screen = screenData.getNowImage();
				drawTempCurve(screen);
				g.drawImage(screen, 0, 0, this);
			}
			else {
				BufferedImage screen = screenData.getNowImageCopy();
				drawTempShape(screen);
				g.drawImage(screen, 0, 0, this);
			}
		}
	}
	
	private boolean isDragged = false;
	
	private Set<Integer> pressedKey = new HashSet<>();
	/**
	 * 드래그 상태일 경우 임시 도형을 그리는 메소드
	 * 시작점 정보 : screenData.start
	 * 종료점 정보 : screenData.end
	 * 드래그 플래그 : isDragged
	 */
	public Shape getCurrentShape() {
		if(!screenData.isTempShapeDrawable()) throw new IllegalStateException();
		
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
		
		return shape;
	}
	
	private void drawTempCurve(BufferedImage image) {
		Shape shape = curve;
		shape.draw2((Graphics2D)image.getGraphics());
	}
	
	private void drawTempShape(BufferedImage image){
		try {
//			System.out.println("isDragged = "+isDragged+", "+screenData.isTempShapeDrawable());
			Shape shape = getCurrentShape();
			shape.draw2((Graphics2D)image.getGraphics());
		}
		catch(IllegalStateException e) {}
	}
	
	/**
	 * 임시 도형을 저장하는 메소드
	 */
	private void saveShape(){
		try {
			Shape shape = getCurrentShape();
			screenData.addShape(shape);
		}
		catch(IllegalStateException e) {}
	}
	
	/**
	 * 텍스트 도형을 저장하는 메소드
	 */
	private void saveTextShape() {
		screenData.addShape(text);
	}

	/**
	 * 화면 저장 메소드
	 */
	private void saveScreen() throws AWTException{
		this.setKeyboardUnprevent();
		int sel = chooser.showSaveDialog(this);
		this.setKeyboardPrevent();
		if(sel != 0) return;

		chooser.saveImage(screenData.getNowImage(), this.getClass());
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
					return;
//				case KeyEvent.VK_SHIFT:
//					if(isDefaultMode()) {
//						mode = CHOICE_MODE;
//						setCursor();
//					}
//					break;
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
				getContentPane().setCursor(CursorManager.createIconCursor(selectedIcon));
				mode = ICON_MODE;
				return;
			}
			
			switch(mode) {
			
			case TEXT_MODE:
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
			
//			System.out.println("mode = "+mode);
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
				saveShape();
				screenData.clearStart();
				screenData.clearEnd();
				break;
			case TEXT_MODE:
				if(text != null) {
					text.finish();
					saveTextShape();
				}
				
				screenData.setStart(e.getX(), e.getY());
//				int end_x = DrawingFrame.this.getX()+DrawingFrame.this.getWidth();
//				int end_y = DrawingFrame.this.getY()+DrawingFrame.this.getHeight();
//				screenData.setEnd(end_x, end_y);
				screenData.setEnd(e.getX(), e.getY());
				
				
				text = new Text(screenData.getStart(), screenData.getEnd(), options.getPointThickness(), options.getPointColorCopy(), "", options.getFontCopy());
//				saveTextShape();
				break;
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
				getContentPane().setCursor(CursorManager.createTextCursor());
				break;
			case ICON_MODE:
				if(e.getWheelRotation() > 0) {
					options.decreaseIconSize();
				}else if(e.getWheelRotation() < 0) {
					options.increaseIconSize();
				}
				getContentPane().setCursor(CursorManager.createIconCursor(selectedIcon));
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
					getContentPane().setCursor(CursorManager.createCircleCursor());
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
//		private long lastTime = System.currentTimeMillis();
		@Override
		public void mouseDragged(MouseEvent e) {
			oldX = screenData.getCursor().x;
			oldY = screenData.getCursor().y;
			screenData.setCursor(e.getX(), e.getY());
			screenData.setEnd(e.getX(), e.getY());
			switch(mode) {
			case DRAWING_MODE:
				if(pressedKey.isEmpty()) {
//						long nowTime = System.currentTimeMillis();
//						System.out.println("시차 : "+(nowTime - lastTime)+"ms");
						curve.add(e.getX(), e.getY(), options.getPointThickness(), options.getPointColor());
//						lastTime = nowTime;
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
	}

}

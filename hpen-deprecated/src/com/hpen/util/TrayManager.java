package com.hpen.util;

import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.hpen.draw.ui.CaptureFrame;
import com.hpen.draw.ui.DrawingFrame;
import com.hpen.draw.ui.State;
import com.hpen.property.ProgramIcon;
import com.hpen.property.ui.MarkDownViewer;
import com.hpen.property.ui.PropertyFrame;
import com.hpen.util.file.RegisterManager;
import com.hpen.util.file.TextReader;
import com.hpen.value.Version;

public class TrayManager{
	private TrayIcon tray;
	
	private PopupMenu popup = new PopupMenu();
	
	private MenuItem note = new MenuItem("필기하기(투명)");
	private MenuItem whiteboard = new MenuItem("필기하기(화이트보드)");
	private MenuItem capture = new MenuItem("캡쳐하기");
	private MenuItem zoom = new MenuItem("돋보기");
	private MenuItem setting = new MenuItem("환경설정");
	private CheckboxMenuItem onStart = new CheckboxMenuItem("시작프로그램 등록");
	private CheckboxMenuItem onDesktop = new CheckboxMenuItem("바탕화면에 바로가기 만들기");
	private MenuItem tutorial = new MenuItem("사용설명서");
	
	private MenuItem exit = new MenuItem("종료하기");
	
	public static void start(){
		try{
			TrayManager mt = new TrayManager();
			if(!SystemTray.isSupported())
				throw new Exception("트레이를 지원하지 않습니다");
			mt.tray = new TrayIcon(ProgramIcon.getIcon());
			mt.tray.setToolTip(Version.getInstance().toString());
			SystemTray.getSystemTray().add(mt.tray);
			
			mt.add();
			mt.event();
		}catch(Exception e){
			//System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private  TrayManager(){}
	
	private void add(){
		tray.setPopupMenu(popup);
		popup.add(note);
		popup.add(whiteboard);
		popup.add(capture);
		popup.add(zoom);
		popup.addSeparator();
		popup.add(setting);
		popup.add(tutorial);
		popup.addSeparator();
		popup.add(onDesktop);
		popup.add(onStart);
		popup.addSeparator();
		popup.add(exit);
		onStart.setState(RegisterManager.checkRegisterStartProgram());
		onDesktop.setState(RegisterManager.checkRegisterDesktop());
	}
	
	private void event(){
		note.addActionListener(e->{
			DrawingFrame.start(State.TRANSPARENT);
		});
		whiteboard.addActionListener(e->{
			DrawingFrame.start(State.WHITEBOARD);
		});
		capture.addActionListener(e->{
			CaptureFrame.start();
		});
		setting.addActionListener(e->{
			PropertyFrame.start();
		});
		zoom.addActionListener(e->{
			//ZoomFrame.start();
			MagnifyManager.work();
		});
		tutorial.addActionListener(e->{
			try{
				//윈도우에 연결된 프로그램으로 자동 실행하는 명령
				//Desktop desktop = Desktop.getDesktop();
				//desktop.edit(new File("README.md"));
				String url = "https://raw.githubusercontent.com/hiphop5782/hpen-src/master/README.md";
				MarkDownViewer viewer = new MarkDownViewer(TextReader.loadText(url));
				//Runtime.getRuntime().exec("cmd.exe /C start https://github.com/hiphop5782/hpen-src/blob/master/README.md");
			}catch(Exception err){
				err.printStackTrace();
			}
		});
		onStart.addItemListener(e->{
			if(onStart.getState()){
				RegisterManager.registerStartProgram();
			}else{
				RegisterManager.unregisterStartProgram();
			}
		});
		onDesktop.addItemListener(e->{
			if(onDesktop.getState()){
				RegisterManager.createShortcutToDesktop();
			}else{
				RegisterManager.removeShortcutFromDesktop();
			}
		});
		exit.addActionListener(e->{
			System.exit(0);
		});
	}
	
}









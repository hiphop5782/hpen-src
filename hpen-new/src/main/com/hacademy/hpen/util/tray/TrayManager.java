package com.hacademy.hpen.util.tray;

import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.hacademy.hpen.ui.capture.CaptureFullScreenFrame;
import com.hacademy.hpen.ui.note.NoteFullScreenFrame;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Getter;

@Component
public class TrayManager {
	
	@Autowired
	private NoteFullScreenFrame noteFullScreenFrame;
	
	@Autowired
	private CaptureFullScreenFrame captureFullScreenFrame;
	
	@Getter
	private TrayIcon tray;
	
	public void init() {
		if(!SystemTray.isSupported()) {
			System.err.println("Tray not support");
			System.exit(-1);
		}
		
		try(InputStream in = getClass().getResourceAsStream("/icon/hpen.png");){
			tray = new TrayIcon(ImageIO.read(in));
			SystemTray.getSystemTray().add(tray);
			display();
			event();
		}
		catch(Exception e) {
			System.err.println("아이콘 로딩 실패");
		}
	}
	
	private PopupMenu popup = new PopupMenu();
	private MenuItem noteMenu = new MenuItem("필기하기");
	private MenuItem captureMenu = new MenuItem("캡쳐하기");
	
	private void display() throws UnsupportedEncodingException {
		tray.setPopupMenu(popup);
		
		popup.add(noteMenu);
		popup.add(captureMenu);
	}
	
	private void event() {
		noteMenu.addActionListener(e->{
			noteFullScreenFrame.open();
		});
		captureMenu.addActionListener(e->{
			
		});
	}
}

package com.hacademy.hpen.util.tray;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.hacademy.hpen.ui.function.CaptureFrame;
import com.hacademy.hpen.ui.function.HoldFrame;
import com.hacademy.hpen.ui.note.NoteFullScreenFrame;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TrayManager {
	
	@Autowired
	private NoteFullScreenFrame noteFullScreenFrame;
	
	@Autowired
	private CaptureFrame captureFrame;
	
	@Autowired
	private HoldFrame holdFrame;
	
	@Getter
	private TrayIcon tray;
	
	public void init() {
		if(!SystemTray.isSupported()) {
			log.error("Tray not support");
			System.exit(-1);
		}
		
		try(InputStream in = getClass().getResourceAsStream("/icon/hpen.png");){
			tray = new TrayIcon(ImageIO.read(in));
			SystemTray.getSystemTray().add(tray);
			display();
			event();
		}
		catch(Exception e) {
			log.error("아이콘 로딩 실패", e);
		}
	}
	
	private PopupMenu popup = new PopupMenu();
	private MenuItem note = new MenuItem("필기하기");
	private MenuItem capture = new MenuItem("캡쳐하기");
	private MenuItem hold = new MenuItem("화면고정");
	
	private void display() throws UnsupportedEncodingException {
		tray.setPopupMenu(popup);
		
		popup.add(capture);
		popup.add(hold);
		popup.addSeparator();
		popup.add(note);
	}
	
	private void event() {
		note.addActionListener(e->{
//			noteFullScreenFrame.open();
		});
		capture.addActionListener(e->{
			captureFrame.setFillCurrentMonitor();
			captureFrame.open();
		});
		hold.addActionListener(e->{
			holdFrame.setFillCurrentMonitor();
			holdFrame.open();
		});
	}
}

package com.hpen.update.subutil;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import com.hakademy.utility.screen.ScreenManager;

public class FrameManager {
	
	static {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}catch(Exception e) {
			System.err.println("Look&Feel 설정 오류");
		}
	}
	
	public static FrameManager manager = new FrameManager();
	public static FrameManager getManager() {
		return manager;
	}
	
	private FrameManager() {}
	
	private Map<String, JFrame> base = new HashMap<>();
	
	public void showFrame(String name) {
		getFrame(name).setVisible(true);
	}
	
	public void hideFrame(String name) {
		getFrame(name).setVisible(false);
	}
	
	public boolean addFrame(String name, JFrame frame) {
		if(!base.containsValue(frame)) {
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			base.put(name, frame);
			return true;
		}
		return false;
	}
	
	public int getCount() {
		return base.size();
	}
	
	public void removeFrame(String name) {
		base.remove(name);
	}
	
	public void removeAndKillFrame(String name) {
		base.remove(name).dispose();
	}
	
	public JFrame getActiveFrame() {
		Iterator<String> it = base.keySet().iterator();
		while(it.hasNext()) {
			String name = it.next();
			JFrame frame = base.get(name);
			if(frame.isVisible())
				return frame;
		}
		return null;
	}
	
	public JFrame getFrame(String name) {
		return base.get(name);
	}
	
	public void changeFrame(String name) {
		JFrame before = getActiveFrame();
		JFrame frame = getFrame(name);
		frame.setLocationRelativeTo(before);
		before.setVisible(false);
		frame.setVisible(true);
	}
	
	
	public void alert(String message) {
		JFrame frame = getActiveFrame();
		JOptionPane.showMessageDialog(frame, message, "알림", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void clear() {
		for(Map.Entry<String, JFrame> entry : base.entrySet()) {
			entry.getValue().dispose();
		}
		base.clear();
	}

	public static final int LOCATE_BY_PLATFORM = 1;
	public static final int LOCATE_AT_CENTER = 2;
	public JFrame getInstantFrame(int width, int height) {
		return getInstantFrame(width, height, LOCATE_BY_PLATFORM);
	}
	
	/**
	 * 
	 * @param width 창의 폭
	 * @param height 창의 높이
	 * @param locationOption 창의 위치(운영체제 또는 가운데 중 선택)
	 * @return 완성된 임시 창 객체
	 */
	public JFrame getInstantFrame(int width, int height, int locationOption) {
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		if(locationOption == LOCATE_BY_PLATFORM)
			frame.setLocationByPlatform(true);
		else if(locationOption == LOCATE_AT_CENTER) {
			Rectangle rect = ScreenManager.getManager().getMonitorRect(ScreenManager.MAIN_MONITOR);
			int xpos = (rect.x + rect.width - frame.getWidth()) / 2;
			int ypos = (rect.y + rect.height - frame.getHeight()) / 2;
			frame.setLocation(xpos, ypos);
		}
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		return frame;
	}
	
	public static class ProgressFrame extends JFrame{
		private ProgressPanel progressPanel;
		public ProgressPanel getProgressPanel() {
			return progressPanel;
		}
		public ProgressFrame() {
			progressPanel = new ProgressPanel();
			this.add(progressPanel);
		}
		public ProgressFrame(int count) {
			progressPanel = new ProgressPanel(count);
			this.add(progressPanel);
		}
		
	}
	
	public static class ProgressPanel extends JPanel{
		private int count;
		public void setCount(int count) {
			if(count <= 0) return;
			this.count = count;
		}
		public int getCount() {
			return count;
		}
		public void clearCount() {
			this.count = 0;
		}
		private JProgressBar progressBar = new JProgressBar();
		{
			this.setLayout(new BorderLayout());
			this.add(progressBar);
			progressBar.setStringPainted(true);
		}
		public ProgressPanel() {}
		public ProgressPanel(int count) {
			this.count = count;
		}
		public void setMax(int max) {
			progressBar.setMaximum(max);
		}
		public void setValue(int value) {
			progressBar.setValue(value);
		}
	}
	
	
}

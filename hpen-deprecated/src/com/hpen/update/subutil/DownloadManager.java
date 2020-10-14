package com.hpen.update.subutil;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class DownloadManager {
	private static DownloadManager manager = new DownloadManager();
	public static DownloadManager getManager() {
		return manager;
	}
	
	private DownloadManager() {}
	
	public File download(String url, String path) throws IOException{
		return download(url, path, true);
	}
	public File download(String url, String path, boolean windowVisible) throws IOException {
		URL link = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)link.openConnection();
		
		connection.setConnectTimeout(5000);
		
		try {
			connection.connect();
			
			int resCode = connection.getResponseCode();
			if(resCode != HttpURLConnection.HTTP_OK)
				throw new IOException("다운로드 할 수 없는 URL입니다");
			
			int length = (int)connection.getContentLengthLong();
			
			byte[] buffer = new byte[1024];
			try(
				InputStream in = connection.getInputStream();
				FileOutputStream out = new FileOutputStream(path);
			) {
				int downloadSize = 0;
				
				JFrame frame = FrameManager.getManager().getInstantFrame(400, 80, FrameManager.LOCATE_AT_CENTER);
				ProgressPanel p = new ProgressPanel(length);
				if(windowVisible) {
					frame.setTitle("다운로드중");
					frame.setContentPane(p);
					frame.setResizable(true);
					frame.setVisible(true);
				}
				
				while(true) {
					int size = in.read(buffer);
					if(size == -1) break;
					downloadSize += size;
					//event
					if(windowVisible) {
						p.setDownloadState(downloadSize);
						frame.setTitle("hpen 다운로드중...("+ downloadSize + " / " +length+")");
					}
					out.write(buffer, 0, size);
				}
				
				frame.dispose();
				
				return new File(path);
			}
		}finally {
			connection.disconnect();
		}
	}
	
	public static class ProgressPanel extends JPanel{
		private JProgressBar progressBar = new JProgressBar();
		public ProgressPanel(int max) {
			this.setLayout(new BorderLayout());
			this.add(progressBar);
			if(max >= 0) {
				progressBar.setMaximum(max);
				progressBar.setStringPainted(true);
			}
		}
		public void setDownloadState(int download) {
			progressBar.setValue(download);
		}
	}
}

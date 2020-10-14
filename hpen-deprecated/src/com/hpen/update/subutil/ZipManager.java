package com.hpen.update.subutil;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.jazzlib.ZipEntry;
import net.sf.jazzlib.ZipInputStream;
import net.sf.jazzlib.ZipOutputStream;

public class ZipManager {
	
	private static ZipManager manager = new ZipManager();
	public static ZipManager getManager() {
		return manager;
	}
	private ZipManager() {}
	
	private byte[] buffer = new byte[1024];
	
	/**
	 * 파일을 압축하는 메소드
	 * @param targetPath	압축할 대상의 상대경로 또는 절대경로
	 * @param zipPath 압축할 파일의 상대경로 또는 절대경로
	 * @throws IOException 
	 */
	public void zip(String targetPath, String zipPath) throws IOException{
		File target = new File(targetPath);
		if(!target.exists()) 
			throw new FileNotFoundException("폴더가 존재하지 않거나 폴더가 아닙니다");
		
		try(
			ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(new FileOutputStream(zipPath)));
		){
			zip(target, out);
		}
		catch(IOException e) {
			System.err.println("압축 오류 발생 : "+e.getMessage());
			throw new IOException("압축 오류 발생 : "+e.getMessage());
		}
	}
	
	private void zip(File target, ZipOutputStream out) throws IOException{
		File[] files = null;
		if(target.isDirectory()){
			files = target.listFiles();
		}
		else if(target.isFile()) {
			files = new File[] {target};
		}
		
		for(File f : files) {
			if(f.isFile()) {
				try(
					BufferedInputStream in = new BufferedInputStream(new FileInputStream(f.getPath()));	
				){			
					ZipEntry entry = new ZipEntry(f.getPath()); 
					out.putNextEntry(entry);
					while(true) {
						int size = in.read(buffer);
						if(size == -1) break;
						out.write(buffer, 0, size);
					}
				}
				finally {
					out.closeEntry();
				}
			}
			else if(f.isDirectory()){
				zip(f, out);
			}
		}
	}
	
	public void unzip(String zipName) throws IOException{
		unzip(zipName, false);
	}
	
	public void unzip(String zipName, boolean zipFileRemove) throws IOException{
		unzip(zipName, null, zipFileRemove, true);
	}
	
	public void unzip(String zipName, String destFolderName) throws IOException{
		unzip(zipName, destFolderName, false, true);
	}
	
	public void unzip(String zipName, String destFolderName, boolean zipFileRemove) throws IOException{
		unzip(zipName, destFolderName, zipFileRemove, true);
	}
	
	public void unzip(String zipName, String destFolderName, boolean zipFileRemove, boolean dialogVisible) throws IOException{
		File zipFile = new File(zipName);
		if(!zipFile.exists()) 
			throw new FileNotFoundException("압축파일이 존재하지 않습니다");
		
		if(destFolderName == null||destFolderName.equals(""))
			destFolderName = System.getProperty("user.dir");
		
		File destFolder = new File(destFolderName);
		if(!destFolder.exists()) destFolder.mkdirs();
		
		JFrame frame = FrameManager.getManager().getInstantFrame(500, 500, FrameManager.LOCATE_AT_CENTER);
		LogPanel panel = new LogPanel();
		if(dialogVisible) {
			frame.setContentPane(panel);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setTitle("압축 해제중 ["+destFolder+"]");
			frame.setResizable(false);
			frame.setVisible(true);
		}
		
		try(
			ZipInputStream in = new ZipInputStream(
				new BufferedInputStream(new FileInputStream(zipFile)));
		){
			while(true) {
				ZipEntry entry = in.getNextEntry();
				if(entry == null) break;
				String n = entry.getName();
				n = n.substring(n.indexOf("/")+1);
				
//				System.out.println(destFolder+" / "+n);
				if(dialogVisible) {
					panel.append(destFolder+"/"+n);
				}
				File f = new File(destFolder, n);
//				System.out.println(f);
//				System.out.println(f.getParentFile());
				if(!f.getParentFile().exists()) 
					f.getParentFile().mkdirs();
				
				if(entry.isDirectory()) {
					f.mkdirs();
				}
				else {
					try(
						FileOutputStream out = new FileOutputStream(f);
					){
						byte[] buffer = new byte[1024];
						
						while(true) {
							int size = in.read(buffer);
							if(size == -1) break;
							out.write(buffer, 0, size);
						}
					}catch(Exception e) {
						System.err.println(e.getMessage());
					}
				}
				if(dialogVisible) {
					panel.appendLine("[완료]");
				}
				in.closeEntry();
			}
		}
		if(zipFileRemove) {
			zipFile.delete();
		}
		frame.dispose();
	}
	
	public static class LogPanel extends JPanel{
		private JTextArea area = new JTextArea();
		private JScrollPane scrollPane = new JScrollPane(area);
		
		public LogPanel() {
			area.setFont(new Font("", Font.PLAIN, 20));
			setLayout(new BorderLayout());
			add(scrollPane);
		}
		
		public void appendLine(String str) {
			append(str+"\n");
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		}
		
		public void append(String str) {
			area.append(str);
		}
		
		public void clear() {
			area.setText("");
		}
	}
}

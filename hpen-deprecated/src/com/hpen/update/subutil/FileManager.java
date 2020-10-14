package com.hpen.update.subutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>파일 매니저</h1>
 * @author HWANG
 */
public class FileManager {
	private static FileManager manager = new FileManager();
	public static FileManager getManager() {
		return manager;
	}
	
	private FileManager() {
		
	}
	
	/**
	 * 지정한 폴더에 들어있는 파일/폴더를 반환(하위폴더는 제외)
	 * @param path 파일의 경로
	 * @return 파일 리스트
	 * @throws FileNotFoundException 지정한 파일이 존재하지 않을 경우
	 */
	public List<File> getFileList(String path) throws FileNotFoundException{
		File target = new File(path);
		return getFileList(target);
	}
	
	/**
	 * 지정한 폴더에 들어있는 파일/폴더를 반환(하위폴더는 제외)
	 * @param target 파일의 객체
	 * @return 파일 리스트
	 * @throws FileNotFoundException 지정한 파일이 존재하지 않을 경우
	 */
	public List<File> getFileList(File target) throws FileNotFoundException{
		if(!target.exists())
			throw new FileNotFoundException("대상 파일/폴더가 존재하지 않습니다");
		List<File> list = new ArrayList<>();
		if(target.isFile()) {
			list.add(target);
		}else if(target.isDirectory()){
			File[] files = target.listFiles();
			if(files != null) {
				for(File f : files) {
					list.add(f);
				}
			}
		}
		return list;
	}
	
	/**
	 * 하위 폴더를 포함한 모든 파일의 정보를 반환하는 메소드
	 * @param path 기준 경로
	 * @return 파일 리스트
	 * @throws FileNotFoundException 지정한 파일이 존재하지 않을 경우
	 */
	public List<File> getAllFileList(String path) throws FileNotFoundException{
		File target = new File(path);
		return getAllFileList(target);
	}
	
	/**
	 * 하위 폴더를 포함한 모든 파일의 정보를 반환하는 메소드
	 * @param target 기준 파일 객체
	 * @return 파일 리스트
	 * @throws FileNotFoundException 지정한 파일이 존재하지 않을 경우
	 */
	public List<File> getAllFileList(File target) throws FileNotFoundException{
		if(!target.exists())
			throw new FileNotFoundException("대상 파일/폴더가 존재하지 않습니다");
		List<File> list = new ArrayList<>();
		getAllFileListProc(list, target);
		return list;
	}
	
	/**
	 * getAllFileList의 실제 수행 코드(재귀호출로 구현)
	 * @param list 파일 객체가 추가될 list 참조
	 * @param target 대상 파일 객체
	 */
	private void getAllFileListProc(List<File> list, File target) {
		if(target.isDirectory()) {
			list.add(target);
			File[] files = target.listFiles();
			if(files != null) {
				for(File f : files) {
					getAllFileListProc(list, f);
				}
			}
		}
		else if(target.isFile()) {
			list.add(target);
		}
	}
	
	private byte[] buffer = new byte[2048];
	public File copy(File target, File dest) throws IOException{
		if(target.isFile()) {
			if(!dest.exists())
				dest.mkdirs();
			File nFile = new File(dest, target.getName());
			try(
				FileOutputStream out = new FileOutputStream(nFile);
				FileInputStream in = new FileInputStream(target);
			) {
				while(true) {
					int s = in.read(buffer);
					if(s == -1) break;
					out.write(buffer, 0, s);
				}
			}
			return nFile;
		}else if(target.isDirectory()) {
			File[] list = target.listFiles();
			if(list != null) {
				for(File f : list) {
					copy(f, dest);
				}
			}
			return target;
		}
		return null;
	}
}

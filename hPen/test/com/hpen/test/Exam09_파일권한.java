package com.hpen.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

public class Exam09_파일권한 {
	public static void main(String[] args) throws Exception{
		
//		Files.setPosixFilePermissions(Paths.get("C:\\Windows\\test.txt"), 
//				EnumSet.of(PosixFilePermission.OWNER_READ, 
//						PosixFilePermission.OWNER_WRITE, 
//						PosixFilePermission.OWNER_EXECUTE, 
//						PosixFilePermission.GROUP_WRITE,
//						PosixFilePermission.GROUP_READ, 
//						PosixFilePermission.GROUP_EXECUTE,
//						PosixFilePermission.OTHERS_READ,
//						PosixFilePermission.OTHERS_EXECUTE,
//						PosixFilePermission.OTHERS_WRITE));
		
//		File file = new File("C:\\Windows\\test.txt");
//		System.out.println(file.createNewFile());
//		Files.createFile(Paths.get("C:\\Windows\\test.txt"));
		
//		Path path = Paths.get("C:\\test.txt");
//		FileOwnerAttributeView view = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
//		UserPrincipal owner = view.getOwner();
//		System.out.println(owner.getName());
	}
}





package com.hpen.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Collections;
import java.util.EnumSet;

public class Exam09_파일권한JNI {
	public static void main(String[] args) throws Exception{
		Path file = Paths.get("c:/touch.txt");
	    AclFileAttributeView aclAttr = Files.getFileAttributeView(file, AclFileAttributeView.class);
	    System.out.println(aclAttr.getOwner());
	    for(AclEntry aclEntry : aclAttr.getAcl()){
	        System.out.println(aclEntry);
	    }
	    System.out.println();

	    UserPrincipalLookupService upls = file.getFileSystem().getUserPrincipalLookupService();
	    UserPrincipal user = upls.lookupPrincipalByName(System.getProperty("user.name"));
	    AclEntry.Builder builder = AclEntry.newBuilder();       
	    builder.setPermissions( EnumSet.of(AclEntryPermission.READ_DATA, AclEntryPermission.EXECUTE, 
	            AclEntryPermission.READ_ACL, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.READ_NAMED_ATTRS,
	            AclEntryPermission.WRITE_ACL, AclEntryPermission.DELETE
	    ));
	    builder.setPrincipal(user);
	    builder.setType(AclEntryType.ALLOW);
	    aclAttr.setAcl(Collections.singletonList(builder.build()));
	}
}

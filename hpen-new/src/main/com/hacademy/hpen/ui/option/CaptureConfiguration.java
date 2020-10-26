package com.hacademy.hpen.ui.option;

import java.io.Serializable;

import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@Slf4j
public class CaptureConfiguration extends AppConfiguration{
	public CaptureConfiguration() {
		super("CaptureConfiguration");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 	배경 속성
	 * 	- background
	 * 		- transparent : 배경 투명 설정(기본값)
	 * 		- pause : 이미지로 고정 화면 처리
	 */
	private String background = "transparent";
	public void setBackground(String type) {
		switch(type.toLowerCase()) {
		case "transparent":
		case "pause":
			background = type;
		}
	}
	
	/**
	 * 	화면 속성
	 * 	- borderThickness : 가이드 및 캡쳐영역 테두리 두께
	 * 	- mouseGuideColor : 마우스 보조선 색상
	 * 	- captureAreaColor : 캡쳐 영역 색상
	 */
	private int borderThickness = 3;
	public void setBorderThickness(int thickness) {
		if(between(thickness, 1, 10)) {
			borderThickness = thickness;
		}
	}
	
	@Setter
	private String mouseGuideColor = "black";
	@Setter
	private String captureAreaColor = "blue";
	
	/**
	 * 	캡쳐 속성
	 * 	- captureAction : 캡쳐 이후에 할 행동 유형
	 * 		- clipboard : 클립보드로 전송
	 * 		- saveTempFile : 자체 임시 파일로 저장
	 * 		- saveAsFile : 사용자가 저장할 파일명 선택
	 * 	- captureFileType : 캡쳐 저장 시 파일의 유형
	 * 		- png : PNG 파일
	 * 		- jpg : JPG 파일
	 * 	- captureFileSavePath : 캡쳐 파일 저장 시 저장 위치
	 * 	- captureFilePrefix : 캡쳐 파일 저장 시 접두사
	 * 	- captureFileSequence : 캡쳐 파일 시작번호
	 */
	private String captureAction = "clipboard";
	public void setCaptureAction(String action) {
		switch(action.toLowerCase()) {
		case "clipboard":
		case "saveTempFile":
		case "saveAsFile":
			captureAction = action;
		}
	}
	private String captureFileType = "png";
	public void setCaptureFileType(String captureFileType) {
		switch(captureFileType.toLowerCase()) {
		case "png":
		case "jpg":
			this.captureFileType = captureFileType;
		}
	}
	
	private String captureFileSavePath = System.getProperty("user.dir") + "/capture";
	
	private String captureFilePrefix = "capture";
	public void setCaptureFilePrefix(String captureFilePrefix) {
		if(captureFilePrefix == null) captureFilePrefix = "";
		this.captureFilePrefix = captureFilePrefix;
	}
	
	private int captureFileSequence = 1;
	public void setCaptureFileSequence(int captureFileSequence) {
		if(captureFileSequence < 0) return;
		this.captureFileSequence = captureFileSequence;
	}
	
	/**
	 * 시작 시 파일에서 정보를 불러오도록 설정
	 * 만약 파일이 없을 경우에는 기본 정보로 설정
	 */
	public void init() {
		try {
//			Properties props = PropertyLoader.load("capture.properties");
		}
		catch(Exception e) {
			log.error("capture.properties 불러오기 오류", e);
		}
	}
	
	/**
	 * 숫자 구간 검사 메소드
	 */
	private boolean between(int v, int a, int b) {
		return v >= a && v <= b;
	}

	@Override
	public void afterLoad(Object object) {
		
	}
}

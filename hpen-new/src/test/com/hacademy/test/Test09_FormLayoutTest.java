package com.hacademy.test;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test09_FormLayoutTest {
	
	public static JLabel label(String text) {
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setOpaque(false);
		return label;
	}
	public static JCheckBox checkbox() {
		JCheckBox checkbox = new JCheckBox();
		return checkbox;
	}
	public static JRadioButton radio(String text) {
		JRadioButton radio = new JRadioButton(text);
		return radio;
	}
	public static JRadioButton radioCk(String text) {
		JRadioButton radio = radio(text);
		radio.setSelected(true);
		return radio;
	}
	public static JTextField field(String text) {
		JTextField field = new JTextField();
		field.setText(text);
		return field;
	}
	public static JButton button(String text) {
		JButton button = new JButton(text);
		return button;
	}
	public static JComboBox<String> combo(String ... unit){
		JComboBox<String> box = new JComboBox<String>();
		for(int i=0; i <unit.length;i++) {
			box.addItem(unit[i]);
		}
		return box;
	}
	
	static class TestFrame extends JFrame{
		TestFrame(){
			screen();
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setBounds(100, 100, 500, 500);
			setResizable(false);
			setVisible(true);
		}
		void screen() {
			CellConstraints cc = new CellConstraints();
			FormLayout layout = new FormLayout(
				"100px,15px,120px,15px,100px,15px,120px"
			);
			DefaultFormBuilder builder = new DefaultFormBuilder(layout);
			
			builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			builder.appendSeparator("캡쳐 옵션");
			builder.append(label("화면 정지"), checkbox());
			builder.append(label("픽셀 표시"), checkbox());
	        builder.nextLine();
	        
	        builder.appendSeparator("마우스 가이드 설정");
	        builder.append(label("마우스 표시"), checkbox());
	        builder.nextLine();
	        
	        builder.append(label("가이드 표시"), checkbox());
	        builder.nextLine();
	        
	        builder.append(label("가이드 두께"));
	        builder.append(radio("가늘게"), radioCk("보통"), radio("두껍게"));
	        builder.nextLine();
	        
	        builder.append(label("가이드 색상"), button("클릭 후 설정"));
	        builder.nextLine();
	        
	        builder.appendSeparator("저장 설정");
	        
	        builder.append(label("저장 방식"));
	        builder.append(radioCk("클립보드"), radio("임시파일"), radio("물어보기"));
	        builder.nextLine();
	        
	        builder.append(label("저장 위치"));
	        builder.append(field(""), 3);
	        builder.append(button("위치 찾기"));
	        builder.nextLine();
	        
	        builder.append(label("파일 접두사"));
	        builder.append(field(""), 2);
	        builder.nextLine();
	        
	        builder.append(label("파일 확장자"));
	        builder.append(combo("PNG", "JPG"));
	        builder.nextLine();
	        
	        builder.append(label("파일 시작 번호"));
	        builder.append(field(""), 2);
	        builder.nextLine();
	        
			setContentPane(builder.getContainer());
		}
	}
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new McWinLookAndFeel());
		TestFrame t = new TestFrame();
	}
}

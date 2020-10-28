package com.hacademy.test;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

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
				"100px,15px,100px,15px,100px,15px,100px"
			);
			DefaultFormBuilder builder = new DefaultFormBuilder(layout);
			
			builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			builder.appendSeparator("캡쳐 옵션");
			builder.append(label("화면 정지"), checkbox());
	        builder.nextLine();
	        builder.append(label("저장 방식"));
	        builder.append(radioCk("클립보드"), radio("임시파일"), radio("물어보기"));
	        builder.nextLine();
	        builder.appendSeparator("마우스 가이드 설정");
	        builder.append(label("마우스 표시"), checkbox());
	        builder.nextLine();
	        builder.append(label("가이드 표시"), checkbox());
	        builder.nextLine();
	        builder.append(label("가이드 두께"));
	        builder.append(radio("가늘게"), radioCk("보통"), radio("두껍게"));
	        builder.nextLine();
	        
	        
	        
			setContentPane(builder.getContainer());
		}
	}
	
	public static void main(String[] args) {
		TestFrame t = new TestFrame();
	}
}

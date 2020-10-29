package com.hacademy.hpen.ui.option;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.hacademy.hpen.ui.option.process.CaptureConfiguration;
import com.hacademy.hpen.ui.option.process.ComponentMap;
import com.hacademy.hpen.util.component.ComponentManager;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

@Component
public class CaptureConfigurationPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComponentManager c;
	
	@Autowired
	private CaptureConfiguration conf;
	
	private ComponentMap components = new ComponentMap();
	
	public CaptureConfigurationPanel() {}
	
	public void init() {
		component();
		value();
		event();
		display();
	}
	private void component() {
		//create
		components.put("pause", c.checkbox());
		components.put("pixel", c.checkbox());
		components.put("mouse", c.checkbox());
		components.put("guide", c.checkbox());
		ButtonGroup g1 = new ButtonGroup();
		components.put("guideThickness1", c.radio(g1, "가늘게"));
		components.put("guideThickness2", c.radio(g1, "보통"));
		components.put("guideThickness3", c.radio(g1, "두껍게"));
		
		components.put("guideColorButton", c.button("클릭 후 설정", conf.getMouseGuideColor()));
		
		ButtonGroup g2 = new ButtonGroup();
		components.put("saveType1", c.radio(g2, "클립보드", conf.getCaptureAction() == CaptureConfiguration.SAVE_CLIPBOARD));
		components.put("saveType2", c.radio(g2, "임시파일", conf.getCaptureAction() == CaptureConfiguration.SAVE_TEMP_FILE));
		components.put("saveType3", c.radio(g2, "물어보기", conf.getCaptureAction() == CaptureConfiguration.SAVE_AS_FILE));
		
		components.put("saveFilePath", c.field(conf.getCaptureFileSavePath() == null ? "" : conf.getCaptureFileSavePath()));
		components.put("saveFilePathFindButton", c.button("위치 찾기"));
		
		components.put("saveFilePrefix", c.field());
		components.put("saveFileSuffix", c.combo("PNG", "JPG"));
		components.put("saveFileSequence", c.field());
	}
	private void value() {
		//화면 정지 옵션
		components.get("pause", JCheckBox.class).setSelected(
			conf.getBackground() == CaptureConfiguration.PAUSE
		);
		
		//픽셀 표시 옵션
		components.get("pixel", JCheckBox.class).setSelected(
			conf.getPixel() == CaptureConfiguration.SHOW
		);
		
		//마우스 표시 옵션
		components.get("mouse", JCheckBox.class).setSelected(
			conf.getMouse() == CaptureConfiguration.SHOW
		);
		
		//마우스 가이드 표시 옵션
		components.get("guide", JCheckBox.class).setSelected(
			conf.getGuide() == CaptureConfiguration.SHOW
		);
		
		//가이드 두께 라디오버튼
		components.get("guideThickness1", JRadioButton.class).setSelected(
			conf.getBorderThickness() == CaptureConfiguration.THIN
		);
		components.get("guideThickness2", JRadioButton.class).setSelected(
			conf.getBorderThickness() == CaptureConfiguration.NORMAL
		);
		components.get("guideThickness3", JRadioButton.class).setSelected(
			conf.getBorderThickness() == CaptureConfiguration.THICK
		);
		//가이드 버튼 색상
		components.get("guideColorButton", JButton.class).setBackground(
			conf.getMouseGuideColor()
		);
		components.get("guideColorButton", JButton.class).setForeground(
			c.getContrastColor(conf.getMouseGuideColor())
		);
		
		//저장 방식 라디오버튼
		components.get("saveType1", JRadioButton.class).setSelected(
			conf.getCaptureAction() == CaptureConfiguration.SAVE_CLIPBOARD
		);
		components.get("saveType2", JRadioButton.class).setSelected(
			conf.getCaptureAction() == CaptureConfiguration.SAVE_TEMP_FILE
		);
		components.get("saveType3", JRadioButton.class).setSelected(
			conf.getCaptureAction() == CaptureConfiguration.SAVE_AS_FILE
		);
		
		
	}
	private void event() {
		//화면 정지 옵션
		components.get("pause", JCheckBox.class).addChangeListener(e->{
			conf.setPause(components.isSelected("pause"));
		});
		
		//픽셀 표시 옵션
		components.get("pixel", JCheckBox.class).addChangeListener(e->{
			conf.setPixel(components.isSelected("pixel"));
		});
		
		//마우스 표시 옵션
		components.get("mouse", JCheckBox.class).addChangeListener(e->{
			conf.setMouse(components.isSelected("mouse"));
		});
		
		//마우스 가이드 표시 옵션
		components.get("guide", JCheckBox.class).addChangeListener(e->{
			conf.setGuide(components.isSelected("guide"));
		});
		
		//가이드 두께 라디오버튼
		components.get("guideThickness1", JRadioButton.class).addActionListener(e->{
			conf.setBorderThickness(CaptureConfiguration.THIN);
		});
		components.get("guideThickness2", JRadioButton.class).addActionListener(e->{
			conf.setBorderThickness(CaptureConfiguration.NORMAL);
		});
		components.get("guideThickness3", JRadioButton.class).addActionListener(e->{
			conf.setBorderThickness(CaptureConfiguration.THICK);
		});
		//가이드 버튼 색상
		components.get("guideColorButton", JButton.class).addActionListener(e->{
			Color color = JColorChooser.showDialog(this, "가이드 색상 선택", conf.getMouseGuideColor());
			if(color != null) {
				conf.setMouseGuideColor(color);
				components.get("guideColorButton", JButton.class).setBackground(color);
				components.get("guideColorButton", JButton.class).setForeground(c.getContrastColor(color));
			}
		});
		
		//저장 방식 라디오버튼
		components.get("saveType1", JRadioButton.class).addActionListener(e->{
			conf.setCaptureAction(CaptureConfiguration.SAVE_CLIPBOARD);
		});
		components.get("saveType2", JRadioButton.class).addActionListener(e->{
			conf.setCaptureAction(CaptureConfiguration.SAVE_TEMP_FILE);
		});
		components.get("saveType3", JRadioButton.class).addActionListener(e->{
			conf.setCaptureAction(CaptureConfiguration.SAVE_AS_FILE);
		});
		
	}
	private void display() {
		FormLayout layout = new FormLayout(
			"100px,15px,120px,15px,100px,15px,120px"//column spec
		);
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		builder.appendSeparator("캡쳐 옵션");
		builder.append(c.label("화면 정지"), components.get("pause"));
		builder.append(c.label("픽셀 표시"), components.get("pixel"));
        builder.nextLine();
        
        builder.appendSeparator("마우스 가이드 설정");
        builder.append(c.label("마우스 표시"), components.get("mouse"));
        builder.nextLine();
        
        builder.append(c.label("가이드 표시"), components.get("guide"));
        builder.nextLine();
        
        builder.append(c.label("가이드 두께"));
        builder.append(
    		components.get("guideThickness1"),
    		components.get("guideThickness2"),
    		components.get("guideThickness3")
        );
        builder.nextLine();
        
        builder.append(c.label("가이드 색상"));
        builder.append(components.get("guideColorButton"));
        builder.nextLine();
        
        builder.appendSeparator("저장 설정");
        
        builder.append(c.label("저장 방식"));
        builder.append(
    		components.get("saveType1"),
    		components.get("saveType2"),
    		components.get("saveType3")
		);
        builder.nextLine();
        
        builder.append(c.label("저장 위치"));
        builder.append(components.get("saveFilePath"), 3);
        builder.append(components.get("saveFilePathFindButton"));
        builder.nextLine();
        
        builder.append(c.label("파일 접두사"));
        builder.append(components.get("saveFilePrefix"), 2);
        builder.nextLine();
        
        builder.append(c.label("파일 확장자"));
        builder.append(components.get("saveFileSuffix"));
        builder.nextLine();
        
        builder.append(c.label("파일 시작 번호"));
        builder.append(components.get("saveFileSequence"), 2);
        builder.nextLine();
        
        setLayout(new BorderLayout());
        add(builder.getPanel());
	}
}

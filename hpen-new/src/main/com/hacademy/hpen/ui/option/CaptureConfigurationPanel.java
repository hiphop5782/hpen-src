package com.hacademy.hpen.ui.option;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.hacademy.hpen.ui.option.process.CaptureConfiguration;
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
	
	private Map<String, JComponent> components = new HashMap<>();
	
	public CaptureConfigurationPanel() {}
	
	public void init() {
		component();
		display();
	}
	private void component() {
		components.put("pause", c.checkbox(conf.getBackground() == CaptureConfiguration.PAUSE));
		components.put("pixel", c.checkbox());
		components.put("mouse", c.checkbox());
		components.put("guide", c.checkbox());
		
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
        
        ButtonGroup g1 = new ButtonGroup();
        builder.append(c.label("가이드 두께"));
        builder.append(
    		c.radio(g1, "가늘게", conf.getBorderThickness() == CaptureConfiguration.THIN), 
    		c.radio(g1, "보통", conf.getBorderThickness() == CaptureConfiguration.NORMAL), 
    		c.radio(g1, "두껍게", conf.getBorderThickness() == CaptureConfiguration.THICK)
        );
        builder.nextLine();
        
        builder.append(c.label("가이드 색상"), c.button("클릭 후 설정", conf.getMouseGuideColor(), e->{
        	Color color = JColorChooser.showDialog(this, "마우스 가이드 색상 설정", conf.getMouseGuideColor());
        	if(color == null) return;
        	conf.setMouseGuideColor(color);
        }));
        builder.nextLine();
        
        builder.appendSeparator("저장 설정");
        
        ButtonGroup g2 = new ButtonGroup();
        builder.append(c.label("저장 방식"));
        builder.append(
    		c.radio(g2, "클립보드", conf.getCaptureAction() == CaptureConfiguration.SAVE_CLIPBOARD), 
    		c.radio(g2, "임시파일", conf.getCaptureAction() == CaptureConfiguration.SAVE_TEMP_FILE), 
    		c.radio(g2, "물어보기", conf.getCaptureAction() == CaptureConfiguration.SAVE_AS_FILE)
		);
        builder.nextLine();
        
        String saveLocation = conf.getCaptureFileSavePath() == null ? "" : conf.getCaptureFileSavePath();
        
        JButton b1 = c.button("위치 찾기", e->{
        	
        });
        builder.append(c.label("저장 위치"));
        builder.append(c.field(saveLocation), 3);
        builder.append(c.button("위치 찾기", e->{
        	
        }));
        builder.nextLine();
        
        builder.append(c.label("파일 접두사"));
        builder.append(c.field(""), 2);
        builder.nextLine();
        
        builder.append(c.label("파일 확장자"));
        builder.append(c.combo("PNG", "JPG"));
        builder.nextLine();
        
        builder.append(c.label("파일 시작 번호"));
        builder.append(c.field(""), 2);
        builder.nextLine();
        
        setLayout(new BorderLayout());
        add(builder.getPanel());
	}
}

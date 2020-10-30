package com.hacademy.hpen.ui.option;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(()->{
			try {TimeUnit.MILLISECONDS.sleep(200);}catch(Exception e) {}
			value();
		});
		component();
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
		
		components.put("guideColorButton", c.button("클릭 후 설정"));
		components.put("captureAreaBorderColorButton", c.button("클릭 후 설정"));
		
		ButtonGroup g2 = new ButtonGroup();
		components.put("saveType1", c.radio(g2, "클립보드"));
		components.put("saveType2", c.radio(g2, "임시파일"));
		components.put("saveType3", c.radio(g2, "물어보기"));
		
		components.put("saveFilePath", c.field());
		components.put("saveFilePathFindButton", c.button("위치 찾기"));
		
		components.put("saveFilePrefix", c.field());
		
		ButtonGroup g3 = new ButtonGroup();
		components.put("saveFileSuffix1", c.radio(g3, "PNG 파일"));
		components.put("saveFileSuffix2", c.radio(g3, "JPG 파일"));
		components.put("saveFileSequence", c.field());
	}
	private void value() {
		//화면 정지 옵션
		components.get("pause", JCheckBox.class).setSelected(conf.isPause());
		
		//픽셀 표시 옵션
		components.get("pixel", JCheckBox.class).setSelected(conf.isPixelVisible());
		
		//마우스 표시 옵션
		components.get("mouse", JCheckBox.class).setSelected(conf.isMouseVisible());
		
		//마우스 가이드 표시 옵션
		components.get("guide", JCheckBox.class).setSelected(conf.isGuideVisible());
		
		//가이드 두께 라디오버튼
		components.get("guideThickness1", JRadioButton.class).setSelected(conf.isThin());
		components.get("guideThickness2", JRadioButton.class).setSelected(conf.isNormal());
		components.get("guideThickness3", JRadioButton.class).setSelected(conf.isThick());
		//가이드 버튼 색상
		components.get("guideColorButton", JButton.class).setBackground(conf.getMouseGuideColor());
		components.get("guideColorButton", JButton.class).setForeground(c.getContrastColor(conf.getMouseGuideColor()));
		//캡쳐 영역 테두리 색상
		components.get("captureAreaBorderColorButton", JButton.class).setBackground(conf.getCaptureAreaColor());
		components.get("captureAreaBorderColorButton", JButton.class).setForeground(c.getContrastColor(conf.getCaptureAreaColor()));
		
		//저장 방식 라디오버튼
		components.get("saveType1", JRadioButton.class).setSelected(conf.isSaveClipboard());
		components.get("saveType2", JRadioButton.class).setSelected(conf.isSaveTempFile());
		components.get("saveType3", JRadioButton.class).setSelected(conf.isSaveAsFile());
		
		//저장 경로
		components.get("saveFilePath", JTextField.class).setText(conf.getCaptureFileSavePath());
		components.get("saveFilePrefix", JTextField.class).setText(conf.getCaptureFilePrefix());
		components.get("saveFileSuffix1", JRadioButton.class).setSelected(conf.isPng());
		components.get("saveFileSuffix2", JRadioButton.class).setSelected(conf.isJpg());
		components.get("saveFileSequence", JTextField.class).setText(String.valueOf(conf.getCaptureFileSequence()));
		
	}
	private void event() {
		//화면 정지 옵션
		components.get("pause", JCheckBox.class).addActionListener(e->{
			conf.setPause(components.isSelected("pause"));
		});
		
		//픽셀 표시 옵션
		components.get("pixel", JCheckBox.class).addActionListener(e->{
			conf.setPixel(components.isSelected("pixel"));
		});
		
		//마우스 표시 옵션
		components.get("mouse", JCheckBox.class).addActionListener(e->{
			conf.setMouse(components.isSelected("mouse"));
		});
		
		//마우스 가이드 표시 옵션
		components.get("guide", JCheckBox.class).addActionListener(e->{
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
		
		components.get("captureAreaBorderColorButton", JButton.class).addActionListener(e->{
			Color color = JColorChooser.showDialog(this, "캡쳐 영역 테두리 색상 선택", conf.getCaptureAreaColor());
			if(color != null) {
				conf.setCaptureAreaColor(color);
				components.get("captureAreaBorderColorButton", JButton.class).setBackground(color);
				components.get("captureAreaBorderColorButton", JButton.class).setForeground(c.getContrastColor(color));
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
		
		//저장 경로
		components.get("saveFilePath", JTextField.class).addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				conf.setCaptureFileSavePath(components.get("saveFilePath", JTextField.class).getText());
			}
		});
		components.get("saveFilePathFindButton", JButton.class).addActionListener(e->{
			String root = conf.getCaptureFileSavePath();
			if(root == null || root.isEmpty()) root = ".";
			JFileChooser chooser = new JFileChooser(root);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getAbsolutePath();
				conf.setCaptureFileSavePath(path);
				components.get("saveFilePath", JTextField.class).setText(path);
			}
		});
		components.get("saveFilePrefix", JTextField.class).addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				conf.setCaptureFilePrefix(components.get("saveFilePrefix", JTextField.class).getText());
			}
		});
		components.get("saveFilePrefix", JTextField.class).addActionListener(e->{
			conf.setCaptureFilePrefix(components.get("saveFilePrefix", JTextField.class).getText());
		});
		components.get("saveFileSuffix1", JRadioButton.class).addActionListener(e->{
			conf.setCaptureFileType(CaptureConfiguration.PNG);
		});
		components.get("saveFileSuffix2", JRadioButton.class).addActionListener(e->{
			conf.setCaptureFileType(CaptureConfiguration.JPG);
		});
		components.get("saveFileSequence", JTextField.class).addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					int seq = Integer.parseInt(components.get("saveFileSequence", JTextField.class).getText());
					conf.setCaptureFileSequence(seq);
				}
				catch(Exception err) {
					JOptionPane.showMessageDialog(CaptureConfigurationPanel.this, "시작번호는 숫자로 입력해야 합니다", "알림", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		components.get("saveFileSequence", JTextField.class).addActionListener(e->{
			try {
				int seq = Integer.parseInt(components.get("saveFileSequence", JTextField.class).getText());
				conf.setCaptureFileSequence(seq);
			}
			catch(Exception err) {
				JOptionPane.showMessageDialog(CaptureConfigurationPanel.this, "시작번호는 숫자로 입력해야 합니다", "알림", JOptionPane.PLAIN_MESSAGE);
			}
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
        
        builder.append(c.label("캡쳐 테두리 색상"));
        builder.append(components.get("captureAreaBorderColorButton"));
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
        builder.append(components.get("saveFileSuffix1"), components.get("saveFileSuffix2"));
        builder.nextLine();
        
        builder.append(c.label("파일 시작 번호"));
        builder.append(components.get("saveFileSequence"), 2);
        builder.nextLine();
        
        setLayout(new BorderLayout());
        add(builder.getPanel());
	}
}

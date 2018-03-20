package com.hpen.property.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import com.hpen.property.CaptureOption;

public class CaptureSettingPanel extends JPanel{
	private JLabel[] lb = new JLabel[11];
	private String[] lbText = new String[]{
			"캡쳐 설정", "실시간 캡쳐","이미지 저장",
			"그리기 설정", "캡쳐 사각형 두께","캡쳐 사각형 색상",
			"확대 설정", "확대 보기 배율","확대 보기 크기",
			"저장 설정", "저장 폴더"
	};
	private Font font = new Font("굴림", Font.PLAIN, 15);
		
	private String[] colorString = new String[]{
			"black","darkGray","gray","lightGray","white",
			"red","magenta","pink","orange","yellow","green","cyan","blue"
	};
	private JComboBox<String> combo = new JComboBox<>(colorString);
	
	private JSpinner spinner = new JSpinner();
	private JSpinner zoomSpinner = new JSpinner();
	private JSpinner zoomSpinner2 = new JSpinner();
	
	private JTextField saveFolderField = new JTextField();
	private JButton saveFolderBtn = new JButton("...");
	
	private JRadioButton[][] radio = new JRadioButton[2][2];
	private String[][] radioText = new String[][]{
		{"사용","사용하지 않음"},{"클립보드에 저장","파일로 저장"}
	};
	public CaptureSettingPanel(){
		super(null);
		this.setBackground(Color.white);
		display();
		event();
		initialValue();
	}
	
	private void initialValue(){
		CaptureOption options = CaptureOption.getInstance();
		
		radio[0][0].setSelected(options.isLiveCapture());
		radio[0][1].setSelected(!options.isLiveCapture());

		radio[1][0].setSelected(options.isCopytoClipboard());
		radio[1][1].setSelected(!options.isCopytoClipboard());

		combo.setSelectedItem(options.getCaptureBorderColorString());
		saveFolderField.setText(options.getSaveFolder());
		spinner.setValue(options.getCaptureBorderThickness());
		zoomSpinner.setValue(options.getZoomrate());
		zoomSpinner2.setValue(options.getZoomsize());
	}
	
	private void display(){
		this.setBackground(Color.white);
		
		//label
		for(int i=0; i<lb.length; i++){
			lb[i] = new JLabel(lbText[i], JLabel.CENTER);
			lb[i].setOpaque(true);
			lb[i].setBackground(Color.white);
			lb[i].setFont(font);
			//lb[i].setBorder(border);
			lb[i].setSize(120, 20);
			this.add(lb[i]);
		}
		
		Border linear = BorderFactory.createLineBorder(Color.BLACK, 3, true);
		Border empty = BorderFactory.createEmptyBorder(0, 10, 0, 0);
		Border comp = BorderFactory.createCompoundBorder(linear, empty);
		for(int i=0; i<=lb.length; i++){
			if(i % 3 == 0){
				lb[i].setBorder(comp);
				lb[i].setHorizontalAlignment(JLabel.LEFT);
				lb[i].setBackground(Color.black);
				lb[i].setForeground(Color.white);
			}
		}
		this.setBorder(linear);
		
		//radio
		for(int i=0; i<radio.length; i++){
			for(int j=0; j<radio[i].length; j++){
				radio[i][j] = new JRadioButton(radioText[i][j]);
				radio[i][j].setFont(font);
				radio[i][j].setSize(130, 20);
				radio[i][j].setBackground(Color.white);
				this.add(radio[i][j]);
			}
		}
		
		radio[0][0].setEnabled(false);
		radio[0][1].setEnabled(false);
		
		ButtonGroup groupA = new ButtonGroup();
		ButtonGroup groupB = new ButtonGroup();
		groupA.add(radio[0][0]);		groupA.add(radio[0][1]);
		groupB.add(radio[1][0]); 	groupB.add(radio[1][1]);
		
		//combo
		combo.setFont(font);
		ComboboxColorRenderer renderer = new ComboboxColorRenderer(combo, colorString);
		combo.setRenderer(renderer);
		this.add(combo);
		
		//spinner
		this.add(spinner);
		
		spinner.setFont(font);
		Border compound = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(3, 3, 3, 3));
		spinner.setBorder(compound);
		SpinnerModel spinModel = new SpinnerNumberModel(3, 1, 5, 1);
		spinner.setModel(spinModel);
		
		zoomSpinner.setFont(font);
		zoomSpinner.setBorder(compound);
		SpinnerModel zoomSpinModel = new SpinnerNumberModel(5, 1, 10, 1);
		zoomSpinner.setModel(zoomSpinModel);
		this.add(zoomSpinner);
		
		zoomSpinner2.setFont(font);
		zoomSpinner2.setBorder(compound);
		SpinnerModel zoomSpinModel2 = new SpinnerNumberModel(200, 100, 300, 100);
		zoomSpinner2.setModel(zoomSpinModel2);
		this.add(zoomSpinner2);
		
		//saveFolder textfield
		saveFolderField.setBorder(compound);
		saveFolderField.setFont(font);
		this.add(saveFolderField);
		
		saveFolderBtn.setBackground(Color.lightGray);
		saveFolderBtn.setBorder(compound);
		this.add(saveFolderBtn);
		
		int x = 10, y = 10;
		lb[0].setBounds(x, y, 400, 30);
		
		lb[1].setLocation(x, y+=40);	
		radio[0][0].setLocation(x+130, y);
		radio[0][1].setLocation(x+270, y);
		
		lb[2].setLocation(x, y+=30);
		radio[1][0].setLocation(x+130, y);
		radio[1][1].setLocation(x+270, y);
		
		lb[3].setBounds(x, y+=30, 400, 30);
		
		lb[4].setBounds(x, y+=40, 120, 25);
		spinner.setBounds(x+130, y, 75, 25);
		
		lb[5].setBounds(x, y+=35, 120, 25);
		combo.setBounds(x+130, y, 80, 25);
		
		lb[6].setBounds(x, y+=35, 400, 30);
		
		lb[7].setBounds(x, y+=40, 120, 25);
		zoomSpinner.setBounds(x+130, y, 75, 25);
		
		lb[8].setLocation(x, y+=35);
		zoomSpinner2.setBounds(x+130, y, 75, 25);
		
		lb[9].setBounds(x, y+=35, 400, 30);
		lb[10].setBounds(x, y+=40, 120, 25);
		saveFolderField.setBounds(x+130, y, 230, 25);
		saveFolderBtn.setBounds(x+370, y, 30, 25);
	}
	
	private JFileChooser fileChooser = new JFileChooser(CaptureOption.getInstance().getSaveFolder());
	private void event(){
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveFolderBtn.addActionListener(e->{
			int select = fileChooser.showOpenDialog(CaptureSettingPanel.this);
			if(select != 0) return;
			File file = fileChooser.getSelectedFile();
			saveFolderField.setText(file.toString());
			CaptureOption.getInstance().setSaveFolder(file.toString());
		});
		
		combo.addItemListener(e->{
			if(e.getStateChange() == ItemEvent.SELECTED){
				String colorName = (String)e.getItem();
				CaptureOption.getInstance().setCaptureBorderColorString(colorName);
				return;
			}
		});
		
		spinner.addChangeListener(e->{
			CaptureOption.getInstance().setCaptureBorderThickness((Integer)spinner.getValue());
		});
		zoomSpinner.addChangeListener(e->{
			CaptureOption.getInstance().setZoomrate((Integer)zoomSpinner.getValue());
		});
		zoomSpinner2.addChangeListener(e->{
			CaptureOption.getInstance().setZoomsize((Integer)zoomSpinner2.getValue());
		});
		
		for(int j=0; j<radio.length; j++){
			radio[0][j].addActionListener(e->{
				CaptureOption.getInstance().setLiveCapture(radio[0][0].isSelected());
			});
		}
		for(int j=0; j<radio.length; j++){
			radio[1][j].addActionListener(e->{
				CaptureOption.getInstance().setCaptureCopytoClipboard(radio[1][0].isSelected());
			});
		}
	}
	
	class ComboboxColorRenderer extends JPanel implements ListCellRenderer{
		private static final long serialVersionUID = 4245806196033457534L;
		private String[] colorString;
		private ArrayList<Color> colorlist = new ArrayList<>();
		public String[] getColorString() {
			return colorString;
		}
		public void setColorString(String[] colorString) {
			this.colorString = colorString;
			try{
				Class<?> cs = Class.forName("java.awt.Color");
				for(String s : colorString){
					Field f = cs.getField(s);
					Color c = (Color)f.get(null);
					colorlist.add(c);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		private JPanel textPanel;
		private JLabel label;
		public ComboboxColorRenderer(JComboBox<?> combo, String[] colorString){
			this.setColorString(colorString);
			this.setLayout(new BorderLayout());
			textPanel = new JPanel(new BorderLayout());
			label = new JLabel();
			textPanel.add(label);
			label.setOpaque(true);
			label.setFont(combo.getFont());
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if(isSelected){
				setBackground(list.getSelectionBackground());
			}else{
				setBackground(Color.white);
			}
			label.setBackground(getBackground());
			label.setText(value.toString());
			if(index >= 0)
				label.setForeground(colorlist.get(index));
			return label;
		}
		
	}
	
}

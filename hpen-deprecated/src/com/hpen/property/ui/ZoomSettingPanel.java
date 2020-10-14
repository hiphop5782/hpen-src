package com.hpen.property.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import com.hpen.property.ZoomOption;

public class ZoomSettingPanel extends JPanel{
	private String[] labelString = new String[]{
			"위치 설정", "시작 X지점", "시작 Y지점", "폭", "높이", "배율 옵션","배율"
	};
	private JLabel[] label = new JLabel[labelString.length];
	private JSpinner[] spinner = new JSpinner[labelString.length - 2];
	private Font font = new Font("굴림", Font.PLAIN, 15);
	
	public ZoomSettingPanel(){
		super(null);
		this.setBackground(Color.white);
		display();
		event();
		initialValue();
	}
	
	private void display(){
		//label
		for(int i=0; i<label.length; i++){
			label[i] = new JLabel(labelString[i], JLabel.CENTER);
			label[i].setOpaque(true);
			label[i].setBackground(Color.white);
			label[i].setFont(font);
			label[i].setSize(120, 25);
			this.add(label[i]);
		}
		
		Border linear = BorderFactory.createLineBorder(Color.BLACK, 3, true);
		Border empty = BorderFactory.createEmptyBorder(0, 10, 0, 0);
		Border comp = BorderFactory.createCompoundBorder(linear, empty);
		label[0].setHorizontalAlignment(JLabel.LEFT);
		label[5].setHorizontalAlignment(JLabel.LEFT);
		label[0].setBorder(comp);		label[0].setSize(400, 30);
		label[0].setBackground(Color.black);
		label[0].setForeground(Color.white);
		label[5].setBorder(comp);		label[5].setSize(400, 30);
		label[5].setBackground(Color.black);
		label[5].setForeground(Color.white);
//		this.setBorder(linear);
		
		Border compound = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(3, 3, 3, 3));
		for(int i=0; i<spinner.length; i++){
			spinner[i] = new JSpinner();
			spinner[i].setFont(font);
			spinner[i].setBackground(Color.white);
			spinner[i].setBorder(compound);
			spinner[i].setSize(75, 25);
			this.add(spinner[i]);
		}
		
		spinner[0].setModel(new SpinnerNumberModel(0, 0, 1000, 50));
		spinner[1].setModel(new SpinnerNumberModel(0, 0, 1000, 50));
		spinner[2].setModel(new SpinnerNumberModel(400, 150, 1000, 50));
		spinner[3].setModel(new SpinnerNumberModel(350, 150, 1000, 50));
		spinner[4].setModel(new SpinnerNumberModel(3,1,10,1));
		
		int x = 10, y = 10;
		label[0].setLocation(x, y);
		label[1].setLocation(x, y += 40);		spinner[0].setLocation(x+130, y);
		label[2].setLocation(x, y += 35);		spinner[1].setLocation(x+130, y);
		label[3].setLocation(x, y += 35);		spinner[2].setLocation(x+130, y);
		label[4].setLocation(x, y += 35);		spinner[3].setLocation(x+130, y);
		label[5].setLocation(x, y += 35);		
		label[6].setLocation(x, y += 40);		spinner[4].setLocation(x+130, y);
	}
	
	private void event(){
		for(int i=0; i<spinner.length; i++){
			spinner[i].addChangeListener(e->{
				JSpinner target = (JSpinner)e.getSource();
				int value = (Integer)target.getValue();
				if(target == spinner[0])
					ZoomOption.getInstance().setX(value);
				else if(target == spinner[1])
					ZoomOption.getInstance().setY(value);
				else if(target == spinner[2])
					ZoomOption.getInstance().setWidth(value);
				else if(target == spinner[3])
					ZoomOption.getInstance().setHeight(value);
				else if(target == spinner[4])
					ZoomOption.getInstance().setZoom(value);
			});
		}
	}
	
	private void initialValue(){
		spinner[0].setValue(ZoomOption.getInstance().getX());
		spinner[1].setValue(ZoomOption.getInstance().getY());
		spinner[2].setValue(ZoomOption.getInstance().getWidth());
		spinner[3].setValue(ZoomOption.getInstance().getHeight());
		spinner[4].setValue(ZoomOption.getInstance().getZoom());
	}
}

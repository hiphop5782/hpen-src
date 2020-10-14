package com.hpen.property.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import com.hpen.property.DrawingOption;

public class DefaultSettingPanel extends JPanel {

	private JLabel[] lb = new JLabel[18];
	private String[] lbText = new String[] { "포인터 색상", "포인터 두께", "색상1", "색상2", "색상3", "색상4", "색상5", "색상6", "색상7", "색상8",
			"색상9", "색상0", "필기 글꼴", "글자 크기", "저장 폴더", "포인터 옵션", "필기 옵션", "저장 옵션" };
	private Font font = new Font("굴림", Font.PLAIN, 15);

	private JButton pointColorButton = new JButton();
	private JButton[] colorButton = new JButton[10];

	private JSpinner spinner = new JSpinner();
	private JSpinner fontSizer = new JSpinner();

	private String[] fontString = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private JComboBox<String> fontCombo = new JComboBox<>(fontString);

	private JTextField saveFolderField = new JTextField();
	private JButton saveFolderBtn = new JButton("...");

	public DefaultSettingPanel() {
		super(null);
		this.setBackground(Color.white);
		display();
		event();
		initialValue();
	}

	private void initialValue() {
		for (int i = 0; i < colorButton.length; i++) {
			colorButton[i].setBackground(DrawingOption.getInstance().getPointColor(i));
		}
		pointColorButton.setBackground(DrawingOption.getInstance().getPointColor());
		fontCombo.setSelectedItem(DrawingOption.getInstance().getFontfamily());
		saveFolderField.setText(DrawingOption.getInstance().getSaveFolder().getPath());
		spinner.setValue(DrawingOption.getInstance().getPointThickness());
		fontSizer.setValue(DrawingOption.getInstance().getFontSize());
	}

	private void display() {
		this.setBackground(Color.white);

		// label
		for (int i = 0; i < lb.length; i++) {
			lb[i] = new JLabel(lbText[i], JLabel.RIGHT);
			lb[i].setOpaque(true);
			lb[i].setBackground(Color.white);
			lb[i].setFont(font);
			// lb[i].setBorder(border);
			lb[i].setSize(80, 20);
			this.add(lb[i]);
		}

		int x = 10, y = 10;
		lb[14].setBounds(x, y, 400, 30);
		lb[0].setLocation(x, y += 40);
		lb[1].setLocation(x + 200, y);
		lb[15].setBounds(x, y += 30, 400, 30);
		lb[2].setLocation(x, y += 40);
		lb[3].setLocation(x + 200, y);
		lb[4].setLocation(x, y += 30);
		lb[5].setLocation(x + 200, y);
		lb[6].setLocation(x, y += 30);
		lb[7].setLocation(x + 200, y);
		lb[8].setLocation(x, y += 30);
		lb[9].setLocation(x + 200, y);
		lb[10].setLocation(x, y += 30);
		lb[11].setLocation(x + 200, y);
		lb[12].setBounds(x, y += 30, 80, 25);
		lb[13].setBounds(x, y += 30, 80, 25);
		lb[16].setBounds(x, y += 30, 400, 30);
		lb[17].setBounds(x, y += 40, 80, 25);

		Border linear = BorderFactory.createLineBorder(Color.BLACK, 3, true);
		Border empty = BorderFactory.createEmptyBorder(0, 10, 0, 0);
		Border comp = BorderFactory.createCompoundBorder(linear, empty);
		for (int i = 14; i <= 16; i++) {
			lb[i].setBorder(comp);
			lb[i].setHorizontalAlignment(JLabel.LEFT);
			lb[i].setBackground(Color.black);
			lb[i].setForeground(Color.white);
		}
//		this.setBorder(linear);

		// color button
		Border line = BorderFactory.createLineBorder(Color.black, 1, true);
		for (int i = 0; i < colorButton.length; i++) {
			colorButton[i] = new JButton();
			colorButton[i].setBorder(line);
			colorButton[i].setSize(20, 20);
			this.add(colorButton[i]);
		}
		pointColorButton.setBorder(line);
		pointColorButton.setSize(20, 20);
		this.add(pointColorButton);

//		combo
//		for(int i=0; i<combo.length; i++){
//			combo[i] = new JComboBox<String>(colorString);
//			combo[i].setFont(font);
//			combo[i].setSize(80, 20);
//			ComboboxColorRenderer renderer = new ComboboxColorRenderer(combo[i], colorString);
//			combo[i].setRenderer(renderer);
//			this.add(combo[i]);
//		}

		x = 110;
		y = 50;
		pointColorButton.setLocation(x, y);
		colorButton[1].setLocation(x, y += 70);
		colorButton[2].setLocation(x + 200, y);
		colorButton[3].setLocation(x, y += 30);
		colorButton[4].setLocation(x + 200, y);
		colorButton[5].setLocation(x, y += 30);
		colorButton[6].setLocation(x + 200, y);
		colorButton[7].setLocation(x, y += 30);
		colorButton[8].setLocation(x + 200, y);
		colorButton[9].setLocation(x, y += 30);
		colorButton[0].setLocation(x + 200, y);

		// spinner
		this.add(spinner);
		spinner.setBounds(310, 50, 75, 25);
		spinner.setFont(font);
		Border compound = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
				BorderFactory.createEmptyBorder(3, 3, 3, 3));
		spinner.setBorder(compound);
		SpinnerModel spinModel = new SpinnerNumberModel(5, 1, 10, 1);
		spinner.setModel(spinModel);

		// font combobox
		fontCombo.setBounds(110, 270, 300, 25);
		ComboboxFontRenderer fRenderer = new ComboboxFontRenderer(fontCombo, fontString);
		fontCombo.setRenderer(fRenderer);
		this.add(fontCombo);

		// font sizer
		this.add(fontSizer);
		fontSizer.setBounds(110, 300, 75, 25);
		fontSizer.setFont(font);
		fontSizer.setBorder(compound);
		spinner.setModel(new SpinnerNumberModel(1, null, null, 1) {
			private static final long serialVersionUID = -6049167388732468655L;

			@Override
			public Object getNextValue() {
				Object nextValue = super.getValue();
				int x = Integer.valueOf(nextValue.toString()) + 1;
				return x;
			}
		});

		// saveFolder textfield
		saveFolderField.setBounds(110, 370, 250, 25);
		saveFolderField.setBorder(compound);
		saveFolderField.setFont(font);
		this.add(saveFolderField);

		saveFolderBtn.setBounds(370, 370, 30, 25);
		saveFolderBtn.setBackground(Color.lightGray);
		saveFolderBtn.setBorder(compound);
		this.add(saveFolderBtn);

	}

	private JFileChooser fileChooser = new JFileChooser(DrawingOption.getInstance().getSaveFolder());

	private void event() {
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveFolderBtn.addActionListener(e -> {
			int select = fileChooser.showOpenDialog(DefaultSettingPanel.this);
			if (select != 0)
				return;
			File file = fileChooser.getSelectedFile();
			saveFolderField.setText(file.toString());
			DrawingOption.getInstance().setSaveFolder(file);
		});

		for (int i = 0; i < colorButton.length; i++) {
			colorButton[i].addActionListener(e -> {
				JButton button = (JButton) e.getSource();
				Color color = JColorChooser.showDialog(DefaultSettingPanel.this, "색상 선택", button.getBackground());
				if (color == null)
					return;
				for (int j = 0; j < colorButton.length; j++) {
					if (button == colorButton[j]) {
						DrawingOption.getInstance().setPointColor(j, color);
						button.setBackground(color);
						return;
					}
				}
			});
		}

		pointColorButton.addActionListener(e -> {
			Color color = JColorChooser.showDialog(DefaultSettingPanel.this, "색상 선택", pointColorButton.getBackground());
			if (color == null)
				return;
			DrawingOption.getInstance().setPointColor(color);
			pointColorButton.setBackground(color);
		});

		fontCombo.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				String fontName = (String) e.getItem();
				DrawingOption.getInstance().setFontfamily(fontName);
			}
		});

		spinner.addChangeListener(e -> {
			DrawingOption.getInstance().setPointThickness(Integer.parseInt(String.valueOf(spinner.getValue())));
		});

		fontSizer.addChangeListener(e -> {
			DrawingOption.getInstance().setFontSize(Integer.parseInt(String.valueOf(fontSizer.getValue())));
		});

	}

//	class ComboboxColorRenderer extends JPanel implements ListCellRenderer{
//		private static final long serialVersionUID = 4245806196033457534L;
//		private String[] colorString;
//		private ArrayList<Color> colorlist = new ArrayList<>();
//		public String[] getColorString() {
//			return colorString;
//		}
//		public void setColorString(String[] colorString) {
//			this.colorString = colorString;
//			try{
//				Class<?> cs = Class.forName("java.awt.Color");
//				for(String s : colorString){
//					Field f = cs.getField(s);
//					Color c = (Color)f.get(null);
//					colorlist.add(c);
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		
//		private JPanel textPanel;
//		private JLabel label;
//		public ComboboxColorRenderer(JComboBox<?> combo, String[] colorString){
//			this.setColorString(colorString);
//			this.setLayout(new BorderLayout());
//			textPanel = new JPanel(new BorderLayout());
//			label = new JLabel();
//			textPanel.add(label);
//			label.setOpaque(true);
//			label.setFont(combo.getFont());
//		}
//		
//		@Override
//		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
//				boolean cellHasFocus) {
//			if(isSelected){
//				setBackground(list.getSelectionBackground());
//			}else{
//				setBackground(Color.white);
//			}
//			label.setBackground(getBackground());
//			label.setText(value.toString());
//			if(index >= 0)
//				label.setForeground(colorlist.get(index));
//			return label;
//		}
//		
//	}

	class ComboboxFontRenderer extends JPanel implements ListCellRenderer {
		private static final long serialVersionUID = 4245806196033457534L;
		private String[] fontString;
		private ArrayList<Font> fontList = new ArrayList<>();

		private void setFontString(String[] fontString) {
			this.fontString = fontString;
			for (String f : fontString) {
				// System.out.println(f);
				fontList.add(new Font(f, Font.PLAIN, 20));
			}
		}

		private JPanel textPanel;
		private JLabel label;

		public ComboboxFontRenderer(JComboBox<?> combo, String[] fontString) {
			this.setFontString(fontString);
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
			if (isSelected) {
				setBackground(list.getSelectionBackground());
			} else {
				setBackground(Color.white);
			}
			label.setBackground(getBackground());
			label.setText(value.toString());
			if (index >= 0)
				label.setFont(fontList.get(index));
			return label;
		}

	}

}

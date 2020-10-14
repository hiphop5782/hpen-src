package com.hpen.util.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import com.hpen.draw.shapes.Icon;
import com.hpen.draw.ui.component.WrapLayout;

public class IconManager extends JDialog{
	public static final int icon_size = 30;
	
	private JScrollPane scroll = new JScrollPane();
	private JPanel panel = new JPanel(new WrapLayout(WrapLayout.LEFT));
	private ArrayList<Icon> list = new ArrayList<>();
	
	private IconManager(){
		this(new JFrame(), 0, 0);
	}
	private IconManager(Point p){
		this(new JFrame(), p);
	}
	private IconManager(int x, int y){
		this(new JFrame(), x, y);
	}
	private IconManager(JFrame window){
		this(window, 0, 0);
	}
	private IconManager(JFrame window, Point p){
		this(window, p.x, p.y);
	}
	private IconManager(JFrame window, int x, int y){
		super(window, "", true);
		setBackground(Color.white);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setSize(200, 200);
		setLocation(x, y);
		loadIcon();
		display();
	}
	
	private Border lineBorder = BorderFactory.createLineBorder(Color.black, 2, true);
	private Icon selectedIcon;
	public Icon getSelectedIcon() {
		return selectedIcon;
	}
	private MouseListener m = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() instanceof JLabel){
				JLabel label = (JLabel)e.getSource();
				if(label.getIcon() instanceof ImageIcon){
					selectedIcon = new Icon((ImageIcon)label.getIcon());
					dispose();
				}
			}
		}
		public void mouseEntered(MouseEvent e) {
			if(e.getSource() instanceof JLabel){
				JLabel label = (JLabel)e.getSource();
				label.setBorder(lineBorder);
			}
		}
		public void mouseExited(MouseEvent e) {
			if(e.getSource() instanceof JLabel){
				JLabel label = (JLabel)e.getSource();
				label.setBorder(null);
			}
		}
	};
	private void loadIcon(){
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				dispose();
			}
		});
		WindowAdapter w = new WindowAdapter() {
			public void windowLostFocus(WindowEvent e) {
				dispose();
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				dispose();
			}
			@Override
			public void windowStateChanged(WindowEvent e) {
				dispose();
			}
		};
		this.addWindowFocusListener(w);
		this.addWindowListener(w);
		
		readEmoticonFileList();
	}
	
	private void readEmoticonFileList(){
//		loadIconFromFile("rpg.jpg", 30, 14);
//		loadIconFromFile("pocketmon.png", 32, 24);
//		loadIconFromFile("silk-sprite.png", 32, 32);
		class Information{
			String name;
			int row, col;
			@Override
			public String toString() {
				return "Information [name=" + name + ", row=" + row + ", col=" + col + "]";
			}
		}
		ArrayList<Information> list = new ArrayList<>();
		try(Scanner s = new Scanner(new File("image/emoticon/emoticonList"));){
			while(s.hasNextLine()){
				String line = s.nextLine();
				String[] part = line.split(",");
				Information info = new Information();
				info.name = part[0].trim();
				info.row = Integer.parseInt(part[1].trim());
				info.col = Integer.parseInt(part[2].trim());
				list.add(info);
			}
		}catch(IOException e){
		}
		
		for(Information info : list){
			loadIconFromFile(info.name, info.row, info.col);
		}
	}
	private void display(){
		getContentPane().add(scroll);
		scroll.setViewportView(panel);
		scroll.getVerticalScrollBar().setUnitIncrement(25);
		
		if(list.size() == 0) return;
		
		JLabel[] label = new JLabel[list.size()];
		for(int i=0; i<label.length; i++){
			label[i] = new JLabel(list.get(i).getIcon());
			label[i].setPreferredSize(new Dimension(icon_size, icon_size));
			label[i].addMouseListener(m);
			panel.add(label[i]);
		}
	}
	
	private void loadIconFromFile(String url){
		loadIconFromFile(url, 1, 1);
	}
	private void loadIconFromFile(String name, int row, int column){
		ArrayList<Icon> iconList = new ArrayList<>();
		try{
			File target = new File("image/emoticon/"+name);
			BufferedImage origin = ImageIO.read(target);
			BufferedImage[][] array = ImageManager.loadSpriteImage(origin, row, column);
			
			for(int i=0; i < array.length; i++){
				for(int j=0; j< array[i].length; j++){
					iconList.add(new Icon(array[i][j], icon_size, icon_size));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		list.addAll(iconList);
	}
	
	public static Icon showIconDialog(JFrame window){
		IconManager manager = new IconManager(window, MouseInfo.getPointerInfo().getLocation());
		manager.setVisible(true);
		Icon icon = manager.getSelectedIcon();
		return icon;
	}
	public static Icon showIconDialog(){
		return showIconDialog(new JFrame());
	}
}







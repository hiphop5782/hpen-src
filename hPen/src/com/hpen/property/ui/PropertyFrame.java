package com.hpen.property.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.hpen.property.DrawingOption;
import com.hpen.property.ProgramIcon;
import com.hpen.property.ZoomOption;
import com.hpen.value.Version;
import com.hpen.property.CaptureOption;

public class PropertyFrame extends JFrame{
	private static PropertyFrame frame = new PropertyFrame();
	public static PropertyFrame getInstance(){
		return frame;
	}
	
	public static void start(){
		PropertyFrame.getInstance().setVisible(true);
	}
	
	private void initialize(){
		pair.put("필기 설정", defaultSettingPanel);
		pair.put("캡쳐 설정", captureSettingPanel);
		pair.put("돋보기 설정", zoomSettingPanel);
	}
	
	private PropertyFrame(){
		initialize();
		display();
		event();
		menu();
		super.setTitle(Version.getInstance().toString()+" 환경 설정");
		super.setSize(550, 500);
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (di.width - getWidth())/2;
		int y = (di.height - getHeight())/2;
		super.setLocation(x, y);
		super.setIconImage(ProgramIcon.getIcon());
		super.setResizable(false);
	}
	
	private JPanel sPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
	private JButton closeBtn = new JButton("닫기");

	private JPanel wPanel = new JPanel(new BorderLayout());
	
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("설정");
	private DefaultMutableTreeNode root_drawSetting = new DefaultMutableTreeNode("필기 설정");
	private DefaultMutableTreeNode root_captureSetting = new DefaultMutableTreeNode("캡쳐 설정");
	private DefaultMutableTreeNode root_zoomSetting = new DefaultMutableTreeNode("돋보기 설정");
	private JTree tree = new JTree(root);
	
	private DefaultSettingPanel defaultSettingPanel = new DefaultSettingPanel();
	private CaptureSettingPanel captureSettingPanel = 	new CaptureSettingPanel();
	private ZoomSettingPanel zoomSettingPanel = new ZoomSettingPanel();
	
	private Map<String, JPanel> pair = new HashMap<>();
	private JPanel cPanel = new JPanel(new BorderLayout());
	
	private void display(){
		setLnf();
		
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add(sPanel, BorderLayout.SOUTH);
		sPanel.add(closeBtn);
		
		con.add(wPanel, BorderLayout.WEST);
		wPanel.add(tree);
		wPanel.setPreferredSize(new Dimension(120, 450));
		
		root.add(root_drawSetting);
		root.add(root_captureSetting);
		root.add(root_zoomSetting);
		
		con.add(cPanel, BorderLayout.CENTER);
		cPanel.add(defaultSettingPanel);
		
		sPanel.setBackground(Color.white);
		wPanel.setBackground(Color.white);
	
		tree.expandRow(0);
	}
	
	private void event(){
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();//save property
			}
		});
		closeBtn.addActionListener(e->{close();});
		tree.addTreeSelectionListener(e->{
			//System.out.println(e.getPath().getLastPathComponent());
			String s = e.getPath().getLastPathComponent().toString();
			if(s == null) return;
			changePanel(s);
		});
	}
	
	private void changePanel(String s){
		if(pair.containsKey(s)){
			cPanel.removeAll();
			cPanel.add(pair.get(s));
			cPanel.revalidate();
			cPanel.repaint();
			setLnf();
		}
	}
	
	private void setLnf(){
		try {
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void menu(){}
	
	private void close(){
		this.dispose();
//		PropertyLoader.save();
//		DrawingOption.saveProperty();
//		CaptureOption.saveProperty();
//		ZoomOption.saveProperty();
	}
	
//	public static void main(String[] args){
//		PropertyFrame.start();
//	}
}

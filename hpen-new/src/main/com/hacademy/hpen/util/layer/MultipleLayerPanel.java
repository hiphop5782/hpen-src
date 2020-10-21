package com.hacademy.hpen.util.layer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JPanel;

import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Component
public class MultipleLayerPanel extends JPanel{
	
	@Data @Builder @NoArgsConstructor @AllArgsConstructor
	@EqualsAndHashCode(exclude = {"order", "drawable"})
	static class State{
		int order;
		String name;
		boolean drawable;
	}
	

	private static final long serialVersionUID = 1L;

	public static final String BACKGROUND_LAYER = "background";
	public static final String MOUSE_LAYER = "mouse";
	
	private Comparator<State> c = (s1, s2)->s2.order-s1.order;
	private Map<State, Image> layers = new TreeMap<>(c);
	
	public MultipleLayerPanel() {}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	private Image background;
	
	@Override
	public void paint(Graphics g) {
		if(background == null) {
			background = createImage(getWidth(), getHeight());
		}
		else {
			background.getGraphics().clearRect(0, 0, getWidth(), getHeight());
		}
		
		for(State state : layers.keySet()) {
			if(state.drawable) {
				Image im = layers.get(state);
				background.getGraphics().drawImage(im, 0, 0, getWidth(), getHeight(), this);
			}
		}
		
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}
	
	public boolean addLayer(String name) {
		Image image = createImage(getWidth(), getHeight());
		return addLayer(name, image);
	}
	
	public boolean addLayer(String name, Image image) {
		boolean contains = layers.containsKey(State.builder().name(name).build());
		layers.put(State.builder().name(name).drawable(true).build(), image);
		repaint();
		return contains;
	}
	
	public Image getLayerImage(String name) {
		return layers.get(State.builder().name(name).build());
	}
	
	public void setLayer(String name, BufferedImage image) {
		clearLayer();
		addLayer(name, image);
	}
	
	public void clearLayer() {
		layers.clear();
		repaint();
	}

	public boolean hasLayer(String name) {
		return layers.containsKey(State.builder().name(name).build());
	}
	
	//마우스 전용 기능
	public void refreshMouseLayer(int xpos, int ypos) {
		if(hasLayer(MOUSE_LAYER)) {
			addLayer(MOUSE_LAYER);
		}
		
		Image img = getLayerImage(MOUSE_LAYER);
		Graphics2D g2d = (Graphics2D)img.getGraphics();
//		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
//		g2d.fillRect(0, 0, getWidth(), getHeight());
//		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		if(g2d.getColor() != Color.black) 
			g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(0.1f));
		g2d.drawLine(xpos, 0, xpos, getHeight());
		g2d.drawLine(0, ypos, getHeight(), ypos);
		repaint();
	}

	public int getLayerCount() {
		return layers.size();
	}
	
}

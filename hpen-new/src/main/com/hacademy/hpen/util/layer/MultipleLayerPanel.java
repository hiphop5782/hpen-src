package com.hacademy.hpen.util.layer;

import java.awt.Graphics;
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
	
	private Comparator<State> c = (s1, s2)->s2.order-s1.order;
	private Map<State, BufferedImage> layers = new TreeMap<>(c);
	
	public MultipleLayerPanel() {}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	private BufferedImage background;
	
	@Override
	public void paint(Graphics g) {
		if(background == null) {
			background = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		}
		else {
			background.getGraphics().clearRect(0, 0, getWidth(), getHeight());
		}
		
		for(State state : layers.keySet()) {
			if(state.drawable) {
				BufferedImage im = layers.get(state);
				background.getGraphics().drawImage(im, 0, 0, getWidth(), getHeight(), this);
			}
		}
		
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}
	
	public boolean addLayer(String name) {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		return addLayer(name, image);
	}
	
	public boolean addLayer(String name, BufferedImage image) {
		boolean contains = layers.containsKey(State.builder().name(name).build());
		layers.put(State.builder().name(name).drawable(true).build(), image);
		return contains;
	}
	
	public BufferedImage getLayerImage(String name) {
		return layers.get(State.builder().name(name).build());
	}
	
}

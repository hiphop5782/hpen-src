package com.hacademy.hpen.ui.note.element;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *	도형의 기본이 되는 컴포넌트
 *	- 모든 도형은 한개의 이미지 형태로 관리
 *	- 따라서 모든 도형은 반드시 사각형 형태의 영역을 가져야함
 *	- 영역 내에서 어떻게 그리는지에 대해서는 도형마다 자율적으로 설정 
 */
public abstract class Node {
	protected int x, y, width, height;
	private Image image;
	public Image getGraphic() {
		if(image == null) {
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			draw((Graphics2D)image.getGraphics());
		}
		return image;
	}
	public abstract void draw(Graphics2D g);
}






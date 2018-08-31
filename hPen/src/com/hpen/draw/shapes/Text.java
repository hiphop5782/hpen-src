package com.hpen.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.hpen.property.DrawingOption;
import com.hpen.util.English2KoreanParser;
import com.hpen.util.KoreanCharacter;

public class Text extends Rect{
	
	private static final int fix = 10;//보정치
	
	private StringBuffer textBuffer = new StringBuffer();
	private Font font;
	private boolean isEdit = false;
	
	public String getText() {
		return textBuffer.toString();
	}

	public void setText(String text) {
		textBuffer = new StringBuffer(text);
	}
	
	public void append(String str){
		if(DrawingOption.getInstance().isKorean()) {
//			str = KoreanCharacter.parseKorean(str);
			str = English2KoreanParser.getParser().parse(str);
		}
		textBuffer.append(str);
		
		try{
			renew();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void renew() throws Exception{
//		[기존방식] 전체를 재조사하여 한글로 변환
		int index = Math.max(textBuffer.lastIndexOf("\n"), textBuffer.lastIndexOf(" "));
		StringBuffer nonFix = new StringBuffer();
		String fix;
		if(index > -1){
			nonFix.append(textBuffer.substring(0, index+1));
			fix = textBuffer.substring(index+1);
		}else{
			fix = textBuffer.toString();
		}
		
//		String tmp= KoreanCharacter.seperateString(fix);
//		String fixText = KoreanCharacter.mergeString(tmp);
		String tmp = English2KoreanParser.getParser().seperate(fix);
		String merge = English2KoreanParser.getParser().parse(tmp);
		textBuffer = new StringBuffer(nonFix.append(merge));
	}
	
	public void undo(){
		if(textBuffer.length() > 0)
			textBuffer.deleteCharAt(textBuffer.length()-1);
	}
	
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Text(){
		this(new Point(-1, -1), new Point(-1, -1), DrawingOption.getInstance().getPointThickness(), DrawingOption.getInstance().getPointColor(), "", DrawingOption.getInstance().getFont());
	}
	public Text(Point s, Point e, int thick, Color color, String text, Font font) {
		this(s.x , s.y, e.x, e.y, thick, color, text, font);
	}
	
	public Text(int sx, int sy, int ex, int ey, int thick, Color color, String text, Font font) {
		super(sx - fix, sy - fix, ex - fix, ey - fix, thick, color);
		this.setText(text);
	    this.setFont(font);
		isEdit = true;
	}

	public void finish(){
		isEdit = false;
	}
	
	@Override
	public boolean isSamePosition(Point p) {
		return p.x >= sx && p.x <= ex && p.y >= sy && p.y <= ey;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setFont(font);
		//\n에 의한 개행 처리
		String text = getText();
		if(!isEdit && text.trim().length() == 0) return;
		int x = sx;
		int y = sy;
		
		int a = 0;
		int len = text.split("\n").length;
		boolean enterFlag = text.endsWith("\n");
		for(String line : text.split("\n")){
			a++;
			if(isEdit && a == len && !enterFlag){
				g2d.drawString(line+"|", x, y += g2d.getFontMetrics().getHeight());
			}else if(isEdit && a == len && enterFlag){
				g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
				g2d.drawString("|", x, y += g2d.getFontMetrics().getHeight());
			}else if(!isEdit && a == len && !enterFlag){
				g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
			}else if(!isEdit && a == len && enterFlag){
				g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
			}else{
				g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
			}
		}
	}

}

package com.hpen.test;
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;

class Magnifier extends JFrame{
	  
	  /* * Main vessel   */
	  private Container container = getContentPane ();  
	  
	  /** * Size Magnifier */ 
	  private int magnifierSize = 100;

	  /** * magnifying glass panels content */ 
	  public MagnifierPanel magnifierPanel = new MagnifierPanel(magnifierSize);
	  
	  /** * Mouse pressed the relative coordinates x */ 
	  private int relativeCoordinateXWhenMousePressed;  

	  /** * Mouse pressed the relative y coordinates */ 
	  private int relativeCoordinateYWhenMousePressed;  

	  /** * Labeled mouse is pressed.    If the press is true, it is false */ 
	  private boolean mousePressedNow;

	  /** * X coordinate of a magnifying glass * Calculation: setCoordinateX = absoluteCoordinateX  
	   * - RelativeCoordinateXWhenMousePressed */ 
	  private int setCoordinateX;

	  /** * Y coordinates magnifying glass * Calculation: setCoordinateY = absoluteCoordinateY  
	   * - RelativeCoordinateYWhenMousePressed */ 
	  private int setCoordinateY;  

	  /** * Mouse absolute coordinates x */ 
	  private int absoluteCoordinateX;

	  /** * Y coordinates of the mouse is */ 
	  private int absoluteCoordinateY;

	  /** * Constructor function, the creation of a magnifying glass window */ 
	  public Magnifier() {
		  
		  setUndecorated (true); // edge of the form  
		  setResizable (false);   
		  container.add (magnifierPanel);  
		  addMouseListener(new Mousefunction());  
		  addMouseMotionListener(new MouseMotionFunctions());  
		  updateSize (magnifierSize);  
		  this.setVisible (true);  
	  }
	  
	  /** * Updated form  
	   * 
	   * @ Param magnifierSize  
	   * Size Magnifier  
	   */ 
	  public void updateSize (int magnifierSize){  
		  magnifierPanel.setMagnifierSize (magnifierSize + 100);  
		  setSize(magnifierSize + 100 + 100, magnifierSize);  
		  validate(); // update all of Control  
	  }
	  
	  class Mousefunction extends MouseAdapter{

		  public void mousePressed (MouseEvent e){  
			  if (e.getClickCount () == 1){  
				  // If a point to the left mouse button, hold down the note of the form  
				  mousePressedNow = true; 
				  relativeCoordinateXWhenMousePressed = e.getX ();  
				  relativeCoordinateYWhenMousePressed = e.getY ();  
			  }  
		  } 
		  
		  public void mouseReleased (MouseEvent e){  
			  mousePressedNow = false; 
		  }
	}
	  
	  public static class MagnifierPanel extends JPanel {  
			 private Image screenImage;  
			 /** * The size of the magnifying glass */ 
			 private int magnifierSize;  
			 private int locationX;  
			 private int locationY;  
			 public Robot robot;  
			 /** * With the constructor function parameters  
			  * @ Param magnifierSize  
			  * Larger Size  
			  */ 
			 public MagnifierPanel(int magnifierSize) {
				 try{
					 robot = new Robot();
					 screenImage = robot.createScreenCapture
						 (new Rectangle (0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 
								 Toolkit.getDefaultToolkit().getScreenSize().height));
				 }catch(Exception e){
					 e.printStackTrace();
				 }
				   
				 this.magnifierSize = magnifierSize;  
			 }  
			 /** * Set up the location of the magnifying glass  
			  * @ Param locationX  
			  * X coordinates  
			  * @ Param locationY  
			  * Y coordinates  
			  */ 
			 public void setMagnifierLocation (int locationX, int locationY){  
				 this.locationX = locationX;  
				 this.locationY = locationY;  
				 repaint(); // draw attention to weight control  
			 }

			 /** * Set up the size of the magnifying glass  
			  * @ Param magnifierSize  
			  * Size Magnifier  
			  */ 
			 
			 public void setMagnifierSize (int magnifierSize){  
				 this.magnifierSize = magnifierSize;  
			 }  
		 
			 public void paintComponent (Graphics g){  
				 super.paintComponent((Graphics2D) g);  
				 // Key handling code  
				 g.drawImage (  
						 screenImage, // to the picture painted  
						 0, // the first goal rectangular angle x coordinates  
						 0, // the first goal rectangular angle y coordinates  
						 magnifierSize, // the second goal rectangular angle x coordinates  
						 magnifierSize, // the second goal rectangular angle y coordinates  
						 locationX + (magnifierSize/4), // source of a rectangular angle x coordinates  
						 locationY + (magnifierSize / 4), // source of a rectangular angle y coordinates  
						 locationX + (magnifierSize / 4 * 3), // the second source rectangular angle x coordinates  
						 locationY + (magnifierSize / 4 * 3), // source rectangular angle of the second y coordinate  
						 this);  
			 }  
		}
	  
	  class MouseMotionFunctions extends MouseMotionAdapter{
			public void mouseDragged (MouseEvent e) {  
				if (mousePressedNow == true){
					// If this mouse click, and drag in the form  
				absoluteCoordinateX = Magnifier.this.getLocationOnScreen().x + e.getX ();  
				absoluteCoordinateY = Magnifier.this.getLocationOnScreen().y + e.getY ();  
				setCoordinateX = absoluteCoordinateX - relativeCoordinateXWhenMousePressed;  
				setCoordinateY = absoluteCoordinateY - relativeCoordinateYWhenMousePressed;  
				magnifierPanel.setMagnifierLocation (setCoordinateX, setCoordinateY);  
				setLocation(setCoordinateX, setCoordinateY);  
				}  
			}
	  }
}

public class Exam07_¡‹¿Œ¡‹æ∆øÙ4 {
	/** * Procedure entry point  
	   * 
	   * @ Param arg  
	   * Boot parameters, here is empty  
	   */ 
	  
	  public static void main (String arg []){  
		  Magnifier magnifier = new Magnifier ();  
		  magnifier.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);  
	  }
}

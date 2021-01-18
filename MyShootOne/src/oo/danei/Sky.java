package oo.danei;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject{
	private int y1;
	private int speed;

	public Sky(){
		super(1100,855,0,0);	
		y1 = -World.HEIGHT;
		speed = 1;
	}
	
	public void step(){
		y += speed;
		y1 += speed;
		if(y>=World.HEIGHT){
			y = -World.HEIGHT;
		}
		if(y1>=World.HEIGHT){
			y1 = -World.HEIGHT;
		}
	}
	
	public BufferedImage getImages(){
		return Images.sky;
	}
	
	public int getY1(){
		return y1;
	}
	
	public  boolean outOfBound(){
		return false;
	}
}

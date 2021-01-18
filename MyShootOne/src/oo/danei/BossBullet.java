package oo.danei;

import java.awt.image.BufferedImage;
import java.util.Random;

public class BossBullet extends FlyingObject{
	
	private int speed;
	
	public BossBullet(int x,int y){
		super(50,80,x,y);
		speed = 10;
		
	}
	
	public void step(){
		y += speed;		
	}
	
	private int index = 1;
	public BufferedImage getImages(){
		if(isLife()){
			return Images.bossbullets[0];
		}else if(isDead()){
			BufferedImage img;
			img = Images.bossbullets[index++];
			if(index ==Images.bossbullets.length){
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	public  boolean outOfBound(){
		return y>=World.HEIGHT;
	}	
}
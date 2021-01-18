package oo.danei;

import java.awt.image.BufferedImage;

public class BigAirplane extends FlyingObject implements Score {
	
	private int speed;
	
	public BigAirplane(){
		super(120,101);
		speed = 2;
	}
	
	public void step(){
		y += speed;
	}
	
	public int getScore(){
		return SCORE2;
	}
	
	private int index = 1;
	public BufferedImage getImages(){
		if(isLife()){
			return Images.bairs[0];
		}else if(isDead()){
			BufferedImage img;
			img = Images.bairs[index++];
			if(index ==Images.bairs.length){
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	public  boolean outOfBound(){
		return y>=World.HEIGHT;
	}
	
	public AirBullet airBulletShoot(){
		return new AirBullet(this.x+this.width/2,this.y+this.height); 
	}
}

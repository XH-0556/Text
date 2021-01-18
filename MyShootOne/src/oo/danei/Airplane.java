package oo.danei;
import java.awt.image.BufferedImage;

public class Airplane extends FlyingObject implements Score{
	
	private int speed;
	
	public Airplane(){
		super(48,50);
		speed = 2;
	}
	
	public void step(){
		y += speed;
	}
	
	public int getScore(){
		return SCORE1;
	}
	private int index = 1;
	public BufferedImage getImages(){
		if(isLife()){
			return Images.airs[0];
		}else if(isDead()){
			BufferedImage img;
			img = Images.airs[index++];
			if(index ==Images.airs.length){
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

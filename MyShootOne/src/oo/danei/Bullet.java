package oo.danei;
import java.awt.image.BufferedImage;
import java.util.Random;





public class Bullet extends FlyingObject implements HeroBullet {
	
	private int speed;
	private int xSpeed;
	private int ySpeed;
	
	public Bullet(int x,int y,int xSpeed){
		super(45,45,x,y);
		speed = 10;
		this.xSpeed =xSpeed;
		
	
		
		
	}

	
	public void step(){
		y -= speed;
		x -=xSpeed;
	}
	

	private int index = 0;
	public BufferedImage getImages(){
		if(isLife()){
			return Images.bullets[index++%Images.bullets.length];
		}else if(isDead()){
			state = REMOVE;
		}
		return null;
		
	}
	
	public  boolean outOfBound(){
		return y<=-this.height;
	}
	
	
}

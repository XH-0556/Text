package oo.danei;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingObject implements Award{

	private int xSpeed;
	private int ySpeed;
	private int awardType;
	
	public Bee(){
		super(66,89);
		ySpeed = 2;
		xSpeed = 1;
		Random rand = new Random();
		awardType = rand.nextInt(2);
	}
	
	public void step(){
		y += ySpeed;
		x += xSpeed;
		if(x>=World.WIDTH-this.width || x<=0 ){
			xSpeed *= -1;
		}
	}
	
	public int getAward(){
		return awardType;
	}
	
	private int index = 1;
	public BufferedImage getImages(){
		if(isLife()){
			return Images.bees[0];
		}else if(isDead()){
			BufferedImage img;
			img = Images.bees[index++];
			if(index ==Images.bees.length){
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

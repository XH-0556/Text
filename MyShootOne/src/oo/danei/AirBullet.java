package oo.danei;
import java.util.Random;
import java.awt.image.BufferedImage;

public class AirBullet extends FlyingObject{
	private int xSpeed;
	private int ySpeed;
	
	public AirBullet(int x,int y){
		super(12,12,x,y);
		ySpeed = 3;
		
		Random rand =new Random();
		int type = rand.nextInt(2);
		switch(type){
		case 0:
			xSpeed = 3;
			break;
		case 1:
			xSpeed = -3;
		}
	}
	
	public void step(){
		x += xSpeed;
		y += ySpeed;
		
		if(x<=0 || x>World.WIDTH-this.width){
			xSpeed *=-1;
		}
		
	}
	
	private int index = 1;
	public BufferedImage getImages(){
		if(isLife()){
			return Images.airbullets[index++%Images.airbullets.length];
		}else if(isDead()){
			state = REMOVE;
			}
			
		
		return null;
	}
	
	public  boolean outOfBound(){
		return y>=World.HEIGHT;
	}	
	
	
}

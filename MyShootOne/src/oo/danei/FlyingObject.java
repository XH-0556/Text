package oo.danei;
import java.util.Random;
import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	public static final int LIFE = 0;			
	public static final int DEAD = 1;		
	public static final int REMOVE = 2;	
	protected int state = LIFE;					
	
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	
	
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random();
		x = rand.nextInt(World.WIDTH-width);
		y = -height;		
	}
	
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	
	public abstract void step();
	
	public abstract BufferedImage getImages();
	
	public boolean isLife(){
		return state == LIFE;
	}
	
	public boolean isDead(){
		return state == DEAD;
	}
	
	public boolean isRemove(){
		return state == REMOVE;
	}
	
	public abstract boolean outOfBound();
	
	public boolean hit(FlyingObject other){
		int x1 = this.x - other.width;
		int x2 = this.x + this.width;
		int y1 = this.y -other.height;
		int y2 = this.y + this.height;
		return other.x >= x1 && other.x <= x2 && other.y >=y1 && other.y <= y2 ;
	}
	
	public void goDead(){
		state = DEAD;
	}
}

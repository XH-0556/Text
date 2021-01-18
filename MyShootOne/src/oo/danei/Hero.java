package oo.danei;
import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
	
	private int life;
	int fire;
	private int bigFire;
	
	public Hero(){
		super(120,120,480,620);
		life = 3;
		fire = 100;
		bigFire =0;
	}
	
	public void step(){
		
	}
	
	private int index = 0;
	public BufferedImage getImages(){
		return Images.hero;
	}
	
	public HeroBullet[] shoot(){
		if(World.bulletState == World.BIGBULLET && bigFire>0){
			BigBullet[] bb = new BigBullet[1];
			bb[0] = new BigBullet(this.x+this.width/2,this.y-600);
			return bb;
		}else{
			int xStep =  this.width/4;
			int yStep = 1;
			if(fire>10) {
				Bullet[] bs = new Bullet[5];
				bs[0] = new Bullet(this.x+1*xStep-10,this.y-yStep,+1);
				bs[1] = new Bullet(this.x+2*xStep,this.y-yStep,0);
				bs[2] = new Bullet(this.x+3*xStep+10,this.y-yStep,-1);
				bs[3] = new Bullet(this.x+4*xStep+20,this.y-yStep,-2);
				bs[4] = new Bullet(this.x-20,this.y-yStep,+2);
				
				return bs;
			}else if(fire>0){
				Bullet[] bs = new Bullet[2];
				bs[0] = new Bullet(this.x+1*xStep-18,this.y-yStep,0);
				bs[1] = new Bullet(this.x+3*xStep-18,this.y-yStep,0);
				fire -=2;
				return bs;
			}else{
				Bullet[] bs = new Bullet[1];
				bs[0] = new Bullet(this.x+2*xStep,this.y-yStep,0);
				return bs;
			}		
		}
		
	}
	
	public  boolean outOfBound(){
		return false;
	}
	
	public void moveTo(int x,int y){
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	
	public void addLife(){
		life += 1;
	}
	
	public void addFire(){
		fire +=40;
	}
	
	public void subtractLife(){
		life -= 1;
	}
	
	public void clearFire(){
		fire = 0;
	}
	
	public int getLife(){
		return life;
	}
	
	public void addBigFire(){
		bigFire +=50000;
	}
	
	public int getBigFire(){
		return bigFire;
	}
	
	public void subtractBigFire(){
		bigFire -=100;
	}
}

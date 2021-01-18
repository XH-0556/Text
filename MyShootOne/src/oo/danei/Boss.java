package oo.danei;

import java.awt.image.BufferedImage;

public class Boss extends FlyingObject {
	
	private int xSpeed;
	private int ySpeed;
	private int life;
	
	
	public Boss( ) {
		super(500,389);
		xSpeed +=2;
		ySpeed +=1;
		life = 400;
		
	}

	@Override
	public void step() {
		x +=xSpeed;
		y +=ySpeed;
		if(y==0){
			ySpeed =0;
		}
		if(x>=World.WIDTH-this.width || x<=0 ){
			xSpeed *= -1;
		}
		
	}


	private int index = 1;
	public BufferedImage getImages(){
		if(isLife()){
			return Images.boss[0];
		}else if(isDead()){
			BufferedImage img;
			img = Images.boss[index++];
			if(index ==Images.boss.length){
				state = REMOVE;
			}	
			return img;
		}	
				
		return null;
	}

	@Override
	public boolean outOfBound() {
		
		return false;
	}
	
	public int getLife(){
		return life;
	}
	
	public void subtractLife(){
		life -= 1;
	}
	
	public BossBullet bossBulletShoot(){
		return new BossBullet(this.x+this.width/2-125,this.y+this.height); 
	}

	
}

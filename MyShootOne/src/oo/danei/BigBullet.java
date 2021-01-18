package oo.danei;

import java.awt.image.BufferedImage;

public class BigBullet extends FlyingObject implements HeroBullet  {
	
	public BigBullet(int x,int y){
		super(123,1110,x,y);
	}

	@Override
	public void step() {
		
		
	}

	private int index = 0;
	public BufferedImage getImages() {
		if(isLife()){
			return Images.bigbullets[index++%Images.bigbullets.length];
		}else{
			state = REMOVE;
		}
		return null;
		
	}

	@Override
	public boolean outOfBound() {
		
		return false;
	}
	
}

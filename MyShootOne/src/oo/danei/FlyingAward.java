package oo.danei;

import java.awt.image.BufferedImage;

public class FlyingAward extends FlyingObject implements Award {
	private int speed;

	
	public FlyingAward() {
		super(50, 50);
		speed = 2;
	}

	@Override
	public int getAward() {
		return 2;
	}

	@Override
	public void step() {
		y += speed;
		
	}

	@Override
	public BufferedImage getImages() {
		if(isLife()){
			return Images.flyingaward;
		}else if(isDead()){
			state = REMOVE;
		}
		return null;
	}

	@Override
	public boolean outOfBound() {
		return y >= World.HEIGHT;
	}
	
	
}

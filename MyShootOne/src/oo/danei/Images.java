package oo.danei;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Images {
	
	public static BufferedImage sky;
	public static BufferedImage blood;
	public static BufferedImage allblood;
	public static BufferedImage flyingaward;
	public static BufferedImage hero;
	public static BufferedImage[] bullets;
	public static BufferedImage[] bigbullets;
	public static BufferedImage[] boss;
	public static BufferedImage[] bossbullets;
	public static BufferedImage[] airbullets;
	public static BufferedImage[] airs;
	public static BufferedImage[] bairs;
	public static BufferedImage[] bees;
	
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage end;
	
	
	static{
		sky = loadImage("121.png");
		blood = loadImage("血量.jpg");
		allblood = loadImage("血条.jpg");
		flyingaward = loadImage("掉落.png"); 
		hero = loadImage("190.png");
		
		bullets = new BufferedImage[2];
		bullets[0] = loadImage("timg82.png");
		bullets[1] = loadImage("timg82.png");
		
		
		bigbullets = new BufferedImage[4];
		bigbullets[0] = loadImage("激光1.png");
		bigbullets[1] = loadImage("激光2.png");
		bigbullets[0] = loadImage("激光3.png");
		bigbullets[1] = loadImage("激光4.png");
	
		
		
		airbullets = new BufferedImage[2];
		airbullets[0] = loadImage("bullet0.png");
		airbullets[1] = loadImage("bullet1.png");
		
		
		airs = new BufferedImage[5];
		bairs = new BufferedImage[5];
		bees = new BufferedImage[5];
		
		boss = new BufferedImage[5];
		bossbullets = new BufferedImage[5];
		airs[0] = loadImage("小敌机.png");
		bairs[0] = loadImage("BIG.png");
		bees[0] = loadImage("bee.png");
		
		boss[0] = loadImage("545.png");
		bossbullets[0] = loadImage("bossbullet.png");
		for(int i=1;i<airs.length;i++){
			airs[i] = loadImage("bom"+i+".png");
			bairs[i] = loadImage("bom"+i+".png");
			bees[i] = loadImage("bom"+i+".png");	
			bossbullets[i] = loadImage("bom"+i+".png");
			boss[i] = loadImage("boom"+i+".png");
		}
		
		start = loadImage("start1.png");  
		pause = loadImage("pause1.png");
		end = loadImage("gameover1.png");
		
	}

	
	
	
	public static BufferedImage loadImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}

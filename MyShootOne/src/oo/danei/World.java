package oo.danei;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class World extends JPanel implements KeyListener {
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	public static final int FIRE = 0;
	public static final int LIFE = 1;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int END = 3;
	public static final int BULLET = 1;
	public static final int  BIGBULLET = -1;
	
	public static int  bulletState = BULLET;
	
	private int state = START;
	
	private Sky sky = new Sky();
	private Hero hero = new Hero();
	private Boss boss;
	private BossBullet[] bossBullets ={};
	private AirBullet[] airBullets = {};

	private FlyingObject[] enemies = {};
	private HeroBullet[] bullets = {};
	
	
	public boolean isBigBullet(){
		if(bullets != null){
			for(HeroBullet hb: bullets){
				if(hb instanceof BigBullet  ){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
		return false;
	}
	
	private int enterIndex = 0;
	public void enterAction(){
		enterIndex++;
		if(enterIndex%20 ==0){
			enemies = Arrays.copyOf(enemies, enemies.length+1);
			FlyingObject f = nextOne();
			enemies[enemies.length-1] =f;			
		}		
	}
	
	public FlyingObject nextOne(){
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type<5){
			return new Bee();
		}else if(type<6){
			return new FlyingAward();
		}else if(type<13){
			return new Airplane();
		}else {
			return new BigAirplane();
		}	
	}
		
	
	private int enemieShootIndex = 0;
	public void enemieShootAction(){
		enemieShootIndex ++;
		if(enemieShootIndex%100 ==0){
			for(FlyingObject f: enemies){
				if(f instanceof BigAirplane){
					BigAirplane bair=(BigAirplane)f;	
					airBullets = Arrays.copyOf(airBullets, airBullets.length+1);
					airBullets[airBullets.length-1] = bair.airBulletShoot();
				}
			}
		}
		
		if(enemieShootIndex%100 ==0){
			for(FlyingObject f: enemies){
				if(f instanceof Airplane){
					Airplane air=(Airplane)f;	
					airBullets = Arrays.copyOf(airBullets, airBullets.length+1);
					airBullets[airBullets.length-1] = air.airBulletShoot();
				}
			}
		}		
	}
	
	private int bossShootIndex = 0;
	public void bossShootAction(){
		bossShootIndex ++;
		if(boss != null && boss.isLife() && bossShootIndex%40==0){ 
			bossBullets = Arrays.copyOf(bossBullets, bossBullets.length+1);
			bossBullets[bossBullets.length-1] = boss.bossBulletShoot();			
		}
	}
	
	private int shootIndex = 0;
	public void shootAction(){
		if(isBigBullet()){
			hero.subtractBigFire();
		}
		if(bulletState ==BIGBULLET && (isBigBullet()==false) && hero.getBigFire()>0){
			bullets =new HeroBullet[0];
			HeroBullet[] hb = hero.shoot();  
			bullets =Arrays.copyOf(bullets, bullets.length+1);
			bullets[bullets.length-1] = hb[0];
		}else if(bulletState == BULLET){
			shootIndex ++;
			if(shootIndex%10 ==0){
				if(isBigBullet()){
					for(HeroBullet hb: bullets){
						if(hb instanceof BigBullet  ){
							((BigBullet) hb).goDead();
						}
					}	
				}
				HeroBullet[] bs = hero.shoot();
				bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
				System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
			}	
		}
		
	}
	
	public void stepAction(){
		sky.step();
		if(boss!=null){
			boss.step();
		}
		
		for(int i=0;bullets!=null &&i<bullets.length;i++){
			bullets[i].step();
		}
		for(FlyingObject f : enemies){
			f.step();
		}
		for(AirBullet a : airBullets){
			a.step();
		}
		for(BossBullet bb : bossBullets){
			bb.step();
		}
		if(isBigBullet()){
			for(HeroBullet hb: bullets){
				if(hb instanceof BigBullet){
					((BigBullet) hb).x  = hero.x+hero.width/2-30;
					((BigBullet) hb).y = hero.y-600;
				}
			}
			if(isBigBullet() && hero.getBigFire()<=0){
				bullets = new HeroBullet[0];
			}
		}	
	}
	
	public void outOfBoundAction(){
		int index = 0;
		FlyingObject[] enemieslife = new FlyingObject[enemies.length];
		for(FlyingObject f: enemies){
			if(!f.outOfBound() && !f.isRemove() ){
				enemieslife[index++] = f;
			}
		}
		enemies = Arrays.copyOf(enemieslife, index);
		
		index = 0;
		HeroBullet[] bulletslife = new HeroBullet[bullets.length];
		for(HeroBullet b : bullets){
			if(!((FlyingObject)b).outOfBound() && !((FlyingObject)b).isRemove()){
				bulletslife[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletslife, index);
		
		index = 0;
		AirBullet[] airBulletslife = new AirBullet[airBullets.length];
		for(AirBullet ab : airBullets){
			if(!ab.outOfBound()){
				airBulletslife[index++] = ab;
			}
		}
		airBullets = Arrays.copyOf(airBulletslife, index);
		
		index = 0;
		BossBullet[] bossBulletslife = new BossBullet[bossBullets.length];
		for(BossBullet bb : bossBullets){
			if(!bb.outOfBound()){
				bossBulletslife[index++] = bb;
			}
		}
		bossBullets = Arrays.copyOf(bossBulletslife, index);
	}
	
	private int score = 0;
	private int bossIndex = 0;
	private int bossBulletIndex = 0;
	public void bangAction(){
		for(HeroBullet b : bullets){
			for(FlyingObject f  : enemies){
				if(f.hit(((FlyingObject)b)) && f.isLife() && ((FlyingObject)b).isLife()&& !(f instanceof FlyingAward)){
					f.goDead();
					if(b instanceof Bullet){
						((Bullet)b).goDead();
					}					
					if(f instanceof Score){
						Score s = (Score) f;
						score +=s.getScore();
					}
					if(f instanceof Award  ){
						Award a = (Award) f ;
						switch(a.getAward()){
						case LIFE : 
							hero.addLife();
							break;
						case FIRE:
							hero.addFire();
							break;
						}
					}
				}
			}
			if(boss != null){
				if(((FlyingObject)b).isLife() && boss.isLife() && boss.hit(((FlyingObject)b))){					
					if(b instanceof Bullet){
						((Bullet)b).goDead();
						boss.subtractLife();
					}else{
						if(bossIndex++%30==0){
							boss.subtractLife();
						}
					}
				}
			}
			for(AirBullet ab: airBullets){
				if(b instanceof BigBullet){
					if(((FlyingObject)b).isLife() && ab.isLife() && ab.hit((FlyingObject)b)){
							ab.goDead();	
							score -=1;
					}
				}
			}
			for(BossBullet bb: bossBullets){
				if(b instanceof BigBullet){
					if(((FlyingObject)b).isLife() && bb.isLife() && bb.hit((FlyingObject)b)){
						if(bossBulletIndex++%15==0){
							bb.goDead();
							score -= 2;
						}
				}
				
				}
			}
		}
	}
	
	public void hitAction(){
		for(FlyingObject f: enemies){
			if(hero.isLife() && f.isLife() && f.hit(hero)){
				f.goDead();
				if(f instanceof FlyingAward){
					hero.addBigFire();
				}else {
					hero.subtractLife();
					hero.clearFire();	
				}
				
			}
		}
		for(AirBullet ab: airBullets){
			if(hero.isLife() && ab.isLife() && ab.hit(hero)){
				ab.goDead();
				hero.subtractLife();
				hero.clearFire();
			}
		}
		if(boss != null){
			if(hero.isLife() && boss.isLife() && boss.hit(hero)){
				hero.clearFire();
				hero.subtractLife();
				boss.subtractLife();
			}
		}
		for(BossBullet bb: bossBullets){
			if(hero.isLife() && bb.isLife() && bb.hit(hero)){
				bb.goDead();
				hero.subtractLife();
				hero.subtractLife();
				hero.clearFire();
				hero.clearFire();
			}
		}
	}
	
	public void checkAction(){
		if(hero.getLife()<=0 || (boss!=null&&boss.isRemove())){
			state = END;
		}
	}
	 
	public void bossAction(){
		if(score >=500){
			boss = new Boss();
		}
	}
	
	public void checkBossAction(){
		if(boss !=null){
			if(boss.getLife()<=0){
				boss.goDead();
			}
		}	
	}
	
	//读取本地存储的最高分
		public int readScore()  {
			File file=new File("score2.txt");
			try {
				if(!file.exists()) {
					file.createNewFile();
				}
				FileReader fileReader=new FileReader(file);
				BufferedReader r=new BufferedReader(fileReader);
				//result存储读到的字符串
				String result = "";
				int score=0;
				result = r.readLine();
				if(result!=null && !result.equals("") ) {
					score=new Integer(result.trim());
	
				}
				return score;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}
		/**
		 * 计算最高
		 * @throws IOException
		 */
		public int  maxScore() throws IOException {
			int maxscore=0;
			File file=new File("score2.txt");
			
			try {
				//读到本地存储的最高分
				int histroyscore=readScore();
				//当前英雄得到的最高分
				
				
				//本地和英雄机的得分进行比较
				if(histroyscore<score) {
					maxscore=score;
					BufferedWriter write=new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(file)));
					
					String str=new String().valueOf(score);
					//进了if判断说明英雄得分大于本地存储分数，则写入本地
					write.write(str);
					write.flush();
					write.close();
				}else
					maxscore=histroyscore;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return maxscore;
		}
	public void action(){
		MouseAdapter l = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(state ==RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x,y);
				}
				
			}
			public void mouseClicked(MouseEvent e){
				switch(state){
				case START:
					state = RUNNING;
					break;
				case END:
					score = 0;
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new HeroBullet[0];
					airBullets = new AirBullet[0];
					bossBullets = new BossBullet[0];
					boss = null;
					state = START;
					break;
				}
			}
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state = PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		
	    	Timer timer = new Timer();
	    	int intervel = 10;
	    	timer.schedule(new TimerTask(){
	    		public void run(){ 
	    			if(state ==RUNNING){
	    				enterAction();
		    			shootAction();
		    			enemieShootAction();
		    			bossShootAction();
		    			stepAction();
		    			outOfBoundAction();
		    			bangAction();
					hitAction();
					if(boss==null){
						bossAction();
					}
					if(boss!=null&&boss.isLife()){
						checkBossAction();
					}
				

					
					checkAction();

		    			
	    			}
	    			
	    			
	    			
	    			repaint();                         ///重画（重新画敌人）
	    			
	    			
	    			
	    		}
	    	}, intervel, intervel);

	    
	    
	    
	    }
	    	

	private int bossDeadIndex= 0;
	public void paint(Graphics g){
		 g.drawImage(sky.getImages(),sky.x,sky.y,null);
		 g.drawImage(sky.getImages(),sky.x,sky.getY1(),null);
		 g.drawImage(hero.getImages(),hero.x,hero.y,null);
//		 if(isBigBullet()){
//			 for(HeroBullet hb : bullets){
//				 g.drawImage(((FlyingObject)hb).getImages(),((FlyingObject)hb).x,((FlyingObject)hb).y,null); 	 
//			 }			 
//		 }		
		 if(boss!=null && boss.isLife()){
			 g.drawImage(Images.allblood,12,70,null);
			 for(int i=0; i<boss.getLife()/3; i++){
				 int x = 12 +5*i;
				 g.drawImage(Images.blood,x,70,null); 
			 }	 
		 }
		 
		 if(boss != null && !boss.isRemove()){
			 if(boss.isLife()){
				 g.drawImage(boss.getImages(),boss.x,boss.y,null); 	 
			 }else{
				 if(bossDeadIndex++%50==0){
					 g.drawImage(boss.getImages(),boss.x,boss.y,null);  
				 }
			 }
					
		 }		
		 for(HeroBullet b: bullets){
			 g.drawImage(((FlyingObject)b).getImages(), ((FlyingObject)b).x, ((FlyingObject)b).y, null);
		 }
		 for(int i=0;i<enemies.length;i++){
			 FlyingObject f = enemies[i];
			 g.drawImage(f.getImages(), f.x, f.y,null);
		 }
		 for(AirBullet ab :airBullets ){
			 g.drawImage(ab.getImages(), ab.x, ab.y, null);
		 }
		 for(BossBullet bb : bossBullets){
			 g.drawImage(bb.getImages(), bb.x, bb.y, null);
		 }
		 
		 
		 
		 g.setColor(Color.green);
		 g.drawString("SCORE:"+score, 10, 25);
		 g.drawString("LIFE:"+hero.getLife(), 10, 40);
		 g.drawString("MAXSCORE: "+readScore(),10,65);
			if(state==END){
				try {
					Font font=new Font(null,Font.PLAIN,50);
					g.setFont(font);
					g.setColor(Color.blue);
					g.drawString("最高分为:"+maxScore(),80,350);
					Font font1=new Font(null,Font.PLAIN,30);
					g.setFont(font1);
					g.setColor(Color.black);
					g.drawString("当前得分"+score,150,500);
					maxScore();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		 switch(state){
		 case START:
			 g.drawImage(Images.start,0,0,null);
			 break;
		 case PAUSE:
			 g.drawImage(Images.pause,0,0,null);
			 break; 
		 case END:
			 g.drawImage(Images.end,0,0,null);
			 
			 break;  
		 }
	}
	
	public static void music() throws MalformedURLException{
		 File file = new File("tower.wav");
			URL url = file.toURL();
			 AudioClip ac = Applet.newAudioClip(url);
			 ac.loop();
	}
	 
	 public static void main(String[] args) throws MalformedURLException {
		
		 
		 
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		frame.addKeyListener(world);

		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
		
		world.action();
		music();
	
	}

		public void keyTyped(KeyEvent e) {
		}
		public void keyPressed(KeyEvent e) {
			int value=e.getExtendedKeyCode();
			if(value==KeyEvent.VK_S) {
				if(hero.y<HEIGHT-hero.height) {
					hero.y+=50;
				}
			}else if(value==KeyEvent.VK_W) {
				if(hero.y>HEIGHT-HEIGHT) {
					hero.y-=50;
				}
			}else if(value==KeyEvent.VK_A) {
				if(hero.x>0) {
					hero.x-=50;
				}
			}else if(value==KeyEvent.VK_D) {
				if(hero.x<WIDTH-hero.width) {
					hero.x+=50;
				}
			}else if(value==KeyEvent.VK_SPACE) {
				if(isBigBullet()){
					World.bulletState *= -1;
					bullets = new HeroBullet[0];
				}else{
					World.bulletState *= -1;
				}
				
			}
		}
		public void keyReleased(KeyEvent e) {
		}



}

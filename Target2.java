import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Target2 {
	
	//(x,y) DEFINES THE CENTER
	private float x;
	private float y;
	private float oldx;
	private float oldy;
	private float radius;
	private float speed;
	private float maxDeviance;
	
	private int coinReward;
	private int ballReward;
	
	private int type;
	private boolean hit;
	private boolean isFirstDraw = true;
	private Random gen;
	int counter;
	private BufferedImage icon;
	private Image img;
	
	
	static final int RED = 0;
	static final int BLUE = 1;
	static final int BLACK = 2;
	static final int YELLOW = 3;
	private Image coinImg = Game.coinImg.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	private Image ballImg = Game.ballImg.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	
//-----------CONSTRUCTORS--------------------
	public Target2(float radius, int type, float x, float y){
		this(radius,type);
		this.x = x;
		this.y = y;
		isFirstDraw = true;
	}
	public Target2(float radius, int type) {
		gen = new Random();
		x = gen.nextFloat() * 400 + 400;
		y = gen.nextFloat() * 200 + 50;
		this.radius = radius;
		this.type = type;
		hit = false;
		counter = 0;
		speed = 0;
		maxDeviance = 0;
		isFirstDraw = true;
		
		//for different types
		double giveBall = Math.random();
		if (giveBall >= 0.5)
			giveBall = 1;
		else
			giveBall = 0;
		try {
			switch(type){
			case RED: coinReward = 1 + Game.getLevel()-1;
					ballReward = (int)giveBall;
					icon = ImageIO.read(new File("src/resources/target red.png"));
					break;
			case BLUE: coinReward = 3 + Game.getLevel()-1;
					ballReward = 1;
					icon = ImageIO.read(new File("src/resources/target blue.png"));
					break;
			case BLACK: coinReward = 15 + 5 * (Game.getLevel()-1);
					ballReward = 1;
					speed = 4 + Game.getLevel()-1;
					x = 0;
					icon = ImageIO.read(new File("src/resources/target.png"));
					break;
			case YELLOW: coinReward = 20 + 5 * (Game.getLevel()-1);
					ballReward = 1;
					maxDeviance = 100 + 30 * (Game.getLevel()-1);
					counter = 0;
					icon = ImageIO.read(new File("src/resources/target yellow.png"));
					break;
			}
		} catch (IOException e) { 
			e.printStackTrace();
		}
		img = icon.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
		
	}

//-------GETTERS---------------------------------
		public float getX(){
			return x;
		}
		
		public float getY(){
			return y;
		}
		
		public float getRadius(){
			return radius;
		}
		
		public int getCoinReward(){
			return coinReward;
		}
		
		public int getBallReward(){ 
			return ballReward;
			
		}
		
		public boolean isHit(){
			return hit;
		}
		public Image getImg(){
			return img;
		}
//-----------SETTERS------------------------
		public void setX(float x){
			if(!hit){
				oldx = this.x;
				this.x = x;
			}
		}
		
		public void setY(float y){
			if(!hit){
				oldy = this.y;
				this.y = y;
			}
		}
		public void setFirstDraw(boolean isFirstDraw){
			this.isFirstDraw = isFirstDraw;
		}
//---------------OTHER-STUUFF-----------------
		public void hit(){
			hit = true;
			speed = 0;
			counter = 0;
		}
		public boolean checkFirstDraw(){
			return isFirstDraw;
		}
		
		public void draw(Graphics g){
			Graphics2D g2d = (Graphics2D) g;
			if(!hit){
				if(type == YELLOW)
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) ( 1.0 - (counter/(25 * Game.getLevel()-150.0)))));
				g.drawImage(img, (int)(x - radius), (int)(y - radius), (int)(radius * 2), (int)(radius * 2), null);
				g2d.setComposite(AlphaComposite.SrcAtop);
				x += speed;
				if(x > 809 && type == BLACK){
					x = 0;
					y = gen.nextFloat() * 200 + 50;
				}
				if(type == YELLOW){
					if(counter == (25 * Game.getLevel()-150)){
						x += gen.nextFloat() * maxDeviance - maxDeviance/2;
						y += gen.nextFloat() * maxDeviance - maxDeviance/2;
						counter = 0;
					}
					if(x > 809 || x < 0)
						x = oldx;
					if(y > 306 || y < 0)
						y = oldy;
					counter--;
				}
				oldy = y;
				oldx = x;
				//g.drawImage(icon.getScaledInstance((int)(radius*2), (int)(radius * 2), Image.SCALE_SMOOTH), (int)(x - radius), (int)(y - radius), null);
				//g.drawOval((int)(x - radius), (int)(y - radius), (int)(radius * 2), (int)(radius * 2));//FIX
			}else{
				int xBuffer;
				if (coinReward >9)
					xBuffer = 4;
				else
					xBuffer = 0;
				g.setFont(new Font("Arial", Font.PLAIN, 13));
				g.setColor(new Color(0, 0, 0, 255 - counter*8));
				g.drawString("+" + coinReward, (int)(x - 20), (int)((y - 20) - counter));
				g.drawImage(coinImg,(int)(x+xBuffer), (int)((y-20-11) - counter), null);
				g.drawString("+" + ballReward, (int)(x - 20), (int)((y - 20 - 20) - counter));
				g.drawImage(ballImg,(int)(x+0), (int)((y-20-30) - counter), null);
				if(counter*8 + 8 < 255)
					counter++;
				
			}
		}
}

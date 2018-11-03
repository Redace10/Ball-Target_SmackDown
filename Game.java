import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

//contains code for setting up the game panel + game logic
public class Game extends JPanel implements ActionListener{
	
	//Graphics stuff
	private JLabel moneyLabel; 
	private JLabel ballCountLabel;
	private JLabel shopIcon;
	private JLabel levelIcon;
	private JProgressBar powerBar;
	private JFrame parent;
	private Game me;
	
	//game things
	private ArrayList<Target2> targets;
	private int numTargets;
	private Ball ball;
	
	//images
	BufferedImage barrelImg;
	static BufferedImage coinImg;
	static BufferedImage ballImg;
	BufferedImage wheels;
	BufferedImage shopButton;
	BufferedImage background;
	BufferedImage levelImg;
	Image coinIcon;
	Image ballIcon;
	
	//timers
	private Timer powerTracker;
	private Timer angleTracker;
	private Timer main;
	
	//general variables
	private int money;
	private int ballCount;
	private int level;
	private int moneyMultiplier = 1;
	private int missCounter = 0;//for keeping track of num misses to spawn a new target.
	private static int LEVEL = 0;
	private int rarityBonus;
	private boolean ready;	//is game ready for next shot?
	private boolean hitMade; //did the player hit a target this shot?
	private boolean isGameLost = false; //does the player still have balls left?
	private float bouncyness;
	private float powerBarMultiplier = 1;
	private float speedMultiplier = 0;
	private int angle;	//0 to 90, use Math.toRadians(angle) cuz stuff takes radians
	private float launchSpeed;	//for use with powerTracker
	private int change = 1;	//for use with powerTracker
	private float grav = (float) 0.6;
	private Random gen;
	
	
	public Game(JFrame parent) {
		//general variables
		angle = 0;
		money = 0;
		ballCount = 25;
		level = 0;
		ready = true;
		change = 1;
		bouncyness = (float) .7;
		gen = new Random();
		rarityBonus = 0;
		
		//game things
		targets = new ArrayList<>();
		numTargets = 0;
		ball = new Ball(10, 71, 287);
		
		
		//Graphics
		this.parent = parent;
		me = this;
		powerBar = new JProgressBar(0,30);
		ballCountLabel = new JLabel();
		moneyLabel = new JLabel();
		shopIcon = new JLabel();
		levelIcon = new JLabel();
			//set initial values
		this.setPreferredSize(new Dimension(809,306));
		powerBar.setValue(0);
				//images
		try {
			barrelImg = ImageIO.read(new File("resources/cannon barrel final.png"));
			wheels = ImageIO.read(new File("resources/wheels good.png"));
			shopButton = ImageIO.read(new File("resources/Shop.png"));
			coinImg = ImageIO.read((new File("resources/Coin2.png")));
			ballImg = ImageIO.read((new File("resources/ball.png")));
			background = ImageIO.read((new File("resources/default.png"))); 
			levelImg = ImageIO.read((new File("resources/Level Up 25.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		shopIcon.setIcon(new ImageIcon(shopButton.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
		levelIcon.setIcon(new ImageIcon(levelImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			//adding to game panel
		this.add(ballCountLabel);
		this.add(moneyLabel);
		this.add(powerBar);
		this.add(shopIcon);
		this.add(levelIcon);
		//Action Listeners/timers
		coinIcon = coinImg.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		ballIcon = ballImg.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				requestFocusInWindow();
				//if HAX
				Point p = MouseInfo.getPointerInfo().getLocation();
				
				if(e.getButton() == MouseEvent.BUTTON1){
					for(int i = 0; i < targets.size(); i++){
						Target2 t = targets.get(i);
						if(p.distance(t.getX()+getLocationOnScreen().getX(), t.getY()+getLocationOnScreen().getY()) <= t.getRadius()){
							t.hit();
							money += t.getCoinReward();
							ballCount += t.getBallReward();
							numTargets--;
							//newTarget();
							/*System.out.println("ridded  target in hax");
							System.out.println(numTargets);
							for(int j = 0; j < targets.size(); j++){
								System.out.println(targets.get(j));
							}*/
						}
					}	
				}else if(e.getButton() == MouseEvent.BUTTON3){
					newTarget((float)(p.getX()-getLocationOnScreen().getX()),(float)(p.getY()-getLocationOnScreen().getY()));
				}
				
			}
		});
		
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()== KeyEvent.VK_SPACE && isGameLost == false){	//when space is pressed
					if(ready){
						powerTracker.start();
					} 
				}
			}
			public void keyReleased(KeyEvent e){
				if(ready){
					powerTracker.stop();
					launch();
				}
			}
		});
		
		//for the shop Icon
		shopIcon.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				shopIcon.setIcon(new ImageIcon(shopButton.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			}
				public void mouseReleased (MouseEvent e){
					shopIcon.setIcon(new ImageIcon(shopButton.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
					Shop shop = new Shop(me);
					parent.setVisible(false);
					shop.addWindowListener(new WindowAdapter(){
						public void windowClosing(WindowEvent e){
							parent.setVisible(true);
							//moneyLabel.setText("x" + money);
							//ballCountLabel.setText("x" + ballCount);
						}
					});
				}
			});
		levelIcon.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (money >= getLevelMilestone()){
					levelIcon.setIcon(new ImageIcon(levelImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
					
				}
			}
				public void mouseReleased (MouseEvent e){
					if (money >= getLevelMilestone()){
						levelIcon.setIcon(new ImageIcon(levelImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
						LevelUp();
						getLevelMilestone();
					}
				}
			});
		powerTracker = new Timer(50, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchSpeed+=change*powerBarMultiplier;
					
				if(powerBar.getValue() == powerBar.getMaximum()){
					change = -1;
					launchSpeed--;
				} else if(powerBar.getValue() == powerBar.getMinimum()){
					change = 1;
					launchSpeed++;
				}				
			}
			
		});
		
		angleTracker = new Timer(10, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Point p = MouseInfo.getPointerInfo().getLocation();
				SwingUtilities.convertPointFromScreen(p, parent);
				double x = p.getX()+51, y = parent.getHeight()-p.getY()+87;
				angle = (int) Math.toDegrees(Math.atan2(y,x)); 
				/*if(angle < 360){
					angle+=2;
					if(angle == 90){
						angle = 360 + 90;
					}
				}else {
					angle-=2;
					if(angle == 360){
						angle = 0;
					}
				} */
			}
			
		});
		angleTracker.start();
		
		main = new Timer(20, this);
		main.start();

	}//constructor
	public void checkGameStatus(){
		if(ballCount <=0){
			isGameLost = true;
			JOptionPane.showMessageDialog(this,"You Lost, Son! Ball is NOT Life!");
			System.exit(0);
		}
	}
	
	public int getMoney(){
		return money;
	}
	public int getBallCount(){
		return ballCount;
	}
	public Ball getBall(){
		return ball;
	}
	public float getSpeedMultiplier(){
		return speedMultiplier;
	}
	public float getBouncyness(){
		return bouncyness;
	}
	public float getPowerBarMultiplier(){
		return powerBarMultiplier;
	}
	public int getLevelMilestone(){
		try {
			switch (LEVEL){
				case 0: levelImg = ImageIO.read((new File("resources/Level Up 50.png")));return 25;
				case 1: levelImg = ImageIO.read((new File("resources/Level Up 100.png")));return 50;
				case 2: levelImg = ImageIO.read((new File("resources/Level Up 250.png")));return 100;
				case 3: levelImg = ImageIO.read((new File("resources/Level Up 750.png")));return 250;
				case 4: levelImg = ImageIO.read((new File("resources/Level Up 1500.png")));return 750;
				case 5: levelImg = ImageIO.read((new File("resources/Level Up 1500.png")));return 1500;
				case 6: return 1000000;
			}

		} catch (IOException e) { 
			e.printStackTrace();
		}
		return 1000000;
	}
	
	public void setBallImg(Image img){
		ball.setImage(img);
	}
	public void changeBouncyness(float bouncyness){
		this.bouncyness += bouncyness;
	}
	public void changeMoney(int money){
		this.money -= money;
	}
	public void changeBallCount(int ballCount){
		this.ballCount += ballCount;
	}
	public void setBackgroundImg(BufferedImage background){
		this.background = background;
	}
	public void setPowerBarMultiplier(float powerBarMultiplier){
		this.powerBarMultiplier = powerBarMultiplier;
	}
	public void setMoneyMultiplier(int moneyMultiplier){
		this.moneyMultiplier = moneyMultiplier;
	}
	public void setSpeedMultiplier(float speedMultiplier){
		this.speedMultiplier = speedMultiplier;
	}
	
	public void launch(){
		ready = false;
		ballCount--;
		ball.setXSpeed((float) (launchSpeed*(1+speedMultiplier) * Math.cos(Math.toRadians(-angle)))); 
		ball.setYSpeed((float) (launchSpeed*(1+speedMultiplier) * Math.sin(Math.toRadians(-angle)))); 
	}
	
	public void actionPerformed(ActionEvent e){
		//everything to be cycled goes here
		if (money >= getLevelMilestone()){
			levelIcon.setEnabled(true);
		}else{
			levelIcon.setEnabled(false);
		}
			
		if(!ready){
		//update balllll
		if((ball.getX() + ball.getXSpeed()) + ball.getRadius() > 809){//ball hit far wall
			ball.setX(809 - ball.getX() - ball.getXSpeed()- 2*ball.getRadius() + 809);//trust me, this works...and I actually know why :D
			ball.setXSpeed(ball.getXSpeed() * -bouncyness);
			ball.setYSpeed(ball.getYSpeed() * bouncyness);
		}else if((ball.getX() + ball.getXSpeed()) < ball.getRadius()){
			ball.setX(0 - ball.getX() - ball.getXSpeed() + 2*ball.getRadius() + 0);//trust me, this works		ball.setXSpeed(ball.getXSpeed() * -bouncyness);
			ball.setYSpeed(ball.getYSpeed() * bouncyness);
			ball.setXSpeed(ball.getXSpeed() * -bouncyness);
		}else{
			ball.setX(ball.getX() + ball.getXSpeed());
		}
		
		if((ball.getY() + ball.getYSpeed()) + ball.getRadius() > 306){//ball hit ground
			ball.setY(306 - ball.getY() - ball.getYSpeed()-2*ball.getRadius() + 306);//trust me, this works
			ball.setXSpeed(ball.getXSpeed() * bouncyness);
			ball.setYSpeed(ball.getYSpeed() * -1 * bouncyness);
		}else if((ball.getY() + ball.getYSpeed()) < ball.getRadius()){
			ball.setY(0 - ball.getY() - ball.getYSpeed()+2*ball.getRadius() + 0);//trust me, this works...and I actually know why :D
			ball.setXSpeed(ball.getXSpeed() * bouncyness);
			ball.setYSpeed(ball.getYSpeed() * -bouncyness);
		}else{
			ball.setY(ball.getY() + ball.getYSpeed());
		}
	
		if(Math.hypot(ball.getXSpeed(), ball.getYSpeed()) < 1 && !ready && ball.getY() > 306 - 5 - ball.getRadius())
			reset();
		}
		if(!ready)//if ball is in flight
			ball.setYSpeed(ball.getYSpeed() + grav);
		
		if(ready && numTargets == 0){
			newTarget();
		}
		checkGameStatus();
		//if(movingTarget || angleTracker.isRunning() || powerTracker.isRunning())
			repaint();
	}
	
	public void reset(){
		ball.setXSpeed(0);
		ball.setYSpeed(0);
		ball.setX(71);
		ball.setY(287);
		//angle = 0;
		launchSpeed = 0;
		ready = true;
		if (!hitMade){
			missCounter++;
			if (missCounter == 3){
				newTarget();
				missCounter =0;
			}
		}
		hitMade = false; 
	}
	
	public void newTarget(){
		int type = gen.nextInt(100 + rarityBonus) + 1;//1 - 100 in future, goes up with rarity
		if(type <= 50)
			type = Target2.RED;
		else if(type > 50 && type <= 75)
			type = Target2.BLUE;
		else if(type > 75 && type <= 90 + rarityBonus/2)
			type = Target2.YELLOW;
		else if(type > 90 + rarityBonus/2 && type <= 100 + rarityBonus)
			type = Target2.BLACK;
		numTargets++;
		targets.add(new Target2(20 - 2*level,type));
	}
	public void changeFontColor(Color color){
		moneyLabel.setForeground(color);
		ballCountLabel.setForeground(color);
	}
	
	public void newTarget(float x, float y){
		int type = gen.nextInt(100) + 1;//1 - 100 in future, goes up with rarity
		if(type <= 50)
			type = Target2.RED;
		else if(type > 50 && type <= 85)
			type = Target2.BLUE;
		else if(type > 85 && type <= 93)
			type = Target2.BLACK;
		else if(type > 93 && type <= 100)
			type = Target2.YELLOW;
		numTargets++;
		targets.add(new Target2(20 - 2*level,type, x, y));
	}
	
	 public void LevelUp(){
	    	
		 	money-= getLevelMilestone();
	    	moneyLabel.setText("x" + money);
		 	level++;
	    	LEVEL = level;
	    	if (LEVEL == 6){
	    		JOptionPane.showMessageDialog(null, "SWEET SHOOTIN'! You Won!\nYou can keep playing, if you like...");
	    	}
	 }
	 
	 public static int getLevel(){
		 return LEVEL;
	 }
	 
	 public void addRarity(int r){
		 rarityBonus += r;
	 }
	public void paintComponent(Graphics g){
		super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		//game things
		ball.draw(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for(int i = 0; i < targets.size(); i++){
			Target2 t = targets.get(i);
			if(!t.isHit() && ball.checkCollision(t.getX(), t.getY(), t.getRadius())){
				t.hit();
				hitMade = true;
				missCounter = 0;
				money += t.getCoinReward()*moneyMultiplier;
				ballCount += t.getBallReward();
				numTargets--;
			}
			if(t.counter >= 31){
				targets.remove(t);
				i--;
			}else{
				targets.get(i).draw(g);
			}
		}
		
		//Graphics
		powerBar.setBounds(55, 70, 120, 13);
		ballCountLabel.setBounds(25,27,80,10);
		moneyLabel.setBounds(25,7,100,10);
		shopIcon.setBounds((60-shopIcon.getHeight())/2,35+((60-shopIcon.getHeight())/2),shopIcon.getHeight(),shopIcon.getWidth());//fancyness to make look like button
		levelIcon.setBounds((225-levelIcon.getHeight())/2,25+((20-levelIcon.getHeight())/2),levelIcon.getHeight(),levelIcon.getWidth());
		moneyLabel.setText("x" + money);
		ballCountLabel.setText("x" + ballCount);
		powerBar.setValue((int) launchSpeed);
		
		powerBar.setForeground(new Color(255 * (int) (powerBar.getValue()) / powerBar.getMaximum(), 255 * (int) (powerBar.getMaximum() - powerBar.getValue()) / powerBar.getMaximum(), 0));
		
		//draw Images
		double locationX = 51;
		double locationY = 87;
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(-angle), locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		 
		g2d.drawImage(op.filter(barrelImg, null), 20, 200, null);
		g.drawImage(wheels, 37, 276, null);	//71, 287
		g.drawImage(coinIcon, 8, 5, null);	//71, 287
		g.drawImage(ballIcon, 10, 26, null);	
	}

}
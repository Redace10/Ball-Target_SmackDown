import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Shop extends JFrame{
	private JLabel moneyLabel = new JLabel();
	private JButton backButton = new JButton();
	static Font font = new Font("Arial", Font.PLAIN, 13);
	
	private static Upgrade blueBall = new Upgrade(3, 1, true);
	private static Upgrade redBall = new Upgrade(3, 2, true);
	private static Upgrade greenBall = new Upgrade(3, 3, true);
	private static Upgrade yellowBall = new Upgrade(3, 4, true);
	private static Upgrade purpleBall = new Upgrade(3, 5, true);
	private static Upgrade dragonBall = new Upgrade(10, 6, true);
	
	private static Upgrade size = new Upgrade(5, 7, false);
	private static Upgrade powerBarSpeed = new Upgrade(5, 8, false);
	private static Upgrade bouncyness = new Upgrade(10, 9, false);
	private static Upgrade doubleCoins = new Upgrade(100, 12, true);
	private static Upgrade blackTargetChance = new Upgrade(5, 11, false); 
	private static Upgrade ballSpeed = new Upgrade(15, 10, false);
	
	private static Upgrade ballQuantity10 = new Upgrade(10, 13, false);
	private static Upgrade ballQuantity25 = new Upgrade(20, 14, false);
	private static Upgrade ballQuantity50 = new Upgrade(40, 15, false);
	private static Upgrade beachBackground = new Upgrade(20, 16, true);
	private static Upgrade parkBackground = new Upgrade(40, 17, true);
	private static Upgrade spaceBackground = new Upgrade(50, 18, true); 
	
	
	private Game game;
	
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//gets the dimensions of the screen
	
	
	private BufferedImage getImg = null;
	private BufferedImage shopBackground;
	private DrawShop shop = new DrawShop();
	private static Color currentColor = Color.BLACK;
	
	private final int SIZE_UP_MULTIPLYER = 2, BLACK_TARGET_MULTIPLYER = 2, POWER_BAR_MULTIPLIER = 2, 
						BOUNCY_MULTIPLIER = 2, SPEED_MULTIPLIER =2;
	public Shop(Game game){
		setTitle("Shop!");
		setSize(610,380);
		setLocation(dim.width/2-305, dim.height/2 - 190); // frame is at the center of the screen
		setVisible(true);
		this.setResizable(false);
		this.game = game;
		checkFunds();
		moneyLabel.setFont(new Font("Arial", Font.BOLD, 15));
		moneyLabel.setText("x" + game.getMoney());
		moneyLabel.setBounds(35,7,90,20);
		try {
			shopBackground = ImageIO.read(new File("src/resources/Shop Background.png"));
		} catch (IOException e1) { 
			e1.printStackTrace();
		}
		blueBall.getButton().setText("FREE");
		checkFunds();
//-------------------------------------------------------------------------------------------------------------------			
		class BlueBall implements ActionListener{ 
			public void actionPerformed(ActionEvent e) {
				if(blueBall.getButton().isEnabled()){		
					if(!blueBall.getButton().getText().equals("FREE")){ 
						checkFunds();
					}
						currentColor = Color.BLUE;
						try {
							getImg = ImageIO.read(new File("src/resources/ball.png"));
							game.setBallImg(getImg.getScaledInstance(512,512, Image.SCALE_SMOOTH));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
			} 
		}
		BlueBall blue = new BlueBall();
		blueBall.getButton().addActionListener(blue);
//-------------------------------------------------------------------------------------------------------------------	
		class RedBall implements ActionListener{ 
			public void actionPerformed(ActionEvent e) {
				if(redBall.getButton().isEnabled()){		
					if(!redBall.getButton().getText().equals("FREE")){
						game.changeMoney(-redBall.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						redBall.getButton().setText("FREE");
						checkFunds();
					}
						currentColor = Color.RED;
						try {
							getImg = ImageIO.read(new File("src/resources/apple.png"));
							game.setBallImg(getImg.getScaledInstance(512,512, Image.SCALE_SMOOTH));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
			} 
		}
		RedBall red = new RedBall();
		redBall.getButton().addActionListener(red);
//-------------------------------------------------------------------------------------------------------------------	
		class GreenBall implements ActionListener{ 
			public void actionPerformed(ActionEvent e) {
				if(greenBall.getButton().isEnabled()){		
					if(!greenBall.getButton().getText().equals("FREE")){
						game.changeMoney(greenBall.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						greenBall.getButton().setText("FREE");
						checkFunds();
					}
					try {
						getImg = ImageIO.read(new File("src/resources/rock.png"));
						game.setBallImg(getImg.getScaledInstance(512,512, Image.SCALE_SMOOTH));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} 
		}
		GreenBall green = new GreenBall();
		greenBall.getButton().addActionListener(green);
//-------------------------------------------------------------------------------------------------------------------	
		class YellowBall implements ActionListener{ 
			public void actionPerformed(ActionEvent e) {
				if(yellowBall.getButton().isEnabled()){		
					if(!yellowBall.getButton().getText().equals("FREE")){
						game.changeMoney(yellowBall.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						yellowBall.getButton().setText("FREE");
						checkFunds();
					}
						currentColor = Color.YELLOW;
						try {
							getImg = ImageIO.read(new File("src/resources/Pokeball.PNG"));
							game.setBallImg(getImg.getScaledInstance(512,512, Image.SCALE_SMOOTH));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
			} 
		}
		YellowBall yellow = new YellowBall();
		yellowBall.getButton().addActionListener(yellow);
//-------------------------------------------------------------------------------------------------------------------		
		class PurpleBall implements ActionListener{ 
			public void actionPerformed(ActionEvent e) {
				if(purpleBall.getButton().isEnabled()){		
					if(!purpleBall.getButton().getText().equals("FREE")){
						game.changeMoney(purpleBall.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						purpleBall.getButton().setText("FREE");
						checkFunds();
					}
						currentColor = new Color(95, 0, 165);
						try {
							getImg = ImageIO.read(new File("src/resources/soccer ball.png"));
							game.setBallImg(getImg.getScaledInstance(512,512, Image.SCALE_SMOOTH));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
			} 
		}
		PurpleBall purple = new PurpleBall();
		purpleBall.getButton().addActionListener(purple);
//-------------------------------------------------------------------------------------------------------------------			
		class DragonBall implements ActionListener{ 
			public void actionPerformed(ActionEvent e) { 
				if(dragonBall.getButton().isEnabled()){		
					if(!dragonBall.getButton().getText().equals("FREE")){
						game.changeMoney(dragonBall.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						dragonBall.getButton().setText("FREE");
						checkFunds();
					}
						currentColor = Color.CYAN;
						try {
							getImg = ImageIO.read(new File("src/resources/super ball.png"));
							game.setBallImg(getImg.getScaledInstance(512,512, Image.SCALE_SMOOTH));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
			}
			
		}
		DragonBall dragon = new DragonBall();
		dragonBall.getButton().addActionListener(dragon);
//-------------------------------------------------------------------------------------------------------------------	
		class Size implements ActionListener{ 
			public void actionPerformed(ActionEvent e) {
				if(size.getButton().isEnabled()){		
					game.changeMoney(size.getPrice());
					moneyLabel.setText("x" + game.getMoney());
					size.setPrice(size.getPrice()*SIZE_UP_MULTIPLYER);
					size.getButton().setText(size.getPrice() + "");
					game.getBall().setRadius(game.getBall().getRadius()+1);
					if(game.getBall().getRadius()>= 20){
						size.getButton().setText("SOLD");
						size.getButton().setEnabled(false);
					}
					checkFunds();
				}
			}
		}	
		Size sizeUpgrade = new Size();	
		size.getButton().addActionListener(sizeUpgrade);
//-------------------------------------------------------------------------------------------------------------------			
		class BlackTargetChance implements ActionListener{ 
			public void actionPerformed(ActionEvent e) {
				if(blackTargetChance.getButton().isEnabled()){		
					game.changeMoney(blackTargetChance.getPrice());
					moneyLabel.setText("x" + game.getMoney());
					blackTargetChance.setPrice(blackTargetChance.getPrice()*BLACK_TARGET_MULTIPLYER);
					blackTargetChance.getButton().setText(blackTargetChance.getPrice() + "");
					game.addRarity(10);
					checkFunds();
				}
			}
			
		}
		BlackTargetChance blackTarget = new BlackTargetChance();
		blackTargetChance.getButton().addActionListener(blackTarget);
//-------------------------------------------------------------------------------------------------------------------			
		class PowerBar implements ActionListener{ 
			public void actionPerformed(ActionEvent arg0) {
				if(powerBarSpeed.getButton().isEnabled()){
					game.changeMoney(powerBarSpeed.getPrice());
					moneyLabel.setText("x" + game.getMoney());
					powerBarSpeed.setPrice(powerBarSpeed.getPrice()*POWER_BAR_MULTIPLIER);
					powerBarSpeed.getButton().setText(powerBarSpeed.getPrice() + "");
					game.setPowerBarMultiplier(game.getPowerBarMultiplier() + .30f);
					if(game.getPowerBarMultiplier()>= 2.20f){
						powerBarSpeed.getButton().setText("SOLD");
						powerBarSpeed.getButton().setEnabled(false);
					}
					checkFunds();
				}
			}
			
		}
		PowerBar power = new PowerBar();
		powerBarSpeed.getButton().addActionListener(power);
//-------------------------------------------------------------------------------------------------------------------	
		class Bouncy implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(bouncyness.getButton().isEnabled()){		
					game.changeMoney(bouncyness.getPrice());
					moneyLabel.setText("x" + game.getMoney());
					bouncyness.setPrice(bouncyness.getPrice()*BOUNCY_MULTIPLIER);
					bouncyness.getButton().setText(bouncyness.getPrice() + "");
					game.changeBouncyness(0.05f);
					if(game.getBouncyness()>= .9){
						bouncyness.getButton().setText("SOLD");
						bouncyness.getButton().setEnabled(false);
					}
					checkFunds();
				}
			}
		}
		Bouncy bouncy = new Bouncy();
		bouncyness.getButton().addActionListener(bouncy);
//-------------------------------------------------------------------------------------------------------------------		
		class BallQ10 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(ballQuantity10.getButton().isEnabled()){		
					game.changeMoney(ballQuantity10.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						game.changeBallCount(10);
						checkFunds();
				}
			}
		}
		BallQ10 ballQ10 = new BallQ10();
		ballQuantity10.getButton().addActionListener(ballQ10);	
//-------------------------------------------------------------------------------------------------------------------				
		class BallQ25 implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(ballQuantity25.getButton().isEnabled()){		
					game.changeMoney(ballQuantity25.getPrice());
					moneyLabel.setText("x" + game.getMoney());
					game.changeBallCount(25);
					checkFunds();
				}
			}
		}		
		BallQ25 ballQ25 = new BallQ25();
		ballQuantity25.getButton().addActionListener(ballQ25);	
//-------------------------------------------------------------------------------------------------------------------			
		class BallQ50 implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(ballQuantity50.getButton().isEnabled()){		
					game.changeMoney(ballQuantity50.getPrice());
					moneyLabel.setText("x" + game.getMoney());
					game.changeBallCount(50);
					checkFunds();
				}
			}	
		}
		BallQ50 ballQ50 = new BallQ50();
		ballQuantity50.getButton().addActionListener(ballQ50);
//-------------------------------------------------------------------------------------------------------------------	
		class CoinMultiplier implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(doubleCoins.getButton().isEnabled()){		
					game.changeMoney(doubleCoins.getPrice());
					moneyLabel.setText("x" + game.getMoney()); 
					doubleCoins.getButton().setText("SOLD");
					game.setMoneyMultiplier(2);
					checkFunds();
				}
			}	
		}
		CoinMultiplier coinMultiplier = new CoinMultiplier();
		doubleCoins.getButton().addActionListener(coinMultiplier);
//-------------------------------------------------------------------------------------------------------------------	
		class BallSpeed implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(ballSpeed.getButton().isEnabled()){		
					game.changeMoney(ballSpeed.getPrice());
					moneyLabel.setText("x" + game.getMoney()); 
					game.setSpeedMultiplier(game.getSpeedMultiplier()+0.2f);
					ballSpeed.setPrice(bouncyness.getPrice()*SPEED_MULTIPLIER);
					ballSpeed.getButton().setText(ballSpeed.getPrice() + "");
					if(game.getSpeedMultiplier()>= 1){
						ballSpeed.getButton().setText("SOLD");
						ballSpeed.getButton().setEnabled(false);
					}
					checkFunds();
				}
			}	
		}
		BallSpeed speed = new BallSpeed();
		ballSpeed.getButton().addActionListener(speed);
//-------------------------------------------------------------------------------------------------------------------			
		class Beach implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(beachBackground.getButton().isEnabled()){
					game.changeFontColor(Color.BLACK);
					if(!beachBackground.getButton().getText().equals("FREE")){
						game.changeMoney(beachBackground.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						beachBackground.getButton().setText("FREE");
						game.changeFontColor(Color.BLACK);
						checkFunds();
					}
					try {                
						game.setBackgroundImg(ImageIO.read(new File("src/resources/Beach.jpg")));
				    } catch (IOException ex) {
				    	System.out.println("No image could be found");
				    }
				}
			}
		}
		Beach beach = new Beach();
		beachBackground.getButton().addActionListener(beach);
//-------------------------------------------------------------------------------------------------------------------		
		class Park implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(parkBackground.getButton().isEnabled()){	
					game.changeFontColor(Color.BLACK);
					if(!parkBackground.getButton().getText().equals("FREE")){
						game.changeMoney(parkBackground.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						parkBackground.getButton().setText("FREE");
						game.changeFontColor(Color.BLACK);
						checkFunds();
					}
					try {                
						game.setBackgroundImg(ImageIO.read(new File("src/resources/Park.jpg")));
				    } catch (IOException ex) {
				    	System.out.println("No image could be found");
				    }
				}
			}
		}
		Park park = new Park();
		parkBackground.getButton().addActionListener(park);
//-------------------------------------------------------------------------------------------------------------------	
		class Space implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(spaceBackground.getButton().isEnabled()){
					game.changeFontColor(Color.RED);
					if(!spaceBackground.getButton().getText().equals("FREE")){
						game.changeMoney(spaceBackground.getPrice());
						moneyLabel.setText("x" + game.getMoney());
						spaceBackground.getButton().setText("FREE"); 
						game.changeFontColor(Color.DARK_GRAY);
						checkFunds();
					}
					try {                
						game.setBackgroundImg(ImageIO.read(new File("src/resources/Space.jpg")));
				    } catch (IOException ex) {
				    	System.out.println("No image could be found");
				    }
				}
			}
		}
		Space space = new Space();
		spaceBackground.getButton().addActionListener(space);
//-------------------------------------------------------------------------------------------------------------------	
		WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		backButton.setFont(font);
		backButton.setBounds(520, 325, 70, 20);
		backButton.setText("Back");
		backButton.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){ 
				blueBall.getButton().removeActionListener(blue);
				redBall.getButton().removeActionListener(red);
				greenBall.getButton().removeActionListener(green);
				yellowBall.getButton().removeActionListener(yellow);
				purpleBall.getButton().removeActionListener(purple);
				dragonBall.getButton().removeActionListener(dragon);
				size.getButton().removeActionListener(sizeUpgrade);
				blackTargetChance.getButton().removeActionListener(blackTarget);
				powerBarSpeed.getButton().removeActionListener(power);
				bouncyness.getButton().removeActionListener(bouncy);
				ballSpeed.getButton().removeActionListener(speed);
				ballQuantity10.getButton().removeActionListener(ballQ10);
				ballQuantity25.getButton().removeActionListener(ballQ25);
				ballQuantity50.getButton().removeActionListener(ballQ50);
				doubleCoins.getButton().removeActionListener(coinMultiplier);
				beachBackground.getButton().removeActionListener(beach);
				parkBackground.getButton().removeActionListener(park);
				spaceBackground.getButton().removeActionListener(space);
				backButton.removeMouseListener(this);
				dispatchEvent(close);
				
			}
		});	
//-------------------------------------------------------------------------------------------------------------------			
		blueBall.setImg("src/resources/cannon icon.png",1);
		redBall.setImg("src/resources/apple icon.png",1);
		yellowBall.setImg("src/resources/Pokeball icon.png",1);
		purpleBall.setImg("src/resources/soccer icon.png",1);
		greenBall.setImg("src/resources/rock icon.png",1);
		dragonBall.setImg("src/resources/super icon.png",1);
		dragonBall.setImg("src/resources/super icon.png",1);
		size.setImg("src/resources/Size Increase.png",0);
		bouncyness.setImg("src/resources/Bounce.png",0);
		powerBarSpeed.setImg("src/resources/Power Bar Speed Icon.png",0);
		ballQuantity10.setImg("src/resources/10 quantity.png",0);
		ballQuantity25.setImg("src/resources/25 quantity.png",0);
		ballQuantity50.setImg("src/resources/50 quantity.png",0);
		doubleCoins.setImg("src/resources/Coin Drop.png",0);
		blackTargetChance.setImg("src/resources/Black Target Increase.png",0);
		beachBackground.setImg("src/resources/beach icon 3.png",0);
		parkBackground.setImg("src/resources/park icon 3.png",0);
		spaceBackground.setImg("src/resources/space icon 3.png",0);
		ballSpeed.setImg("src/resources/Ball Speed.png",0);
		
//-------------------------------------------------------------------------------------------------------------------			
		Container c = this.getContentPane();
		shop.setLayout(null);
		shop.add(moneyLabel);
		shop.add(backButton);
		for (int i = 0; i <Upgrade.upgrades.size(); i ++){
			shop.add(Upgrade.upgrades.get(i).getImgLabel());
			shop.add(Upgrade.upgrades.get(i).getButton());
		}
		c.add(shop);
//-------------------------------------------------------------------------------------------------------------------			
	
	}//constructor
	public void checkFunds(){
		if (game.getMoney() >= blueBall.getPrice() || blueBall.getButton().getText().equals("FREE"))
			blueBall.getButton().setEnabled(true);
		else
			blueBall.getButton().setEnabled(false);
			
		if (game.getMoney() >= redBall.getPrice() || redBall.getButton().getText().equals("FREE"))
			redBall.getButton().setEnabled(true);
		else
			redBall.getButton().setEnabled(false);
		
		if (game.getMoney() >= greenBall.getPrice() || greenBall.getButton().getText().equals("FREE"))
			greenBall.getButton().setEnabled(true);
		else
			greenBall.getButton().setEnabled(false);
		
		if (game.getMoney() >= yellowBall.getPrice() || yellowBall.getButton().getText().equals("FREE"))
			yellowBall.getButton().setEnabled(true);
		else
			yellowBall.getButton().setEnabled(false);

		if (game.getMoney() >= purpleBall.getPrice() || purpleBall.getButton().getText().equals("FREE"))
			purpleBall.getButton().setEnabled(true);
		else
			purpleBall.getButton().setEnabled(false);
		
		if (game.getMoney() >= dragonBall.getPrice() || dragonBall.getButton().getText().equals("FREE"))
			dragonBall.getButton().setEnabled(true);
		else
			dragonBall.getButton().setEnabled(false);
		
		if (game.getMoney() >= size.getPrice())
			size.getButton().setEnabled(true);
		else
			size.getButton().setEnabled(false);
		
		if (game.getMoney() >= blackTargetChance.getPrice())
			blackTargetChance.getButton().setEnabled(true);
		else
			blackTargetChance.getButton().setEnabled(false);
	
		if (game.getMoney() >= powerBarSpeed.getPrice())
			powerBarSpeed.getButton().setEnabled(true);
		else
			powerBarSpeed.getButton().setEnabled(false);
		
		if (game.getMoney() >= bouncyness.getPrice() && !bouncyness.getButton().getText().equals("SOLD"))
			bouncyness.getButton().setEnabled(true);
		else
			bouncyness.getButton().setEnabled(false);
		
		if (game.getMoney() >= ballQuantity10.getPrice())
			ballQuantity10.getButton().setEnabled(true);
		else
			ballQuantity10.getButton().setEnabled(false);
		
		if (game.getMoney() >= ballQuantity25.getPrice())
			ballQuantity25.getButton().setEnabled(true);
		else
			ballQuantity25.getButton().setEnabled(false);
		
		if (game.getMoney() >= ballQuantity50.getPrice())
			ballQuantity50.getButton().setEnabled(true);
		else
			ballQuantity50.getButton().setEnabled(false);
		
		if (game.getMoney() >= doubleCoins.getPrice() && !doubleCoins.getButton().getText().equals("SOLD"))
			doubleCoins.getButton().setEnabled(true);
		else
			doubleCoins.getButton().setEnabled(false);
		
		if (game.getMoney() >= ballSpeed.getPrice() && !ballSpeed.getButton().getText().equals("SOLD"))
			ballSpeed.getButton().setEnabled(true);
		else
			ballSpeed.getButton().setEnabled(false);
		
		if (game.getMoney() >= beachBackground.getPrice() || beachBackground.getButton().getText().equals("FREE"))
			beachBackground.getButton().setEnabled(true);
		else
			beachBackground.getButton().setEnabled(false);
		
		if (game.getMoney() >= parkBackground.getPrice() || parkBackground.getButton().getText().equals("FREE"))
			parkBackground.getButton().setEnabled(true);
		else
			parkBackground.getButton().setEnabled(false);
		
		if (game.getMoney() >= spaceBackground.getPrice() || spaceBackground.getButton().getText().equals("FREE"))
			spaceBackground.getButton().setEnabled(true);
		else
			spaceBackground.getButton().setEnabled(false);
		
		for(int i = 0; i <Upgrade.upgrades.size();i ++)
			Upgrade.upgrades.get(i).adjustButtonIcon();
	}
	public static Color getColor(){
		return currentColor;
	}
	
//-------------------------------------------------------------------------------------------------------------------	
	class DrawShop extends JPanel{
		public DrawShop(){
			setLayout(null);
		}
		public void draw(){
			repaint();
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(shopBackground, 0, -25, null);
			Graphics2D g2d = (Graphics2D) g;
		
			Ellipse2D blueBall = new Ellipse2D.Double(Shop.blueBall.getXLoc(), Shop.blueBall.getYLoc(), 30,30);
			Ellipse2D redBall = new Ellipse2D.Double(Shop.redBall.getXLoc(), Shop.redBall.getYLoc(), 30, 30);
			Ellipse2D greenBall = new Ellipse2D.Double(Shop.greenBall.getXLoc(), Shop.greenBall.getYLoc() ,30,30);
			Ellipse2D yellowBall = new Ellipse2D.Double(Shop.yellowBall.getXLoc(), Shop.yellowBall.getYLoc() ,30,30);
			Ellipse2D purpleBall = new Ellipse2D.Double(Shop.purpleBall.getXLoc(), Shop.purpleBall.getYLoc() ,30,30);
			Ellipse2D dragonBall = new Ellipse2D.Double(Shop.dragonBall.getXLoc(), Shop.dragonBall.getYLoc() ,30,30);
			g.setColor(Color.BLUE);
			g2d.fill(blueBall);
			g.setColor(Color.RED);
			g2d.fill(redBall);
			g.setColor(Color.GREEN);
			g2d.fill(greenBall);
			g.setColor(Color.YELLOW);
			g2d.fill(yellowBall);
			g.setColor(new Color(95, 0, 165));
			g2d.fill(purpleBall);
			g.setColor(Color.CYAN);
			g2d.fill(dragonBall);
			g.drawImage(game.coinImg, 8, 5, 25,25, null); 

		}//method
	}//class
	
}//class

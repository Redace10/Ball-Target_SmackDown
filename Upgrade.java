import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Upgrade {
	private int price, xLoc, yLoc, itemID, locationCounter = 0;
	private Boolean isSinglePurchase;
	private final int BUFFER = 100;
	JButton buyButton = new JButton();
	
	private Image Img = null, coinImg = null;
	private BufferedImage getImg = null, getCoinImg = null;
	private ImageIcon Icon, coinIcon;
	private JLabel imgLabel = new JLabel();
	
	static ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	
	public Upgrade(int price, int itemID, Boolean isSinglePurchase){
		this.price = price;
		this.itemID = itemID;
		this.isSinglePurchase = isSinglePurchase;
		locationCounter = itemID;
		
		if (itemID > 12){
			yLoc = 260;
			locationCounter -= 12;
		}else if (itemID > 6){
			yLoc = 160;
			locationCounter -= 6;
		}else
			yLoc = 60;
		
		switch (locationCounter){
			case 1:
				xLoc = 35; break;
			case 2:
				xLoc = 35 + BUFFER; break;
			case 3:
				xLoc = 35 + (BUFFER * 2); break;
			case 4:
				xLoc = 35 + (BUFFER * 3); break;
			case 5:
				xLoc = 35 + (BUFFER * 4); break;
			case 6:
				xLoc = 35 + (BUFFER * 5); break;	
		}
		try{
			getCoinImg = ImageIO.read(new File("src/resources/Coin2.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		coinImg = getCoinImg.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		coinIcon = new ImageIcon(coinImg);
		
		
		buyButton.setIcon(coinIcon);
		buyButton.setDisabledIcon(coinIcon);
		if (price >99){
			buyButton.setText(price+"");
			buyButton.setIconTextGap(-37);}
		else if(price > 9){
			buyButton.setText(price+"");
			buyButton.setIconTextGap(-30);}
		else{
			buyButton.setText(price+"");
			buyButton.setIconTextGap(-23);}
		
		buyButton.setBounds(xLoc - 25, yLoc + 40, 85, 20);
		buyButton.setText(price+"");
		buyButton.setFont(Shop.font); 
		adjustButtonIcon();
		upgrades.add(this);
	}
	public void setImg(String file, int size){
		try{
			getImg = ImageIO.read(new File(file));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		
		if (size == 0){
			Img = getImg.getScaledInstance(85, 60, Image.SCALE_SMOOTH);
			imgLabel.setBounds(xLoc-25,yLoc-30,85,60);
		}
		if (size == 1){
			Img = getImg.getScaledInstance(56, 56, Image.SCALE_SMOOTH);
			imgLabel.setBounds(xLoc-10,yLoc-20,56,56);
		}
		Icon = new ImageIcon(Img);
		imgLabel.setIcon(Icon);
	}
	public void adjustButtonIcon(){
		if (price >99){
			buyButton.setIconTextGap(-37);}
		else if(price > 9){
			buyButton.setIconTextGap(-30);}
		else{
			buyButton.setIconTextGap(-23);}
		if (buyButton.getText().equals("SOLD") || buyButton.getText().equals("FREE")){
			buyButton.setIcon(null);
			buyButton.setDisabledIcon(null);
		}
	}
	public JButton getButton(){
		return buyButton;
	}
	public JLabel getImgLabel(){
		return imgLabel;
	}
	public int getXLoc(){
		return xLoc;
	}
	public int getYLoc(){
		return yLoc;
	}
	public Boolean getSinglePurchase(){
		return isSinglePurchase;
	}
	public int getPrice(){
		return price;
	}
	public void setPrice(int price){
		this.price = price;
	}
	public void invisible(){
		buyButton.setVisible(false);
	}
	
}

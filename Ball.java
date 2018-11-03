import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ball {

	//DEFINES CENTER OF THE BALL
	private float x;
	private float y;
	private float xSpeed;
	private float ySpeed;
	
	private float radius;
	
	private Image icon;
	
//----------CONSTRUCTORS-------------------- 
	public Ball(int radius){
		x = 0;
		y = 0;
		xSpeed = 0;
		ySpeed = 0;
		
		this.radius = radius;
		try {
			icon = ImageIO.read(new File("resources/ball.png")).getScaledInstance(256,256, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Ball(int radius, int x, int y){
		this.x = x;
		this.y = y;
		xSpeed = 0;
		ySpeed = 0;
		
		this.radius = radius;
		try {
			icon = ImageIO.read(new File("resources/ball.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//-------GETTER---------------------------------
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getRadius(){
		return radius;
	}
	
	public float getXSpeed(){
		return xSpeed;
	}
	
	public float getYSpeed(){
		return ySpeed;
	}
//-----------SETTERS------------------------
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setRadius(float rad){
		radius = rad;
	}
	
	public void setXSpeed(float val){
		xSpeed = val;
	}
	
	public void setYSpeed(float val){
		ySpeed = val;
	}
//--------------OTHER-STUFF---------------------------------
	//returns true if this ball overlaps with another ball at (x,y) with radius radius
	public boolean checkCollision(float x, float y, float radius){
		return Math.sqrt(Math.pow(Math.abs(this.x - x),2)+Math.pow(Math.abs(this.y - y), 2)) < this.radius + radius;
	}
	
	public void setImage(Image icon){
		this.icon = icon;
	}
	
	//paints this ball on the given graphics object
	public void draw(Graphics g){
		g.drawImage(icon,(int)(x - radius), (int)(y - radius),(int)(radius * 2),(int)(radius * 2), null);
	}
}

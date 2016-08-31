import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;

public class TutorialGUI extends JPanel implements ActionListener{
	private JFrame frame = new JFrame(); 
	private JButton nextButton = new JButton();
	private JButton backButton = new JButton();
	private JButton exitButton = new JButton();
	private Container c = getFrame().getContentPane();
	BufferedImage pageImg;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//gets the dimensions of the screen 
	private int pageNumber = 1;
	public TutorialGUI(){
		Timer main = new Timer(20, this);
		//main.start();
		try {
			pageImg = ImageIO.read(new File("src/resources/tutorial page 1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		nextButton.setFont(Shop.font);
		backButton.setFont(Shop.font);
		exitButton.setFont(Shop.font);
		
		nextButton.setText("Next ->");
		backButton.setText("<- Back");
		exitButton.setText("EXIT");
		this.setLayout(null);
		backButton.setBounds(150,425,80,25);
		nextButton.setBounds(370,425,80,25);
		exitButton.setBounds(260,440,80,20);
		checkButton();
		updatePage();
		class BackButton implements ActionListener{
			public void actionPerformed(ActionEvent e){
				pageNumber--;
				checkButton();
				updatePage();
				repaint();
			}
		}
		BackButton back = new BackButton();
		backButton.addActionListener(back);	
		
		class NextButton implements ActionListener{
			public void actionPerformed(ActionEvent e){
				pageNumber++;
				checkButton();
				updatePage();
				repaint();
			}
		}
		NextButton next = new NextButton();
		nextButton.addActionListener(next);
		WindowEvent close = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		class ExitButton implements ActionListener{
			public void actionPerformed(ActionEvent e){
				frame.dispatchEvent(close);
			}
		}
		ExitButton exit = new ExitButton();
		exitButton.addActionListener(exit);
		
		this.add(nextButton);
		this.add(backButton);
		this.add(exitButton);
		c.add(this);
		getFrame().setTitle("Kick Foot Game");
		getFrame().setSize(600,500);
		getFrame().setLocation(dim.width/2 - 300, dim.height/2 - 250);	//frame is at the center of the screen
		getFrame().setResizable(false);
		getFrame().setVisible(true);
		repaint();
	}
	public void checkButton(){
		if (pageNumber == 1){
			backButton.setEnabled(false);
		}else if (pageNumber == 5){
			nextButton.setEnabled(false);
		}else{
			backButton.setEnabled(true);
			nextButton.setEnabled(true);
		}
	}
	public void updatePage(){
	try{
		switch(pageNumber){
			case 1: pageImg = ImageIO.read(new File("src/resources/tutorial page 1.png")); break;
			case 2: pageImg = ImageIO.read(new File("src/resources/tutorial page 2.png")); break;
			case 3: pageImg = ImageIO.read(new File("src/resources/tutorial page 3.png"));break;
			case 4: pageImg = ImageIO.read(new File("src/resources/tutorial page 4.png"));break;
			case 5: pageImg = ImageIO.read(new File("src/resources/tutorial page 5.png")); break;
		}
	} catch (IOException e) { 
		e.printStackTrace();
	}
	}
	public  JFrame getFrame() {
		return frame;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		g.drawImage(pageImg, 0, 0, null);
	} 
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
	}
}

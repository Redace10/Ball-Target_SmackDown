import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MenuGUI{
	private BufferedImage playImg;
	private BufferedImage tutImg;
	private BufferedImage exitImg;
	private BufferedImage clearAccImg;
	private JLabel startButton = new JLabel();
		private JLabel tutorialButton = new JLabel();
		private JLabel exitButton = new JLabel();
		private JLabel previousAccountButton = new JLabel();
		private JLabel clearAccountsButton = new JLabel();

		static JFrame gameFrame = new JFrame();
		
		static Game game = new Game(gameFrame);
		
	private DrawBack panel = new DrawBack();
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//gets the dimensions of the screen
	private JFrame frame = new JFrame(); 
	private Container c = frame.getContentPane();
	private WindowEvent close = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
	private File users = new File ("resources/Users.txt");
	private FileReader in; private FileWriter out;
	private BufferedReader readFile; private BufferedWriter writeFile;
	private Scanner scan;
	private Boolean usernameMatched = false;
	private String usernameInput, passwordInput, username, password;
	private JTextField usernameField = new JTextField();
	private JTextField passwordField = new JPasswordField();
	private Object[] message = {
	    "Username:", usernameField,
	    "Password:", passwordField
	};
	public MenuGUI(){
		
		/*try {
			in = new FileReader(users);
			readFile = new BufferedReader(in);
			out = new FileWriter(users, true);
			writeFile = new BufferedWriter(out);
			scan = new Scanner(users);

		//JOptionPane.showMessageDialog(null, message, "Login", JOptionPane.OK_OPTION);
		
		usernameInput = usernameField.getText();
		passwordInput = passwordField.getText();
		checkEmpty();
			if (scan.hasNextLine() == false){
				writeFile.write(usernameInput+"\n");
				writeFile.write(passwordInput+"\n");
			}else{
			
				while(scan.hasNext()){
					username = scan.nextLine();
					password = scan.nextLine();
					checkAccount();
				}
			
			if (usernameMatched == false){
				
					writeFile.write(usernameInput+"\n");
					writeFile.write(passwordInput+"\n");
				
			}
		}
			
		    if (usernameField.getText().equals("h") && passwordField.getText().equals("h")) {
		        System.out.println("Login successful");
		    } else {
		       // System.out.println("login failed");
		    }
		    writeFile.close();
			readFile.close();
			in.close();
			out.close();
			scan.close();
		} catch (FileNotFoundException e1) { 
			e1.printStackTrace();
		} catch (IOException e1) { 
			e1.printStackTrace();
		}*/
		int[][] xPoints = {{5,299,304,309,312,314,316,317,318,319,319,318,317,316,314,312,310,307,304,300,296,291,63,44},{4,232,245,252,256,258,257,251,243,235,4,8,},{62,293,305,313,317,318,316,311,306,300,295,6,38,54}};
		int[][] yPoints = {{4,3,5,8,11,14,17,20,23,29,33,41,45,47,50,53,55,57,59,61,62,63,63,31},{3,3,8,14,21,33,45,55,61,63,62,33},{3,3,8,16,25,34,47,55,59,62,63,62,40,19}};
		Polygon tut = new Polygon(xPoints[0], yPoints[0], xPoints[0].length);
		Polygon clear = new Polygon(xPoints[1], yPoints[1], xPoints[1].length);
		Polygon exit = new Polygon(xPoints[2], yPoints[2], xPoints[2].length);
		startButton.setBounds(102+165-200, 122+166-200+60, 195, 195);
		tutorialButton.setBounds(234+165-200, 115+166-200+60, 323, 67);
		exitButton.setBounds(234+165-200, 257+166-200+60, 323, 67);
		clearAccountsButton.setBounds(296+165-200, 186+166-200+60, 262, 67);
		//startButton.setText("Start");
		try {
			playImg = ImageIO.read(new File("resources\\button play.png"));
			tutImg = ImageIO.read(new File("resources\\button top.png"));
			exitImg = ImageIO.read(new File("resources\\button bottom.png"));
			clearAccImg = ImageIO.read(new File("resources\\button middle.png"));
			
			startButton.setIcon(new ImageIcon(playImg));
			tutorialButton.setIcon(new ImageIcon(tutImg));
			exitButton.setIcon(new ImageIcon(exitImg));
			clearAccountsButton.setIcon(new ImageIcon(clearAccImg));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		frame.addMouseListener(new MouseAdapter(){
			int buttonType;
			public void mousePressed(MouseEvent e){
				Point p = MouseInfo.getPointerInfo().getLocation();
				
				int x = (int) (p.getX() - frame.getLocationOnScreen().getX());//165
				int y = (int) (p.getY() - frame.getLocationOnScreen().getY());//166
				p = new Point(x,y);
				Point pTut = new Point(x-tutorialButton.getX(), y-tutorialButton.getY()-50+25);
				Point pClear = new Point(x-clearAccountsButton.getX(), y-clearAccountsButton.getY()-50+23);
				Point pExit = new Point(x-exitButton.getX(), y-exitButton.getY()-50+25);
				if(p.distance(startButton.getX() + startButton.getWidth()/2, startButton.getY() + startButton.getHeight()/2) <= startButton.getHeight()/2){
					startButton.setIcon(new ImageIcon(playImg.getScaledInstance(startButton.getWidth() - 10, startButton.getHeight() - 10, Image.SCALE_SMOOTH)));
					startButton.setBounds(startButton.getX()+5, startButton.getY()+5, startButton.getWidth() - 10, startButton.getHeight() - 10);
					buttonType = 1;
				}else if(tut.contains(pTut)){
					tutorialButton.setIcon(new ImageIcon(tutImg.getScaledInstance(tutorialButton.getWidth() - 10, tutorialButton.getHeight() - 10, Image.SCALE_SMOOTH)));
					tutorialButton.setBounds(tutorialButton.getX(), tutorialButton.getY()+5, tutorialButton.getWidth() - 10, tutorialButton.getHeight() - 10);
					buttonType = 2;
				}else if(exit.contains(pExit)){
					exitButton.setIcon(new ImageIcon(exitImg.getScaledInstance(exitButton.getWidth() - 10, exitButton.getHeight() - 10, Image.SCALE_SMOOTH)));
					exitButton.setBounds(exitButton.getX(), exitButton.getY()+5, exitButton.getWidth() - 10, exitButton.getHeight() - 10);
					buttonType = 4;
				}else if(clear.contains(pClear)){
					clearAccountsButton.setIcon(new ImageIcon(clearAccImg.getScaledInstance(clearAccountsButton.getWidth() - 10, clearAccountsButton.getHeight() - 10, Image.SCALE_SMOOTH)));
					clearAccountsButton.setBounds(clearAccountsButton.getX(), clearAccountsButton.getY()+5, clearAccountsButton.getWidth() - 10, clearAccountsButton.getHeight() - 10);
					buttonType = 3;
				}
			}
			
			public void mouseReleased(MouseEvent e){
				if(buttonType == 1){
					startButton.setIcon(new ImageIcon(playImg));
					startButton.setBounds(startButton.getX()-5, startButton.getY()-5, startButton.getWidth() + 10, startButton.getHeight() + 10);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//gets the dimensions of the screen
					gameFrame.setLocation(dim.width/2-404, dim.height/2 - 153);	//frame is at center of the screen
					gameFrame.getContentPane().add(game);
					gameFrame.pack();
					gameFrame.setVisible(true);
					game.setVisible(true);
					frame.setVisible(false);
				} else if (buttonType == 2){
					tutorialButton.setIcon(new ImageIcon(tutImg));
					tutorialButton.setBounds(tutorialButton.getX(), tutorialButton.getY()-5, tutorialButton.getWidth() + 10, tutorialButton.getHeight() + 10);
					TutorialGUI tutorial = new TutorialGUI();
					tutorial.getFrame().addWindowListener(new WindowAdapter(){
						public void windowClosing(WindowEvent e){
							frame.setVisible(true); 
						}
					});
					frame.setVisible(false);
				} else if (buttonType == 3){
					clearAccountsButton.setIcon(new ImageIcon(clearAccImg));
					clearAccountsButton.setBounds(clearAccountsButton.getX(), clearAccountsButton.getY()-5, clearAccountsButton.getWidth() + 10, clearAccountsButton.getHeight() + 10);
				} else if (buttonType == 4){
					exitButton.setIcon(new ImageIcon(exitImg));
					exitButton.setBounds(exitButton.getX(), exitButton.getY()-5, exitButton.getWidth() + 10, exitButton.getHeight() + 10);
					System.exit(0);
				}
			}
		});    
		
		
		panel.setLayout(null);
		panel.add(startButton); 
		panel.add(tutorialButton); 
		panel.add(previousAccountButton); 
		panel.add(exitButton);
		panel.add(clearAccountsButton);
		c.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Menu");
		frame.setSize(600,400);
		frame.setLocation(dim.width/2 - 125, dim.height/2 - 110);	//frame is at the center of the screen
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getGraphics().drawPolygon(tut);
		
		
	}
	class DrawBack extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("resources\\menu background.png")), 0, 0, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/*public Boolean checkEmpty(){
		if (usernameInput.equals("") || passwordInput.equals("")){
			//JOptionPane.showMessageDialog(null, "Please Fill ALL Textbox Fields.");
			//JOptionPane.showMessageDialog(null, message, "Login", JOptionPane.OK_OPTION, null); 
			usernameInput = usernameField.getText();
			passwordInput = passwordField.getText();
			checkEmpty();
		}
		return false;
	}
	public Boolean checkAccount(){
		
			if (username.equals(usernameField.getText())){
				usernameMatched = true;
				if(password.equals(passwordField.getText())){
					return true;
				}else{
					//JOptionPane.showMessageDialog(null, "Incorrect Password");
					//JOptionPane.showMessageDialog(null, message, "Login", JOptionPane.OK_OPTION, null); 
					checkAccount();
				}
			}
		
		return false;
	}*/
}

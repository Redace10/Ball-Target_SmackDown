import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		
		MenuGUI menu = new MenuGUI();
		
		MenuGUI.gameFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				MenuGUI.gameFrame.setVisible(true); 
			}
		});
	}
}



package chess;

import javax.swing.*;
import java.awt.*;

class Board{

	public static void main(String[] args){
		StartScreen screen = new StartScreen();
	}
	
	static class StartScreen{
		JFrame frame;
		JTextField name;
		JButton start;

		StartScreen(){
			frame = new JFrame();
			frame.setTitle("New Chess Game");
	      	frame.setSize(384,64);
	     	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setResizable(false);
	        name = new JTextField("Name");
	        frame.add(name);
	       	frame.setVisible(true);
		}	
	}	
		
}
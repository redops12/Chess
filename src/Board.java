package chess;

import javax.swing.*;
import java.awt.*;

class Board{

	public static void main(String[] args){
		StartScreen screen = new StartScreen();
	}
	
	static class StartScreen{
		JFrame frame;
		Game board;

		StartScreen(){
			frame = new JFrame();
			frame.setTitle("Chess");
	      	frame.setPreferredSize(new Dimension(1088,1088));
	     	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			board = new Game();
			frame.add(board);
			frame.pack();
	       	frame.setVisible(true);
		}
	}		
}
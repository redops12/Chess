package chess;

import javax.swing.*;
import java.awt.*;

class Board extends JFrame{
	Board board;

	public static void main(String[] args){
		StartScreen screen = new StartScreen();

	}


	static class StartScreen{
		JFrame frame;
		Game board;

		StartScreen(){
			frame = new JFrame();
			frame.setTitle("Chess");
	     	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			board = new Game();
			frame.add(board);
			frame.pack();
	       	frame.setVisible(true);
		}
	}		
}
package Chess;

import javax.swing.*;
import java.awt.*;


class Window extends JFrame{
	JFrame frame;
	Game board;
	Window window;
	JPanel panel;

	Window(String white_name, String black_name, Piece[][] startingBoard){		
		frame = new JFrame();
		board = new Game(white_name, black_name, startingBoard);
		panel = new JPanel();

		panel.add(board);
		frame.setTitle("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}	
}
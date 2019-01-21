package Chess;

import javax.swing.*;
import java.awt.*;


class Window extends JFrame{
	JFrame frame;
	Game board;
	Window window;
	JPanel panel;

	Window(Side side){		
		frame = new JFrame();
		board = new Game(side);
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
package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Game extends JPanel{
	JFrame frame;

	Game() {
		add(new visuals());
	}

	static class visuals extends JComponent{
		visuals() {
			setPreferredSize(new Dimension(1088,1088));
		}

		@Override
		public void paintComponent(Graphics g){
			super.paintComponents(g);
			drawBoard(g);
		}

		private static void drawBoard(Graphics g){
			g.setColor(Color.GREEN);
			g.fillRect(0,0,1088,1088);
		}
	}
}
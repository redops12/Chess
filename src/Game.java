package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Game extends JPanel{
	static final boolean BLACK_BOARD = false;
	static final boolean WHITE_BOARD = true;
	
	Game() {
		add(new visuals(WHITE_BOARD));
	}



	static class visuals extends JComponent{
		boolean boardOrient = WHITE_BOARD;

		visuals(boolean side) {
			setPreferredSize(new Dimension(576,576));
			boardOrient = side;
		}

		@Override
		public void paintComponent(Graphics g){
			super.paintComponents(g);
			drawBoard(g);
		}

		private void drawBoard(Graphics g){
			g.setColor(new Color(24,94,48));
			g.fillRect(0,0,576,576);
			for (int i = 0; i < 8; i ++){
				for (int j = 0; j < 8; j++){
					if (boardOrient == WHITE_BOARD){
						if ((i+j)%2 == 0){
							g.setColor(Color.WHITE);
						} else {
							g.setColor(Color.BLACK);
						}
					} else {
						if ((i+j)%2 == 0){
							g.setColor(Color.BLACK);
						} else {
							g.setColor(Color.WHITE);
						}
					}
					g.fillRect(i*64+32, j*64+32, 64, 64);
				}
			}
			
			g.setColor(Color.WHITE);	
			String[] letters = {"h","g","f","e","d","c","b","a"};
			if (boardOrient==WHITE_BOARD){
				for (int i = 0; i < 8; i++){
					g.drawString(letters[7-i], 32+i*64, 560);
					g.drawString("" + (8-i), 16, 48+i*64);
				}
			} else {
				for (int i = 0; i < 8; i++){
					g.drawString(letters[i], 32+i*64, 560);
					g.drawString("" + (i+1), 16, 48+i*64);
				}
			}
		}
	}
}
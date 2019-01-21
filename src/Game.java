package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class Game extends JComponent{
	Side boardOrient;
	Side opposingSide;
	Position selected = new Position(-1,-1);
	ArrayList<Position> moves = new ArrayList<Position>();
	Board board;

	MouseListener mouse = new MouseListener(){
		
		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getX() < 32 || e.getX() > 544 || e.getY() < 32 || e.getY() > 544) return;
	
			Position temp_clicked = new Position((e.getX()-32)/64,(e.getY()-32)/64);
			Position temp_selected = new Position(-1,-1);			
			if ((board.pieceAt(temp_clicked).empty() || board.pieceAt(temp_clicked).color == opposingSide) && !selected.equals(-1,-1)){
				for (Position i : moves){
					if (temp_clicked.equals(i)){
						board.move(selected,temp_clicked);
					}
				}
				moves.clear();
			} else if (board.pieceAt(temp_clicked).color==boardOrient && !selected.equals(temp_clicked)){
				temp_selected = temp_clicked;
			}
			selected.set(temp_selected);
			if (!selected.equals(-1,-1))
				moves = board.getMoves(selected);
			else {
				moves.clear();
			}
			repaint();			
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
	
		@Override
		public void mouseClicked(MouseEvent e) {
		}
	};
	
	public Game(Side side) {
		setPreferredSize(new Dimension(576,576));
		addMouseListener(mouse);
		boardOrient = side;
		if (boardOrient == Side.WHITE_SIDE){
			opposingSide = Side.BLACK_SIDE;
		} else {
			opposingSide = Side.WHITE_SIDE;
		}
		board = new Board(Board.defaultBoard, boardOrient);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		drawBoard(g);
		drawPieces(g);
	}

	private void drawBoard(Graphics g){
		g.setColor(new Color(24,94,48));
		g.fillRect(0,0,576,576);
		for (int i = 0; i < 8; i ++){
			for (int j = 0; j < 8; j++){
				if (boardOrient == Side.WHITE_SIDE){
					if ((i+j)%2 == 0){
						g.setColor(new Color(160,82,45));
					} else {
						g.setColor(new Color(210,180,140));
					}
				} else {
					if ((i+j)%2 == 0){
						g.setColor(new Color(210,180,140));
					} else {
						g.setColor(new Color(160,82,45));
					}
				}
				if (i == selected.x && j == selected.y){
					g.setColor(Color.YELLOW);
				}
				g.fillRect(i*64+32, j*64+32, 64, 64);
			}
		}

		g.setColor(new Color(135,206,250));
		for (Position i : moves){
			g.fillRect(i.x*64+36, i.y*64+36, 56, 56);
		}
		
		g.setColor(Color.WHITE);	
		String[] letters = {"h","g","f","e","d","c","b","a"};
		if (boardOrient==Side.WHITE_SIDE){
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

	private void drawPieces(Graphics g){
		for (int i = 0; i < 8; i ++){
			for (int j = 0; j < 8; j++){
				Piece temp = board.pieceAt(new Position(i,j)); 
				if (!temp.empty()){
					g.drawImage(temp.image,i*64+32,j*64+32,null);
				}
			}
		}
	}
}

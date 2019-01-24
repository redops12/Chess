package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.io.File;
import java.awt.Image;

class Game extends JComponent{
	//which side the game is being played by/against
	Side boardOrient;
	Side opposingSide;

	//selected piece position
	Position selected = new Position(-1,-1);

	//list of all the moves available for the selected piece
	ArrayList<Position> moves = new ArrayList<Position>();
	
	//bulk of the game logic is in this class
	Board board;

	//piece images
	Image blackKing;
	Image blackQueen;
	Image blackRook;
	Image blackKnight;
	Image blackBishop;
	Image blackPawn;
	Image whiteKing;
	Image whiteQueen;
	Image whiteRook;
	Image whiteKnight;
	Image whiteBishop;
	Image whitePawn;

	//mouse processing and selected piece logic
	MouseListener mouse = new MouseListener(){
		
		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getX() <= 32 || e.getX() >= 544 || e.getY() <= 32 || e.getY() >= 544) return;
	
			Position temp_clicked = new Position((e.getX()-32)/64,(e.getY()-32)/64);
			Position temp_selected = new Position(-1,-1);
			if ((board.pieceAt(temp_clicked).empty() || board.pieceAt(temp_clicked).color == opposingSide) && !selected.equals(-1,-1)){
				for (Position i : moves){
					if (temp_clicked.equals(i)){
						board.move(selected,temp_clicked);
						Side temp_side = opposingSide;
						opposingSide = boardOrient;
						boardOrient = temp_side;
						board.setOrient(boardOrient);
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
	
	//constructor, mostly just sets sides and imports the images
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

		try{
			blackKing = ImageIO.read(new File("resources/BlackKing.png"));
			blackQueen = ImageIO.read(new File("resources/BlackQueen.png"));
			blackBishop = ImageIO.read(new File("resources/BlackBishop.png"));
			blackKnight = ImageIO.read(new File("resources/BlackKnight.png"));
			blackRook = ImageIO.read(new File("resources/BlackRook.png"));
			blackPawn = ImageIO.read(new File("resources/BlackPawn.png"));
			whiteKing = ImageIO.read(new File("resources/WhiteKing.png"));
			whiteQueen = ImageIO.read(new File("resources/WhiteQueen.png"));
			whiteBishop = ImageIO.read(new File("resources/WhiteBishop.png"));
			whiteKnight = ImageIO.read(new File("resources/WhiteKnight.png"));
			whiteRook = ImageIO.read(new File("resources/WhiteRook.png"));
			whitePawn = ImageIO.read(new File("resources/WhitePawn.png"));
		} catch (Exception e){
			
		}
	}

	//graphics stuff
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		drawBoard(g);
		drawPieces(g);
	}

	//sets up the background, including the border, squares, numbers on the side, as well as the selected square and available moves
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

	//displays all of the pieces on the board
	private void drawPieces(Graphics g){
		for (int i = 0; i < 8; i ++){
			for (int j = 0; j < 8; j++){
				Piece temp = board.pieceAt(new Position(i,j));
				Image image;
				if (!temp.empty()){
					if (temp.color == Side.BLACK_SIDE){
						switch (temp.pieceType){
							case KING:
								image = blackKing;
								break;
							case QUEEN:
								image = blackQueen;
								break;
							case BISHOP:
								image = blackBishop;
								break;
							case KNIGHT:
								image = blackKnight;
								break;
							case ROOK:
								image = blackRook;
								break;
							case PAWN:
								image = blackPawn;
								break;
							default:
								image = blackPawn;
						}
					} else {
						switch (temp.pieceType){
							case KING:
								image = whiteKing;
								break;
							case QUEEN:
								image = whiteQueen;
								break;
							case BISHOP:
								image = whiteBishop;
								break;
							case KNIGHT:
								image = whiteKnight;
								break;
							case ROOK:
								image = whiteRook;
								break;
							case PAWN:
								image = whitePawn;
								break;
							default: 
								image = blackPawn;
						}
					}
					g.drawImage(image,i*64+32,j*64+32,null);
				}
			}
		}
	}
}

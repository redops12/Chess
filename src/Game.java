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
import java.lang.Thread;

class Game extends JComponent{
	//names for the sides
	String mWhiteName;
	String mBlackName;

	//which side the game is being currently played by/against
	Side boardOrient = Side.WHITE_SIDE;
	Side opposingSide = Side.BLACK_SIDE;

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
			if (e.getX() <= 128 || e.getX() >= 640 || e.getY() <= 32 || e.getY() >= 544) return;
	
			Position tempClicked = new Position((e.getX()-128)/64,(e.getY()-32)/64);
			Position tempSelected = new Position(-1,-1);
			//decides whether to change selected square or unselect
			if ((board.pieceAt(tempClicked).empty() || board.pieceAt(tempClicked).color == opposingSide) && !selected.equals(-1,-1)){
				for (Position i : moves){
					if (tempClicked.equals(i)){
						board.move(selected,tempClicked);
						Side tempSide = opposingSide;
						opposingSide = boardOrient;
						boardOrient = tempSide;
						board.setOrient(boardOrient);
						try {
							Thread.sleep(250);
						} catch (Exception exc){}
					}
				}
				moves.clear();
			} else if (board.pieceAt(tempClicked).color==boardOrient && !selected.equals(tempClicked)){
				tempSelected = tempClicked;
			}

			//handels moves for selecte piece
			selected.set(tempSelected);
			if (!selected.equals(-1,-1))
				moves = board.getMoves(selected);
			else {
				moves.clear();
			}
			if (board.noMoves()){
				JFrame endScreen = new JFrame("Game Over");
				endScreen.setSize(256,256);
				endScreen.setBackground(Color.YELLOW);
				JLabel label;
				if (!board.inCheck()){
					label = new JLabel("Stalemate"); 
				} else if (boardOrient == Side.WHITE_SIDE){
					label = new JLabel("Black Wins!");
				} else {
					label = new JLabel("White Wins!");
				}
				endScreen.add(label);
				endScreen.setVisible(true);
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
	public Game(String whiteName, String blackName, Piece[][] startBoard) {
		setPreferredSize(new Dimension(672,576));
		addMouseListener(mouse);
		board = new Board(startBoard, Side.WHITE_SIDE);
		if (whiteName.length() > 10){
			mWhiteName = whiteName.substring(0,9);
		} else {
			mWhiteName = whiteName;
		}
		if (blackName.length() > 10){
			mBlackName = blackName.substring(0,9);
		} else {
			mBlackName = blackName;
		}
		//import piece visuals
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
		g.fillRect(0,0,672,576);
		for (int i = 0; i < 8; i ++){
			for (int j = 0; j < 8; j++){
				if ((i+j)%2 == 0){
					g.setColor(new Color(210,180,140));
				} else {
					g.setColor(new Color(160,82,45));
				}
				if (i == selected.x && j == selected.y){
					g.setColor(Color.YELLOW);
				}
				g.fillRect(i*64+128, j*64+32, 64, 64);
			}
		}

		//highlight move just played
		g.setColor(Color.YELLOW);
		if (!board.previousStartPos.equals(-1,-1))
			g.fillRect((7-board.previousStartPos.x)*64+132, (7-board.previousStartPos.y)*64+36, 56, 56);
		if (!board.previousEndPos.equals(-1,-1))
			g.fillRect((7-board.previousEndPos.x)*64+132, (7-board.previousEndPos.y)*64+36, 56, 56);



		//highlights available moves
		g.setColor(new Color(135,206,250));
		for (Position i : moves){
			g.fillRect(i.x*64+132, i.y*64+36, 56, 56);
		}
		
		//coordinates on the side
		g.setColor(Color.WHITE);	
		String[] letters = {"h","g","f","e","d","c","b","a"};
		if (boardOrient==Side.WHITE_SIDE){
			for (int i = 0; i < 8; i++){
				g.drawString(letters[7-i], 128+i*64, 560);
				g.drawString("" + (8-i), 112, 48+i*64);
			}
		} else {
			for (int i = 0; i < 8; i++){
				g.drawString(letters[i], 128+i*64, 560);
				g.drawString("" + (i+1), 112, 48+i*64);
			}
		}

		//names
		g.setFont(new Font("TimesRoman", Font.BOLD, 12)); 
		if (boardOrient==Side.WHITE_SIDE){
			g.drawString(mWhiteName, 16, 568);
			g.drawString(mBlackName, 16, 16);
		} else {
			g.drawString(mWhiteName, 16, 16);
			g.drawString(mBlackName, 16, 568);
		}

		ArrayList<Piece> whiteTaken = board.getPiecesTaken(Side.WHITE_SIDE);
		ArrayList<Piece> blackTaken = board.getPiecesTaken(Side.BLACK_SIDE);
		if (boardOrient == Side.WHITE_SIDE){
			for (int i = 0; i < 16; i++){
				if (i < whiteTaken.size()){
					g.drawImage(getImage(whiteTaken.get(i)), 16, 528-i*16, 16, 16, null);
				} else {
					break;
				}
			}
			for (int i = 0; i < 16; i++){
				if (i < blackTaken.size()){
					g.drawImage(getImage(blackTaken.get(i)), 16, 48+i*16, 16, 16, null);
				} else {
					break;
				}
			}
		} else {
			for (int i = 0; i < 16; i++){
				if (i < whiteTaken.size()){
					g.drawImage(getImage(whiteTaken.get(i)), 16, 48+i*16, 16, 16, null);
				} else {
					break;
				}
			}
			for (int i = 0; i < 16; i++){
				if (i < blackTaken.size()){
					g.drawImage(getImage(blackTaken.get(i)), 16, 528-i*16, 16, 16, null);
				} else {
					break;
				}
			}
		}
	}

	//returns the image that corresponds to a specific picece type and color 
	Image getImage(Piece piece){
		Image image = null;
		if (!piece.empty()){
			if (piece.color == Side.BLACK_SIDE){
				switch (piece.pieceType){
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
				switch (piece.pieceType){
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
		}
		return image;
	}

	//displays all of the pieces on the board
	private void drawPieces(Graphics g){
		for (int i = 0; i < 8; i ++){
			for (int j = 0; j < 8; j++){
				Piece temp = board.pieceAt(new Position(i,j));
				g.drawImage(getImage(temp),i*64+128,j*64+32,null);
			}
		}
	}
}

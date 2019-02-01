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

class BoardMaker extends JFrame{
    Rectangle[] whiteRects = {new Rectangle(0,16,64,64),new Rectangle(0,80,64,64),new Rectangle(0,144,64,64),new Rectangle(0,208,64,64),new Rectangle(0,272,64,64),new Rectangle(0,336,64,64), new Rectangle(0,400,64,64)};
    Rectangle[] blackRects = {new Rectangle(64,16,64,64),new Rectangle(64,80,64,64),new Rectangle(64,144,64,64),new Rectangle(64,208,64,64),new Rectangle(64,272,64,64),new Rectangle(64,336,64,64), new Rectangle(64,400,64,64)};
    PieceType[] pieceList = {PieceType.PAWN,PieceType.BISHOP,PieceType.KNIGHT,PieceType.ROOK,PieceType.QUEEN,PieceType.KING};

	//selected piece for placing
    Piece selected = new Piece(PieceType.PAWN, Side.WHITE_SIDE);
    Rectangle selectedRect = whiteRects[0];

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
    
    Piece[][] board = {
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
    };

	//mouse processing and selected piece logic
	MouseListener mouse = new MouseListener(){
		
		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            for (int i = 0; i<6; i++){
                if (whiteRects[i].contains(x,y)){
                    selected = new Piece(pieceList[i],Side.WHITE_SIDE);
                    selectedRect = whiteRects[i];
                }
                if (blackRects[i].contains(x,y)){
                    selected = new Piece(pieceList[i],Side.BLACK_SIDE);
                    selectedRect = blackRects[i];
                }
            }

            if(x>160 && x<672 && y>32 && y<544){
                board[7-(x-160)/64][7-(y-32)/64] = selected;
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
    
    JComponent component = new JComponent(){
        //graphics stuff
        @Override
        public void paintComponent(Graphics g){
            super.paintComponents(g);
            drawBoard(g);
            drawPieces(g);
        }
    };
	
	public BoardMaker() {
        setTitle("Board Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		component.setPreferredSize(new Dimension(704,576));
        component.addMouseListener(mouse);
        add(component);
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
        pack();
        setVisible(true);
	}

	//sets up the background, including the border, squares, numbers on the side, as well as the selected square and available moves
	private void drawBoard(Graphics g){
		g.setColor(new Color(24,94,48));
		g.fillRect(0,0,704,576);
		for (int i = 0; i < 8; i ++){
			for (int j = 0; j < 8; j++){
				if ((i+j)%2 == 0){
					g.setColor(new Color(160,82,45));
				} else {
					g.setColor(new Color(210,180,140));
				}
				g.fillRect(i*64+160, j*64+32, 64, 64);
			}
		}
		
		//coordinates on the side
		g.setColor(Color.WHITE);	
		String[] letters = {"h","g","f","e","d","c","b","a"};
        for (int i = 0; i < 8; i++){
            g.drawString(letters[7-i], 160+i*64, 560);
            g.drawString("" + (8-i), 144, 48+i*64);
        }

        for (int i = 0; i<6; i++){
            g.drawImage(getImage(new Piece(pieceList[i], Side.WHITE_SIDE)),(int) whiteRects[i].getX(),(int) whiteRects[i].getY(),null);
            g.drawImage(getImage(new Piece(pieceList[i], Side.BLACK_SIDE)),(int) blackRects[i].getX(),(int) blackRects[i].getY(),null);
        }

        g.setColor(Color.YELLOW);
        g.drawRect(selectedRect.x,selectedRect.y,64,64);
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
				Piece temp = board[7-i][7-j];
				g.drawImage(getImage(temp),i*64+160,j*64+32,null);
			}
		}
    }
}
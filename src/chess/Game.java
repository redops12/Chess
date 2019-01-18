package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;

class Game extends JPanel{
	Piece[][] board = 
	{
		{new Piece(PieceType.ROOK, Pos.a.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.KNIGHT, Pos.b.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.BISHOP, Pos.c.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.QUEEN, Pos.d.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.KING, Pos.e.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.BISHOP, Pos.f.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.KNIGHT, Pos.g.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.ROOK, Pos.h.pos, 1, Side.WHITE_SIDE)},
		{new Piece(PieceType.PAWN, Pos.a.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Pos.b.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Pos.c.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Pos.d.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Pos.e.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Pos.f.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Pos.g.pos, 1, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Pos.g.pos, 1, Side.WHITE_SIDE)},
		{null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null},
		{new Piece(PieceType.ROOK, Pos.a.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.KNIGHT, Pos.b.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.BISHOP, Pos.c.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.QUEEN, Pos.d.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.KING, Pos.e.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.BISHOP, Pos.f.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.KNIGHT, Pos.g.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.ROOK, Pos.h.pos, 1, Side.BLACK_SIDE)},
		{new Piece(PieceType.PAWN, Pos.a.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Pos.b.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Pos.c.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Pos.d.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Pos.e.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Pos.f.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Pos.g.pos, 1, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Pos.g.pos, 1, Side.BLACK_SIDE)}
	};

	
	public Game() {
		add(new visuals(Side.WHITE_SIDE));
	}



	static class visuals extends JComponent{
		Side boardOrient;
		Image w_king;
		Image w_queen;
		Image w_bishop;
		Image w_knight;
		Image w_rook;
		Image w_pawn;
		Image b_king;
		Image b_queen;
		Image b_bishop;
		Image b_knight;
		Image b_rook;
		Image b_pawn;

		visuals(Side side) {
			setPreferredSize(new Dimension(576,576));
			boardOrient = side;
			try {
				w_king = ImageIO.read(new File("WhiteKing.png"));
				w_queen = ImageIO.read(new File("WhiteQueen.png"));
				w_bishop = ImageIO.read(new File("WhiteBishop.png"));
				w_knight = ImageIO.read(new File("WhiteKnight.png"));
				w_rook = ImageIO.read(new File("WhiteRook.png"));
				w_pawn = ImageIO.read(new File("WhitePawn.png"));
				b_king = ImageIO.read(new File("BlackKing.png"));
				b_queen = ImageIO.read(new File("BlackQueen.png"));
				b_bishop = ImageIO.read(new File("BlackBishop.png"));
				b_knight = ImageIO.read(new File("BlackKnight.png"));
				b_rook = ImageIO.read(new File("BlackRook.png"));
				b_pawn = ImageIO.read(new File("BlackPawn.png"));
			} catch (Exception e){
				
			}
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
					if (boardOrient == Side.WHITE_SIDE){
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
					Image temp_image;
					g.drawImage(temp_image);
				}
			}
		}
	}
}
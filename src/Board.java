package Chess;

import java.util.ArrayList;

class Board{
    static final Piece[][] defaultBoard = 
	{
		{new Piece(PieceType.ROOK, Side.WHITE_SIDE), new Piece(PieceType.KNIGHT, Side.WHITE_SIDE), new Piece(PieceType.BISHOP, Side.WHITE_SIDE), new Piece(PieceType.QUEEN, Side.WHITE_SIDE), new Piece(PieceType.KING, Side.WHITE_SIDE), new Piece(PieceType.BISHOP, Side.WHITE_SIDE), new Piece(PieceType.KNIGHT, Side.WHITE_SIDE), new Piece(PieceType.ROOK, Side.WHITE_SIDE)},
		{new Piece(PieceType.PAWN, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Side.WHITE_SIDE), new Piece(PieceType.PAWN, Side.WHITE_SIDE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE),new Piece(PieceType.EMPTY, Side.NONE)},
		{new Piece(PieceType.PAWN, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Side.BLACK_SIDE), new Piece(PieceType.PAWN, Side.BLACK_SIDE)},
		{new Piece(PieceType.ROOK, Side.BLACK_SIDE), new Piece(PieceType.KNIGHT, Side.BLACK_SIDE), new Piece(PieceType.BISHOP, Side.BLACK_SIDE), new Piece(PieceType.QUEEN, Side.BLACK_SIDE), new Piece(PieceType.KING, Side.BLACK_SIDE), new Piece(PieceType.BISHOP, Side.BLACK_SIDE), new Piece(PieceType.KNIGHT, Side.BLACK_SIDE), new Piece(PieceType.ROOK, Side.BLACK_SIDE)}
    };

    private Piece[][] board;
    private Side orient;
    private Side opposingSide;


    Board(Piece[][] boardType, Side orient){
        board = boardType;
        this.orient = orient;
        if (orient == Side.WHITE_SIDE){
			opposingSide = Side.BLACK_SIDE;
		} else {
			opposingSide = Side.WHITE_SIDE;
		}
    }

    void setOrient(Side side){
        orient = side;
    }

    Piece pieceAt(Position pos){
        if (orient == Side.WHITE_SIDE){
            return board[7-pos.y][7-pos.x];
        }
        return board[pos.y][pos.x];
    }

    void move(Position cur_pos, Position end_pos){
        if (orient == Side.WHITE_SIDE){
            board[7-end_pos.y][7-end_pos.x] = pieceAt(cur_pos);
            board[7-cur_pos.y][7-cur_pos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
        } else {
            board[end_pos.y][end_pos.x] = pieceAt(cur_pos);
            board[cur_pos.y][cur_pos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
        }
        board[end_pos.y][end_pos.x].moved = true;
    }

    Position translate(Position pos,Position translation){
		return new Position(pos.x + translation.x, pos.y + translation.y);
	}

	boolean valid(Position pos){
		if (pos.x >= 0 && pos.x <= 7 && pos.y >= 0 && pos.y <= 7) {
			if (pieceAt(pos).empty()){
				return true;
			} else if (pieceAt(pos).color == opposingSide){
				return true;
			}
		}
		return false;
	}

	Position[] translations = {new Position(1,0),new Position(1,1),new Position(0,1),new Position(-1,1),new Position(-1,0),new Position(-1,-1),new Position(0,-1),new Position(1,-1)};
	Position[] knight_translations = {new Position(2,1),new Position(1,2),new Position(-2,1),new Position(1,-2),new Position(-1,-2),new Position(-2,-1),new Position(2,-1),new Position(-1,2)};
	ArrayList<Position> getMoves(Position pos){
		ArrayList<Position> moves = new ArrayList<Position>();
		Piece temp_piece = pieceAt(pos);
		Position temp_move;
        switch(temp_piece.pieceType){
            case KING:
				for (Position i : translations){
					temp_move = translate(pos,i);
					if (valid(temp_move)){
						moves.add(temp_move);
					}	
				}
                break;
			case QUEEN:
				for (Position i : translations){
					temp_move = pos;
					boolean valid = true;
					while (valid){
						temp_move = translate(temp_move,i);
						valid = valid(temp_move);
						if (valid){
							moves.add(temp_move);
							if (pieceAt(temp_move).color == opposingSide)
								valid = false;
						}

					}
				}
                break;
            case BISHOP:
				for (int index = 1; index<translations.length;index+=2){
					Position i = translations[index];
					temp_move = pos;
					boolean valid = true;
					while (valid){
						temp_move = translate(temp_move,i);
						valid = valid(temp_move);
						if (valid){
							moves.add(temp_move);
							if (pieceAt(temp_move).color == opposingSide)
								valid = false;
						}

					}
				}
                break;
            case KNIGHT:
				for (Position i : knight_translations){
					temp_move = translate(pos,i);
					if (valid(temp_move)){
						moves.add(temp_move);
					}	
				}
                break;
            case ROOK:
				for (int index = 0; index<translations.length;index+=2){
					Position i = translations[index];
					temp_move = pos;
					boolean valid = true;
					while (valid){
						temp_move = translate(temp_move,i);
						valid = valid(temp_move);
						if (valid){
							moves.add(temp_move);
							if (pieceAt(temp_move).color == opposingSide)
								valid = false;
						}

					}
				}
                break;
			case PAWN:
				if (!temp_piece.moved){
					temp_move = translate(pos, new Position(0,-2));
					if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && pieceAt(temp_move).empty()){
						moves.add(temp_move);
					}
				}
				temp_move = translate(pos, new Position(0,-1));
				if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && pieceAt(temp_move).empty())
					moves.add(temp_move);
					
				temp_move = translate(pos, new Position(-1,-1));
				if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && pieceAt(temp_move).color == opposingSide)
					moves.add(temp_move);
				temp_move = translate(pos, new Position(1,-1));
				if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && pieceAt(temp_move).color == opposingSide)
					moves.add(temp_move);
				}
        return moves; 
	}
}
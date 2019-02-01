package Chess;

import java.util.ArrayList;

class Board{
    //to be moved to alternate files
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
    public Position previousStartPos = new Position(-1,-1);
    public Position previousEndPos = new Position(-1,-1);
    private ArrayList<Piece> whiteTakenPieces = new ArrayList<Piece>();
    private ArrayList<Piece> blackTakenPieces = new ArrayList<Piece>();

    //constructor
    Board(Piece[][] boardType, Side orient){
        board = boardType;
        this.orient = orient;
        if (orient == Side.WHITE_SIDE){
			opposingSide = Side.BLACK_SIDE;
		} else {
			opposingSide = Side.WHITE_SIDE;
		}
    }

    //sets which side the board is being played by
    void setOrient(Side side){
        orient = side;
        if (orient == Side.WHITE_SIDE){
			opposingSide = Side.BLACK_SIDE;
		} else {
			opposingSide = Side.WHITE_SIDE;
		}
    }

    //returns the pieces taken by a side
    ArrayList<Piece> getPiecesTaken(Side color){
        if (color == Side.WHITE_SIDE){
            return whiteTakenPieces;
        } 
        return blackTakenPieces;
    }

    //returns the piece at a specific position on the board
    Piece pieceAt(Position pos){
        if (orient == Side.WHITE_SIDE){
            return board[7-pos.y][7-pos.x];
        }
        return board[pos.y][pos.x];
    }

    //makes a copy without references
    Board copy(){
        Board newBoard;
        Piece[][] temp_board = new Piece[8][8];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                temp_board[i][j] = new Piece(board[i][j]);
            }
        }
        newBoard = new Board(temp_board,orient);
        return newBoard;
    }

    void move(Position cur_pos, Position end_pos){
        //Castling rooks
        if (pieceAt(cur_pos).pieceType == PieceType.KING && Math.abs(cur_pos.x-end_pos.x) == 2){
            Position rook_pos;
            Position rook_end_pos;
            if (end_pos.x>3){
                rook_pos = new Position(7,7); 
                rook_end_pos = new Position(end_pos.x-1,7); 
            } 
            else {
                rook_pos = new Position(0, 7);
                rook_end_pos = new Position(end_pos.x+1,7); 
            }

            if (orient == Side.WHITE_SIDE){
                board[7-rook_end_pos.y][7-rook_end_pos.x] = pieceAt(rook_pos);
                board[7-rook_pos.y][7-rook_pos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
                board[7-rook_end_pos.y][7-rook_end_pos.x].moved = true;
            } else {
                board[rook_end_pos.y][rook_end_pos.x] = pieceAt(rook_pos);
                board[rook_pos.y][rook_pos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
                board[rook_end_pos.y][rook_end_pos.x].moved = true;
            }
        }

        //adds taken pieces to taken list
        if (!pieceAt(end_pos).empty()){
            if (orient == Side.WHITE_SIDE){
                whiteTakenPieces.add(pieceAt(end_pos));
            } else {
                blackTakenPieces.add(pieceAt(end_pos));
            }
        }

        //replaces piece at end position with piece highlighted
        if (orient == Side.WHITE_SIDE){
            board[7-end_pos.y][7-end_pos.x] = pieceAt(cur_pos);
            board[7-cur_pos.y][7-cur_pos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
            board[7-end_pos.y][7-end_pos.x].moved = true;
        } else {
            board[end_pos.y][end_pos.x] = pieceAt(cur_pos);
            board[cur_pos.y][cur_pos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
            board[end_pos.y][end_pos.x].moved = true;
        }

        //pawn promotion
        if (orient == Side.WHITE_SIDE){
            if (end_pos.y==0 && board[7-end_pos.y][7-end_pos.x].pieceType == PieceType.PAWN){
                board[7-end_pos.y][7-end_pos.x] = new Piece(PieceType.QUEEN,orient);
            }
        } else {
            if (end_pos.y==0 && board[end_pos.y][end_pos.x].pieceType == PieceType.PAWN){
                board[end_pos.y][end_pos.x] = new Piece(PieceType.QUEEN,orient);
            }
        }

        //passant move
        if (!previousEndPos.equals(-1, -1) && pieceAt(end_pos).pieceType == PieceType.PAWN && pieceAt(new Position(7-previousEndPos.x,7-previousEndPos.y)).pieceType == PieceType.PAWN && pieceAt(new Position(7-previousEndPos.x,7-previousEndPos.y)).color == opposingSide && end_pos.x == 7-previousEndPos.x && end_pos.y == 6-previousEndPos.y){
            if (orient == Side.WHITE_SIDE){
                whiteTakenPieces.add(pieceAt(previousEndPos));
                board[previousEndPos.y][previousEndPos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
            } else {
                blackTakenPieces.add(pieceAt(previousEndPos));
                board[7-previousEndPos.y][7-previousEndPos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
            }
        }
            
        previousEndPos = end_pos.copy();
        previousStartPos = cur_pos.copy();
    }

    //used to apply a move to a position
    Position translate(Position pos,Position translation){
		return new Position(pos.x + translation.x, pos.y + translation.y);
	}

    //checks if move is off board, or if there is already a piece there
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
    
    //returns if the king is in check in this position
    boolean inCheck(){
        Position kingPos = new Position(-1,-1);
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                if (board[i][j].pieceType == PieceType.KING && board[i][j].color == orient){
                    if (orient == Side.BLACK_SIDE){
                        kingPos = new Position(j, i);
                    } else {
                        kingPos = new Position(7-j,7-i);
                    }
                }
            }
        }
        
        Position temp_move = new Position(0,0);
        for (Position i : translations){
            temp_move = translate(kingPos,i);
            if (valid(temp_move) && pieceAt(temp_move).pieceType == PieceType.KING && pieceAt(temp_move).color == opposingSide){
                return true;
            }	
        }
    
        for (Position i : translations){
            temp_move = kingPos;
            do {
                temp_move = translate(temp_move,i);
                if (valid(temp_move) && pieceAt(temp_move).pieceType == PieceType.QUEEN && pieceAt(temp_move).color == opposingSide){
                    return true;
                }
            } while (valid(temp_move) && pieceAt(temp_move).empty());
        }
    
        for (int index = 1; index<translations.length;index+=2){
            Position i = translations[index];
            temp_move = kingPos;
            do {
                temp_move = translate(temp_move,i);
                if (valid(temp_move) && pieceAt(temp_move).pieceType == PieceType.BISHOP && pieceAt(temp_move).color == opposingSide){
                    return true;
                }
            } while (valid(temp_move) && pieceAt(temp_move).empty());
        }
    
        for (Position i : knight_translations){
            temp_move = translate(kingPos,i);
            if (valid(temp_move) && pieceAt(temp_move).pieceType == PieceType.KNIGHT && pieceAt(temp_move).color == opposingSide){
                return true;
            }	
        }
    
        for (int index = 0; index<translations.length;index+=2){
            Position i = translations[index];
            temp_move = kingPos;
            do {
                temp_move = translate(temp_move,i);
                if (valid(temp_move) && pieceAt(temp_move).pieceType == PieceType.ROOK && pieceAt(temp_move).color == opposingSide){
                    return true;
                }
            }while (valid(temp_move) && pieceAt(temp_move).empty());
        }
            
        temp_move = translate(kingPos, new Position(-1,-1));
        if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && pieceAt(temp_move).color == opposingSide){
            return true;
        }
        temp_move = translate(kingPos, new Position(1,-1));
        if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && pieceAt(temp_move).color == opposingSide){
            return true;
        }
        return false;
    }

    boolean noMoves(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (pieceAt(new Position(i, j)).color == orient){
                    if (getMoves(new Position(i,j)).size() > 0){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //switch to find valid moves for all pieces
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
                if (!temp_piece.moved){
                    if (orient == Side.BLACK_SIDE){
                        if (!pieceAt(new Position(7,7)).moved && pieceAt(new Position(6,7)).empty() && pieceAt(new Position(5,7)).empty()){
                            moves.add(new Position(6,7));
                        }
                        if (!pieceAt(new Position(0,7)).moved && pieceAt(new Position(1,7)).empty() && pieceAt(new Position(2,7)).empty() && pieceAt(new Position(3,7)).empty()){
                            moves.add(new Position(2,7));
                        }
                    } else {
                        if (!pieceAt(new Position(7,7)).moved && pieceAt(new Position(4,7)).empty() && pieceAt(new Position(5,7)).empty() && pieceAt(new Position(6,7)).empty()){
                            moves.add(new Position(5,7));
                        }
                        if (!pieceAt(new Position(0,7)).moved && pieceAt(new Position(1,7)).empty() && pieceAt(new Position(2,7)).empty()){
                            moves.add(new Position(1,7));
                        }
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
					if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && pieceAt(temp_move).empty() && pieceAt(translate(pos,new Position(0,-1))).empty()){
                        moves.add(temp_move);
					}
				}
				temp_move = translate(pos, new Position(0,-1));
				if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && pieceAt(temp_move).empty())
					moves.add(temp_move);
					
                temp_move = translate(pos, new Position(-1,-1));
                Position passant = translate(pos, new Position(-1,0));
				if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && (pieceAt(temp_move).color == opposingSide || pieceAt(passant).color == opposingSide))
					moves.add(temp_move);
                temp_move = translate(pos, new Position(1,-1));
                passant = translate(pos, new Position(1,0));
				if (temp_move.x >= 0 && temp_move.x <= 7 && temp_move.y >= 0 && temp_move.y <= 7 && (pieceAt(temp_move).color == opposingSide || pieceAt(passant).color == opposingSide))
					moves.add(temp_move);
        }
        for (int i = 0; i < moves.size(); i++){
            Board temp = copy();
            temp.move(pos,moves.get(i));
            if (temp.inCheck()){
                moves.remove(i);
                i--;
            }
        }
        return moves; 
	}
}
package Chess;

import java.util.ArrayList;

class Board{
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
    
    Side getOrient(){
        return orient;
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
        Piece[][] tempBoard = new Piece[8][8];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                tempBoard[i][j] = new Piece(board[i][j]);
            }
        }
        newBoard = new Board(tempBoard,orient);
        return newBoard;
    }

    void move(Position curPos, Position endPos){
        //Castling rooks
        if (pieceAt(curPos).pieceType == PieceType.KING && Math.abs(curPos.x-endPos.x) == 2){
            Position rookPos;
            Position rookEndPos;
            if (endPos.x>3){
                rookPos = new Position(7,7); 
                rookEndPos = new Position(endPos.x-1,7); 
            } 
            else {
                rookPos = new Position(0, 7);
                rookEndPos = new Position(endPos.x+1,7); 
            }

            if (orient == Side.WHITE_SIDE){
                board[7-rookEndPos.y][7-rookEndPos.x] = pieceAt(rookPos);
                board[7-rookPos.y][7-rookPos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
                board[7-rookEndPos.y][7-rookEndPos.x].moved = true;
            } else {
                board[rookEndPos.y][rookEndPos.x] = pieceAt(rookPos);
                board[rookPos.y][rookPos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
                board[rookEndPos.y][rookEndPos.x].moved = true;
            }
        }

        //adds taken pieces to taken list
        if (!pieceAt(endPos).empty()){
            if (orient == Side.WHITE_SIDE){
                whiteTakenPieces.add(pieceAt(endPos));
            } else {
                blackTakenPieces.add(pieceAt(endPos));
            }
        }

        //replaces piece at end position with piece highlighted
        if (orient == Side.WHITE_SIDE){
            board[7-endPos.y][7-endPos.x] = pieceAt(curPos);
            board[7-curPos.y][7-curPos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
            board[7-endPos.y][7-endPos.x].moved = true;
        } else {
            board[endPos.y][endPos.x] = pieceAt(curPos);
            board[curPos.y][curPos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
            board[endPos.y][endPos.x].moved = true;
        }

        //pawn promotion
        if (orient == Side.WHITE_SIDE){
            if (endPos.y==0 && board[7-endPos.y][7-endPos.x].pieceType == PieceType.PAWN){
                board[7-endPos.y][7-endPos.x] = new Piece(PieceType.QUEEN,orient);
            }
        } else {
            if (endPos.y==0 && board[endPos.y][endPos.x].pieceType == PieceType.PAWN){
                board[endPos.y][endPos.x] = new Piece(PieceType.QUEEN,orient);
            }
        }

        //passant move
        if (!previousEndPos.equals(-1, -1) && pieceAt(endPos).pieceType == PieceType.PAWN && pieceAt(new Position(7-previousEndPos.x,7-previousEndPos.y)).pieceType == PieceType.PAWN && pieceAt(new Position(7-previousEndPos.x,7-previousEndPos.y)).color == opposingSide && endPos.x == 7-previousEndPos.x && endPos.y == 6-previousEndPos.y){
            if (orient == Side.WHITE_SIDE){
                whiteTakenPieces.add(pieceAt(previousEndPos));
                board[previousEndPos.y][previousEndPos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
            } else {
                blackTakenPieces.add(pieceAt(previousEndPos));
                board[7-previousEndPos.y][7-previousEndPos.x] = new Piece(PieceType.EMPTY,Side.NONE); 
            }
        }
            
        previousEndPos = endPos.copy();
        previousStartPos = curPos.copy();
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
	Position[] knightTranslations = {new Position(2,1),new Position(1,2),new Position(-2,1),new Position(1,-2),new Position(-1,-2),new Position(-2,-1),new Position(2,-1),new Position(-1,2)};
    
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
        
        Position tempMove = new Position(0,0);
        for (Position i : translations){
            tempMove = translate(kingPos,i);
            if (valid(tempMove) && pieceAt(tempMove).pieceType == PieceType.KING && pieceAt(tempMove).color == opposingSide){
                return true;
            }	
        }
    
        for (Position i : translations){
            tempMove = kingPos;
            do {
                tempMove = translate(tempMove,i);
                if (valid(tempMove) && pieceAt(tempMove).pieceType == PieceType.QUEEN && pieceAt(tempMove).color == opposingSide){
                    return true;
                }
            } while (valid(tempMove) && pieceAt(tempMove).empty());
        }
    
        for (int index = 1; index<translations.length;index+=2){
            Position i = translations[index];
            tempMove = kingPos;
            do {
                tempMove = translate(tempMove,i);
                if (valid(tempMove) && pieceAt(tempMove).pieceType == PieceType.BISHOP && pieceAt(tempMove).color == opposingSide){
                    return true;
                }
            } while (valid(tempMove) && pieceAt(tempMove).empty());
        }
    
        for (Position i : knightTranslations){
            tempMove = translate(kingPos,i);
            if (valid(tempMove) && pieceAt(tempMove).pieceType == PieceType.KNIGHT && pieceAt(tempMove).color == opposingSide){
                return true;
            }	
        }
    
        for (int index = 0; index<translations.length;index+=2){
            Position i = translations[index];
            tempMove = kingPos;
            do {
                tempMove = translate(tempMove,i);
                if (valid(tempMove) && pieceAt(tempMove).pieceType == PieceType.ROOK && pieceAt(tempMove).color == opposingSide){
                    return true;
                }
            }while (valid(tempMove) && pieceAt(tempMove).empty());
        }
            
        tempMove = translate(kingPos, new Position(-1,-1));
        if (tempMove.x >= 0 && tempMove.x <= 7 && tempMove.y >= 0 && tempMove.y <= 7 && pieceAt(tempMove).color == opposingSide && pieceAt(tempMove).pieceType == PieceType.PAWN){
            return true;
        }
        tempMove = translate(kingPos, new Position(1,-1));
        if (tempMove.x >= 0 && tempMove.x <= 7 && tempMove.y >= 0 && tempMove.y <= 7 && pieceAt(tempMove).color == opposingSide && pieceAt(tempMove).pieceType == PieceType.PAWN){
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
		Piece tempPiece = pieceAt(pos);
		Position tempMove;
        switch(tempPiece.pieceType){
            case KING:
				for (Position i : translations){
					tempMove = translate(pos,i);
					if (valid(tempMove)){
						moves.add(tempMove);
					}	
                }
                if (!tempPiece.moved){
                    if (orient == Side.WHITE_SIDE){
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
					tempMove = pos;
					boolean valid = true;
					while (valid){
						tempMove = translate(tempMove,i);
						valid = valid(tempMove);
						if (valid){
							moves.add(tempMove);
							if (pieceAt(tempMove).color == opposingSide)
								valid = false;
						}

					}
				}
                break;
            case BISHOP:
				for (int index = 1; index<translations.length;index+=2){
					Position i = translations[index];
					tempMove = pos;
					boolean valid = true;
					while (valid){
						tempMove = translate(tempMove,i);
						valid = valid(tempMove);
						if (valid){
							moves.add(tempMove);
							if (pieceAt(tempMove).color == opposingSide)
								valid = false;
						}

					}
				}
                break;
            case KNIGHT:
				for (Position i : knightTranslations){
					tempMove = translate(pos,i);
					if (valid(tempMove)){
						moves.add(tempMove);
					}	
				}
                break;
            case ROOK:
				for (int index = 0; index<translations.length;index+=2){
					Position i = translations[index];
					tempMove = pos;
					boolean valid = true;
					while (valid){
						tempMove = translate(tempMove,i);
						valid = valid(tempMove);
						if (valid){
							moves.add(tempMove);
							if (pieceAt(tempMove).color == opposingSide)
								valid = false;
						}

					}
				}
                break;
			case PAWN:
				if (!tempPiece.moved){
					tempMove = translate(pos, new Position(0,-2));
					if (tempMove.x >= 0 && tempMove.x <= 7 && tempMove.y >= 0 && tempMove.y <= 7 && pieceAt(tempMove).empty() && pieceAt(translate(pos,new Position(0,-1))).empty()){
                        moves.add(tempMove);
					}
				}
				tempMove = translate(pos, new Position(0,-1));
				if (tempMove.x >= 0 && tempMove.x <= 7 && tempMove.y >= 0 && tempMove.y <= 7 && pieceAt(tempMove).empty())
					moves.add(tempMove);
					
                tempMove = translate(pos, new Position(-1,-1));
                Position passant = translate(pos, new Position(-1,0));
				if (tempMove.x >= 0 && tempMove.x <= 7 && tempMove.y >= 0 && tempMove.y <= 7 && (pieceAt(tempMove).color == opposingSide || (pieceAt(passant).color == opposingSide && pieceAt(passant).pieceType == PieceType.PAWN)))
					moves.add(tempMove);
                tempMove = translate(pos, new Position(1,-1));
                passant = translate(pos, new Position(1,0));
				if (tempMove.x >= 0 && tempMove.x <= 7 && tempMove.y >= 0 && tempMove.y <= 7 && (pieceAt(tempMove).color == opposingSide || (pieceAt(passant).color == opposingSide && pieceAt(passant).pieceType == PieceType.PAWN)))
					moves.add(tempMove);
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
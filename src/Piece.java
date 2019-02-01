package Chess;

import java.io.Serializable;

class Piece implements Serializable{
    private static final long serialVersionUID = 1L;

    PieceType pieceType;
    Side color;
    public boolean moved = false;

    Piece(PieceType pieceType, Side color){
        this.pieceType = pieceType;
        this.color = color;
    }

    //for copying
    Piece(Piece piece){
        this.pieceType = piece.pieceType;
        this.color = piece.color;
    }

    boolean empty(){
        return pieceType == PieceType.EMPTY;
    }
}

package Chess;

class Piece{
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

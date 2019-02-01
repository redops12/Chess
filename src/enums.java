package Chess;

//used to simplify piece attributes
enum PieceType {
    KING,QUEEN,BISHOP,KNIGHT,ROOK,PAWN,EMPTY;
}

enum Side{
    BLACK_SIDE, WHITE_SIDE, NONE/*None is never used for the whole board, just for empty pieces*/;
}
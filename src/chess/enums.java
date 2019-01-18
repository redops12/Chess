package chess;

enum PieceType {
    KING,QUEEN,BISHOP,KNIGHT,ROOK,PAWN;
}

enum Pos{
    a(0),b(1),c(2),d(3),e(4),f(5),g(6),h(7);

    public final int pos;

    private Pos(int pos){
        this.pos = pos;
    }
}

enum Side{
    BLACK_SIDE, WHITE_SIDE;
}
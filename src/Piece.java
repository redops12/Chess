import java.util.ArrayList;

class Piece{
    static final int a = 1;
    static final int b = 2;
    static final int c = 3;
    static final int d = 4;
    static final int e = 5;
    static final int f = 6;
    static final int g = 7;
    static final int h = 8;
    PieceType piece;
    int[] position;

    Piece(PieceType piece, int x, int y){
        this.piece = piece;
        position = new int[]{x,y};
    }

    ArrayList<int[]> getMoves(Piece[] board){
        ArrayList<int[]> moves;
        switch(piece){
            case King:

                break;
            case QUEEN:

                break;
            case BISHOP:

                break;
            case KNIGHT:

                break;
            case ROOK:

                break;
            default:

        }
        return moves; 
    }
}
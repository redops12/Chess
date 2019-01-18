package chess;
import java.util.ArrayList;

class Piece{
    PieceType piece;
    int[] position;
    Side color;

    Piece(PieceType piece, int x, int y, Side color){
        this.piece = piece;
        this.color = color;
        position = new int[]{x,y};
    }

    ArrayList<int[]> getMoves(Piece[] board){
        ArrayList<int[]> moves = new ArrayList<int[]>();
        switch(piece){
            case KING:

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
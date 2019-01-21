package Chess;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;

class Piece{
    PieceType pieceType;
    Side color;
    Image image;
    boolean moved = false;

    Piece(PieceType pieceType, Side color){
        this.pieceType = pieceType;
        this.color = color;
        try {
            if (color == Side.BLACK_SIDE){
            switch (this.pieceType){
                case KING:
                image = ImageIO.read(new File("resources/BlackKing.png"));
                        break;
                    case QUEEN:
                        image = ImageIO.read(new File("resources/BlackQueen.png"));
                        break;
                    case BISHOP:
                        image = ImageIO.read(new File("resources/BlackBishop.png"));
                        break;
                    case KNIGHT:
                        image = ImageIO.read(new File("resources/BlackKnight.png"));
                        break;
                    case ROOK:
                        image = ImageIO.read(new File("resources/BlackRook.png"));
                        break;
                    case PAWN:
                        image = ImageIO.read(new File("resources/BlackPawn.png"));
                        break;
                    default:

                }
            } else {
                switch (this.pieceType){
                    case KING:
                        image = ImageIO.read(new File("resources/WhiteKing.png"));
                        break;
                    case QUEEN:
                        image = ImageIO.read(new File("resources/WhiteQueen.png"));
                        break;
                    case BISHOP:
                        image = ImageIO.read(new File("resources/WhiteBishop.png"));
                        break;
                    case KNIGHT:
                        image = ImageIO.read(new File("resources/WhiteKnight.png"));
                        break;
                    case ROOK:
                        image = ImageIO.read(new File("resources/WhiteRook.png"));
                        break;
                    case PAWN:
                        image = ImageIO.read(new File("resources/WhitePawn.png"));
                        break;
                    default: 
                }
            }
        } catch (Exception e){
            
        }
    }

    boolean empty(){
        return pieceType == PieceType.EMPTY;
    }
}

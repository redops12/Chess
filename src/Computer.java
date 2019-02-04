// package Chess;

// class Computer{
//     Board board;
//     Position nextMoveStart;
//     Position nextMoveEnd;


//     Computer(Board board){
//         this.board = board;
//     }

//     int moveValue(int depth, Board board){
//         if (depth == 0)
//             return boardValue.board();
            
//         for (int i = 0; i < 8; i ++){
//             for (int j = 0; j < 8; j ++){
//                 if (board.pieceAt(new Position(i,j)).color == board.getOrient()){
//                     board.getMoves(new Position(i,j));
//                 }
//             }
//         }
//     }

// }
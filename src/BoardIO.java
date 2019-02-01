package Chess;

import java.io.*;

class BoardIO { 
    // public static void main(String[] args) throws FileNotFoundException, IOException, ClassCastException{
    //     System.out.println("test");
    //     BoardIO boardIO = new BoardIO();
    //     Piece[][] temp_board = null;
    //     try {
    //         temp_board = boardIO.readObjectFromFile("DefaultBoard");
    //     } catch (Exception e){

    //     }
    //     for (Piece[] row : temp_board){
    //         for (Piece piece : row){
    //             System.out.println(piece.pieceType);
    //         }
    //     }
    //     // boardIO.WriteObjectToFile(defaultBoard, "DefaultBoard");
    // }
 
    static public void newBoard(Object board, String boardName) {
 
        try {
            String filepath = "resources\\boards\\" + boardName;
            File boardFile = new File(filepath);
            boardFile.createNewFile();

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(board);
            objectOut.close(); 
        } catch (Exception e) {}
    }

    static public File[] availableBoards(){
        File dir = new File("resources\\boards");
        return dir.listFiles();
    }

    static public Piece[][] readBoard(String boardName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream("resources\\boards\\" + boardName);
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        return (Piece[][]) objIn.readObject();
    }
}
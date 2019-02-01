package Chess;

import java.io.*;
import java.util.ArrayList;

class BoardIO {  
    static public void newBoard(Object board, String boardName) {
 
        try {
            String filepath = "resources" + File.separator + "boards" + File.separator + boardName + ".brd";
            File boardFile = new File(filepath);
            boardFile.createNewFile();

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(board);
            objectOut.close(); 
        } catch (Exception e) {}
    }

    static public File[] availableBoards(){
        File dir = new File("resources" + File.separator + "boards");
        return dir.listFiles(new FileFilter(){
        
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().endsWith(".brd");
            }
        });

    }

    static public Piece[][] readBoard(String boardName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream("resources" + File.separator + "boards" + File.separator + boardName);
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        return (Piece[][]) objIn.readObject();
    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        //tests

        // player welcome and name selection
        System.out.print("Welcome player to Infinite E, please type your name: ");
        String name = s.nextLine();

        // ##variable and class initialization##
        // all moves and damage
        String[] moves = getFileData("src/moves").toArray(new String[0]);

        String[] moveNames = new String[moves.length];
        String[] moveResults = new String[moves.length];

        for (int i = 0; i < moves.length; i++){
            // parses the String for the move's name and length
            String moveHappenings = moves[i].substring(moves[i].indexOf(" ") + 1);
            String moveName = moves[i].substring(0,moves[i].indexOf(":"));
            System.out.println(moveName + ": " + moveHappenings);
            moveNames[i] = moveName;
            moveResults[i] = moveHappenings;
        }

        s.close();
    }
    // file reader
    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        } catch (FileNotFoundException e) {
            return fileData;
        }
    }
}
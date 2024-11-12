import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        //tests

        // player welcome and name selection
//        System.out.print("Welcome player to Infinite E, please type your name: ");
//        String name = s.nextLine();

        // ##variable and class initialization##
        Player user = new Player("e");
        boolean run = true; // loop variable
        // all moves and damage
        String[] moves = getFileData("src/moves").toArray(new String[0]);

        String[] moveNames = new String[moves.length];
        String[] moveResults = new String[moves.length];

        for (int i = 0; i < moves.length; i++) {
            // parses the String for the move's name and length
            String moveHappenings = moves[i].substring(moves[i].indexOf(" ") + 1);
            String moveName = moves[i].substring(0, moves[i].indexOf(":"));
            System.out.println(moveName + ": " + moveHappenings);
            moveNames[i] = moveName;
            moveResults[i] = moveHappenings;
        }

        // move happenings
        int move = Integer.parseInt(s.nextLine());
        System.out.println(moveNames[move]);
        System.out.println(moveDamage(moveResults[move]));


//        int damage = (int) ((((2 * user.getLevel()) / 5 + 2 ) * enemy_damage * (user.getAtk() / enemy_defence)) / 50) + 2 * modifier;
        // modifer is a random number from 0.85 and 1.00 (inclusive)
        int damage = (int) ((((2 * user.getLevel()) / 5 + 2 ) * moveDamage(moveResults[move]) * (5 / 1)) / 20) + 2;
        System.out.println(damage);


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

    public static int moveDamage(String moveOutcomes){
        String[] moveResults = moveOutcomes.split(",");
        for (int i = 0; i < moveResults.length; i++) {
            if (moveResults[i].contains("enemy_damage")){
                return Integer.parseInt(moveResults[i].substring(moveResults[i].indexOf("enemy_damage: ") + 14, moveResults[i].indexOf("]")));
            }
        }
        return -1;
    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // player welcome and name selection
//        System.out.print("Welcome player to Infinite E, please type your name: ");
//        String name = s.nextLine();

        // ##variable and class initialization##
        Player user = new Player("e");
        boolean run = true; // loop variable
        int encounterCount = 0;
        String[] playerMoves = user.getMoves();
        // all moves and damage
        String[] moves = getFileData("src/moves").toArray(new String[0]);

        String[] moveNames = new String[moves.length];
        String[] moveResults = new String[moves.length];

        // enemy list
        String[] enemies = getFileData("src/enemyInformation").toArray(new String[0]);
        Enemy currentEnemy = selectEnemy(0, enemies);

        // loops through all the moves and splits them up into names and results with the index numbers corresponding to a move
        for (int i = 0; i < moves.length; i++) {
            // parses the String for the move's name and length
            String moveHappenings = moves[i].substring(moves[i].indexOf(" ") + 1);
            String moveName = moves[i].substring(0, moves[i].indexOf(":"));
            moveNames[i] = moveName;
            moveResults[i] = moveHappenings;
        }

        // move happenings
//        int move = Integer.parseInt(s.nextLine());
//        System.out.println(moveNames[move]);
//        System.out.println(moveDamage(moveResults[move]));

        // ## DAMAGE CALCULATION ##
//        int damage = (int) ((((2 * user.getLevel()) / 5 + 2 ) * enemy_damage * (user.getAtk() / enemy_defence)) / 50) + 2 * modifier;
        // modifer is a random number from 0.85 and 1.00 (inclusive)
//        int damage = (int) ((((2 * user.getLevel()) / 5 + 2 ) * moveDamage(moveResults[move]) * (5 / 1)) / 20) + 2;
//        System.out.println(damage);

        // tests
//        System.out.println(currentEnemy);
//        for (int i = 0; i < moveNames.length; i++){
//            System.out.println(moveNames[i] + " " + moveResults[i]);
//        }
        System.out.println(moveDescription("slap", moveNames, moveResults));
        System.out.println(moveDescription("slice", moveNames, moveResults));
        System.out.println(moveDescription("slime-spit", moveNames, moveResults));
        System.out.println(moveDescription("recoil-strike", moveNames, moveResults));
        // end of tests

        // ## Main Loop ##
        while (run){
            // ends game
            if (user.getStat("hp") <= 0){
                run = false;
            }
            else{
                // player action selection
                System.out.println("What would you like to do?\n1. Fight\n2. Check\n3. Item\n4. Mercy");
                int input = Integer.parseInt(s.nextLine());
                boolean actionDone = false;
                int playerMoveSelectedIndex = 0;
                String enemyMoveSelectedString = "";
                switch(input){
                    case 1 -> {
                        // using a move
                        System.out.println("What move?");
                        System.out.println("0. Back");

                        // prints out the player's moves
                        for (int i = 0; i < playerMoves.length; i++){
                            System.out.println(i + 1 + ". " + playerMoves[i] + moveDescription(playerMoves[i], moveNames, moveResults));
                        }
                        int moveInput = Integer.parseInt(s.nextLine());
                        moveInput--;
                        // uses the move if it corresponds to a move the user can make
                        if (moveInput <= user.getStat("movesLength") && moveInput >= 0){
                            System.out.println(user.getName() + " used " + playerMoves[moveInput] + "!");
                            playerMoveSelectedIndex = moveInput;
                            actionDone = true;
                        }
                    }
                    case 2 -> {
                        System.out.println(currentEnemy);
                        actionDone = true;
                    }
                    case 3 -> {
                        System.out.println("WORK IN PROGRESS AAAAA");
                    }
                    case 4 -> {
                        System.out.println("lol this isn't Undertale");
                    }

                }
                // if the player has done an action
                if (actionDone){
                    enemyMoveSelectedString = currentEnemy.selectMove();
                    System.out.println(currentEnemy.getName() + " selected " + enemyMoveSelectedString);

                    // prints out what happens during a turn
                    if (currentEnemy.getStat("speed") > user.getStat("speed")){
                        System.out.println("enemy action");
                        // check player hp
                        System.out.println("player action");
                    }
                    else{
                        System.out.println("player action");
                        // check enemy hp
                        System.out.println("enemy action");
                    }

                    // check player and enemy hp
                    if (user.getStat("hp") <= 0){
                        run = false;
                        // ends the run
                    }
                    else if (currentEnemy.getStat("hp") <= 0) {
                        System.out.println("round advance!");
                        encounterCount++;
                        // advances to the next encounter
                    }
                    actionDone = false;
                }
            }
        }

        // game end
        System.out.println("Good job " + user.getName() + "!\nYou made it through " + encounterCount + "encounters!");

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

    // parses the string of effects and returns the move damage
    public static int moveDamage(String moveOutcomes){
        String[] moveResults = moveOutcomes.split(",");
        // loops through all the move effects
        for (int i = 0; i < moveResults.length; i++) {
            // if the effect is enemy_damage
            if (moveResults[i].contains("enemy_damage")){
                // returns the damage
                return Integer.parseInt(moveResults[i].substring(moveResults[i].indexOf("enemy_damage: ") + 14, moveResults[i].indexOf("]")));
            }
        }
        return 0;
    }

    // returns an Enemy class of the enemy, with all its stats, at the index number listed inside the enemy list
    public static Enemy selectEnemy(int enemyNumber, String[] enemyList){
        String enemyInfo = enemyList[enemyNumber];

        // parses the name of the enemy
        String name = enemyInfo.substring(0,enemyInfo.indexOf("{"));

        // splits all the stats to be looped through, excluding the name and removing the brackets
        String[] enemyStats = enemyInfo.substring(enemyInfo.indexOf("{") + 1, enemyInfo.length() - 1).split("],\\[");

        // removes the first and last brackets for standardization of how the stats are stored
        enemyStats[0] = enemyStats[0].substring(1);
        enemyStats[enemyStats.length - 1] = enemyStats[enemyStats.length - 1].substring(0, enemyStats[enemyStats.length - 1].indexOf("]"));

        // initializing stats vars to set later
        int hp = -1;
        int level = -1;
        int speed = -1;
        int atk = -1;
        int defence = -1;
        String[] moves = new String[]{"ERROR NOT FOUND"};
        String[] loot = new String[]{"ERROR NOT FOUND"};

        // sets all the stat values
        for (int i = 0; i < enemyStats.length; i++){
            if (enemyStats[i].contains("hp")){
                hp = parseStat("hp", enemyStats[i]);
            }
            else if (enemyStats[i].contains("level")){
                level = parseStat("level", enemyStats[i]);
            }
            else if (enemyStats[i].contains("speed")){
                speed = parseStat("speed", enemyStats[i]);
            }
            else if (enemyStats[i].contains("atk")){
                atk = parseStat("atk", enemyStats[i]);
            }
            else if (enemyStats[i].contains("defence")){
                defence = parseStat("defence", enemyStats[i]);
            }
            else if (enemyStats[i].contains("moves: ")){
                moves = enemyStats[i].substring(7).split(",");
            }
            else if (enemyStats[i].contains("loot: ")){
                loot = enemyStats[i].substring(7).split(",");
            }
            else{
                System.out.println("YOU TYPED SOMETHING WRONG IN THE ENEMYINFORMATION FILE\nENEMY NUMBER: " + enemyNumber);
            }
        }

        // public Enemy(String name, int level, int hp, int speed, int atk, int defence, String[] moves, String[] loot)
        return new Enemy(name, level, hp, speed, atk, defence, moves, loot);
    }

    // returns the stat value (prerequisite that name is contained in stat)
    public static int parseStat(String name, String stat){
        return Integer.parseInt(stat.substring(stat.indexOf(name + ": ") + name.length() + 2));
    }

    // returns a string with the move and its description
    public static String moveDescription(String move, String[] moveNames, String[] moveOutcomes){
        int index = 0;
        // finds the move's index to use
        while (!moveNames[index].contains(move)){
            index++;
        }
        // splits into an array of all the results and description for the move
        String[] moveResults = moveOutcomes[index].split("],\\[");
        // removes the first and last brackets to standardize how the description and results are stored
        moveResults[0] = moveResults[0].substring(1);
        moveResults[moveResults.length - 1] = moveResults[moveResults.length - 1].substring(0, moveResults[moveResults.length - 1].indexOf("]"));

        String power = "";
        String selfDamage = "";
        // finds the self damage and power of the move if the move has them
        for (int i = 0; i < moveResults.length; i++){
            if (moveResults[i].contains("enemy_damage: ")){
                power = "   " + moveResults[i].substring(moveResults[i].indexOf(": ") + 2) + " power";
            }
            else if (moveResults[i].contains("self_damage: ")){
                selfDamage = moveResults[i].substring(moveResults[i].indexOf(": ") + 2) + " self damage";
            }
        }

        // returns the description of the move along with the power and self damage, depending on what the move has
        for (int i = 0; i < moveResults.length; i++){
            if (moveResults[i].contains("description: ") && power.contains("power") && selfDamage.contains("self damage")){
                return "\n   " + moveResults[i].substring(moveResults[i].indexOf("\"")) + "\n" + power + ", " + selfDamage;
            }
            else if (moveResults[i].contains("description: ") && power.contains("power")){
                return "\n   " + moveResults[i].substring(moveResults[i].indexOf("\"")) + "\n" + power;
            }
            else if (moveResults[i].contains("description: ")){
                return "\n   " + moveResults[i].substring(moveResults[i].indexOf("\""));
            }
        }
        return "Error Description not found";
    }
}
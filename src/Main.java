import jdk.jshell.Snippet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // player welcome and name selection
//        System.out.print("Welcome player to Infinite E, please type your name: ");
//        String name = s.nextLine();
        Player user = new Player("player");

        // ##variable and class initialization##
  
        boolean run = true; // loop variable
        int encounterCount = 1;

        // all moves and damage
        String[] moves = getFileData("src/moves").toArray(new String[0]);
        String[] moveNames = new String[moves.length];
        String[] moveResults = new String[moves.length];

        // player moves
        String[] playerMoves = user.getMoves();

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

        // all in between round rewards
        String[] roundRewards = getFileData("src/roundUpgrades").toArray(new String[0]);
        String[] rewardName = new String[roundRewards.length];
        String[] rewardData = new String[roundRewards.length];
        int[] rewardMax = new int[roundRewards.length];

        // loops through all round rewards and splits them into name and data, with the index number corresponding to a specific reward
        for (int i = 0; i < roundRewards.length; i++){
            rewardName[i] = roundRewards[i].substring(0,roundRewards[i].indexOf("{"));
            rewardData[i] = roundRewards[i].substring(roundRewards[i].indexOf("{") + 1, roundRewards[i].length() - 1);
        }

        // sets default max value for items
        for (int i = 0; i < rewardMax.length; i++){
            rewardMax[i] = 2147483647;
        }

        // sets the max for all the rewards
        for (int i = 0; i < rewardData.length; i++){
            // splits and standardizes by removing last and first brackets
            String[] currentRewardData = rewardData[i].split("],\\[");
            currentRewardData[0] = currentRewardData[0].substring(1);
            currentRewardData[currentRewardData.length - 1] = currentRewardData[currentRewardData.length - 1].substring(0, currentRewardData[currentRewardData.length - 1].length() - 1);
            for (int j = 0; j < currentRewardData.length; j++){
                if (currentRewardData[j].contains("max: ")){
                    rewardMax[i] = Integer.parseInt(currentRewardData[j].substring(5));
                }
            }
        }


        // player welcome
        System.out.println("Welcome " + user.getName() + " to Infinite-E");
        System.out.println("Round: " + encounterCount + "!\nEnemy: " + currentEnemy.getName());


        // tests

        // move happenings
//        int move = Integer.parseInt(s.nextLine());
//        System.out.println(moveNames[move]);
//        System.out.println(moveDamage(moveResults[move]));

        // ## DAMAGE CALCULATION ##
//        int damage = (int) ((((2 * user.getLevel()) / 5 + 2 ) * enemy_damage * (user.getAtk() / enemy_defence)) / 50) + 2 * modifier;
        // modifer is a random number from 0.85 and 1.00 (inclusive)
//        int damage = (int) ((((2 * user.getLevel()) / 5 + 2 ) * moveDamage(moveResults[move]) * (5 / 1)) / 20) + 2;
//        System.out.println(damage);


        // System.out.println(currentEnemy);
        // for (int i = 0; i < moveNames.length; i++){
        //     System.out.println(moveNames[i] + " " + moveResults[i]);
        // }
        // System.out.println(moveDescription("slap", moveNames, moveResults));
        // System.out.println(moveDescription("slice", moveNames, moveResults));
        // System.out.println(moveDescription("slime-spit", moveNames, moveResults));
        // System.out.println(moveDescription("recoil-strike", moveNames, moveResults));
//        System.out.println(upgradeInfo(rewardName[1], rewardData[1]));

        // end of tests

        // ## Main Loop ##
        while (run){
            // ends game
            if (user.getStat("hp") <= 0){
                run = false;
            }
            // if the game hasn't ended
            else{
                // player action selection
                System.out.println("What would you like to do?\n1. Fight\n2. Check\n3. Item\n4. Mercy");
                int input = Integer.parseInt(s.nextLine());
                boolean actionDone = false;
                boolean playerUsedMove = false;
                int playerMoveSelectedIndex = 0;
                String enemyMoveSelectedString;
                int enemyMoveSelectedIndex = 0;
                boolean roundAdvance = false;
                int nextLevelExpRequired = (int) (user.getStat("level") * 1.15 + Math.pow(user.getStat("level"), 1.1) + 100);
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
                            // gets the global id of the move
                            for (int i = 0; i < moveNames.length; i++){
                                if (moveNames[i].contains(playerMoves[moveInput])){
                                    playerMoveSelectedIndex = i;
                                }
                            }
                            actionDone = true;
                            playerUsedMove = true;
                        }
                    }
                    case 2 -> {
                        System.out.println("Whomst does thou wish to check?");
                        System.out.println("0. Back\n1. " + user.getName() + "\n2. " + currentEnemy.getName());
                        int checkAction = Integer.parseInt(s.nextLine());
                        if (checkAction == 1){
                            System.out.println(user);
                        }
                        else if (checkAction == 2){
                            System.out.println(currentEnemy);
                            actionDone = true;
                        }
                    }
                    case 3 -> {
                        System.out.println("WORK IN PROGRESS AAAAA");
                    }
                    case 4 -> {
                        System.out.println("lol this isn't Undertale");
                    }

                }

                // enemy move select
                enemyMoveSelectedString = currentEnemy.selectMove();
                // gets the global id of the move the enemy selected
                for (int i = 0; i < moveNames.length; i++){
                    if (moveNames[i].contains(enemyMoveSelectedString)){
                        enemyMoveSelectedIndex = i;
                    }
                }

                // if the player has done an action and used a move
                if (actionDone && playerUsedMove){
                    // checks who goes first and prints out what happens during a turn
                    if (currentEnemy.getStat("speed") > user.getStat("speed")){
                        // calculates how much damage the enemy did to the player
                        int playerDamageTaken = damageDone(currentEnemy.getStat("level"), currentEnemy.getStat("atk"), moveDamage(moveResults[enemyMoveSelectedIndex]), currentEnemy.getStat("defence"));
                        // updates player hp
                        user.damageTaken(playerDamageTaken);
                        System.out.println(currentEnemy.getName() + " used " + enemyMoveSelectedString + "!");
                        System.out.println(currentEnemy.getName() + " did " + playerDamageTaken + " damage!");
                        System.out.println("You're at " + user.getHpFraction() + " hp");
                        // check player hp
                        if (user.getStat("hp") > 0) {
                            // calculates how much damage the player does to the enemy
                            int enemyDamageTaken = damageDone(user.getStat("level"), user.getStat("atk"), moveDamage(moveResults[playerMoveSelectedIndex]), currentEnemy.getStat("defence"));
                            System.out.println(user.getName() + " used " + playerMoves[playerMoveSelectedIndex] + "!");
                            System.out.println(user.getName() + " did " + enemyDamageTaken + " damage!");
                            // update enemy hp
                            currentEnemy.damageTaken(enemyDamageTaken);
                            System.out.println(currentEnemy.getName() + " is at " + currentEnemy.getHpFraction());
                        }
                    }
                    else{
                        // calculates how much damage the player does to the enemy
                        int enemyDamageTaken = damageDone(user.getStat("level"), user.getStat("atk"), moveDamage(moveResults[playerMoveSelectedIndex]), currentEnemy.getStat("defence"));
                        System.out.println(user.getName() + " used " + playerMoves[playerMoveSelectedIndex] + "!");
                        System.out.println(user.getName() + " did " + enemyDamageTaken + " damage!");
                        // update enemy hp
                        currentEnemy.damageTaken(enemyDamageTaken);
                        System.out.println(currentEnemy.getName() + " is at " + currentEnemy.getHpFraction());
                        // check enemy hp
                        if (currentEnemy.getStat("hp") > 0){
                            // calculates how much damage the enemy does to the player
                            int playerDamageTaken = damageDone(currentEnemy.getStat("level"), currentEnemy.getStat("atk"), moveDamage(moveResults[enemyMoveSelectedIndex]), currentEnemy.getStat("defence"));
                            user.damageTaken(playerDamageTaken);
                            System.out.println(currentEnemy.getName() + " used " + enemyMoveSelectedString + "!");
                            System.out.println(currentEnemy.getName() + " did " + playerDamageTaken + " damage!" );
                            System.out.println("You're at " + user.getHpFraction() + " hp");

                        }
                    }


                    // check player and enemy hp
                    if (user.getStat("hp") <= 0){
                        run = false;
                        // ends the run
                    }
                    else if (currentEnemy.getStat("hp") <= 0) {
                        encounterCount++;
                        roundAdvance = true;
                        // advances to the next encounter
                    }                    
                }      
                else if (actionDone){
                    // calculates how much damage the enemy did to the player
                    int playerDamageTaken = damageDone(currentEnemy.getStat("level"), currentEnemy.getStat("atk"), moveDamage(moveResults[enemyMoveSelectedIndex]), currentEnemy.getStat("defence"));
                    // updates player hp
                    user.damageTaken(playerDamageTaken);
                    System.out.println(currentEnemy.getName() + " used " + enemyMoveSelectedString + "!");
                    System.out.println(currentEnemy.getName() + " did " + playerDamageTaken + " damage!");
                    System.out.println("You're at " + user.getHpFraction() + " hp");
                    // checks player and enemy hp
                    if (user.getStat("hp") <= 0){
                        run = false;
                        // ends the run
                    }
                    else if (currentEnemy.getStat("hp") <= 0) {
                        encounterCount++;
                        roundAdvance = true;
                        // advances to the next encounter
                    }
                }
                // advances to the next enemy
                if (roundAdvance){
                    // congrats
                    System.out.println("You defeated the " + currentEnemy.getName() + "!");
                    System.out.println("Round advance!");
                    // select 1 of 4 random upgrade items whose max is not 0


                    // generates the next enemy and tells user what the enemy is
                    int randEnemy = (int) (Math.random() * enemies.length);
                    currentEnemy = selectEnemy(randEnemy, enemies);
                    System.out.println("Round: " + encounterCount + "!\nEnemy: " + currentEnemy.getName());
                }
            }
        }

        // game end
        System.out.println("Good job " + user.getName() + "!\nYou made it through " + encounterCount + " encounters!");

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
    
    // returns the amount of damage a move does
    public static int damageDone(int attackerLevel, int attackerAtk, int movePower,int defenderDefence){
        double modifier = (Math.round((Math.random() * 0.30 + 0.85) * 100) / (double) 100); // randomized value from 0.85 to 1.15 (inclusive)
        if (movePower > 0){
            return (int) (((((2 * attackerLevel) / 5 + 2 ) * movePower * (attackerAtk / defenderDefence)) / 25) + 2 * modifier);
        }
        return 0;
    }

    public static String upgradeInfo(String upgradeName, String upgradeData){
        String returnString = upgradeName;
        // splits and standardizes by removing last and first brackets
        String[] upgradeResults = upgradeData.split("],\\[");
        upgradeResults[0] = upgradeResults[0].substring(1);
        upgradeResults[upgradeResults.length - 1] = upgradeResults[upgradeResults.length - 1].substring(0, upgradeResults[upgradeResults.length - 1].length() - 1);
        for (int i = 0; i < upgradeResults.length; i++){
            // adds the move to the string
            if (upgradeResults[i].contains("move: ")){
                returnString += "\n   Gives move: " + upgradeResults[i].substring(6);
            }
            // adds the stats and their amount to the string
            else if (upgradeResults[i].contains("stat: ")){
                // splits and standardizes the stat upgrades
                String[] statUpgrades = upgradeResults[i].substring(6).split("\\),\\(");
                statUpgrades[0] = statUpgrades[0].substring(1);
                statUpgrades[statUpgrades.length - 1] = statUpgrades[statUpgrades.length - 1].substring(0, statUpgrades[statUpgrades.length - 1].length() - 1);
                // adds the stat name and how much it increases the stat by
                for (int j = 0; j < statUpgrades.length; j++){
                    returnString += "\n   " + statUpgrades[j].substring(0, statUpgrades[j].indexOf(",")) + ": ";
                    int statAmount = Integer.parseInt(statUpgrades[j].substring(statUpgrades[j].indexOf(",") + 1));
                    // determines whether the stat amount is positive or negative
                    if (statAmount >= 0){
                        returnString += "+" + statAmount;
                    }
                    else{
                        returnString += statAmount;
                    }
                }
            }
        }
        return returnString;
    }

}
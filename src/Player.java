import java.util.ArrayList;

/**
 * The player is the user who is playing the game. The player has a max hp, hp, speed, attack, defence, exp, level the exp required for the next level, name, and move pool.
 *
 */
public class Player {
    private int maxHp;
    private int hp;
    private int speed;
    private int atk;
    private int defence;
    private int exp;
    private int level;
    private int nextLevelExpRequired;
    private String name;
    private ArrayList<String> moves;

    /**
     * Constructor for the Player class, creates a new instance of a player given the below parameters.
     * The player stats off at level 1, with 20 hp and max hp, 100 speed, 20 attack, 5 defence, 0 experience, and knowing the move slap
     * The experience required for the next level is dependent on the player's level
     * @param name The name of the player, inputted by the user
     */
    public Player(String name){
        this.name = name;

        maxHp = 20;
        hp = maxHp;
        speed = 100;
        atk = 20;
        defence = 5;
        exp = 0;
        level = 1;
        moves = new ArrayList<>();
        moves.add("slap");
        nextLevelExpRequired = (int) (level * 1.15 + Math.pow(level, 1.1) + 100);
    }

    /**
     * Reduces the hp by damage, can be used to heal as well when the damage is negative
     * @param damage The amount of damage the player takes, can heal if the damage is negative
     */
    public void damageTaken(int damage){
        if (hp - damage > maxHp){
            hp = maxHp;
        }
        else{
            hp -= damage;
        }

    }

    // updates the specified stat

    /**
     * Updates the stats of the player, adds amount to the current stat value, specified by the stat parameter
     * Prerequisite that stat is the name of a valid stat
     * @param stat The name of the stat to be changed
     * @param amount The amount the stat will change by, will increase by that amount if positive, and be reduced if negative
     */
    public void updateStat(String stat, int amount){
        switch (stat) {
            case "maxHp" -> {
                maxHp += amount;
                if (hp >= maxHp){
                    hp = maxHp;
                }
                else{
                    hp += amount;
                }
            }
            case "speed" -> speed += amount;
            case "atk" -> atk += amount;
            case "defence" -> defence += amount;
        }
    }

    /**
     * Returns the integer value of the stat specified, where it won't return a value smaller than 1 for attack and defence
     * Doesn't return below 1 for attack and defence, as it would mess up the prerequisites for the damage calculation
     * Prerequisite that stat is the name of a valid stat
     * @param stat The name of the stat
     * @return The value of the stat, with constraints on attack and defence
     */
    public int getStat(String stat){
        switch (stat) {
            case "hp" -> {
                return hp;
            }
            case "maxHp" -> {
                return maxHp;
            }
            case "atk" -> {
                if (atk <= 0){
                    return 1;
                }
                else{
                    return atk;
                }

            }
            case "speed" -> {
                return speed;
            }
            case "defence" -> {
                if (defence <= 0){
                    return 1;
                }
                else{
                    return defence;
                }

            }
            case "level" -> {
                return level;
            }
            case "movesLength" -> {
                return moves.toArray().length;
            }
            default -> {
                return -1;
            }
        }
    }

    /**
     * Adds the name of the move to the player's move pool
     * Prerequisite that move is a valid move name
     * @param move The name of the move to be added to the player's move pool
     */
    public void addMove(String move){
        moves.add(move);
    }

    /**
     * Increases the player's exp, and checks if they can level up, also randomly increasing one of the player's stats when they do level up
     * Prerequisite that amount is positive, and that nextLevelExpRequired is positive and non-zero
     * @param amount The amount of exp the player obtained
     */
    public void expIncrease(int amount){
        exp += amount;
        while (exp > nextLevelExpRequired){
            level += 1;
            // selects and increases a random stat
            int randStatIncrease = (int) (Math.random() * 4);
            switch (randStatIncrease){
                case 0 -> {
                    maxHp += 2;
                    hp += 2;
                }
                case 1 -> defence += 1;
                case 2 -> speed += 1;
                case 3 -> atk += 1;
            }
            exp -= nextLevelExpRequired;
            nextLevelExpRequired = (int) (level * 1.15 + Math.pow(level, 1.1) + 100);
        }
    }

    /**
     * Getter method, returns the player's name
     * @return The player's name
     */
    public String getName(){
        return name;
    }


    /**
     * Getter method, returns a string array of all the player's moves, so the player knows what moves they have
     * @return A String array of all the player's moves
     */
    public String[] getMoves(){
        String[] moveNames = new String[moves.toArray().length];
        for (int i = 0; i < moveNames.length; i++){
            moveNames[i] = moves.get(i);
        }
        return moveNames;
    }

    // returns player's current hp as a fraction of their max hp

    /**
     * The getHpFraction method returns a String which is a fraction of the player's current hp over their max hp
     * @return A String of the player's current hp over their max hp
     */
    public String getHpFraction(){
        return getStat("hp") + "/" + maxHp + " Hp";
    }

    /**
     * The toString method returns all the info about the player, excluding their moves
     * Used when the player checks themselves
     * @return A string of all the player's variables (except the moves), and their values
     */
    public String toString(){
        return "\"" + name + "\"\nLevel: " + level + "\nExp: " + exp + "\nHealth: " + getHpFraction() + "\nAtk: " + atk + "\nDefence: " + defence + "\nSpeed: "  + speed + "\n\"You, The player\"";
    }
}

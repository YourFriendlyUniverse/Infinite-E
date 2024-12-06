/**
 * An enemy is a monster that the player encounters. An enemy has a name, level, hp, speed, attack, defence, and move set.
 * The description is so the player can get an idea of what the enemy their facing looks like
 */
public class Enemy {
    private String name;
    private int level;
    private int hp;
    private int maxHp;
    private int speed;
    private int atk;
    private int defence;
    private String description;
    private String[] moves;

    /**
     * Constructor for the Enemy class. This creates a new instance of an enemy given the below parameters.
     * The level, hp, speed, attack, and defence are collectively referred to as stats
     * @param name A String representing the enemy's name
     * @param level An integer representing the enemy's level
     * @param hp An integer representing the enemy's hp
     * @param speed An integer representing the enemy's speed
     * @param atk An integer representing the enemy's attack
     * @param defence Enemy's defence
     * @param moves An array representing what moves the enemy can use
     * @param description A String storing the enemy's description
     */
    public Enemy(String name, int level, int hp, int speed, int atk, int defence, String[] moves, String description){
        this.name = name;
        this.level = level;
        this.hp = hp;
        maxHp = hp;
        this.speed = speed;
        this.atk = atk;
        this.defence = defence;
        this.moves = moves;
        this.description = description;
    }

    /**
     * The selectMove method will return a string of a random move from the enemy's moves array
     * @return A random move from the moves array
     */
    public String selectMove(){
        return moves[(int) (Math.random() * moves.length)];
    }

    /**
     * The giveExp method returns the amount of exp the enemy will drop when defeated
     * Calculates the exp dropped based on the enemy's stats
     * @return the exp the enemy gives, randomized by 0.85 to 1.15 (inclusive) times
     */
    public int giveExp(){
        return (int) ((((double) (level * 10) / 25) + (2 * atk) + (defence * maxHp) / (level / 5.5) + 2) * (Math.round((Math.random() * 0.30 + 0.85) * 100) / (double) 100));
    }

    /**
     * The getHpFraction method returns a String which is a fraction of the enemy's current hp over their max hp
     * @return A String of the enemy's current hp over their max hp
     */
    public String getHpFraction(){
        if (hp >= 0){
            return hp + "/" + maxHp;
        }
        else{
            return 0 + "/" + maxHp;
        }
    }

    /**
     * The damageTaken method subtracts hp by damage
     * @param damage How much to subtract the enemy's hp by
     */
    public void damageTaken(int damage){
        hp -= damage;
    }

    /**
     * the getStat method is a getter method, and gets the stat specified by the stat parameter inputted
     * @param stat Determines which stat value to return
     * @return The int value of the stat specified within the stat parameter
     */
    public int getStat(String stat){
        return switch (stat) {
            case "level" -> level;
            case "hp" -> hp;
            case "speed" -> speed;
            case "atk" -> atk;
            case "defence" -> defence;
            default -> -1;
        };
    }

    /**
     * The getName method is a getter method, gets the name of enemy
     * Used when displaying who did what move, and when checking the enemy
     * @return The name of the enemy
     */
    public String getName(){
        return name;
    }

    /**
     * The statScaling method increases the enemy's stats based on the player's current stats, so the game doesn't become too easy
     * It will calculate the enemy's stat increase, as well as randomize the final outcome somewhat
     * @param playerLevel The player's current level
     * @param playerMaxHp The player's current max hp
     * @param playerAttack The player's current attack
     * @param playerDefence The players current defence
     */
    public void statScaling(int playerLevel, int playerMaxHp, int playerAttack, int playerDefence){
        level += (int) ((Math.random() * 0.2 + 0.8) * playerLevel * Math.pow(Math.log10(level + 2 * playerLevel), Math.log10(playerLevel)));
        maxHp += (int) ((Math.random() * 0.2 + 0.35) * (playerAttack / 8.0) * ((level / 13.2) + 1) * (Math.pow(Math.log10(maxHp), 2) + 1));
        hp = maxHp;
        defence += (int) ((Math.random() * 0.3 + 0.3) * (Math.pow(playerAttack, 1.1) / 15.7) * ((level / 8.7) + 1) * (Math.pow(Math.log10(defence), 2) + 1));
        atk += (int) ((((playerDefence / 20.0) * (Math.random() * 0.2 + 0.4)) + ((playerMaxHp / 13.0) * (Math.random() * 0.2 + 0.4))) * ((level / 9.5) + 1) * (atk / 5.0));
    }

    // returns a string of all the enemy's stats

    /**
     * The toString method returns all the information about the enemy, excluding the moves
     * Used when checking the enemy
     * @return A string of all the enemy's variables (except the moves), and their values
     */
    public String toString(){
        return "level: " + level + ", name: " + name + ", hp: " + getHpFraction() + ", speed: " + speed + ", atk: " + atk + ", defence: " + defence + "\n" + description;
    }

}

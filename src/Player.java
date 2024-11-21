import java.util.ArrayList;

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

    // reduces the hp by the damage
    public void damageTaken(int damage){
        if (hp - damage > maxHp){
            hp = maxHp;
        }
        else{
            hp -= damage;
        }

    }

    // updates the specified stat
    public void updateStat(String stat, int amount){
        switch (stat) {
            case "maxHp" -> {
                maxHp += amount;
                if (hp > maxHp){
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

    // returns the specified stat of the player
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

    public void addMove(String move){
        moves.add(move);
    }

    // increases the exp of the player by the amount and levels up the player
    public void expIncrease(int amount){
        exp += amount;
        while (exp > nextLevelExpRequired){
            level += 1;
            exp -= nextLevelExpRequired;
            nextLevelExpRequired = (int) (level * 1.15 + Math.pow(level, 1.1) + 100);
        }
    }

    // returns the player's name
    public String getName(){
        return name;
    }

    // returns an array of all the player's moves' names
    public String[] getMoves(){
        String[] moveNames = new String[moves.toArray().length];
        for (int i = 0; i < moveNames.length; i++){
            moveNames[i] = moves.get(i);
        }
        return moveNames;
    }

    // returns player's current hp as a fraction of their max hp
    public String getHpFraction(){
        return getStat("hp") + "/" + maxHp + " Hp";
    }

    public String toString(){
        return "\"" + name + "\"\nLevel: " + level + "\nExp: " + exp + "\nHealth: " + getHpFraction() + "\nAtk: " + atk + "\nDefence: " + defence + "\nSpeed: "  + speed + "\n\"You, The player\"";
    }
}

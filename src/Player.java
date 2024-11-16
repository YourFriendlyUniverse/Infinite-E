import java.util.ArrayList;

public class Player {
    private int maxHp;
    private int hp;
    private int speed;
    private int atk;
    private int defence;
    private int exp;
    private int level;
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
    }

    // reduces the hp by the damage
    public void damageTaken(int damage){
        hp -= damage;
    }

    // updates the specified stat
    public void updateStat(String stat, int amount){
        if (stat.equals("maxHp")){
            maxHp += amount;
        }
        else if (stat.equals("speed")) {
            speed += amount;
        }
        else if (stat.equals("atk")){
            atk += amount;
        }
        else if (stat.equals("defence")){
            defence += amount;
        }
    }

    // returns the specified stat of the player
    public int getStat(String stat){
        return switch (stat) {
            case "hp" -> hp;
            case "atk" -> atk;
            case "speed" -> speed;
            case "defence" -> defence;
            case "level" -> level;
            case "movesLength" -> moves.toArray().length;
            default -> -1;
        };
    }

    // increases the exp of the player by the amount
    public void expIncrease(int amount){
        exp += amount;
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
}

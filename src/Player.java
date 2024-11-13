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
        atk = 5;
        defence = 5;
        exp = 0;
        level = 1;
        moves = new ArrayList<String>();
        moves.add("slap");
    }

    public void damageTaken(int damage){
        hp -= damage;
    }

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
        else{
            System.out.println("ERROR YOU SPELT" + stat + "INCORRECTLY");
        }
    }

    public int getLevel(){
        return level;
    }

    public void expIncrease(int amount){
        exp += amount;
    }

    // returns the specified stat of the player
    public int getStat(String stat){
        if (stat.equals("hp")) {
            return hp;
        }
        else if (stat.equals("atk")){
            return atk;
        }
        else if (stat.equals("speed")){
            return speed;
        }
        else if (stat.equals("defence")){
            return defence;
        }
        else{
            System.out.println("You spelled" + stat + "wrong :skull:");
            return -1;
        }
    }

}

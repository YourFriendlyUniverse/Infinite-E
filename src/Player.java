import java.util.ArrayList;

public class Player {
    int maxHp;
    int hp;
    int speed;
    int atk;
    ArrayList<String> moves;

    public Player(){
        maxHp = 20;
        hp = maxHp;
        speed = 100;
        atk = 1;
        moves = new ArrayList<String>();
        moves.add("slap");
    }


}

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

    // randomly selects a move from its move table
    public String selectMove(){
        return moves[(int) (Math.random() * moves.length)];
    }

    // gives exp, randomized by a multiplier of 0.85 to 1.15 (inclusive)
    public int giveExp(){
        return (int) ((((double) (level * 10) / 25) + (2 * atk) + (defence * maxHp) / (level / 5.5) + 2) * (Math.round((Math.random() * 0.30 + 0.85) * 100) / (double) 100));
    }

    // returns a string of the enemy's current hp over their max hp
    public String getHpFraction(){
        if (hp >= 0){
            return hp + "/" + maxHp;
        }
        else{
            return 0 + "/" + maxHp;
        }
    }

    // subtracts hp by damage
    public void damageTaken(int damage){
        hp -= damage;
    }

    // gets the specified stat of the enemy
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

    public String getName(){
        return name;
    }

    // scales enemy's stats based on the player's stats
    public void statScaling(int playerLevel, int playerMaxHp, int playerAttack, int playerDefence){
        level += (int) ((Math.random() * 0.2 + 0.8) * playerLevel * Math.pow(Math.log10(level + 2 * playerLevel), Math.log10(playerLevel)));
        maxHp += (int) ((Math.random() * 0.2 + 0.35) * (playerAttack / 8.0) * ((level / 13.2) + 1) * (Math.pow(Math.log10(maxHp), 2) + 1));
        hp = maxHp;
        defence += (int) ((Math.random() * 0.3 + 0.3) * (Math.pow(playerAttack, 1.1) / 15.7) * ((level / 8.7) + 1) * (Math.pow(Math.log10(defence), 2) + 1));
        atk += (int) ((((playerDefence / 20.0) * (Math.random() * 0.2 + 0.4)) + ((playerMaxHp / 13.0) * (Math.random() * 0.2 + 0.4))) * ((level / 9.5) + 1) * (atk / 5.0));
    }

    // returns a string of all the enemy's stats
    public String toString(){
        return "level: " + level + ", name: " + name + ", hp: " + getHpFraction() + ", speed: " + speed + ", atk: " + atk + ", defence: " + defence + "\n" + description;
    }

}

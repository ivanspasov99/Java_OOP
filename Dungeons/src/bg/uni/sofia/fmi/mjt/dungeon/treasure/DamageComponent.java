package bg.uni.sofia.fmi.mjt.dungeon.treasure;

public abstract class DamageComponent implements Treasure {
    String name;
    int damage;

    public String getName() {
        return this.name;
    }
    public int getDamage() {
        return this.damage;
    }
}

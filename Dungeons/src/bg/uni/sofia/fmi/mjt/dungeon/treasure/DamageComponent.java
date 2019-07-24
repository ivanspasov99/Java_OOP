package bg.uni.sofia.fmi.mjt.dungeon.treasure;

public abstract class DamageComponent implements Treasure {
    String name;
    int damage;

    public DamageComponent(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public DamageComponent(DamageComponent other) {
        if(other == null) {
            return;
        } else {
            this.name = other.name;
            this.damage = other.damage;
        }
    }

    public String getName() {
        return this.name;
    }
    public int getDamage() {
        return this.damage;
    }
}

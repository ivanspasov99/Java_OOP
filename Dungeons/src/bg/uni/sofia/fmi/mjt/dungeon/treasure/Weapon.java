package bg.uni.sofia.fmi.mjt.dungeon.treasure;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
public class Weapon extends DamageComponent {

    public Weapon(String name, int damage) {
        super(name, damage);
        this.damage = damage;
    }

    public Weapon(Weapon other) {
        super(other);
    }

    @Override
    public String collect(Hero hero){
        hero.equip(this);
        return "Weapon found! Damage points: "+ getDamage();
    }
}

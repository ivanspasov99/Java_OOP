package bg.uni.sofia.fmi.mjt.dungeon.treasure;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
public class Weapon extends DamageComponent {

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    @Override
    public String collect(Hero hero){
        hero.equip(this);
        return "Weapon found! Damage points: "+ getDamage();
    }
}

package bg.uni.sofia.fmi.mjt.dungeon.treasure;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
public class Spell extends DamageComponent{
    private final int manaCost;

    public Spell(String name, int damage, int manaCost) {
        super(name, damage);
        this.manaCost = manaCost;
    }

    public Spell(Spell other) {
        super(other);
        this.manaCost = other.manaCost;
    }

    public int getManaCost(){
        return this.manaCost;
    }

    @Override
    public String collect(Hero hero){
        hero.learn(this);
        return "Spell found! Damage points: "+ getDamage() +", Mana cost: "+ getManaCost();
    }

}

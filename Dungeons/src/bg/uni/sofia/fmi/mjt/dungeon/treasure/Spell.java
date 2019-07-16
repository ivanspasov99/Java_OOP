package bg.uni.sofia.fmi.mjt.dungeon.treasure;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
public class Spell extends DamageComponent{
    private int manaCost;

    public Spell(String name, int damage, int manaCost) {
        this.name = name;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public int getManaCost(){
        return this.manaCost;
    }

    @Override
    public String collect(Hero hero){
        hero.learn(this);
        return "Spell found! Damage points: "+ getDamage()+ "Mana cost: "+ getManaCost();
    }

}

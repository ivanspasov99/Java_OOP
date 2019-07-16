package bg.uni.sofia.fmi.mjt.dungeon.actor;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;
public class Enemy extends ActorAbstract{

    public Enemy(String name, int hp, int mana, Weapon weapon, Spell spell){
        this.name = name;
        this.health = hp;
        this.mana = mana;
        this.weapon = weapon;
        this.spell = spell;
    }
}

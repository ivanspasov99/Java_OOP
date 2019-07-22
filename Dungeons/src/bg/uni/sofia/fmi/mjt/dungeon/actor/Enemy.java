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
    public Enemy(Enemy enemy) {
        this.name = enemy.getName();
        this.health = enemy.getHealth();
        this.mana = enemy.getMana();
        this.weapon = (enemy.weapon == null) ? null : new Weapon(enemy.getWeapon());
        this.spell = (enemy.spell == null) ? null : new Spell(enemy.getSpell());
    }

    @Override
    public Enemy clone(){
        return new Enemy(this);
    }
}

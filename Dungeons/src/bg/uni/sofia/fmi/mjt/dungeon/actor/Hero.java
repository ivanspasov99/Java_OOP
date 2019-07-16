package bg.uni.sofia.fmi.mjt.dungeon.actor;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;
public class Hero extends ActorAbstract{
    private final int maxLife;
    private final int maxMana;

    public Hero() { this("Unknown", 300, 50); }
    public Hero(String name, int hp, int mana){
        this.name = name;
        this.health = hp;
        this.mana = mana;

        this.maxLife = hp; // limit hp
        this.maxMana = mana; // limit hp
    }

    // better to be boolean??
    public void takeHealing(int healingPoints){
        if(isAlive()){
            if(healingPoints + this.health >= this.maxLife)
                this.health = this.maxLife;
            else this.health+=healingPoints;
        }
    }
    public void takeMana(int manaPoints){
        if(manaPoints + this.mana >= this.maxMana)
            this.mana = this.maxLife;
        else this.mana+=manaPoints;
    }

    // functions for checking if the weapon is weaker??
    public void equip(Weapon weapon){
       if(this.weapon.getDamage() < weapon.getDamage()) this.weapon = weapon;
    }
    public void learn(Spell spell) {
        if(this.spell.getDamage() < spell.getDamage()) this.spell = spell;
    }

    // attack not implemented in Abstract
}

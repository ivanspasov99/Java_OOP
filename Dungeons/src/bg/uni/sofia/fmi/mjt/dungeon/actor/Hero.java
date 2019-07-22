package bg.uni.sofia.fmi.mjt.dungeon.actor;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;
public class Hero extends ActorAbstract{
     final int maxLife;
     final int maxMana;

    public Hero(Hero hero){
        this.name = hero.getName();
        this.health = hero.getHealth();
        this.mana = hero.getMana();
        this.weapon = (hero.weapon == null) ? null : new Weapon(hero.getWeapon());
        this.spell = (hero.spell == null) ? null : new Spell(hero.getSpell());

        this.maxLife = hero.maxLife;
        this.maxMana = hero.maxMana;
    }
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
            if(healingPoints + this.health < this.maxLife) {
                this.health+=healingPoints;
            } else {
                this.health = this.maxLife;
            }

        }
    }
    public void takeMana(int manaPoints){
        if(manaPoints + this.mana < this.maxMana) {
            this.mana+=manaPoints;
        } else {
            this.mana = this.maxMana;
        }

    }
    public void equip(Weapon weapon){
        if(this.weapon == null) {
            this.weapon = weapon;
        }
        if(this.weapon.getDamage() < weapon.getDamage()) {
            this.weapon = weapon;
        }

    }
    public void learn(Spell spell) {
        if(this.spell == null) {
            this.spell = spell;
        }
        if (this.spell.getDamage() < spell.getDamage()) {
            this.spell = spell;
        }
    }
}

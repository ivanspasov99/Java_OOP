package bg.uni.sofia.fmi.mjt.dungeon.actor;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;

public interface Actor {
    String getName();
    int getMana();
    int getHealth();
    Weapon getWeapon();
    Spell getSpell();

    boolean isAlive();
    void takeDamage(int damagePoints);
    int attack();
}

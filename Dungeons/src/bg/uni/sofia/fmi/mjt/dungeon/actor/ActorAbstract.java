package bg.uni.sofia.fmi.mjt.dungeon.actor;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;
public abstract class ActorAbstract implements Actor{
    String name;
    int health;
    int mana;
    Weapon weapon;
    Spell spell;

    public String getName() { return name; }
    public int getMana() { return mana; }
    public int getHealth() {return health; }
    public Weapon getWeapon() {return weapon; }
    public Spell getSpell() { return spell; }

    public boolean isAlive() {if(health > 0) return true; else return false; }
    public void takeDamage(int damagePoints) { if(damagePoints >= health) health = 0; else health-=damagePoints; }
    public int attack() {
        return 0;
    }
}

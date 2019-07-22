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
    public int getHealth() { return health; }
    public Weapon getWeapon() { return weapon; }
    public Spell getSpell() { return spell; }

    public boolean isAlive() {
       return health > 0;
    }
    public void takeDamage(int damagePoints) {
        if(damagePoints >= health) {
            health = 0;
        } else {
            health -= damagePoints;
        }
    }
    public int attack() {
        if(weapon == null && spell == null) {
            return 0;
        }
        if(spell == null) {
            return weapon.getDamage();
        }
        if(weapon == null) {
            if(spell.getManaCost() > mana) {
                return 0;
            }
            mana-=spell.getManaCost();
            return spell.getDamage();
        }
        if(weapon.getDamage() >= spell.getDamage())
        {
            return weapon.getDamage();
        }
        if(spell.getManaCost() <= mana) {
            mana-=spell.getManaCost();
            return spell.getDamage();
        }
        return weapon.getDamage();
    }
}

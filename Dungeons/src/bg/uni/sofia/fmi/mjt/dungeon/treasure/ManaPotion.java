package bg.uni.sofia.fmi.mjt.dungeon.treasure;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;

public class ManaPotion implements Treasure {
    private int manaPoints;

    public ManaPotion(int manaPoints) {
        this.manaPoints = manaPoints;
    }

    public ManaPotion(ManaPotion other) {
        this.manaPoints = other.manaPoints;
    }

    public int heal() {
        return  this.manaPoints;
    }
    @Override
    public String collect(Hero hero) {
        hero.takeMana(manaPoints);
        return "Mana potion found! "+ manaPoints +" mana points added to your hero!";
    }
}

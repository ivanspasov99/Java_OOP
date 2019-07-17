package bg.uni.sofia.fmi.mjt.dungeon.treasure;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;

public class HealthPotion implements Treasure {
    private int healingPoints;
    public HealthPotion(int healingPoints) {
        this.healingPoints = healingPoints;
    }

    @Override
    public String collect(Hero hero) {
        hero.takeHealing(healingPoints);
        return "Health potion found! "+ healingPoints +"Health points added to your hero";
    }
}

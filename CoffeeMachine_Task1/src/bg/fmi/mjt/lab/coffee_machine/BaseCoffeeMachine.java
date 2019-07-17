package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.container.PremiumContainer;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public abstract class BaseCoffeeMachine  {
    protected boolean enoughSupplies(Container container, Beverage beverage, int quantity) {
        return container.getCurrentWater() >= quantity * beverage.getWater()
                && container.getCurrentCacao() >= quantity * beverage.getCacao()
                && container.getCurrentMilk() >= quantity * beverage.getMilk()
                && container.getCurrentCoffee() >= quantity * beverage.getCoffee();
    }

    protected void decreaseSupplies(Container container, Beverage beverage, int quantity) {
        container.setCoffee(container.getCurrentCoffee() - quantity * beverage.getCoffee());
        container.setWater((container.getCurrentWater() - quantity * beverage.getWater()));
        // casting!!!
        if (container instanceof PremiumContainer) {
            PremiumContainer premiumContainer = (PremiumContainer)container;
            premiumContainer.setMilk(premiumContainer.getCurrentMilk() - quantity * beverage.getMilk());
            premiumContainer.setCacao(premiumContainer.getCurrentCacao() - quantity * beverage.getCacao());
        }
    }
}

package bg.coffee_machine;

public class PremiumCoffeeMachine implements CoffeeMachine {
    private PremiumContainer container;
    private boolean autoRefill = false;
    private int luckCounter = -1;
    private final String[] Lucks = {
            "If at first you don't succeed call it version 1.0.",
            "Today you will make magic happen!",
            "Have you tried turning it off and on again?",
            "Life would be much more easier if you had the source code.",
    };

    public PremiumCoffeeMachine(boolean autoRefill) {  this(); this.autoRefill = autoRefill; }
    public PremiumCoffeeMachine() {
        this.container = new PremiumContainer();
    }

    @Override
    public Product brew(Beverage beverage) {

        int flag = (enoughSupplies(beverage)) ? 1 : 0;
        switch (flag) {
            case 0: { if(autoRefill) refill(); else return null; }
            case 1: {
                decreaseSupplies(beverage);
                incrementLuckCounter();
                System.out.println(luckCounter);
                return new Product(beverage, Lucks[luckCounter]);
            }
            default: return null; // cannot be accessed!!!
        }
    }

    @Override
    public Container getSupplies() {
        return container;
    }

    @Override
    public void refill() {
        this.container.setCacao(300);
        this.container.setCoffee(1000);
        this.container.setMilk(1000);
        this.container.setWater(1000);
    }


    private boolean enoughSupplies(Beverage beverage){
        return container.getCurrentWater() >= beverage.getWater()
                && container.getCurrentCacao() >= beverage.getCacao()
                && container.getCurrentMilk() >= beverage.getMilk()
                && container.getCurrentCoffee() >= beverage.getCoffee();
    }
    private void decreaseSupplies(Beverage beverage) {
        container.setCoffee(container.getCurrentCoffee() - beverage.getCoffee());
        container.setMilk(container.getCurrentMilk() - beverage.getMilk());
        container.setCacao(container.getCurrentCacao() - beverage.getCacao());
        container.setWater((container.getCurrentWater() - beverage.getWater()));
    }

    private void incrementLuckCounter(){
        if(luckCounter != 3) luckCounter++;
        else luckCounter = -1;
    }
}

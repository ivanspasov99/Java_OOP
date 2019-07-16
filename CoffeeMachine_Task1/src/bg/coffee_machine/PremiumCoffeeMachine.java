package bg.coffee_machine;

public class PremiumCoffeeMachine implements CoffeeMachine {
    private PremiumContainer container;
    private boolean autoRefill = false;
    private static int luckCounter = 0;
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
        if(beverage instanceof Espresso)
            if(enoughEspresso(beverage)){
                decreaseSuppliesForEspresso(beverage);
                incrementLuckCounter();
                return new Product(beverage, Lucks[luckCounter]);
            }
            else if(autoRefill){
                refill();
                decreaseSuppliesForEspresso(beverage);
                incrementLuckCounter();
                return new Product(beverage, Lucks[luckCounter]);
            }
        if(beverage instanceof Cappuccino)
            if(enoughCappuccino(beverage)) {
                decreaseSuppliesForCappuccino(beverage);
                incrementLuckCounter();
                return new Product(beverage, Lucks[luckCounter]);
            }
            else if(autoRefill){
                refill();
                decreaseSuppliesForCappuccino(beverage);
                incrementLuckCounter();
                return new Product(beverage, Lucks[luckCounter]);
            }
        if(beverage instanceof Mochaccino)
            if(enoughMochaccino(beverage)){
                decreaseSuppliesForMochaccino(beverage);
                incrementLuckCounter();
                return new Product(beverage, Lucks[luckCounter]);
            }
            else if(autoRefill){
                refill();
                decreaseSuppliesForMochaccino(beverage);
                incrementLuckCounter();
                return new Product(beverage, Lucks[luckCounter]);
            }
        return null;
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

    public boolean enoughEspresso(Beverage beverage){
        if(container.getCurrentCoffee() >= beverage.getCoffee() && container.getCurrentWater() >= beverage.getWater() )
            return true;
        else
            return false;
    }
    public boolean enoughCappuccino(Beverage beverage){

        if(container.getCurrentCoffee() >= beverage.getCoffee() && container.getCurrentMilk() >= beverage.getMilk() )
            return true;
        else
            return false;
    }
    public boolean enoughMochaccino(Beverage beverage){
        if(container.getCurrentCoffee() >= beverage.getCoffee()
                && container.getCurrentMilk() >= beverage.getMilk()
                && container.getCurrentCacao() >= beverage.getCacao())
            return true;
        else
            return false;
    }


    private void decreaseSuppliesForEspresso(Beverage beverage) {
        container.setCoffee(container.getCurrentCoffee() - beverage.getCoffee());
        container.setWater(container.getCurrentWater()- beverage.getWater());
    }
    private void decreaseSuppliesForMochaccino(Beverage beverage) {
        container.setCoffee(container.getCurrentCoffee() - beverage.getCoffee());
        container.setMilk(container.getCurrentMilk() - beverage.getMilk());
        container.setCacao(container.getCurrentCacao() - beverage.getCacao());
    }
    private void decreaseSuppliesForCappuccino(Beverage beverage) {
        container.setCoffee(container.getCurrentCoffee() - beverage.getCoffee());
        container.setMilk(container.getCurrentMilk() - beverage.getMilk());
    }


    private void incrementLuckCounter(){
        if(luckCounter != 3) luckCounter++;
        else luckCounter = 0;
    }
}

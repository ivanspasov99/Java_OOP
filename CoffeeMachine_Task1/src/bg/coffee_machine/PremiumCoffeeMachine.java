package bg.coffee_machine;

public class PremiumCoffeeMachine implements CoffeeMachine {
    private PremiumContainer container;
    private boolean autoRefill = false;

    public PremiumCoffeeMachine(boolean autoRefill) {  this(); this.autoRefill = autoRefill; }
    public PremiumCoffeeMachine() {
        this.container = new PremiumContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        if(beverage instanceof Espresso)
            if(container.enoughEspresso()){
                orderEspresso();
                return new Product(beverage, "Premium");
            }
            else if(autoRefill){
                refill();
                orderEspresso();
            }
        if(beverage instanceof Cappuccino)
            if(container.enoughCappuccino()) {
                orderCappuccino();
                return new Product(beverage, "Premium");
            }
            else if(autoRefill){
                refill();
                orderCappuccino();
            }
        if(beverage instanceof Mochaccino)
            if(container.enoughMochaccino()){
                orderMochaccino();
                return new Product(beverage, "Premium");
            }
            else if(autoRefill){
                refill();
                orderMochaccino();
            }
        return null;
    }

    @Override
    public Container getSupplies() {
        return container;
    }

    @Override
    public void refill() {
        // use const??
        this.container.setCacao(300);
        this.container.setCoffee(1000);
        this.container.setMilk(1000);
        this.container.setWater(1000);
    }

    private void orderEspresso() {
        container.setCoffee(container.getCurrentCoffee()- 10);
        container.setWater(container.getCurrentWater()- 30);
    }
    private void orderMochaccino() {
        container.setCoffee(container.getCurrentCoffee()-18);
        container.setMilk(container.getCurrentMilk()-150);
        container.setCacao(container.getCurrentCacao()-10);
    }
    private void orderCappuccino() {
        container.setCoffee(container.getCurrentCoffee()-18);
        container.setMilk(container.getCurrentMilk()-150);
    }
}

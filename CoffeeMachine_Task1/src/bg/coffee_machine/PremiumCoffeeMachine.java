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
            if(container.enoughEspresso())
                return new Product(beverage, "Premium");
            else { refill();}
        if(beverage instanceof Cappuccino)
            if(container.enoughCappuccino())
                return new Product(beverage, "Premium");
        if(beverage instanceof Mochaccino)
            if(container.enoughMochaccino())
                return new Product(beverage, "Premium");
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
        container.setCoffee(container.getCoffee()- 10);
        container.setWater(container.getWater()- 10);
    }
    private void orderMochaccino() {
        container.setCoffee(container.getCoffee()-18);
        container.setMilk(container.getMilk()-150);
        container.setCacao(container.getCacao()-10);
    }
    private void orderCappuccino() {
        container.setCoffee(container.getCoffee()-18);
        container.setMilk(container.getMilk()-150);
    }
}

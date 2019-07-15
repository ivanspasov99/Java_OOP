package bg.coffee_machine;

public class BasicCoffeeMachine implements CoffeeMachine{
    private BasicContainer container;


    public BasicCoffeeMachine() {
        this.container = new BasicContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        if(beverage instanceof Espresso)
            if(container.enoughEspresso()) {
                orderEspresso();
                return new Product(beverage, "Basic");
            }
        return null;
    }

    @Override
    public Container getSupplies() {
        return container;
    }

    @Override
    public void refill() {
        this.container.setCacao(0);
        this.container.setCoffee(600);
        this.container.setMilk(0);
        this.container.setWater(600);
    }

    private void orderEspresso() {
        container.setCoffee(container.getCurrentCoffee()- 10);
        container.setWater(container.getCurrentWater()- 30);
    }
}

package bg.coffee_machine;

public class BasicCoffeeMachine implements CoffeeMachine{
    private BasicContainer container;


    public BasicCoffeeMachine() {
        this.container = new BasicContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        if(beverage instanceof Espresso)
            if(container.enoughEspresso())
                return new Product(beverage, "Basic");
        return null;
    }

    @Override
    public Container getSupplies() {
        return container;
    }

    @Override
    public void refill() {
        // use const??
        this.container.setCacao(0);
        this.container.setCoffee(600);
        this.container.setMilk(0);
        this.container.setWater(600);
    }

    private void orderEspresso() {
        container.setCoffee(container.getCoffee()- 10);
        container.setWater(container.getWater()- 10);
    }
}

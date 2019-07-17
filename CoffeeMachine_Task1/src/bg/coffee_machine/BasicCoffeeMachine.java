package bg.coffee_machine;

public class BasicCoffeeMachine implements CoffeeMachine{
    private BasicContainer container;

    public BasicCoffeeMachine() {
        this.container = new BasicContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        if(beverage instanceof Espresso)
            if(enoughSupplies(beverage)) {
                decreaseSupplies(beverage);
                return new Product(beverage, null);
            }
        return null;
    }

    @Override
    public Container getSupplies() {
        return container;
    }

    @Override
    public void refill() {
        this.container.setCoffee(600);
        this.container.setWater(600);
    }

    private boolean enoughSupplies(Beverage beverage){
        return container.getCurrentWater() >= beverage.getWater()
                && container.getCurrentCacao() >= beverage.getCacao()
                && container.getCurrentMilk() >= beverage.getMilk()
                && container.getCurrentCoffee() >= beverage.getCoffee();
    }
    private void decreaseSupplies(Beverage beverage) {
        container.setCoffee(container.getCurrentCoffee() - beverage.getCoffee());
        container.setWater((container.getCurrentWater() - beverage.getWater()));
    }
}

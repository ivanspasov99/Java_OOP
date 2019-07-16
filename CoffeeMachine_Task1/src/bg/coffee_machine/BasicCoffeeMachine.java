package bg.coffee_machine;

public class BasicCoffeeMachine implements CoffeeMachine{
    private BasicContainer container;

    public BasicCoffeeMachine() {
        this.container = new BasicContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        if(beverage instanceof Espresso)
            if(enoughEspresso(beverage)) {
                decreaseSuppliesForEspresso(beverage);
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
    public boolean enoughEspresso(Beverage beverage){
        return container.getCurrentCoffee() >= beverage.getCoffee() && container.getCurrentWater() >= beverage.getWater();
    }

    private void decreaseSuppliesForEspresso(Beverage beverage) {
        container.setCoffee(container.getCurrentCoffee() - beverage.getCoffee());
        container.setWater(container.getCurrentWater()- beverage.getWater());
    }

}

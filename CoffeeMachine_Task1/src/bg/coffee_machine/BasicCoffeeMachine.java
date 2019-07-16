package bg.coffee_machine;

public class BasicCoffeeMachine implements CoffeeMachine{
    private BasicContainer container;
    private final String[] allowedLucks = {};

    public BasicCoffeeMachine() {
        this.container = new BasicContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        if(beverage instanceof Espresso)
            if(enoughEspresso(beverage)) {
                decreaseSuppliesForEspresso(beverage);
                return new Product(beverage, allowedLucks);
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
        if(container.getCurrentCoffee() >= beverage.getCoffee() && container.getCurrentWater() >= beverage.getWater() )
            return true;
        else
            return false;
    }

    private void decreaseSuppliesForEspresso(Beverage beverage) {
        container.setCoffee(container.getCurrentCoffee() - beverage.getCoffee());
        container.setWater(container.getCurrentWater()- beverage.getWater());
    }

}

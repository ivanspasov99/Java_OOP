package bg.fmi.mjt.lab.coffee_machine;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.container.BasicContainer;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;

public class BasicCoffeeMachine extends BaseCoffeeMachine implements CoffeeMachine{
    private BasicContainer container;

    public BasicCoffeeMachine() {
        this.container = new BasicContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        if(!(beverage instanceof Espresso)) return null;
        if (!enoughSupplies(this.container, beverage, 1)) {
            return null;
        }
        decreaseSupplies(this.container, beverage, 1);
        return new Product(beverage, null);
    }
    @Override
    public Container getSupplies() {
        return container;
    }

    @Override
    public void refill() {
        this.container.refill();
    }
}

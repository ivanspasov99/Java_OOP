package bg.fmi.mjt.lab.coffee_machine;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
public interface CoffeeMachine {
    Product brew(Beverage beverage);
    Container getSupplies();
    void refill();
}

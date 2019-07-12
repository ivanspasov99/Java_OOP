package bg.coffee_machine;

public interface CoffeeMachine {
    Product brew(Beverage beverage);
    Container getSupplies();
    void refill();
}

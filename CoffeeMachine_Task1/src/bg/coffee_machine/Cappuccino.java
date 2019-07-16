package bg.coffee_machine;

public class Cappuccino implements Beverage{
    private static final String name = "Cappuccino";
    private static final double coffee = 18;
    private static final double milk = 150;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getMilk() {
        return milk;
    }

    @Override
    public double getCoffee() {
        return coffee;
    }

    @Override
    public double getWater() {
        return 0;
    }

    @Override
    public double getCacao() {
        return 0;
    }
}

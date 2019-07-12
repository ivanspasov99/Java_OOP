package bg.coffee_machine;

public class Espresso implements Beverage{
    private final String name = "Espresso";
    private final double coffee = 10;
    private final double water = 10;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getMilk() {
        return 0;
    }

    @Override
    public double getCoffee() {
        return coffee;
    }

    @Override
    public double getWater() {
        return water;
    }

    @Override
    public double getCacao() {
        return 0;
    }
}

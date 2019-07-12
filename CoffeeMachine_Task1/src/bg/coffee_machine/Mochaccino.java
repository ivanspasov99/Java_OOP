package bg.coffee_machine;

public class Mochaccino implements  Beverage {
    private final String name = "Mochaccino";
    private final double coffee = 18;
    private final double milk = 150;
    private final double cacao = 10;

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
        return cacao;
    }
}

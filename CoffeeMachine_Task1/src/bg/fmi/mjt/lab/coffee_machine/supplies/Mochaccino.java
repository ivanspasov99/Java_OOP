package bg.fmi.mjt.lab.coffee_machine.supplies;

public class Mochaccino implements  Beverage {
    private static final String name = "Mochaccino";
    private static final double coffee = 18;
    private static final double milk = 150;
    private static final double cacao = 10;

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

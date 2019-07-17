package bg.fmi.mjt.lab.coffee_machine.container;

public class BasicContainer extends Container {
    private static final double maxCoffee = 600;
    private static final double maxWater = 600;
    private static final double maxMilk = 0;
    private static final double maxCacao = 0;

    public BasicContainer() {
        super(maxCoffee, maxMilk, maxWater, maxCacao);
    }

    public void refill() {
       setCoffee(maxCoffee);
       setWater(maxWater);
    }

}

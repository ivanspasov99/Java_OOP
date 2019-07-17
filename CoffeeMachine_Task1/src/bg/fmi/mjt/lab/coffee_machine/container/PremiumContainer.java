package bg.fmi.mjt.lab.coffee_machine.container;
// could extend BasicContainer
public class PremiumContainer extends Container {
    private static final double maxCoffee = 1000;
    private static final double maxWater = 1000;
    private static final double maxMilk = 1000;
    private static final double maxCacao = 300;

    public PremiumContainer() {
        super(maxCoffee, maxMilk, maxWater, maxCacao);
    }

    public void setMilk(double milk) {
        this.milk = milk;
    }
    public void setCacao(double cacao) {
        this.cacao = cacao;
    }

    public void refill() {
        setCacao(maxCacao);
        setCoffee(maxCoffee);
        setMilk(maxMilk);
        setWater(maxWater);
    }
}

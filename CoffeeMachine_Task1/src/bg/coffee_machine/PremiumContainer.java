package bg.coffee_machine;

public class PremiumContainer extends Container {

    public PremiumContainer() {
        super(1000, 1000, 1000, 300);
    }

    public void setMilk(double milk) {
        this.milk = milk;
    }
    public void setCacao(double cacao) {
        this.cacao = cacao;
    }
}

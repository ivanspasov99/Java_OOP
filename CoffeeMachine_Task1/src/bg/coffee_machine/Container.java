package bg.coffee_machine;

public abstract class Container {
    double coffee;
    double milk;
    double water;
    double cacao;

    Container(double coffee, double milk, double water, double cacao) {
        this.coffee = coffee;
        this.water = water;
        this.milk = milk;
        this.cacao = cacao;
    }

    public double getCurrentCoffee() {
        return coffee;
    }
    public double getCurrentMilk() {
        return milk;
    }
    public double getCurrentWater() {
        return water;
    }
    public double getCurrentCacao() {
        return cacao;
    }

    public void setCoffee(double coffee) {
        this.coffee = coffee;
    }
    public void setWater(double water) {
        this.water = water;
    }
}

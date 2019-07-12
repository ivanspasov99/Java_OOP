package bg.coffee_machine;

public abstract class Container {
    private double coffee;
    private double milk;
    private double water;
    private double cacao;

    protected Container(double coffee, double milk, double water, double cacao) {
        this.coffee = coffee;
        this.milk = milk;
        this.water = water;
        this.cacao = cacao;
    }

    public double getCoffee() {
        return coffee;
    }

    public void setCoffee(double coffee) {
        this.coffee = coffee;
    }

    public double getMilk() {
        return milk;
    }

    public void setMilk(double milk) {
        this.milk = milk;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getCacao() {
        return cacao;
    }

    public void setCacao(double cacao) {
        this.cacao = cacao;
    }

    public boolean enoughEspresso(){
        if(getCoffee() >= 10 && getWater() >= 30 ) return true;
        else return false;
    }
}

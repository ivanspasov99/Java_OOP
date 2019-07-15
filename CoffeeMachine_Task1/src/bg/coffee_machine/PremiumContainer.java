package bg.coffee_machine;

public class PremiumContainer extends Container {

    public PremiumContainer() {
        super(1000, 1000, 1000, 300);
    }
    public boolean enoughCappuccino(){
        // Maybe a function for->
        if(getCurrentCoffee() >= 18 && getCurrentMilk() >= 150 ) return true;
        else return false;
    }
    public boolean enoughMochaccino(){
        if(getCurrentCoffee() >= 18 && getCurrentMilk() >= 150 && getCurrentCacao() >= 10) return true;
        else return false;
    }
}

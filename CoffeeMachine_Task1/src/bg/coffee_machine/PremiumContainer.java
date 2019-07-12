package bg.coffee_machine;

public class PremiumContainer extends Container {

    public PremiumContainer() {
        super(1000, 1000, 1000, 300);
    }
    public boolean enoughCappuccino(){
        // Maybe a function for->
        if(getCoffee() >= 18 && getMilk() >= 150 ) return true;
        else return false;
    }
    public boolean enoughMochaccino(){
        if(getCoffee() >= 18 && getMilk() >= 150 && getCacao() >= 10) return true;
        else return false;
    }
}

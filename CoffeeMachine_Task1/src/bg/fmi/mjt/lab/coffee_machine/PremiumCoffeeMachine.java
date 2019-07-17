package bg.fmi.mjt.lab.coffee_machine;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.container.PremiumContainer;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Cappuccino;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;
import bg.fmi.mjt.lab.coffee_machine.supplies.Mochaccino;

public class PremiumCoffeeMachine extends BaseCoffeeMachine implements CoffeeMachine {
    private PremiumContainer container;
    private boolean autoRefill = false;
    private int luckCounter = 0;
    private final String[] Lucks = {
            "If at first you don't succeed call it version 1.0.",
            "Today you will make magic happen!",
            "Have you tried turning it off and on again?",
            "Life would be much more easier if you had the source code.",
    };

    public PremiumCoffeeMachine(boolean autoRefill) {  this(); this.autoRefill = autoRefill; }
    public PremiumCoffeeMachine() {
        this.container = new PremiumContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        //if(beverage == null) return null;
       /* int flag = (enoughSupplies(this.container, beverage)) ? 1 : 0;
        switch (flag) {
            case 0: { if(autoRefill) refill(); else return null; }
            case 1: {
                decreaseSupplies(this.container, beverage);
                incrementLuckCounter();
                return new Product(beverage, Lucks[luckCounter]);
            }
            default: return null; // cannot be accessed!!!
        }*/
        return brew(beverage, 1);
    }
    public Product brew(Beverage beverage, int quantity){
        if(!(beverage instanceof Espresso) && !(beverage instanceof Cappuccino) && !(beverage instanceof Mochaccino)) return null;
        if(quantity > 3 || quantity <= 0) {
            return null;
        }

        if(!enoughSupplies(this.container, beverage, quantity)){
            if(autoRefill) {
                refill();
            }
            else {
                return null;
            }
        }
        decreaseSupplies(this.container, beverage, quantity);

        resetCounterCheck();
        return new Product(beverage, Lucks[luckCounter++], quantity);

    }

    @Override
    public Container getSupplies() {
        return container;
    }

    @Override
    public void refill() {
        this.container.refill();
    }

    private void resetCounterCheck(){
        if(luckCounter == 4) luckCounter = 0;
    }
}

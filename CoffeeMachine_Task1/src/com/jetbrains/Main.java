package com.jetbrains;
import bg.fmi.mjt.lab.coffee_machine.BasicCoffeeMachine;
import bg.fmi.mjt.lab.coffee_machine.PremiumCoffeeMachine;
import bg.fmi.mjt.lab.coffee_machine.supplies.*;

public class Main {
    public static void main(String[] args) {
        Mochaccino moca = new Mochaccino();
        Espresso es = new Espresso();
        Cappuccino cap = new Cappuccino();

        BasicCoffeeMachine bC = new BasicCoffeeMachine();
        //System.out.println(bC.brew(null).getLuck());
        System.out.println(bC.brew(es));
        System.out.println(bC.brew(es));
        System.out.println(bC.brew(cap));
        System.out.println(bC.brew(moca));

        PremiumCoffeeMachine pC = new PremiumCoffeeMachine();
        //System.out.println(pC.brew(null));
        //System.out.println(pC.brew(es,3));
        //System.out.println(pC.brew(es,3));
//        System.out.println(pC.brew(cap));
//        System.out.println(pC.brew(cap,3));
//        System.out.println(pC.brew(moca,3));
//        System.out.println(pC.brew(moca,1));
//        System.out.println(pC.brew(moca,1));
//        System.out.println(pC.brew(moca,1));
    }
}

package bg.coffee_machine;

public class Product {
    private Beverage product;
    private static int quantity = 0;
    private static int counter = 0;
    private String machineType;
    private final String[] lucks = {
            "If at first you don't succeed call it version 1.0.",
            "Today you will make magic happen!",
            "Have you tried turning it off and on again?",
            "Life would be much more easier if you had the source code.",
    };

    public Product(Beverage beverage, String machineType){
        this.product = beverage;
        this.machineType = machineType;
        incrementQuantity();
    } // protected??

    private void incrementQuantity(){
        quantity++;
    }

    public String getName() {
        return this.product.getName();
    }
    public int getQuantity(){ return quantity; }

    public String getLuck() {
        if(machineType.equals("Premium")){
            if(counter == 3) counter = 0;
                return lucks[counter++];
        }
        return null;

    }
}

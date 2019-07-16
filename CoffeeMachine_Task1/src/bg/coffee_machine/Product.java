package bg.coffee_machine;

public class Product {
    private Beverage product;
    private static int quantity = 0;
    private static int counter = 0;
    private String[] lucks = {};

    public Product(Beverage beverage, String[] allowedLucks){
        this.product = beverage;
        this.lucks = allowedLucks.clone();
        incrementQuantity();
    }

    private void incrementQuantity(){
        quantity++;
    }

    public String getName() {
        return this.product.getName();
    }
    public int getQuantity(){ return quantity; }

    public String getLuck() {
        if(lucks.length != 0){
            if(counter == 3) counter = 0;
                return lucks[counter++];
        }
        return null;

    }
}

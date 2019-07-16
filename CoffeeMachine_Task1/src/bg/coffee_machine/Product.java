package bg.coffee_machine;

public class Product {
    private Beverage product;
    private static int quantity = 0;
    private String luck;

    public Product(Beverage beverage, String luck){
        this.product = beverage;
        this.luck = luck;
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
        return this.luck;
    }
}

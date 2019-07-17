package bg.fmi.mjt.lab.coffee_machine;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
public class Product {
    private Beverage beverage;
    private int quantity; // !!!
    private String luck;

    public Product(Beverage beverage, String luck){
        this(beverage, luck, 1);
    }
    public Product(Beverage beverage, String luck, int quantity){
        this.beverage = beverage;
        this.luck = luck;
        this.quantity = quantity;
    }


    public String getName() {
        return this.beverage.getName();
    }
    public int getQuantity(){ return quantity; }

    public String getLuck() {
        return this.luck;
    }
}

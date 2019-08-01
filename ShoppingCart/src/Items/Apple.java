package Items;

public class Apple extends ItemAbstract{
    public Apple() {
        super();
        this.name = "Apple";
    }
    public Apple(String name, String desc, double price) {
        super(name, desc, price);
    }
}

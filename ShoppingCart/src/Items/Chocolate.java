package Items;

public class Chocolate extends ItemAbstract {

    public Chocolate() {
        super();
        this.name = "Chocolate";
    }
    public Chocolate(String name, String desc, double price) {
        super(name, desc, price);
    }
}

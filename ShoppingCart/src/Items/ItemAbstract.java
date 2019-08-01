package Items;

import Interfaces.Item;

public class ItemAbstract implements Item {
    String name = "Item";
    private String desc;
    private double price;

    public ItemAbstract() {}
    public ItemAbstract(String name, String desc, double price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescritpion() {
        return desc;
    }

    @Override
    public double getPrice() {
        return price;
    }

    // frequency is using equals, so if we check by name
    @Override
    public boolean equals(Object obj) {
        Item item = (Item) obj;
        return this.name.equals(item.getName());
    }
}

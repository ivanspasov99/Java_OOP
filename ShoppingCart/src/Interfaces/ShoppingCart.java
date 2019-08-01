package Interfaces;

import java.util.Collection;

public interface ShoppingCart {
    Collection<Item> getUniqueItems();
    Collection<Item> getSortedItems(); // by quantity
    void addItem(Item item);
    void removeItem(Item item); // remove item, first quantity, throws ItemNotFoundException
    double getTotal();
}

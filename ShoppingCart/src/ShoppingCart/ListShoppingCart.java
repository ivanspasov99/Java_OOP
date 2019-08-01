package ShoppingCart;

import Interfaces.Item;
import Interfaces.ShoppingCart;
import Items.Apple;
import Items.Chocolate;

import javax.swing.text.html.parser.Entity;
import java.util.*;

public class ListShoppingCart implements ShoppingCart {
    // duplicates
    List<Item> list = new ArrayList<Item>();
    // better design???
    int appleCounter = 0;
    int chocCounter = 0;
    @Override
    public Collection<Item> getUniqueItems() {
        final int FIRST_ITEM = 0;
        List<Item> tempList = new ArrayList<>(list);

        List<Item> newList = new ArrayList<>();
        newList.add(tempList.get(0));
        tempList.removeAll(Collections.singleton(tempList.get(FIRST_ITEM)));
        if(tempList.size() > 1) {
            newList.add(tempList.get(1));
            tempList.removeAll(Collections.singleton(tempList.get(1)));
        }
        return newList;
    }

    @Override
    public Collection<Item> getSortedItems() {
        if(appleCounter > chocCounter) {
            return new ArrayList<>(Arrays.asList(new Apple(), new Chocolate()));
        }
        return new ArrayList<>(Arrays.asList(new Chocolate(), new Apple()));
    }

    @Override
    public void addItem(Item item) {
        if(item != null) {
            list.add(item);
            if(item instanceof Apple) {
                appleCounter++;
            }
            else {
                chocCounter++;
            }
        }
     }

    @Override
    public void removeItem(Item item) {
        list.remove(item);
    }

    @Override
    public double getTotal() {
        double totalPrice = 0;
        for (Item item: list) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}

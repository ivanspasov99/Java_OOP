package ShoppingCart;

import Interfaces.Item;
import Interfaces.ShoppingCart;

import java.util.*;

public class MapShoppingCart implements ShoppingCart {
    private Map<Item, Integer> map = new HashMap<>();
    private int totalPrice = 0;

    @Override
    public Collection<Item> getUniqueItems() {
        Collection<Item> coll = new HashSet<>(map.keySet());
        // Second way with TreeMap and Comparator
        return coll;
    }

    @Override
    public Collection<Item> getSortedItems() {
        List<Map.Entry<Item, Integer>> listOfEntries = new ArrayList<>(map.entrySet());

        // FIRST WAY
        // coll.sort(new QuantityComparator());

        // SECOND WAY
        Collections.sort(listOfEntries, Comparator.comparing(Map.Entry::getValue));
        Collections.reverse(listOfEntries);

        Collection<Item> sortedListOfEntries = new ArrayList<>();
        for (Map.Entry<Item, Integer> entry : listOfEntries) {
            sortedListOfEntries.add(entry.getKey());
        }
        return sortedListOfEntries;
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            totalPrice += item.getPrice();
            if (!map.containsKey(item)) {
                map.put(item, 0);
            }
            map.replace(item, map.get(item) + 1);

        }
    }

    @Override
    public void removeItem(Item item) {
        if (item != null) {
            map.remove(item);
            totalPrice -= item.getPrice();
        }
    }

    @Override
    public double getTotal() {
        return totalPrice;
    }
}

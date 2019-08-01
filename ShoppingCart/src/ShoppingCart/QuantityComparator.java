package ShoppingCart;

import java.util.Comparator;
import java.util.Map;

public class QuantityComparator implements Comparator<Map.Entry> {
        // interesting way
        @Override
        public int compare(Map.Entry one, Map.Entry two) {
            Integer newInt = (Integer)one.getValue();
            Integer newInt2 = (Integer)two.getValue();
            return  newInt2 - newInt;

        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
}

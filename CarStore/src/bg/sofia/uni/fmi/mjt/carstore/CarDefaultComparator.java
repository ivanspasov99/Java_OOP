package bg.sofia.uni.fmi.mjt.carstore;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;

import java.util.Comparator;

public class CarDefaultComparator implements Comparator<Car> {
    @Override
    public int compare(Car o1, Car o2) {
        // i dont like it
        final int compareByModel = o1.getModel().compareTo(o2.getModel());
        return compareByModel == 0 ? o1.getYear() - o2.getYear() : compareByModel;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}

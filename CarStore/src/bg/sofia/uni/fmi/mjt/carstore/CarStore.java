package bg.sofia.uni.fmi.mjt.carstore;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.exception.CarNotFoundException;

import java.util.*;

public class CarStore {
    Set<Car> store = new TreeSet<>(new CarDefaultComparator());

    // validate
    public boolean add(Car car) {
        if(store.contains(car)) {
            return false;
        }
        return this.store.add(car);
    }
    public boolean addAll(Collection<Car> cars) {
        if(store.containsAll(cars)) {
            return false;
        }
        return store.addAll(cars);
    }
    public boolean remove(Car car) {
        if(!store.contains(car)) {
            return false;
        }
        return store.remove(car);
    }
    public Collection<Car> getCarsByModel(Model model) {
        // need insertion order
        Set<Car> tempColl = new LinkedHashSet<>();

        // could be done with for-each
        Iterator<Car> i = store.iterator();
        while (i.hasNext()) {
            Car tempCar = i.next();
            if(tempCar.getModel() == model) {
                tempColl.add(tempCar);
            }
        }
        return tempColl;
    }
    public Car getCarByRegistrationNumber(String registrationNumber) {
            for (Car car : store) {
                if (car.getRegistrationNumber().equals(registrationNumber)) {
                    return car;
                }
            }
            String mess = String.format("Car with registration number %s not found", registrationNumber);
            throw new CarNotFoundException(mess);

    }
    public Collection<Car> getCars(Comparator<Car> comp, boolean isReversed) {
        if(!isReversed) {
            return getCars(comp);
        }
        Collection<Car> descendingCollection = new TreeSet<>(comp).descendingSet();
        descendingCollection.addAll(store);
        return descendingCollection;
    }
    public Collection<Car> getCars() {
        // important if we copy or we return
        Set<Car> copy = new HashSet<>();
        copy.addAll(store);
        return copy;
        //return Set.copyOf(store);
    }
    public Collection<Car> getCars(Comparator<Car> comparator) {
        // default comparator is going to sort them when we are inserting them
        Collection<Car> sortedCollection = new TreeSet<>(comparator);
        sortedCollection.addAll(store);
        return sortedCollection;
    }
    public int getNumberOfCars() {
        return store.size();
    }
    public int getTotalPriceForCars() {
        int carsPrice = 0;
        for (Car obj: store) {
            carsPrice += obj.getPrice();
        }
        return carsPrice;
    }
}

package com.jetbrains;

import bg.sofia.uni.fmi.mjt.carstore.CarStore;
import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.car.SportsCar;
import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

import java.util.Collection;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        Car one = new SportsCar(Model.AUDI, 1000, 11111, EngineType.DIESEL, Region.SOFIA);
        Car two = new SportsCar(Model.ALFA_ROMEO, 1200, 333333, EngineType.DIESEL, Region.SOFIA);
        Car three = new SportsCar(Model.BMW, 1444, 222222, EngineType.DIESEL, Region.PLOVDIV);
        Car four = new SportsCar(Model.AUDI, 999, 222222, EngineType.DIESEL, Region.PLOVDIV);
        Car five = new SportsCar(Model.AUDI, 1000, 222222, EngineType.DIESEL, Region.PLOVDIV);

        CarStore store = new CarStore();
        store.add(one);
        store.add(two);
        store.add(three);
        store.add(four);
        store.add(five);
        System.out.println("Yes");
        Collection<Car> storeCopy = store.getCars();
        storeCopy.clear();

        System.out.println(store.getNumberOfCars());
    }
}

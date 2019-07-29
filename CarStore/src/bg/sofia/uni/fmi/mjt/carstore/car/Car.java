package bg.sofia.uni.fmi.mjt.carstore.car;

import bg.sofia.uni.fmi.mjt.carstore.CarRegister.RegistrationNumber;
import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;
// !!! COULD HAVE VALIDATION CLASS FOR EVERY FILED THAT CAN TAKE NULL VALUE AND WE DO NOT WANT
public abstract class Car {
    private String regNum;
    private int price;
    private final int year;
    private final Model model;
    private final EngineType engineType;

    Car(Model model, int year, int price, EngineType engineType, Region region) {
        this.model = model;
        this.year = year;
        this.price = price;
        this.engineType = engineType;
        this.regNum = RegistrationNumber.generateRegNumber(region);
    }

    public String getRegistrationNumber() {
        return regNum;
    }

    public int getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public Model getModel() {
        return model;
    }

    public EngineType getEngineType() {
        return engineType;
    }
}

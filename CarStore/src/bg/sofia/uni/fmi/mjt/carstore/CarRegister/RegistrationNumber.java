package bg.sofia.uni.fmi.mjt.carstore.CarRegister;

import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

import java.util.Random;

public abstract class RegistrationNumber {
    public static String generateRegNumber(Region region) {
        Random rnd = new Random();
        return region.getPrefix()
                + RegionCarsCount.generateRegionNumber(region)
                + (char) (rnd.nextInt(26) + 'A')
                + (char) (rnd.nextInt(26) + 'A');
    }
}

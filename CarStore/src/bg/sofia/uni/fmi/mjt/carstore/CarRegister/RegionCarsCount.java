package bg.sofia.uni.fmi.mjt.carstore.CarRegister;

import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public abstract class RegionCarsCount {
    // wanted to be with EnumMap
    private static Map<String, Integer> map = new HashMap<>();
    private static final int startNumber = 1000;
    private static final int incrementNumber = 1;

    public static int generateRegionNumber(Region region) {
        final String prefix = region.getPrefix();
        if(!map.containsKey(prefix)) {
            map.put(prefix, startNumber);
        } else {
            map.replace(prefix, map.get(prefix) + incrementNumber);
        }
        return map.get(prefix);
    }
}

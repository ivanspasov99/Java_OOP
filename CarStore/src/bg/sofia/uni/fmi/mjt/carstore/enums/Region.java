package bg.sofia.uni.fmi.mjt.carstore.enums;

public enum Region {
    SOFIA("CB"),
    BURGAS("A"),
    VARNA("B"),
    PLOVDIV("PB"),
    RUSE("P"),
    GABROVO("EB"),
    VIDIN("BH"),
    VRATSA("BP");

    private String prefix;

    Region(String shortName) {
        this.prefix = shortName;
    }

    public String getPrefix() {
        return prefix;
    }
}

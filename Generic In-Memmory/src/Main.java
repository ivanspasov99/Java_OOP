public class Main {

    public static void main(String[] args) {
        Double d1 = 0.000_000_001_0;
        Double d2 = 0.000_000_001_2;
        Double delta = 0.000_000_000_1;
        if (Double.compare(d1, d2) == 0) {
            System.out.println(Double.compare(d1, d2));
        } else {
            System.out.println(Math.abs(d1 - d2) < delta);
        }
    }
}

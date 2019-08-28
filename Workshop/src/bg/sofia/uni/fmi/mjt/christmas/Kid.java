package bg.sofia.uni.fmi.mjt.christmas;

public class Kid extends Thread {
    private WorksShop worksShop;
    private static final int TIME_TO_CHOOSE_GIFT = 1000;

    public Kid(WorksShop worksShop) {
        this.worksShop = worksShop;
    }

    public void makeWish(Gift gift) {
        try {
            Thread.sleep(TIME_TO_CHOOSE_GIFT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worksShop.postWish(gift);
    }

    @Override
    public void run() {
        this.makeWish(Gift.getGift());
    }
}

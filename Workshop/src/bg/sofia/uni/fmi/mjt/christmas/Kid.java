package bg.sofia.uni.fmi.mjt.christmas;

public class Kid extends Thread {
    private WorkShop workShop;
    private static final int TIME_TO_CHOOSE_GIFT = 1000;

    public Kid(WorkShop workShop) {
        this.workShop = workShop;
    }

    public void makeWish(Gift gift) {
        try {
            Thread.sleep(TIME_TO_CHOOSE_GIFT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workShop.postWish(gift);
    }

    @Override
    public void run() {
        this.makeWish(Gift.getGift());
    }
}

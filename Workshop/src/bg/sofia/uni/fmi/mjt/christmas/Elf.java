package bg.sofia.uni.fmi.mjt.christmas;

public class Elf extends Thread {
    private int id;
    private WorkShop workShop;
    private int giftsMade;

    public Elf(int id, WorkShop workShop) {
        this.id = id;
        this.workShop = workShop;
        this.giftsMade = 0;
    }

    public void craftWish() {
        try {
            Gift gift = workShop.nextGift();
            // could happen on Christmas time
            if (gift == null) {
                return;
            }
            Thread.sleep(gift.getCraftTime());
            workShop.addFinishedGiftForDelivery(gift);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        giftsMade++;
    }

    public int getTotalGiftsCrafted() {
        return giftsMade;
    }

    @Override
    public void run() {
        while (workShop.getBackLogSize() != 0 || !workShop.isChristmasTime()) {
            craftWish();
        }
    }
}

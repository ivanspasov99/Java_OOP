package bg.sofia.uni.fmi.mjt.christmas;

public class Elf extends Thread {
    private int id;
    private WorksShop worksShop;
    private int giftsMade;

    public Elf(int id, WorksShop worksShop) {
        this.id = id;
        this.worksShop = worksShop;
        this.giftsMade = 0;
    }

    public void craftWish() {
        try {
            Gift gift = worksShop.nextGift();
            // could happen on Christmas time
            if (gift == null) {
                return;
            }
            Thread.sleep(gift.getCraftTime());
            worksShop.addFinishedGiftForDelivery(gift);
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
        while (worksShop.getBackLogSize() != 0 || !worksShop.isChristmasTime()) {
            craftWish();
        }
    }
}

package bg.sofia.uni.fmi.mjt.christmas;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkShop {
    private static final int ELVES_NUMBER = 20;
    private Elf[] elves = new Elf[ELVES_NUMBER];


    private volatile boolean isChristmasTime = false; // with synch
    // good use for Producer Consumer design
    private LinkedBlockingQueue<Gift> backLog;
    private ArrayBlockingQueue<Gift> giftsForDelivery;
    private AtomicInteger wishCount; // could be volatile

    public WorkShop() {
        backLog = new LinkedBlockingQueue<>();
        giftsForDelivery = new ArrayBlockingQueue<>(100);
        wishCount = new AtomicInteger(0);

        // elves Threads, starting to work
        for (int i = 0; i < ELVES_NUMBER; i++) {
            elves[i] = new Elf(i, this);
            elves[i].start();
        }
        // they are going to execute run method until Christmas time (another Thread will track)
        // they will wait if no gift in backLog
    }

    public void postWish(Gift gift) {
        // put() works with wait() when queue is full
        synchronized (backLog) {
            backLog.offer(gift); // backlog capacity is infinity(for the task)
            wishCount.incrementAndGet(); // i do not want to get value
            backLog.notify();
        }
    }

    public Gift nextGift() {

        synchronized (backLog) {
            try {
                if (backLog.isEmpty()) {
                    backLog.wait(); // require to be owner of the object's monitor
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return backLog.poll();
        }
    }

    public Elf[] getElves() {
        return elves;
    }

    public int getWishCount() {
        return wishCount.intValue();
    }

    public int getBackLogSize() {
        return backLog.size();
    }

    public void addFinishedGiftForDelivery(Gift gift) {
        synchronized (giftsForDelivery) {
            try {
                giftsForDelivery.add(gift);
            } catch (IllegalStateException e) {
                new Santa().start();
                // getting the gifts....
                giftsForDelivery.clear();
                giftsForDelivery.add(gift);
            }
        }

    }

    public int getGiftsForDelivery() {
        return giftsForDelivery.size();
    }

    public void setChristmasTimeOn() {
        this.isChristmasTime = true;
        synchronized (backLog) {
            backLog.notifyAll();
        }
    }

    public boolean isChristmasTime() {
        return this.isChristmasTime;
    }
}

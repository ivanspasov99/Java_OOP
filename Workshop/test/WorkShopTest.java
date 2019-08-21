import bg.sofia.uni.fmi.mjt.christmas.Elf;
import bg.sofia.uni.fmi.mjt.christmas.Kid;
import bg.sofia.uni.fmi.mjt.christmas.WorkShop;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WorkShopTest {
    private static final int ELVES_NUMBER = 20;
    private static WorkShop workShop;

    @Before
    public void setUp() {
        workShop = new WorkShop();
    }
    // with THREAD -> KID CLASS TESTING
    @Test
    public void testShouldGetCorrectSingleThreadWish() throws InterruptedException {
        Thread kid = new Thread(new Kid(workShop));
        kid.start();
        kid.join();

        assertEquals(1, workShop.getWishCount());
    }
    @Test
    public void testShouldGetOneReadyGift() throws InterruptedException {
        final int KIDS_CREATED = 25;
        List<Thread> kidList = new ArrayList<>();

        for (int i = 0; i < KIDS_CREATED; i++) {
            kidList.add(new Thread(new Kid(workShop), "KID"));
        }

        kidList.forEach(Thread::start);
        for (int i = 0; i < KIDS_CREATED; i++) {
            kidList.get(i).join();
        }
        workShop.setChristmasTimeOn();
        Elf[] elves = workShop.getElves();
        for (int i = 0; i < ELVES_NUMBER; i++) {
            elves[i].join();
        }

        assertEquals(KIDS_CREATED, workShop.getWishCount());
        assertEquals(KIDS_CREATED, workShop.getGiftsForDelivery());
    }
    @Test
    public void testShouldGetMultipleReadyGifts() throws InterruptedException {
        final int KIDS_CREATED = 401;
        List<Thread> kidList = new ArrayList<>();

        for (int i = 0; i < KIDS_CREATED; i++) {
            kidList.add(new Thread(new Kid(workShop), "KID"));
        }

        kidList.forEach(Thread::start);
        for (int i = 0; i < KIDS_CREATED; i++) {
            kidList.get(i).join();
        }
        workShop.setChristmasTimeOn();
        Elf[] elves = workShop.getElves();
        for (int i = 0; i < ELVES_NUMBER; i++) {
            elves[i].join();
        }
        // 1 is remaining because santa is getting 100 each time (when reached)
        assertEquals(KIDS_CREATED, workShop.getWishCount());
        assertEquals(1, workShop.getGiftsForDelivery());
    }
}

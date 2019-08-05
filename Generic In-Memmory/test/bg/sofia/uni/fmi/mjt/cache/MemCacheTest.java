package bg.sofia.uni.fmi.mjt.cache;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

// how to test with generics
public class MemCacheTest {
    private static Cache<String, Integer> cache;
    private static Cache<String, Integer> cacheWithLongCapacity;

    @Before
    public void setUp() {
        cache = new MemCache<>();
        cacheWithLongCapacity = new MemCache<>(3);
    }

    @Test
    public void testShouldReturnNull() {
        Integer result = cacheWithLongCapacity.get(null);
        assertNull(result);
    }

    @Test
    public void testShouldReturnNullValue() {
        Integer result = cacheWithLongCapacity.get("ivan");
        assertNull(result);
    }

    @Test
    public void testShouldNotPutNullValueToTheCache() throws CapacityExceededException {
        cacheWithLongCapacity.set(null, 1, null);
        assertEquals(0, cacheWithLongCapacity.size());
        cacheWithLongCapacity.set("", null, null);
        assertEquals(0, cacheWithLongCapacity.size());
    }

    @Test
    public void testShouldPutIntegerValuesToTheCache() throws CapacityExceededException {
        cacheWithLongCapacity.set("First", 1, null);
        assertEquals(1, cacheWithLongCapacity.get("First").intValue());
        assertEquals(1, cacheWithLongCapacity.size());

        cacheWithLongCapacity.set("Second", 2, null);
        assertEquals(2, cacheWithLongCapacity.get("Second").intValue());
        assertEquals(2, cacheWithLongCapacity.size());
    }

    @Test
    public void testShouldGetAllItems() throws CapacityExceededException {
        final int SIZE = 3;
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }

        for (int i = 0; i < SIZE; i++) {
            cacheWithLongCapacity.set("Number: "+i, list.get(i), null);
        }

        for (int i = 0; i < SIZE; i++) {
            assertEquals(i, cacheWithLongCapacity.get("Number: "+i).intValue());
        }
        assertEquals(3, cacheWithLongCapacity.size());
    }

    @Test
    public void testShouldGetExpirationDate() throws CapacityExceededException {
        LocalDateTime time = LocalDateTime.now().plusDays(1);
        cacheWithLongCapacity.set("First", 1, time);
        assertEquals(time, cacheWithLongCapacity.getExpiration("First"));
    }

    @Test
    public void testShouldGetNullExpirationDateWhenElementNotExist() {
        assertNull(cacheWithLongCapacity.getExpiration("First"));
    }

    @Test
    public void testShouldReplaceValueAndTime() throws CapacityExceededException {
        LocalDateTime time = LocalDateTime.now().plusDays(1);

        cacheWithLongCapacity.set("First", 1, null);
        assertEquals(1, cacheWithLongCapacity.get("First").intValue());
        assertNull(cacheWithLongCapacity.getExpiration("First"));

        cacheWithLongCapacity.set("First", 4, time);
        assertEquals(4, cacheWithLongCapacity.get("First").intValue());
        assertEquals(time, cacheWithLongCapacity.getExpiration("First"));

        assertEquals(1, cacheWithLongCapacity.size());
    }

    @Test
    public void testShouldRemoveExpiredItemWhenGetIt() throws CapacityExceededException {
        LocalDateTime time = LocalDateTime.now().minusDays(1);

        cacheWithLongCapacity.set("First", 1, time);

        Integer result = cacheWithLongCapacity.get("First");
        assertEquals(0, cacheWithLongCapacity.size());
        assertNull(result);
    }

    @Test
    public void testShouldRemoveExpiredItemWhenAddingNewWithExpiredCapacity() throws CapacityExceededException {
        LocalDateTime notExpiredTime = LocalDateTime.now().plusDays(1);
        LocalDateTime expiredTime = LocalDateTime.now().minusDays(1);

        cacheWithLongCapacity.set("First", 1, notExpiredTime);
        cacheWithLongCapacity.set("Second", 2, null);
        cacheWithLongCapacity.set("Third", 3, expiredTime);

        assertEquals(3, cacheWithLongCapacity.size());

        cacheWithLongCapacity.set("Fourth", 4, null);

        assertEquals(3, cacheWithLongCapacity.size());

        assertNull(cacheWithLongCapacity.get("Third"));
        assertEquals(4, cacheWithLongCapacity.get("Fourth").intValue());
    }

    @Test(expected = CapacityExceededException.class)
    public void testShouldThrowExceptionWhenItemsAreNotExpired() throws CapacityExceededException {
        for (int i = 0; i < 4; i++) {
            cacheWithLongCapacity.set("First" + i, 1, null);
        }
    }

    @Test
    public void testShouldRemoveItemFromCacheSuccessfully() throws CapacityExceededException {
        cacheWithLongCapacity.set("First", 1, null);
        assertTrue(cacheWithLongCapacity.remove("First"));

    }

    @Test
    public void testShouldRemoveItemFromCacheUnsuccessfully() throws CapacityExceededException {
        cacheWithLongCapacity.set("First1", 1, null);
        assertFalse(cacheWithLongCapacity.remove("First"));
    }

    @Test
    public void testShouldNotExecuteClearWhenEmpty() {
        cacheWithLongCapacity.clear();
        assertEquals(0, cacheWithLongCapacity.size());
    }

    @Test
    public void testShouldRemoveAllItemsInCache() throws CapacityExceededException {
        cacheWithLongCapacity.set("First", 1, null);
        cacheWithLongCapacity.set("Second", 2, null);
        cacheWithLongCapacity.set("Fourth", 4, null);

        cacheWithLongCapacity.clear();
        assertEquals(0, cacheWithLongCapacity.size());
    }

    @Test
    public void testShouldReturnZeroSuccessfulHitRate() {
        assertEquals(0.0, cacheWithLongCapacity.getHitRate(), 1);
    }

    @Test
    public void testShouldReturnMaxSuccessfulRate() throws CapacityExceededException {
        cacheWithLongCapacity.set("Fourth", 4, null);
        assertEquals(1.0, cacheWithLongCapacity.getHitRate(), 1);
    }

    @Test
    public void testShouldReturnSpecificHitRate() throws CapacityExceededException {
        for (int i = 0; i < 6; i++) {
            cache.set("First" + i, 1, null);
        }
        for (int i = 0; i < 4; i++) {
            cache.set("First" + i, null, null);
        }
        assertEquals(0, cache.getHitRate(), 1);
    }

    @Test
    public void testShouldReturnSpecificHitRatePeriod() throws CapacityExceededException {
        cache.set("First", 1, null);

        for (int i = 0; i < 3; i++) {
            cache.set("First" + i, null, null);
        }
        assertEquals(0.2, cache.getHitRate(), 1);
    }

    @Test
    public void testShouldReturnSpecificHiRationNull() throws CapacityExceededException {
        cache.set("First", 1, null);
        cache.get("First");
        cache.get("asd");
        assertEquals(0.5, cache.getHitRate(), 1);
    }

    @Test
    public void testShouldReturnSpecificHiRationNull2() throws CapacityExceededException {
        cache.set("First", 1, null);
        cache.get("asd");
        cache.get("asd");
        assertEquals(0, cache.getHitRate(), 1);
    }
}

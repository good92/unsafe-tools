package com.alexkasko.unsafe;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

/**
* User: alexkasko
* Date: 2/20/13
*/
public class OffHeapSorterTest {
    private static final int THRESHOLD = 1 << 20;

    @Test
    public void test() throws Exception {
        long[] heap = gendata();
        long[] unsafe = heap.clone();
//        long start = System.currentTimeMillis();
        Arrays.sort(heap);
//        System.out.println((System.currentTimeMillis() - start));
        OffHeapLongArray la = new OffHeapLongArray(THRESHOLD);
        for (int i = 0; i < THRESHOLD; i++) {
            la.set(i, unsafe[i]);
        }
//        start = System.currentTimeMillis();
        OffHeapSorter.sort(la, 0, THRESHOLD);
//        System.out.println((System.currentTimeMillis() - start));
        for (int i = 0; i < THRESHOLD; i++) {
            unsafe[i] = la.get(i);
        }
        assertArrayEquals(heap, unsafe);
        la.free();
    }

    private static long[] gendata() throws Exception {
        Random random = new Random(42);
        long[] res = new long[THRESHOLD];
        for (int i = 0; i < THRESHOLD; i++) {
            res[i] = random.nextLong();
        }
        return res;
    }
}
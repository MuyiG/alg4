package sort;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by 光 on 2016/12/16.
 */
public class QuickSort {
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo + 1;
        int j = hi;
        while (true) {
            while (i <= hi && SortUtil.less(a[i], a[lo])) {
                i++;
            }
            while (j >= lo && SortUtil.less(a[lo], a[j])) {
                j--;
            }
            if (i >= j) {
                SortUtil.exchange(a, lo, j);
                return j;
            }
            SortUtil.exchange(a, i, j);
        }
    }
}

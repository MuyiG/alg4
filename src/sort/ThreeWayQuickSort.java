package sort;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by 光 on 2016/12/17.
 */
public class ThreeWayQuickSort {

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        Comparable v = a[lo];
        int lt = lo, gt = hi, i = lo + 1;
        while(i <= gt) {
            if (a[i].compareTo(v) < 0) {
                SortUtil.exchange(a, lt++, i++);
            } else if (a[i].compareTo(v) > 0) {
                SortUtil.exchange(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

}

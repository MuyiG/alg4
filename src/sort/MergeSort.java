package sort;

/**
 * Created by 光 on 2016/12/17.
 */
public class MergeSort {

    public static void sort(Comparable[] a) {
        Comparable[] copy = new Comparable[a.length];

        sort(a, copy, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, Comparable[] copy, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        sort(a, copy, lo, mid);
        sort(a, copy, mid + 1, hi);
        merge(a, copy, lo, mid, hi);
    }

    private static void merge(Comparable[] a, Comparable[] copy, int lo, int mid, int hi) {
        System.arraycopy(a, lo, copy, lo, hi - lo + 1);
        int i = lo, j = lo, k = mid + 1;
        while (i <= hi) {
            if (j > mid) {
                a[i++] = copy[k++];
            } else if (k > hi) {
                a[i++] = copy[j++];
            } else if (SortUtil.less(copy[k], copy[j])) {
                a[i++] = copy[k++];
            } else {
                a[i++] = copy[j++];
            }
        }
    }

}

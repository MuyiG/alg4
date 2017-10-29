package sort;

/**
 * Created by 光 on 2016/11/2.
 */
public class InsertionSort {
    public static void sort(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            int pos = i;
            Comparable temp = a[i];
            for (int j = i; j > 0; j--) {
                if (SortUtil.less(temp, a[j - 1])) {
                    a[j] = a[j - 1];
                    pos = j - 1;
                }
            }
            a[pos] = temp;
        }
    }
}

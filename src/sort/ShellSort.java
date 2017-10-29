package sort;

/**
 * Created by 光 on 2016/11/3.
 */
public class ShellSort {
    public static void sort(Comparable[] a) {
        int h = 1;
        while ((3 * h + 1) < a.length) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            // 使用插入排序进行一次h-sort
            int pos;
            Comparable temp;
            for (int i = h; i < a.length; i++) {
                pos = i;
                temp = a[i];
                for (int j = i; j >= h; j -= h) {
                    if (SortUtil.less(temp, a[j - h])) {
                        a[j] = a[j - h];
                        pos = j - h;
                    }
                }
                a[pos] = temp;
            }

            h = h / 3;
        }
    }
}

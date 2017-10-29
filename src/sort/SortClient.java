package sort;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by 光 on 2016/11/2.
 */
public class SortClient {

    public static void main(String[] args) {
        int size = 100;
        Double[] a = new Double[size];
        for (int i = 0; i < a.length; i++) {
            a[i] = StdRandom.uniform();
        }
        Double[] copy = new Double[size];

        System.arraycopy(a, 0, copy, 0, a.length);
        long begin = System.currentTimeMillis();
        SelectionSort.sort(copy);
        long end = System.currentTimeMillis();
        System.out.println("SelectionSort:" + (end - begin) + " " + SortUtil.isSorted(copy));

        System.arraycopy(a, 0, copy, 0, a.length);
        begin = System.currentTimeMillis();
        InsertionSort.sort(copy);
        end = System.currentTimeMillis();
        System.out.println("InsertionSort:" + (end - begin) + " " + SortUtil.isSorted(copy));

        System.arraycopy(a, 0, copy, 0, a.length);
        begin = System.currentTimeMillis();
        ShellSort.sort(copy);
        end = System.currentTimeMillis();
        System.out.println("ShellSort:" + (end - begin) + " " + SortUtil.isSorted(copy));

        System.arraycopy(a, 0, copy, 0, a.length);
        begin = System.currentTimeMillis();
        MergeSort.sort(copy);
        end = System.currentTimeMillis();
        System.out.println("MergeSort:" + (end - begin) + " " + SortUtil.isSorted(copy));

        System.arraycopy(a, 0, copy, 0, a.length);
        begin = System.currentTimeMillis();
        QuickSort.sort(copy);
        end = System.currentTimeMillis();
        System.out.println("QuickSort:" + (end - begin) + " " + SortUtil.isSorted(copy));

        System.arraycopy(a, 0, copy, 0, a.length);
        begin = System.currentTimeMillis();
        ThreeWayQuickSort.sort(copy);
        end = System.currentTimeMillis();
        System.out.println("ThreeWayQuickSort:" + (end - begin) + " " + SortUtil.isSorted(copy));

//        System.out.println(SortUtil.isSorted(copy));
//        for (int i = 0; i < a.length;i++) {
//            System.out.print(a[i] + " ");
//        }
//        System.out.println();

    }
}

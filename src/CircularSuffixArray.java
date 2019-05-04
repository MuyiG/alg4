
public class CircularSuffixArray {

    private static final int R = 256;

    private String originalString;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        originalString = s;
        index = new int[s.length()];
        int n = originalString.length();
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        int[] count = new int[R + 1];
        int[] temp = new int[n];
        for (int pos = n - 1; pos >= 0; pos--) {
            // count
            for (int i = 0; i < R - 1; i++) {
                count[i] = 0;
            }
            for (int i = 0; i < n; i++) {
                count[originalString.charAt((pos + i) % n) + 1]++;
            }
            // accumulate
            for (int i = 0; i < R; i++) {
                count[i + 1] += count[i];
            }
            // sort
            System.arraycopy(index, 0, temp, 0, n);
            for (int i = 0; i < n; i++) {
                int j = temp[i];
                index[count[originalString.charAt((pos + j) % n)]++] = j;
            }
        }
    }

    // length of s
    public int length() {
        return originalString.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > originalString.length() - 1) {
            throw new IllegalArgumentException();
        }
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            System.out.println(circularSuffixArray.index(i));
        }
    }
}

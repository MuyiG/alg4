
public class CircularSuffixArray {
    private int length;
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        length = s.length();
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > length - 1) {
            throw new IllegalArgumentException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}

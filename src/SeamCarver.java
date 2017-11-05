import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > picture.width() -1 || y < 0 || y > picture.height() - 1) {
            throw new IllegalArgumentException();
        }

        if (x == 0 || x == picture.width() -1 || y == 0 || y == picture.height() -1) {
            return 1000;
        }

        Color x1 = picture.get(x - 1, y);
        Color x2 = picture.get(x + 1, y);
        Color y1 = picture.get(x, y - 1);
        Color y2 = picture.get(x, y + 1);
        return Math.sqrt(difference(x1, x2) + difference(y1, y2));

    }

    private double difference(Color c1, Color c2) {
        return (c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed())
                + (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen())
                + (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue());
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }
}

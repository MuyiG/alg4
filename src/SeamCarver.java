import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;
    private double[][] energys;
    private double[][] distTo;
    private int[][] edgeTo;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        energys = new double[picture.height()][picture.width()];
        distTo = new double[picture.height()][picture.width()];
        edgeTo = new int[picture.height()][picture.width()];
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
        Picture originalPicture = new Picture(picture);
        picture = transpose(picture);
        int[] seam = findVerticalSeam();
        picture = originalPicture;
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        energys = new double[picture.height()][picture.width()];
        distTo = new double[picture.height()][picture.width()];
        edgeTo = new int[picture.height()][picture.width()];
        for (int i = 0 ; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                energys[i][j] = energy(j, i);
                if (i == 0) {
                    distTo[i][j] = energys[i][j];
                    edgeTo[i][j] = -1;
                } else {
                    distTo[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // topological order relax
        for (int i = 0 ; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                relax(i , j);
            }
        }

        double minDist = Double.POSITIVE_INFINITY;
        int minPos = 0;
        for (int j = 0; j < picture.width(); j++) {
            if (distTo[picture.height() - 1][j] < minDist) {
                minDist = distTo[picture.height() - 1][j];
                minPos = j;
            }
        }
        int[] seam = new int[picture.height()];
        for (int k = picture.height() - 1; k >= 0; k--) {
            seam[k] = minPos;
            minPos = edgeTo[k][minPos];
        }
        return seam;
    }

    // not elegant...
    private void relax(int i, int j) {
        if (i < picture.height() - 1) {
            if (j > 0 && distTo[i + 1][j - 1] > distTo[i][j] + energys[i + 1][j - 1]) {
                distTo[i + 1][j - 1] = distTo[i][j] + energys[i + 1][j - 1];
                edgeTo[i + 1][j - 1] = j;
            }
            if (distTo[i + 1][j] > distTo[i][j] + energys[i + 1][j]) {
                distTo[i + 1][j] = distTo[i][j] + energys[i + 1][j];
                edgeTo[i + 1][j] = j;
            }
            if (j < picture.width() - 1 && distTo[i + 1][j + 1] > energys[i][j] + energys[i + 1][j + 1]) {
                distTo[i + 1][j + 1] = distTo[i][j] + energys[i + 1][j + 1];
                edgeTo[i + 1][j + 1] = j;
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        Picture originalPicture = new Picture(picture);
        picture = transpose(picture);
        removeVerticalSeam(seam);
        picture = originalPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != picture.height()) {
            throw new IllegalArgumentException();
        }
        Picture newPicture = new Picture(picture.width() - 1, picture.height());
        for (int i = 0 ; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                if (j != seam[i]) {
                    newPicture.set(j, i, picture.get(j, i));
                }
            }
        }
        picture = newPicture;
    }

    private Picture transpose(Picture picture) {
        Picture transposePicture = new Picture(picture.height(), picture.width());
        for (int x = 0 ; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                transposePicture.set(y, x, picture.get(x, y));
            }
        }
        return transposePicture;
    }
}

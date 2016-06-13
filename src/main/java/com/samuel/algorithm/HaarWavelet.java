package com.samuel.algorithm;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Created by Samuel on 5/11/2016.
 */
public class HaarWavelet {

    /**
     * The division factor.
     */
    private static final float t = 2.0f;

    private Integer level;

    public HaarWavelet() {
        this.level = 3;
    }

    public HaarWavelet(int level) {
        this.level = level;
    }

    public void haar1D(float[] data) {
        float[] temp = new float[data.length];

        int h = data.length / 2;

        for (int i = 0; i < h; i++) {
            int k = i * 2;
            temp[i] = (data[k] + data[k + 1]) / t;
            temp[i + h] = (data[k] - data[k + 1]) / t;
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = temp[i];
        }
    }

    public void haar2D(ImagePlus imagePlus) {
        ImageProcessor processor = imagePlus.getProcessor();
        float[][] data = processor.getFloatArray();

        /*int rows = imagePlus.getHeight();
        int cols = imagePlus.getWidth();*/
        int rows = imagePlus.getWidth();
        int cols = imagePlus.getHeight();

        float[] row;
        float[] col;

        for (int k = 0; k < level; k++) {
            int lev = (int) Math.pow(2.0, (double) k);
            int levRows = rows / lev;
            int levCols = cols / lev;

           /* row = new float[levCols];
            for (int i = 0; i < levRows; i++) {
                for (int j = 0; j < row.length; j++) {
                    row[j] = data[j][i];
                }
                haar1D(row);
                for (int j = 0; j < row.length; j++) {
                    data[j][i] = row[j];
                }
            }

            col = new float[levRows];
            for (int j = 0; j < levCols; j++) {
                for (int i = 0; i < col.length; i++) {
                    col[i] = data[j][i];
                }
                haar1D(col);
                for (int i = 0; i < col.length; i++) {
                    data[j][i] = col[i];
                }
            }*/

            row = new float[levCols];
            for (int i = 0; i < levRows; i++) {
                for (int j = 0; j < row.length; j++) {
                    row[j] = data[i][j];
                }
                haar1D(row);
                for (int j = 0; j < row.length; j++) {
                    data[i][j] = row[j];
                }
            }

            col = new float[levRows];
            for (int j = 0; j < levCols; j++) {
                for (int i = 0; i < col.length; i++) {
                    col[i] = data[i][j];
                }
                haar1D(col);
                for (int i = 0; i < col.length; i++) {
                    data[i][j] = col[i];
                }
            }
        }
        processor.setFloatArray(data);
    }

    public void inverseHaar1D(float[] data) {
        float[] temp = new float[data.length];

        int h = data.length / 2;

        for (int i = 0; i < h; i++) {
            int k = i * 2;
            temp[k] = data[i] + data[i + h];
            temp[k + 1] = data[i] - data[i + h];
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = temp[i];
        }
    }

    public void inversHaar2D(ImagePlus imagePlus) {
        ImageProcessor processor = imagePlus.getProcessor();
        float[][] data = processor.getFloatArray();

        int rows = imagePlus.getWidth();
        int cols = imagePlus.getHeight();

        float[] row;
        float[] col;

        for (int k = level - 1; k >= 0; k--) {
            int lev = (int) Math.pow(2.0, (double) k);
            int levRows = rows / lev;
            int levCols = cols / lev;

            row = new float[levCols];
            for (int i = 0; i < levRows; i++) {
                for (int j = 0; j < row.length; j++) {
                    row[j] = data[i][j];
                }
                inverseHaar1D(row);
                for (int j = 0; j < row.length; j++) {
                    data[i][j] = row[j];
                }
            }

            col = new float[levRows];
            for (int j = 0; j < levCols; j++) {
                for (int i = 0; i < col.length; i++) {
                    col[i] = data[i][j];
                }
                inverseHaar1D(col);
                for (int i = 0; i < col.length; i++) {
                    data[i][j] = col[i];
                }
            }
        }
        processor.setFloatArray(data);
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

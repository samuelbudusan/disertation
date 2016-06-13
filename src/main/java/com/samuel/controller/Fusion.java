package com.samuel.controller;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Samuel on 5/7/2016.
 */
public class Fusion {

    public static ImagePlus fuse(ImagePlus image1, ImagePlus image2) {
        ImagePlus image1Duplicate = image1.duplicate();
        ImageProcessor processor1 = image1Duplicate.getProcessor();

        ImageProcessor processor2 = image2.getProcessor();

        float[][] pixels1 = processor1.getFloatArray();
        float[][] pixels2 = processor2.getFloatArray();
        float[][] result = new float[image1.getWidth()][image1.getHeight()];

        for (int i = 0; i < image1.getHeight(); i++) {
            for (int j = 0; j < image1.getWidth(); j++) {
                result[j][i] = (pixels1[j][i] + pixels2[j][i]) / 2;
            }
        }

        processor1.setFloatArray(result);

        image1Duplicate.setTitle("Average " + image1.getShortTitle() + " + " + image2.getShortTitle());

        return image1Duplicate;
    }

    public static ImagePlus SobelFilter(ImagePlus imagePlus) {
        ImagePlus image = imagePlus.duplicate();
        ImageProcessor processor = image.getProcessor();

        float[][] pixels = processor.getFloatArray();
        float[][] result = new float[image.getWidth()][image.getHeight()];

        float[] sobelX = {-1, 0, 1, -2, 0, 2, -1, 0, 1};

        float[] sobelY = {-1, -2, -1, 0, 0, 0, 1, 2, 1};

        List<Float> vecini = new ArrayList<Float>();

        for (int i = 1; i < image.getHeight() - 1; i++) {
            for (int j = 1; j < image.getWidth() - 1; j++) {

                float A = pixels[i - 1][j - 1];
                float B = pixels[i - 1][j];
                float C = pixels[i - 1][j + 1];

                float D = pixels[i][j - 1];
                float x = pixels[i][j];
                float E = pixels[i][j + 1];

                float F = pixels[i + 1][j - 1];
                float G = pixels[i + 1][j];
                float H = pixels[i + 1][j + 1];

                vecini.clear();
                vecini.add(A);
                vecini.add(B);
                vecini.add(C);
                vecini.add(D);
                vecini.add(x);
                vecini.add(E);
                vecini.add(F);
                vecini.add(G);
                vecini.add(H);

                float pixelX = 0;
                float pixelY = 0;

                for (int k = 0; k < vecini.size(); k++) {
                    pixelX += vecini.get(k) * sobelX[k];
                    pixelY += vecini.get(k) * sobelY[k];
                }

                result[i][j] = (float) Math.sqrt((pixelX * pixelX) + (pixelY * pixelY));
//                result[i][j] = Math.abs(sum);

            }
        }
        processor.setFloatArray(result);
        return image;
    }

    public static ImagePlus highPassFilter(ImagePlus imagePlus) {
        ImagePlus image = imagePlus.duplicate();
        ImageProcessor processor = image.getProcessor();

        float[][] pixels = processor.getFloatArray();
        float[][] result = new float[image.getWidth()][image.getHeight()];

        float[] hp = {-1, -1, -1, -1, 9, -1, -1, -1, -1};

        List<Float> vecini = new ArrayList<Float>();

        for (int i = 1; i < image.getHeight() - 1; i++)
            for (int j = 1; j < image.getWidth() - 1; j++) {


                float A = pixels[i - 1][j - 1];
                float B = pixels[i - 1][j];
                float C = pixels[i - 1][j + 1];

                float D = pixels[i][j - 1];
                float x = pixels[i][j];
                float E = pixels[i][j + 1];

                float F = pixels[i + 1][j - 1];
                float G = pixels[i + 1][j];
                float H = pixels[i + 1][j + 1];

                vecini.clear();
                vecini.add(A);
                vecini.add(B);
                vecini.add(C);
                vecini.add(D);
                vecini.add(x);
                vecini.add(E);
                vecini.add(F);
                vecini.add(G);
                vecini.add(H);

                float sum = 0;

                for (int k = 0; k < vecini.size(); k++) {
                    sum += vecini.get(k) * hp[k];
                }

                result[i][j] = Math.abs(sum) > 255 ? 255 : Math.abs(sum);
//                result[i][j] = Math.abs(sum);

            }
        processor.setFloatArray(result);
        return image;
    }
}

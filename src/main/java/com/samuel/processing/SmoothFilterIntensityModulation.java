package com.samuel.processing;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel on 6/15/2016.
 */
public class SmoothFilterIntensityModulation {

    public static ImagePlus enhanceImageResolution(ImagePlus highResolutionImage, ImagePlus lowResolutionImage) {

        ImagePlus resultImage = highResolutionImage.duplicate();
        ImageProcessor processor1 = resultImage.getProcessor();

        ImageProcessor processor2 = lowResolutionImage.getProcessor();

        float[][] pixels1 = processor1.getFloatArray();
        float[][] pixels2 = processor2.getFloatArray();
        float[][] result = new float[highResolutionImage.getWidth()][highResolutionImage.getHeight()];

        List<Float> neighbours = new ArrayList<Float>();

        for (int i = 1; i < highResolutionImage.getHeight() - 1; i++) {
            for (int j = 1; j < highResolutionImage.getWidth() - 1; j++) {
                float A = pixels1[i - 1][j - 1];
                float B = pixels1[i - 1][j];
                float C = pixels1[i - 1][j + 1];

                float D = pixels1[i][j - 1];
                float x = pixels1[i][j];
                float E = pixels1[i][j + 1];

                float F = pixels1[i + 1][j - 1];
                float G = pixels1[i + 1][j];
                float H = pixels1[i + 1][j + 1];

                neighbours.clear();
                neighbours.add(A);
                neighbours.add(B);
                neighbours.add(C);
                neighbours.add(D);
                neighbours.add(x);
                neighbours.add(E);
                neighbours.add(F);
                neighbours.add(G);
                neighbours.add(H);

                float sum = 0;

                for (int k = 0; k < neighbours.size(); k++) {
                    sum += neighbours.get(k) / 9;
                }
                sum = Math.abs(sum) > 255 ? 255 : Math.abs(sum);

                result[i][j] = (pixels2[i][j] * pixels1[i][j]) / sum;
            }
        }

        processor1.setFloatArray(result);

        resultImage.setTitle("Enhanced " + resultImage.getShortTitle() + " + " + resultImage.getShortTitle());
        return resultImage;
    }
}

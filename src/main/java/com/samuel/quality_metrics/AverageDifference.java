package com.samuel.quality_metrics;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Created by Samuel on 6/11/2016.
 */
public class AverageDifference {

    public static double calculateQuality(ImagePlus perfectImage, ImagePlus fusedImage) {
        double error = 0.0;

        ImageProcessor perfectImageProcessor = perfectImage.getProcessor();
        ImageProcessor fusedImageProcessor = fusedImage.getProcessor();

        for (int i = 0; i < perfectImage.getHeight(); i++) {
            for (int j = 0; j < perfectImage.getWidth(); j++) {
                error += Math.abs(perfectImageProcessor.get(i, j) - fusedImageProcessor.get(i, j));
            }
        }
        double AD = error / (perfectImage.getHeight() * perfectImage.getWidth());

        return AD;
    }
}

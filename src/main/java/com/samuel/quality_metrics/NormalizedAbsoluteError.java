package com.samuel.quality_metrics;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Created by Samuel on 6/11/2016.
 */
public class NormalizedAbsoluteError {

    public static double calculateQuality(ImagePlus perfectImage, ImagePlus fusedImage) {
        ImageProcessor perfectImageProcessor = perfectImage.getProcessor();
        ImageProcessor fusedImageProcessor = fusedImage.getProcessor();

        double difference = 0.0;
        double perfectImageIntermediateResult = 0.0;

        for (int i = 0; i < perfectImage.getHeight(); i++) {
            for (int j = 0; j < perfectImage.getWidth(); j++) {
                difference  += Math.abs(perfectImageProcessor.get(i, j) - fusedImageProcessor.get(i, j));
                perfectImageIntermediateResult += Math.pow(perfectImageProcessor.get(i, j), 2);
            }
        }

        double NAE = difference / perfectImageIntermediateResult;
        return NAE;
    }

}

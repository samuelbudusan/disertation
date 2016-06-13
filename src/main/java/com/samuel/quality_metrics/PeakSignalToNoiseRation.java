package com.samuel.quality_metrics;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Created by Samuel on 6/11/2016.
 */
public class PeakSignalToNoiseRation {

    public static double calculateQuality(ImagePlus perfectImage, ImagePlus fusedImage) {
        ImageProcessor fusedImageProcessor = fusedImage.getProcessor();
        double peak = fusedImageProcessor.maxValue();

        double MSE = MeanSquaredError.calculateQuality(perfectImage, fusedImage);
        double PSNR = 10 * Math.log10(Math.pow(peak, 2) / MSE);

        return PSNR;
    }

}

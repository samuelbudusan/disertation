package com.samuel.processing;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 * Created by Samuel on 5/9/2016.
 */
public class ImageResizer {

    /**
     * If dimensions of the input images are not equal, resizes them both to the
     * average of their dimensions.
     *
     * @param image1
     * @param image2
     */
    public void resize(ImagePlus image1, ImagePlus image2) {

        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            int newWidth = (image1.getWidth() + image2.getWidth()) / 2;
            int newHeight = (image2.getHeight() + image2.getHeight()) / 2;

            ImageProcessor processor1 = image1.getProcessor();
            ImageProcessor processor2 = image2.getProcessor();

            processor1.resize(newWidth, newHeight);
            processor2.resize(newWidth, newHeight);
        }
    }
}

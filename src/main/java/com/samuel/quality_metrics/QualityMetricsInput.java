package com.samuel.quality_metrics;

import ij.ImagePlus;

/**
 * Created by Samuel on 6/22/2016.
 */
public class QualityMetricsInput {

    private ImagePlus imageOne;
    private ImagePlus imageTwo;
    private ImagePlus perfectImage;

    public QualityMetricsInput() {
    }

    public QualityMetricsInput(ImagePlus imageOne, ImagePlus imageTwo, ImagePlus perfectImage) {
        this.imageOne = imageOne;
        this.imageTwo = imageTwo;
        this.perfectImage = perfectImage;
    }

    public ImagePlus getImageOne() {
        return imageOne;
    }

    public void setImageOne(ImagePlus imageOne) {
        this.imageOne = imageOne;
    }

    public ImagePlus getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(ImagePlus imageTwo) {
        this.imageTwo = imageTwo;
    }

    public ImagePlus getPerfectImage() {
        return perfectImage;
    }

    public void setPerfectImage(ImagePlus perfectImage) {
        this.perfectImage = perfectImage;
    }
}

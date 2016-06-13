package com.samuel.fusion;

import ij.ImagePlus;

/**
 * Created by Samuel on 5/9/2016.
 */
public interface FusionMethod {

    ImagePlus fuse(ImagePlus image1, ImagePlus image2);
}

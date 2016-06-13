package com.samuel.fusion;

import com.samuel.enums.FusionMethodEnum;
import ij.ImagePlus;

/**
 * Created by Samuel on 5/9/2016.
 */
public interface FusionService {

    ImagePlus fuse(ImagePlus image1, ImagePlus image2, FusionMethodEnum fusionMethodEnum);
}

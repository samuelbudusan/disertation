package com.samuel.processing;

import ij.ImagePlus;
import ij.plugin.ZProjector;

import java.awt.*;

/**
 * Created by Samuel on 6/22/2016.
 */
public class StackConverter {

    /**
     * Converts image stack to image doing a Z-plane projection of the slices
     * @param imagePlus
     * @return
     */
    public ImagePlus convertImageStack(ImagePlus imagePlus) {
        if(imagePlus.getStackSize() > 1) {
            ZProjector projector = new ZProjector(imagePlus);
            projector.setMethod(ZProjector.MAX_METHOD);
            projector.doProjection();
            return projector.getProjection();
        }
        return imagePlus;
    }
}

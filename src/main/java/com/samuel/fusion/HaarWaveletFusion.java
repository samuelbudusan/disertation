package com.samuel.fusion;

import com.samuel.algorithm.HaarWavelet;
import com.samuel.controller.Fusion;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.SaltAndPepper;

import java.awt.*;

/**
 * Created by Samuel on 5/11/2016.
 */
public class HaarWaveletFusion implements FusionMethod {

    private HaarWavelet haarWavelet;
    private FusionMethod simpleFusion;

    HaarWaveletFusion() {
        haarWavelet = new HaarWavelet();
        this.simpleFusion = new SimpleMaximumFusion();
    }

    HaarWaveletFusion(Integer level, FusionMethod simpleFusion) {
        haarWavelet = new HaarWavelet(level);
        this.simpleFusion = simpleFusion;
    }

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2) {
        ImagePlus haarImage1 = image1.duplicate();
        haarWavelet.haar2D(haarImage1);

        ImagePlus haarImage2 = image2.duplicate();
        haarWavelet.haar2D(haarImage2);

        ImagePlus result = simpleFusion.fuse(haarImage1, haarImage2);
        haarWavelet.inversHaar2D(result);

        result.setTitle("Haar_" + haarWavelet.getLevel() + " " + image1.getShortTitle() + " + " + image2.getShortTitle());

        /*ImagePlus testImage = Fusion.SobelFilter(result);
        testImage.show();*/
       /* ImagePlus testContrast = result.duplicate();
        testContrast.getProcessor().gamma(1.8d);*/
       /* SaltAndPepper saltAndPepper = new SaltAndPepper();
        saltAndPepper.run(testContrast.getProcessor());*/
//        testContrast.show();
      /*  IJ.run(testContrast, "Enhance Contrast", "saturated=2 normalize");
        testContrast.show();*/

        return result;
    }
}

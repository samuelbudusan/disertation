package com.samuel.fusion;

import com.samuel.algorithm.LaplacianPyramid;
import ij.ImagePlus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel on 5/9/2016.
 */
public class LaplacianPyramidFusion implements FusionMethod {

    private LaplacianPyramid pyramidCalculator;
    private final int level;
    private final double sigma;
    private final FusionMethod simpleFusion;

    public LaplacianPyramidFusion() {
        this.level = 3;
        this.sigma = 3.0;
        this.simpleFusion = new SimpleMaximumFusion();
    }

    public LaplacianPyramidFusion(int level, double sigma, FusionMethod simpleFusion) {
        this.level = level;
        this.sigma = sigma;
        this.simpleFusion = simpleFusion;
    }

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2) {

        pyramidCalculator = new LaplacianPyramid(level, sigma);
        pyramidCalculator.calcLaplacianPyramid(image1);
        List<ImagePlus> pyramid1 = pyramidCalculator.getLaplacianPyramid();

        pyramidCalculator = new LaplacianPyramid(level, sigma);
        pyramidCalculator.calcLaplacianPyramid(image2);
        List<ImagePlus> pyramid2 = pyramidCalculator.getLaplacianPyramid();

        List<ImagePlus> result = new ArrayList<ImagePlus>(pyramid1.size());

        for (int i = 0; i < pyramid1.size(); i++) {
            result.add(simpleFusion.fuse(pyramid1.get(i), pyramid2.get(i)));
        }

        pyramidCalculator.setLaplacianPyramid(result);

        ImagePlus resultImage = pyramidCalculator.reconstrLaplacianPyramid();

        resultImage.setTitle("Laplacian_" + level + "_" + sigma + " " + image1.getShortTitle() + " + " + image2.getShortTitle());

        return resultImage;
    }
}

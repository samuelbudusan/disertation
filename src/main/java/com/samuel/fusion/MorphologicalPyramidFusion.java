package com.samuel.fusion;

import com.samuel.algorithm.LaplacianPyramid;
import com.samuel.algorithm.MorphologicalPyramid;
import ij.ImagePlus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel on 5/10/2016.
 */
public class MorphologicalPyramidFusion implements FusionMethod {

    private MorphologicalPyramid morphologicalPyramid;
    private final int level;
    private final double sigma;
    private final FusionMethod simpleFusion;

    public MorphologicalPyramidFusion() {
        this.level = 3;
        this.sigma = 3.0;
        this.simpleFusion = new SimpleMaximumFusion();
    }

    public MorphologicalPyramidFusion(int level, double sigma, FusionMethod simpleFusion) {
        this.level = level;
        this.sigma = sigma;
        this.simpleFusion = simpleFusion;
    }

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2) {

        morphologicalPyramid = new MorphologicalPyramid(level, sigma);
        morphologicalPyramid.calcMorphologicalPyramid(image1);
        List<ImagePlus> pyramid1 = morphologicalPyramid.getMorphologicalPyramid();

        morphologicalPyramid = new MorphologicalPyramid(level, sigma);
        morphologicalPyramid.calcMorphologicalPyramid(image2);
        List<ImagePlus> pyramid2 = morphologicalPyramid.getMorphologicalPyramid();

        List<ImagePlus> result = new ArrayList<ImagePlus>(pyramid1.size());

        for (int i = 0; i < pyramid1.size(); i++) {
            result.add(simpleFusion.fuse(pyramid1.get(i), pyramid2.get(i)));
        }

        morphologicalPyramid.setMorphologicalPyramid(result);

        ImagePlus resultImage = morphologicalPyramid.reconstructMorphologicalPyramid();

        resultImage.setTitle("Morphological_" + level + "_" + sigma + " " + image1.getShortTitle() + " + " + image2.getShortTitle());

        return resultImage;
    }
}

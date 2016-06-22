package com.samuel.fusion;

import com.samuel.enums.FusionMethodEnum;
import com.samuel.processing.ImageResizer;
import com.samuel.processing.StackConverter;
import ij.ImagePlus;

/**
 * Created by Samuel on 5/9/2016.
 */
public class FusionServiceImpl implements FusionService {

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2, FusionMethodEnum fusionMethodEnum) {
        FusionMethod fusionMethod;

        switch (fusionMethodEnum) {
            case SIMPLE_AVERAGE_FUSION:
                fusionMethod = new SimpleAverageFusion();
                break;
            case SIMPLE_MAXIMUM_FUSION:
                fusionMethod = new SimpleMaximumFusion();
                break;
            case SIMPLE_MINIMUM_FUSION:
                fusionMethod = new SimpleMinimumFusion();
                break;
            case LAPLACIAN_PYRAMID_FUSION:
                fusionMethod = new LaplacianPyramidFusion();
                break;
            case MORPHOLOGICAL_PYRAMID_FUSION:
                fusionMethod = new MorphologicalPyramidFusion();
                break;
            case HAAR_WAVELET_FUSION:
                fusionMethod = new HaarWaveletFusion();
                break;
            default:
                fusionMethod = new SimpleAverageFusion();
                break;
        }

        StackConverter stackConverter = new StackConverter();
        image1 = stackConverter.convertImageStack(image1);
        image2 = stackConverter.convertImageStack(image2);

        ImageResizer resizer = new ImageResizer();
        resizer.resize(image1, image2);

        ImagePlus imagePlus = fusionMethod.fuse(image1, image2);
        return imagePlus;
    }
}

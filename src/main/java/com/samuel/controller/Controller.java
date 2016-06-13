package com.samuel.controller;

import com.samuel.enums.FusionMethodEnum;
import com.samuel.fusion.FusionService;
import com.samuel.fusion.FusionServiceImpl;
import com.samuel.quality_metrics.*;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.StackCombiner;
import ij.process.ImageConverter;

import java.text.DecimalFormat;

/**
 * Created by Samuel on 5/6/2016.
 */
public class Controller {

    private static ImagePlus image1;
    private static ImagePlus image2;
    private static ImagePlus resultImage;

    public static boolean loadImage(String path, int imageNr) {
        Opener opener = new Opener();

        boolean ok = false;

        if (imageNr == 1) {
            if (image1 != null) {
                image1.close();
            }
            image1 = opener.openImage(path);
            image1.show();
            ok = true;
        } else if (imageNr == 2) {
            if (image2 != null) {
                image2.close();
            }
            image2 = opener.openImage(path);
            image2.show();
            ok = true;
        }

        return ok;
    }

    public static void fuse(Integer fusionMethodId) {
        FusionService fusionService = new FusionServiceImpl();

        long startMillis = System.currentTimeMillis();

        image1.show();
        image2.show();
        resultImage = fusionService.fuse(image1, image2, FusionMethodEnum.getById(fusionMethodId));

        long endMillis = System.currentTimeMillis();

        // calculate duration
        long duration = endMillis - startMillis;

        System.out.println("duration: " + duration);
        resultImage.setTitle(resultImage.getTitle() + "_" + duration + "ms");

        resultImage.show();
        DecimalFormat df = new DecimalFormat("##.00");
        double error = AverageDifference.calculateQuality(image1, resultImage);
        System.out.println("AverageDifference: " + error);
        error = MeanSquaredError.calculateQuality(image1, resultImage);
        System.out.println("MeanSquaredError: " + error);
        error = NormalizedAbsoluteError.calculateQuality(image1, resultImage);
        System.out.println("NormalizedAbsoluteError: " + error);
        error = NormalizedCrossCorrelation.calculateQuality(image1, resultImage);
        System.out.println("NormalizedCrossCorrelation: " + error);
        error = PeakSignalToNoiseRation.calculateQuality(image1, resultImage);
        System.out.println("PeakSignalToNoiseRation: " + error);
        error = StructuralContent.calculateQuality(image1, resultImage);
        System.out.println("StructuralContent: " + error);
    }

    public static void improveQuality() {
        ImagePlus imageImproved = Fusion.highPassFilter(resultImage);
        imageImproved.show();
    }
}

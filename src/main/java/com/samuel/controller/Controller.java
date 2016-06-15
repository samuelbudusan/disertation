package com.samuel.controller;

import com.samuel.enums.FusionMethodEnum;
import com.samuel.enums.ImagesEnum;
import com.samuel.fusion.FusionService;
import com.samuel.fusion.FusionServiceImpl;
import com.samuel.processing.ImageResizer;
import com.samuel.quality_metrics.*;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.StackCombiner;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by Samuel on 5/6/2016.
 */
public class Controller {

    private static ImagePlus image1;
    private static ImagePlus image2;
    private static ImagePlus resultImage;

    public static void loadImage(String path, ImagesEnum imagesEnum, Graphics graphics) {
        Opener opener = new Opener();

        if (imagesEnum.equals(ImagesEnum.IMAGE_ONE)) {
            if (image1 != null) {
                image1.close();
            }
            image1 = opener.openImage(path);
        } else {
            if (image2 != null) {
                image2.close();
            }
            image2 = opener.openImage(path);
        }
        displayImage(imagesEnum, graphics);
    }

    public static void displayImage(ImagesEnum imagesEnum, Graphics graphics) {
        ImagePlus duplicatedImage = null;
        switch (imagesEnum) {
            case IMAGE_ONE:
                duplicatedImage = image1.duplicate();
                break;
            case IMAGE_TWO:
                duplicatedImage = image2.duplicate();
                break;
            case FUSED_IMAGE:
                duplicatedImage = resultImage.duplicate();
                break;
        }
        ImageProcessor imageProcessor = duplicatedImage.getProcessor();
        imageProcessor.setInterpolate(true);
        ImageProcessor resizedImageProcessor = imageProcessor.resize(225, 200);
        ImagePlus small = new ImagePlus("small", resizedImageProcessor);
        graphics.drawImage(small.getBufferedImage(), 0, 0, null);
    }

    public static void showImage(Integer imageNr) {
        if (imageNr == 1 && image1 != null) {
            image1.show();
        } else {
            if (imageNr == 2 && image2 != null) {
                image2.show();
            } else {
                if (imageNr == 3 && resultImage != null) {
                    resultImage.show();
                }
            }
        }
    }

    public static void fuse(Integer fusionMethodId, Graphics graphics) {
        FusionService fusionService = new FusionServiceImpl();

        long startMillis = System.currentTimeMillis();

        ImageResizer imageResizer = new ImageResizer();
        imageResizer.resize(image1, image2);

        resultImage = fusionService.fuse(image1, image2, FusionMethodEnum.getById(fusionMethodId));

        long endMillis = System.currentTimeMillis();

        // calculate duration
        long duration = endMillis - startMillis;

        System.out.println("duration: " + duration);
        resultImage.setTitle(resultImage.getTitle() + "_" + duration + "ms");

        displayImage(ImagesEnum.FUSED_IMAGE, graphics);
        /*DecimalFormat df = new DecimalFormat("##.00");
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
        System.out.println("StructuralContent: " + error);*/

    }
}

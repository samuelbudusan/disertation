package com.samuel.controller;

import com.samuel.enums.FusionMethodEnum;
import com.samuel.enums.ImagesEnum;
import com.samuel.enums.MessageDialogEnum;
import com.samuel.fusion.FusionService;
import com.samuel.fusion.FusionServiceImpl;
import com.samuel.io.DicomIO;
import com.samuel.io.ResultsWriter;
import com.samuel.processing.ImageResizer;
import com.samuel.processing.SmoothFilterIntensityModulation;
import com.samuel.processing.StackConverter;
import com.samuel.quality_metrics.*;
import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ImageProcessor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Samuel on 5/6/2016.
 */
public class Controller {

    private static ImagePlus image1;
    private static ImagePlus image2;
    private static ImagePlus resultImage;
    private static ImagePlus perfectImage;

    public static void loadImage(String path, ImagesEnum imagesEnum, Graphics graphics) {
        Opener opener = new Opener();
        DicomIO dicomIO = new DicomIO();

        if (imagesEnum.equals(ImagesEnum.IMAGE_ONE)) {
            if (image1 != null) {
                image1.close();
            }
            if (path.contains(".dcm")) {
                image1 = dicomIO.open(path);
            } else {
                image1 = opener.openImage(path);
            }
        } else {
            if (image2 != null) {
                image2.close();
            }
            if (path.contains(".dcm")) {
                image2 = dicomIO.open(path);
            } else {
                image2 = opener.openImage(path);
            }
        }
        boolean result = displayImage(imagesEnum, graphics);
        if (!result) {
            System.out.println("Error opening image!");
        }
    }

    public static boolean openPerfectImage(String path) {
        Opener opener = new Opener();
        DicomIO dicomIO = new DicomIO();

        if (perfectImage != null) {
            perfectImage.close();
        }
        if (path.contains(".dcm")) {
            perfectImage = dicomIO.open(path);
        } else {
            perfectImage = opener.openImage(path);
        }

        if (perfectImage != null && perfectImage.getImage() != null) {
            return true;
        }
        return false;
    }

    public static boolean displayImage(ImagesEnum imagesEnum, Graphics graphics) {
        ImagePlus duplicatedImage = null;
        switch (imagesEnum) {
            case IMAGE_ONE:
                if (image1 != null && image1.getImage() != null) {
                    duplicatedImage = image1.duplicate();
                }
                break;
            case IMAGE_TWO:
                if (image2 != null && image2.getImage() != null) {
                    duplicatedImage = image2.duplicate();
                }
                break;
            case FUSED_IMAGE:
                if (resultImage != null && resultImage.getImage() != null) {
                    duplicatedImage = resultImage.duplicate();
                }
                break;
        }
        if (duplicatedImage != null) {
            ImageProcessor imageProcessor = duplicatedImage.getProcessor();
            imageProcessor.setInterpolate(true);
            ImageProcessor resizedImageProcessor = imageProcessor.resize(225, 200);
            ImagePlus small = new ImagePlus("small", resizedImageProcessor);
            graphics.drawImage(small.getBufferedImage(), 0, 0, null);
            return true;
        }
        return false;
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

    public static void fuse(Integer fusionMethodId, Integer level, Double sigma, Graphics graphics) {
        FusionService fusionService = new FusionServiceImpl();

        long startMillis = System.currentTimeMillis();

        resultImage = fusionService.fuse(image1, image2, level, sigma, FusionMethodEnum.getById(fusionMethodId));

        long endMillis = System.currentTimeMillis();

        // calculate duration
        long duration = endMillis - startMillis;

        System.out.println("duration: " + duration);
        resultImage.setTitle(resultImage.getTitle() + "_" + duration + "ms");

        displayImage(ImagesEnum.FUSED_IMAGE, graphics);


    }

    public static void getQualityMetrics(JTextArea textArea) {
//        DecimalFormat df = new DecimalFormat("##.00");
        textArea.setText("");
        double error = AverageDifference.calculateQuality(perfectImage, resultImage);
        textArea.append("AverageDifference: " + error + "\n");
        error = MeanSquaredError.calculateQuality(perfectImage, resultImage);
        textArea.append("MeanSquaredError: " + error + "\n");
        error = NormalizedAbsoluteError.calculateQuality(perfectImage, resultImage);
        textArea.append("NormalizedAbsoluteError: " + error + "\n");
        error = NormalizedCrossCorrelation.calculateQuality(perfectImage, resultImage);
        textArea.append("NormalizedCrossCorrelation: " + error + "\n");
        error = PeakSignalToNoiseRation.calculateQuality(perfectImage, resultImage);
        textArea.append("PeakSignalToNoiseRation: " + error + "\n");
        error = StructuralContent.calculateQuality(perfectImage, resultImage);
        textArea.append("StructuralContent: " + error + "\n");
    }

    public static void enhanceResult() {
        ImagePlus enhancedImage = SmoothFilterIntensityModulation.enhanceImageResolution(image1, resultImage);
        enhancedImage.show();
    }

    public static void runQualityMetricsProcess() {
        QualityMetricsService qualityMetricsService = new QualityMetricsService();
        qualityMetricsService.setPredefinedValues();
        qualityMetricsService.runQualityMetrics();
        List<QualityMetricsOutput> result = qualityMetricsService.getQualityMetricsOutputs();
        ResultsWriter resultsWriter = new ResultsWriter();
        resultsWriter.writeResults("results", "results", result);
        MessageDialog.showMessage("Successfully ran quality metrics process ", MessageDialogEnum.INFO);
    }

    public static void saveImage(String path) {
        if (resultImage != null) {
            String imagePath = path + "\\" + resultImage.getTitle() + ".jpg";
            File outputfile = new File(imagePath);
            outputfile.mkdirs();
            try {
                ImageIO.write(resultImage.getBufferedImage(), "jpg", outputfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

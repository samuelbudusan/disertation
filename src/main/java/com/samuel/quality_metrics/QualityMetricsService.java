package com.samuel.quality_metrics;

import com.samuel.fusion.*;
import ij.ImagePlus;
import ij.io.Opener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel on 6/22/2016.
 */
public class QualityMetricsService {

    private List<FusionMethod> fusionMethods;
    private List<QualityMetricsInput> qualityMetricsInputs;
    private List<QualityMetricsOutput> qualityMetricsOutputs;

    public QualityMetricsService() {
        fusionMethods = new ArrayList<FusionMethod>();
        qualityMetricsInputs = new ArrayList<QualityMetricsInput>();
        qualityMetricsOutputs = new ArrayList<QualityMetricsOutput>();
    }

    public void setPredefinedValues() {
        fusionMethods.add(new SimpleMinimumFusion());
        fusionMethods.add(new SimpleMaximumFusion());
        fusionMethods.add(new SimpleAverageFusion());
        fusionMethods.add(new LaplacianPyramidFusion());
        fusionMethods.add(new MorphologicalPyramidFusion());
        fusionMethods.add(new HaarWaveletFusion());

        Opener opener = new Opener();

        String path = "D:\\diseratation\\licenta\\src\\main\\resources\\";
        ImagePlus perfectImage = opener.openImage(path + "mri.jpg");
        ImagePlus image1 = opener.openImage(path + "mri_soft2.jpg");
        ImagePlus image2 = opener.openImage(path + "mri_hard2.jpg");
        qualityMetricsInputs.add(new QualityMetricsInput(image1, image2, perfectImage));

        ImagePlus image1a = opener.openImage(path + "mri_soft.jpg");
        ImagePlus image2a = opener.openImage(path + "mri_hard.jpg");

        qualityMetricsInputs.add(new QualityMetricsInput(image1a, image2a, perfectImage));

        ImagePlus image1b = opener.openImage(path + "mri_left_blurred.jpg");
        ImagePlus image2b = opener.openImage(path + "mri_right_blurred.jpg");

        qualityMetricsInputs.add(new QualityMetricsInput(image1b, image2b, perfectImage));

    }

    public void runQualityMetrics() {
        for (QualityMetricsInput qualityMetricsInput : qualityMetricsInputs) {
            for (FusionMethod fusionMethod : fusionMethods) {
                ImagePlus result = fusionMethod.fuse(qualityMetricsInput.getImageOne(), qualityMetricsInput.getImageTwo());
                QualityMetricsOutput qualityMetricsOutput = new QualityMetricsOutput();
                qualityMetricsOutput.setResultedImage(result);

                double ad = AverageDifference.calculateQuality(qualityMetricsInput.getPerfectImage(), result);
                double mse = MeanSquaredError.calculateQuality(qualityMetricsInput.getPerfectImage(), result);
                double nae = NormalizedAbsoluteError.calculateQuality(qualityMetricsInput.getPerfectImage(), result);
                double ncc = NormalizedCrossCorrelation.calculateQuality(qualityMetricsInput.getPerfectImage(), result);
                double psnr = PeakSignalToNoiseRation.calculateQuality(qualityMetricsInput.getPerfectImage(), result);
                double sc = StructuralContent.calculateQuality(qualityMetricsInput.getPerfectImage(), result);

                qualityMetricsOutput.setAd(ad);
                qualityMetricsOutput.setMse(mse);
                qualityMetricsOutput.setNae(nae);
                qualityMetricsOutput.setNcc(ncc);
                qualityMetricsOutput.setPsnr(psnr);
                qualityMetricsOutput.setSc(sc);

                qualityMetricsOutputs.add(qualityMetricsOutput);
            }

        }
    }

    public List<QualityMetricsOutput> getQualityMetricsOutputs() {
        return qualityMetricsOutputs;
    }
}

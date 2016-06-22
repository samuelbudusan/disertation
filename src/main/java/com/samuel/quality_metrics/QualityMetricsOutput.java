package com.samuel.quality_metrics;

import ij.ImagePlus;

/**
 * Created by Samuel on 6/22/2016.
 */
public class QualityMetricsOutput {

    private ImagePlus resultedImage;
    private double ad;
    private double mse;
    private double nae;
    private double ncc;
    private double psnr;
    private double sc;

    public ImagePlus getResultedImage() {
        return resultedImage;
    }

    public void setResultedImage(ImagePlus resultedImage) {
        this.resultedImage = resultedImage;
    }

    public double getAd() {
        return ad;
    }

    public void setAd(double ad) {
        this.ad = ad;
    }

    public double getMse() {
        return mse;
    }

    public void setMse(double mse) {
        this.mse = mse;
    }

    public double getNae() {
        return nae;
    }

    public void setNae(double nae) {
        this.nae = nae;
    }

    public double getNcc() {
        return ncc;
    }

    public void setNcc(double ncc) {
        this.ncc = ncc;
    }

    public double getPsnr() {
        return psnr;
    }

    public void setPsnr(double psnr) {
        this.psnr = psnr;
    }

    public double getSc() {
        return sc;
    }

    public void setSc(double sc) {
        this.sc = sc;
    }

    @Override
    public String toString() {
        String formatted = String.format("%100s\tAD=%.2f\tMSE=%.2f\tNAE=%.2f\tNCC=%.2f\tPSNR=%.2f\tSC=%.2f\n", this.getResultedImage().getTitle(), ad, mse, nae,ncc,psnr,sc);
        return formatted;
    }
}

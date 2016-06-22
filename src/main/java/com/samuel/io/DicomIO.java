package com.samuel.io;

import ij.ImagePlus;
import ij.plugin.DICOM;

/**
 * Created by Samuel on 6/22/2016.
 */
public class DicomIO {

    private DICOM dicom;

    public ImagePlus open(String path) {
        dicom = new DICOM();
        dicom.open(path);
        ImagePlus imagePlus = null;
        if (dicom.getImage() != null) {
            if (dicom.getStackSize() > 1) {
                imagePlus = new ImagePlus(dicom.getShortTitle(), dicom.getImageStack());
            } else {
                imagePlus = new ImagePlus(dicom.getShortTitle(), dicom.getImage());
            }
        }
        return imagePlus;
    }

   /* public ImagePlus getImage() {
        ImagePlus imagePlus = null;
        if(dicom.getStackSize() > 1) {
            imagePlus = new ImagePlus(dicom.getShortTitle(), dicom.getImageStack());
        } else {
            imagePlus = new ImagePlus(dicom.getShortTitle(), dicom.getImage());
        }
        return imagePlus;
    }*/
}

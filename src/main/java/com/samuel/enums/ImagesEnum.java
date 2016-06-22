package com.samuel.enums;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * Created by Samuel on 6/14/2016.
 */
public enum ImagesEnum {

    IMAGE_ONE(1),
    IMAGE_TWO(2),
    FUSED_IMAGE(3),
    PERFECT_IMAGE(4);

    private static Map<Integer, ImagesEnum> IMAGES = Maps.newHashMapWithExpectedSize(values().length);

    static {
        for (ImagesEnum imagesEnum : EnumSet.allOf(ImagesEnum.class)) {
            IMAGES.put(imagesEnum.getImageNr(), imagesEnum);
        }
    }

    Integer imageNr;

    private ImagesEnum(Integer imageNr) {
        this.imageNr = imageNr;
    }

    public Integer getImageNr() {
        return imageNr;
    }

    public static ImagesEnum getByImageNumber(Integer imageNr) {
        return IMAGES.get(imageNr);
    }
}

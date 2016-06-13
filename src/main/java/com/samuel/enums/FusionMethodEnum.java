package com.samuel.enums;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * Created by Samuel on 5/9/2016.
 */
public enum FusionMethodEnum {

    SIMPLE_AVERAGE_FUSION(0),
    SIMPLE_MAXIMUM_FUSION(1),
    SIMPLE_MINIMUM_FUSION(2),
    LAPLACIAN_PYRAMID_FUSION(3),
    MORPHOLOGICAL_PYRAMID_FUSION(4),
    HAAR_WAVELET_FUSION(5);

    private static Map<Integer, FusionMethodEnum> FUSION_METHODS = Maps.newHashMapWithExpectedSize(values().length);

    static {
        for(FusionMethodEnum fusionMethodEnum: EnumSet.allOf(FusionMethodEnum.class)) {
            FUSION_METHODS.put(fusionMethodEnum.getId(), fusionMethodEnum);
        }
    }

    private FusionMethodEnum(Integer id) {
        this.id = id;
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public static FusionMethodEnum getById(Integer id) {
        return FUSION_METHODS.get(id);
    }
}

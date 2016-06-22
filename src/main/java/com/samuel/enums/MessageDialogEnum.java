package com.samuel.enums;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * Created by Samuel on 6/22/2016.
 */
public enum MessageDialogEnum {

    INFO(1, "Info"),
    WARNING(2, "Warning"),
    ERROR(3, "Error");

    private static Map<Integer, MessageDialogEnum> MESSAGES_DIALOG = Maps.newHashMapWithExpectedSize(values().length);

    static {
        for (MessageDialogEnum messageDialogEnum : EnumSet.allOf(MessageDialogEnum.class)) {
            MESSAGES_DIALOG.put(messageDialogEnum.getId(), messageDialogEnum);
        }
    }

    Integer id;
    String value;

    private MessageDialogEnum(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static MessageDialogEnum getById(Integer id) {
        return MESSAGES_DIALOG.get(id);
    }
}

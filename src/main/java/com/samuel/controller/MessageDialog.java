package com.samuel.controller;

import com.samuel.enums.MessageDialogEnum;

import javax.swing.*;

/**
 * Created by Samuel on 6/22/2016.
 */
public class MessageDialog {

    public static void showMessage(String message, MessageDialogEnum messageDialogEnum) {
        int messageType;
        switch (messageDialogEnum) {
            case WARNING:
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            case ERROR:
                messageType = JOptionPane.ERROR_MESSAGE;
                break;
            case INFO:
                messageType = JOptionPane.INFORMATION_MESSAGE;
                break;
            default: messageType = JOptionPane.INFORMATION_MESSAGE;
        }

        JOptionPane.showMessageDialog(new JFrame(messageDialogEnum.getValue()), message, messageDialogEnum.getValue(), messageType);
    }
}

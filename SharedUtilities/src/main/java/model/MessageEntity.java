package model;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageEntity implements MessageEntityInterface {


    protected boolean underLineText;
    protected boolean boldText;
    protected boolean italicText;
    protected double messageFontSize;
    protected String messageFontFamily;
    protected String messageTextFill;
    protected String messageBubbleFill;
    private String receiver;
    private String sender;
    private String MSGBody;
    private LocalDateTime time;

    public MessageEntity(String receiver, String sender, String MSGBody) {
        this.receiver = receiver;
        this.sender = sender;
        this.MSGBody = MSGBody;
        this.time = LocalDateTime.now();
        this.underLineText = false;
        this.boldText = false;
        this.italicText = false;
        this.messageFontSize = 14.0;
        this.messageFontFamily = ("Segoe UI");
        this.messageTextFill = "#3333333";
        this.messageBubbleFill = "#dddfe8";
    }

    public MessageEntity() {

    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getMessage() {
        return MSGBody;
    }

    @Override
    public void setMessage(String MSGBody) {
        this.MSGBody = MSGBody;
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean isUnderLineText() {
        return underLineText;
    }

    @Override
    public void setUnderLineText(boolean underLineText) {
        this.underLineText = underLineText;
    }

    @Override
    public double getMessageFontSize() {
        return messageFontSize;
    }

    @Override
    public void setMessageFontSize(double messageFontSize) {
        this.messageFontSize = messageFontSize;
    }

    @Override
    public String getMessageFontFamily() {
        return messageFontFamily;
    }

    @Override
    public void setMessageFontFamily(String messageFontFamily) {
        this.messageFontFamily = messageFontFamily;
    }

    @Override
    public String getMessageTextFill() {
        return messageTextFill;
    }

    @Override
    public void setMessageTextFill(String messageTextFill) {
        this.messageTextFill = messageTextFill;
    }

    @Override
    public String getMessageBubbleFill() {
        Color color = Color.valueOf(messageBubbleFill);

        // Convert the color to a hexadecimal string
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));

        return hex;
    }

    @Override
    public void setMessageBubbleFill(String messageBubbleFill) {
        this.messageBubbleFill = messageBubbleFill;
    }

    @Override
    public boolean isBoldText() {
        return boldText;
    }

    @Override
    public void setBoldText(boolean boldText) {
        this.boldText = boldText;
    }

    @Override
    public boolean isItalicText() {
        return italicText;
    }

    @Override
    public void setItalicText(boolean italicText) {
        this.italicText = italicText;
    }
}

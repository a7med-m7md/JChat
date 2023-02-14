package model;

import javafx.beans.property.*;

public class FormattedMessageEntity extends MessageEntity{
//    private transient BooleanProperty underLine = new SimpleBooleanProperty(false);
//    private transient DoubleProperty fontSize = new SimpleDoubleProperty(14.0);
//    private transient StringProperty fontFamily = new SimpleStringProperty("Segoe UI");
//    private transient StringProperty textFill = new SimpleStringProperty("#333333");
//    private transient StringProperty bubbleFill = new SimpleStringProperty();

    private boolean underLineText;
    private boolean boldText;
    private boolean italicText;
    private double messageFontSize;
    private String messageFontFamily;
    private String messageTextFill;
    private String messageBubbleFill;

    public FormattedMessageEntity(String receiver, String sender, String MSGBody) {
        super(receiver, sender, MSGBody);
        this.underLineText = false;
        this.messageFontSize = 14.0;
        this.messageFontFamily = ("Segoe UI");
        this.messageTextFill = "#3333333";
        this.messageBubbleFill = "#dddfe8";
        this.boldText = false;
        this.italicText = false;
    }

    public boolean isUnderLineText() {
        return underLineText;
    }

    public void setUnderLineText(boolean underLineText) {
        this.underLineText = underLineText;
    }

    public double getMessageFontSize() {
        return messageFontSize;
    }

    public void setMessageFontSize(double messageFontSize) {
        this.messageFontSize = messageFontSize;
    }

    public String getMessageFontFamily() {
        return messageFontFamily;
    }

    public void setMessageFontFamily(String messageFontFamily) {
        this.messageFontFamily = messageFontFamily;
    }

    public String getMessageTextFill() {
        return messageTextFill;
    }

    public void setMessageTextFill(String messageTextFill) {
        this.messageTextFill = messageTextFill;
    }

    public String getMessageBubbleFill() {
        return messageBubbleFill;
    }

    public void setMessageBubbleFill(String messageBubbleFill) {
        this.messageBubbleFill = messageBubbleFill;
    }

    public boolean isBoldText() {
        return boldText;
    }

    public void setBoldText(boolean boldText) {
        this.boldText = boldText;
    }

    public boolean isItalicText() {
        return italicText;
    }

    public void setItalicText(boolean italicText) {
        this.italicText = italicText;
    }
}

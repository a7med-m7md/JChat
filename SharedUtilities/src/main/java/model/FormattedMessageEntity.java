package model;

import javafx.beans.property.*;

public class FormattedMessageEntity extends MessageEntity{
    private transient BooleanProperty underLine = new SimpleBooleanProperty(false);
    private transient DoubleProperty fontSize = new SimpleDoubleProperty(14.0);
    private transient StringProperty fontFamily = new SimpleStringProperty("Segoe UI");
    private transient StringProperty textFill = new SimpleStringProperty("#333333");
    private transient StringProperty bubbleFill = new SimpleStringProperty();

    private boolean underLineText;
    private double messageFontSize;
    private String messageFontFamily;
    private String messageTextFill;
    private String messageBubbleFill;

    public FormattedMessageEntity(String receiver, String sender, String MSGBody) {
        super(receiver, sender, MSGBody);
        this.underLineText = underLine.get();
        this.messageFontSize = fontSize.get();
        this.messageFontFamily = fontFamily.get();
        this.messageTextFill = textFill.get();
        this.messageBubbleFill = bubbleFill.get();
    }

    public boolean isUnderLine() {
        return underLine.get();
    }

    public BooleanProperty underLineProperty() {
        return underLine;
    }

    public void setUnderLine(boolean underLine) {
        this.underLine.set(underLine);
    }

    public double getFontSize() {
        return fontSize.get();
    }

    public DoubleProperty fontSizeProperty() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize.set(fontSize);
    }

    public String getFontFamily() {
        return fontFamily.get();
    }

    public StringProperty fontFamilyProperty() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily.set(fontFamily);
    }

    public String getTextFill() {
        return textFill.get();
    }

    public StringProperty textFillProperty() {
        return textFill;
    }

    public void setTextFill(String textFill) {
        this.textFill.set(textFill);
    }

    public String getBubbleFill() {
        return bubbleFill.get();
    }

    public StringProperty bubbleFillProperty() {
        return bubbleFill;
    }

    public void setBubbleFill(String bubbleFill) {
        this.bubbleFill.set(bubbleFill);
    }
}

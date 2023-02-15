package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface MessageEntityInterface extends Serializable {
    String getSender();

    void setSender(String sender);

    String getMessage();

    void setMessage(String MSGBody);

    LocalDateTime getTime();

    void setTime(LocalDateTime time);

    boolean isUnderLineText();

    void setUnderLineText(boolean underLineText);

    double getMessageFontSize();

    void setMessageFontSize(double messageFontSize);

    String getMessageFontFamily();

    void setMessageFontFamily(String messageFontFamily);

    String getMessageTextFill();

    void setMessageTextFill(String messageTextFill);

    String getMessageBubbleFill();

    void setMessageBubbleFill(String messageBubbleFill);

    boolean isBoldText();

    void setBoldText(boolean boldText);

    boolean isItalicText();

    void setItalicText(boolean italicText);
}

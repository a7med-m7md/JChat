package model;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.rmi.server.RMIClassLoader;
import java.time.LocalDateTime;
import java.util.List;

public class GroupMessageEntity implements MessageEntityInterface {
    protected boolean underLineText;
    protected boolean boldText;
    protected boolean italicText;
    protected double messageFontSize;
    protected String messageFontFamily;
    protected String messageTextFill;
    protected String messageBubbleFill;

    private Group group;
    private String sender;
    private String message;
    private List<FriendEntity> list;
    private LocalDateTime time;


    public GroupMessageEntity(Group group, String sender, String message) {
        super();
        this.group = group;
        this.message = message;
        this.list = group.getListMembers();
        this.sender = sender;
        this.time = LocalDateTime.now();
        this.underLineText = false;
        this.boldText = false;
        this.italicText = false;
        this.messageFontSize = 14.0;
        this.messageFontFamily = ("Segoe UI");
        this.messageTextFill = "#3333333";
        this.messageBubbleFill = "#dddfe8";
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FriendEntity> getList() {
        return list;
    }

    public void setList(List<FriendEntity> list) {
        this.list = list;
    }

    public boolean isUnderLineText() {
        return underLineText;
    }

    public void setUnderLineText(boolean underLineText) {
        this.underLineText = underLineText;
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
        Color color = Color.valueOf(messageBubbleFill);

        // Convert the color to a hexadecimal string
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));

        return hex;
    }


    public void setMessageBubbleFill(String messageBubbleFill) {
        this.messageBubbleFill = messageBubbleFill;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

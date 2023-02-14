package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class GroupMessageEntity extends MessageEntity implements Serializable {
    protected boolean underLineText;
    protected boolean boldText;
    protected boolean italicText;
    protected double messageFontSize;
    protected String messageFontFamily;
    protected String messageTextFill;
    protected String messageBubbleFill;

    private long groupId;
    private String sender;
    private String message;
    private List<FriendEntity> list;
    private LocalDateTime time;


    public GroupMessageEntity(Group group, String sender, String message) {
        super();
        this.groupId = group.getId();
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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
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
        return messageBubbleFill;
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

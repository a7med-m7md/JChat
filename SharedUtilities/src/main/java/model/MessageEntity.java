package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageEntity implements Serializable {
    private String receiver;
    private String sender;
    private String MSGBody;
    private LocalDateTime time;

    public MessageEntity(String receiver, String sender, String MSGBody) {
        this.receiver = receiver;
        this.sender = sender;
        this.MSGBody = MSGBody;
        this.time = LocalDateTime.now();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMSGBody() {
        return MSGBody;
    }

    public void setMSGBody(String MSGBody) {
        this.MSGBody = MSGBody;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

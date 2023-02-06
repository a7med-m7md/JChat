package Client.ui.models;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    Contact contact;
    String senderName;
    String messageBody;
    String timeStamp;
    long id;

    public Message(Contact contact, String messageBody) {
        this.contact = contact;
        this.senderName = contact.getDisplayName();
        this.messageBody = messageBody;
        this.timeStamp = getMessageTime();
        this.id = contact.getId();
        this.timeStamp = getMessageTime();

    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessageTime() {
        DateFormat timeFormat = new SimpleDateFormat("hh.mm aa");
        String currentTime = timeFormat.format(new Date()).toString();
        return currentTime;
    }

}

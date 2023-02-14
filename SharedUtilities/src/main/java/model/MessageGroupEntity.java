package model;

import java.io.Serializable;
import java.util.List;

public class MessageGroupEntity implements Serializable {
    private int groupId;
    private String sender;
    private String message;
    private List<GroupMember> list;

    public MessageGroupEntity() {
    }

    public MessageGroupEntity(int groupId, String message, List<GroupMember> list, String sender) {
        this.groupId = groupId;
        this.message = message;
        this.list = list;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GroupMember> getList() {
        return list;
    }

    public void setList(List<GroupMember> list) {
        this.list = list;
    }
}

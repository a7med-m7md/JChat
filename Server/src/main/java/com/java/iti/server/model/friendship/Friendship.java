package com.java.iti.server.model.friendship;

public class Friendship {
    private long senderId;
    private long receiverId;
    private FriendshipStatus status;

    public Friendship(long senderId, long receiverId, FriendshipStatus status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}

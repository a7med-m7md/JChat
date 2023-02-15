package model;

import model.FriendEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Group implements Serializable {
    private long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String owner_mobile;
    private byte[] picture;
    private List<FriendEntity> listMembers;

    public Group() {
    }

    public Group(String name, String description, String owner_mobile) {
        this.name = name;
        this.description = description;
        this.owner_mobile = owner_mobile;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOwner_mobile() {
        return owner_mobile;
    }

    public void setOwner_mobile(String owner_mobile) {
        this.owner_mobile = owner_mobile;
    }

    public List<FriendEntity> getListMembers() {
        return listMembers;
    }

    public void setListMembers(List<FriendEntity> listMembers) {
        this.listMembers = listMembers;
    }
}
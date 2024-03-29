package model;

import model.user.UserStatus;

import java.io.Serializable;

//todo Change it to be model
public class FriendEntity implements Serializable {
    private String mobile;
    private String name;
    private String bio;
    private UserStatus status;


    // I didn't want to transfer the image
//    private transient SimpleObjectProperty<byte[]> userPhoto =new SimpleObjectProperty<>();
    private byte[] picture;

    public FriendEntity(String mobile, String name, String bio, UserStatus status, byte[] picture){
        this.mobile = mobile;
        this.name = name;
        this.bio = bio;
        this.status = status;
        this.picture = picture;
    }

    public FriendEntity(String name) {
        this.name = name;
    }

    public FriendEntity() {

    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

}

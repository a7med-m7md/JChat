package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

//todo Change it to be model
public class FriendEntity implements Serializable {
    private String mobile;
    private String name;
    private String bio;
    private String status;
    // I didn't want to transfer the image
    private transient SimpleObjectProperty<byte[]> userPhoto =new SimpleObjectProperty<>();


    public FriendEntity(String mobile, String name, String bio, String status){
        this.mobile = mobile;
        this.name = name;
        this.bio = bio;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getUserPhoto() {
        return userPhoto.get();
    }

    public SimpleObjectProperty<byte[]> userPhotoProperty() {
        return userPhoto;
    }

    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto.set(userPhoto);
    }



}

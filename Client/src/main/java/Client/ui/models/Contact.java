package Client.ui.models;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import model.FriendEntity;

import java.io.ByteArrayInputStream;

public class Contact {
    private StringProperty mobile;
    private StringProperty name;
    private StringProperty bio;
    private StringProperty status;

    private Image avatar;

    public Contact(FriendEntity friendEntity) {
        name = new SimpleStringProperty();
        mobile = new SimpleStringProperty();
        bio = new SimpleStringProperty();
        status = new SimpleStringProperty();

        name.set(friendEntity.getName());
        mobile.set(friendEntity.getMobile());
        bio.set(friendEntity.getBio());
        status.set(friendEntity.getStatus());
//        if (friendEntity.getUserPhoto() != null)
//            avatar = new Image(new ByteArrayInputStream(friendEntity.getUserPhoto()));
//        else
//            avatar = new Image(getClass().getResourceAsStream("/FXML/image-placeholder.png"));
    }

    public String getMobile() {
        return mobile.get();
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getBio() {
        return bio.get();
    }

    public StringProperty bioProperty() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio.set(bio);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
}

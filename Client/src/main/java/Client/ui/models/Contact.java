package Client.ui.models;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import model.FriendEntity;
import model.user.UserStatus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Contact implements UserModel {
    private StringProperty mobile;
    private StringProperty name;
    private StringProperty bio;
    private SimpleObjectProperty<UserStatus> status;

    private ObjectProperty<Image> picture = new SimpleObjectProperty<>();

    public Contact(FriendEntity friendEntity) {
        name = new SimpleStringProperty(friendEntity.getName());
        mobile = new SimpleStringProperty(friendEntity.getMobile());
        bio = new SimpleStringProperty(friendEntity.getBio());
        status = new SimpleObjectProperty<UserStatus>(friendEntity.getStatus());
        this.picture.set(new Image(new ByteArrayInputStream(friendEntity.getPicture())));

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

    public UserStatus getStatus() {
        return status.get();
    }

    public SimpleObjectProperty<UserStatus> statusProperty() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status.set(status);
    }
    public ObjectProperty<Image> pictureProperty() {
        return picture;
    }

    public Image getImage() {
        return picture.get();
    }

    public void setPicture(Image picture) {
        this.picture.set(picture);
    }

    public void setPictureAsBytes(byte[] pictureByteArray) {
        this.picture.set(new Image(new ByteArrayInputStream(pictureByteArray)));
    }

    public byte[] getPictureAsBytes() throws IOException {
        int width = (int) picture.get().getWidth();
        int height = (int) picture.get().getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(SwingFXUtils.fromFXImage(picture.get(), null), 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        byte[] imageData = baos.toByteArray();
        return imageData;
    }

}

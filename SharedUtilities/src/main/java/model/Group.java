package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;

import model.FriendEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    private transient ObjectProperty<Image> image = new SimpleObjectProperty<>();


    public Group() {
    }

    public Group(String name, String description, String owner_mobile) {
        this.name = name;
        this.description = description;
        this.owner_mobile = owner_mobile;
    }
    public byte[] getPictureAsBytes() throws IOException {
        int width = (int) image.get().getWidth();
        int height = (int) image.get().getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(SwingFXUtils.fromFXImage(image.get(), null), 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        byte[] imageData = baos.toByteArray();
        return imageData;
    }

    public Image getImage() {
        image.set(new Image(new ByteArrayInputStream(picture)));
        if(image == null)
        return image.get();
        else
        return new Image(new ByteArrayInputStream(picture));

    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) throws IOException {
        this.image.set(image);
        setPicture(getPictureAsBytes());
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
        image.set(new Image(new ByteArrayInputStream(picture)));
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
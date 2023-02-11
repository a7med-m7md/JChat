package Client.ui.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import model.user.Gender;
import model.user.UserEntity;
import model.user.UserStatus;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.*;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;

public class CurrentUserAccount {
    private StringProperty phoneNumber;
    private StringProperty name;
    private StringProperty email;
    private Image picture;
    private StringProperty password;
    private SimpleObjectProperty<Gender> gender;
    private StringProperty country;
    private StringProperty dateOfBirth;
    private StringProperty bio;
    private SimpleObjectProperty<String> status;
    private static CurrentUserAccount myAccount;

    private CurrentUserAccount() {
        name  = new SimpleStringProperty();
        phoneNumber  = new SimpleStringProperty();
        email  = new SimpleStringProperty();
        password  = new SimpleStringProperty();
        gender  = new SimpleObjectProperty();
        country = new SimpleStringProperty();
        dateOfBirth = new SimpleStringProperty();
        bio = new SimpleStringProperty();
        status = new SimpleObjectProperty<>();
        picture = new Image(getClass().getResourceAsStream("/images/image-placeholder.png"));

    }
    public static CurrentUserAccount getInstance() {
        if (myAccount == null)
            myAccount = new CurrentUserAccount();
        return myAccount;
    }


    public void populateCurrentUserData(UserEntity userDataFromDB) {
        this.name.set(userDataFromDB.getName());
        this.phoneNumber.set(userDataFromDB.getMobile());
        this.email.set(userDataFromDB.getEmail());
        this.gender.set(userDataFromDB.getGender());
        this.password.set(userDataFromDB.getPassword());
        this.country.set(userDataFromDB.getCountry());
        this.dateOfBirth.set(userDataFromDB.getDateOfBirth());
        this.bio.set(userDataFromDB.getBio());
        this.status.set(userDataFromDB.getStatus().getStatus());
        this.picture = new Image(new ByteArrayInputStream(userDataFromDB.getPicture()));
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public void setPictureAsBytes(byte[] pictureByteArray) {
        this.picture = new Image(new ByteArrayInputStream(pictureByteArray));
    }

    public byte[] getPictureAsBytes() throws IOException {
        int width = (int) picture.getWidth();
        int height = (int) picture.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(SwingFXUtils.fromFXImage(picture, null), 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        byte[] imageData = baos.toByteArray();
        return imageData;
    }

    public static CurrentUserAccount getMyAccount() {
        return myAccount;
    }

    public static void setMyAccount(CurrentUserAccount myAccount) {
        CurrentUserAccount.myAccount = myAccount;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Gender getGender() {
        return gender.get();
    }

    public SimpleObjectProperty<Gender> genderProperty() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender.set(gender);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public StringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
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
        return UserStatus.valueOf(status.get());
    }

    public SimpleObjectProperty<String> statusProperty() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status.set(status.getStatus());
    }
}


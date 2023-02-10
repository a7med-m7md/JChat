package Client.ui.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import model.user.Gender;
import model.user.UserEntity;
import model.user.UserStatus;

public class CurrentUserAccount {

    private static CurrentUserAccount myAccount;

    private CurrentUserAccount() {
    }
    public static CurrentUserAccount getInstance() {
        if (myAccount == null)
            myAccount = new CurrentUserAccount();
        return myAccount;
    }
    private StringProperty phoneNumber;
    private StringProperty name;
    private StringProperty email;
    private StringProperty picture;
    private StringProperty password;
    private SimpleObjectProperty<Gender> gender;
    private StringProperty country;
    private StringProperty dateOfBirth;
    private StringProperty bio;
    private SimpleObjectProperty<UserStatus> status;

    public void populateCurrentUserData(UserEntity userDataFromDB) {
        this.name.set(userDataFromDB.getName());
        this.phoneNumber.set(userDataFromDB.getMobile());
        this.email.set(userDataFromDB.getEmail());
        this.gender.set(userDataFromDB.getGender());
        this.password.set(userDataFromDB.getPassword());
        this.country.set(userDataFromDB.getCountry());
        this.dateOfBirth.set(userDataFromDB.getDateOfBirth());
        this.bio.set(userDataFromDB.getBio());
        this.status.set(userDataFromDB.getStatus());
    }


}


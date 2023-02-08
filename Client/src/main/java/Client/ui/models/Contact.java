package Client.ui.models;


import javafx.scene.image.Image;

public class Contact {
    String displayName;
    long id;
    Image avatar;

    String group;

    public Contact(String displayName) {
        this.displayName = displayName;
        this.avatar = new Image(getClass().getResourceAsStream("/images/error.png"));
    }

    public String getDisplayName() {
        return displayName;
    }

    public Image getAvatar() {
        return avatar;
    }

    public long getId() {
        return id;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

}

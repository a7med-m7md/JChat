package model.user;
import javafx.scene.paint.Color;

public enum UserStatus {
    AVAILABLE("Available", Color.valueOf("#14E88F")),
    BUSY("Busy", Color.valueOf("#FF7C5F")),
    AWAY("Away", Color.valueOf("#E4E900")),
    OFFLINE("Offline", Color.valueOf("#D2D2D2"));
    private String status;
    private Color color;
    UserStatus(String status, Color color) {
        this.status = status;
        this.color = color;
    }
    public String getStatusName(){return status;}

    public static UserStatus getStatus(String status) {
        if (status == null) {
            return null;
        }
        if (status.equalsIgnoreCase("Available")) {
            return AVAILABLE;
        }
        if (status.equalsIgnoreCase("Busy")) {
            return BUSY;
        }
        if (status.equalsIgnoreCase("Away")) {
            return AWAY;
        }
        if (status.equalsIgnoreCase("Offline")) {
            return OFFLINE;
        }
        return null;
    }

    public Color getColor() {return color;}
}
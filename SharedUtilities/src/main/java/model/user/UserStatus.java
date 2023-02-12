package model.user;
import javafx.scene.paint.Color;

public enum UserStatus {
    AVAILABLE("Available", Color.GREEN),
    BUSY("Busy", Color.RED),
    AWAY("Away", Color.YELLOW),
    OFFLINE("Offline", Color.GRAY);
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
package model.user;

public enum UserStatus {
    AVAILABLE("Available"), BUSY("busy"), AWAY("Away"), OFFLINE("Offline");
    private String status;
    UserStatus(String status) {
        this.status = status;
    }
}

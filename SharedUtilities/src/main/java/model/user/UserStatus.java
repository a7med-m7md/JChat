package model.user;

public enum UserStatus {
    AVAILABLE("Available"), BUSY("Busy"), AWAY("Away"), OFFLINE("Offline");
    private String status;
    UserStatus(String status) {
        this.status = status;
    }
    public String getStatus(){return status;}

}

package com.java.iti.server.model.user;

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");
    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return gender;
    }
}

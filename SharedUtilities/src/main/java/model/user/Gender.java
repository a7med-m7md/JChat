package model.user;

public enum Gender {
    MALE("Male"), FEMALE("Female");
    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGenderName() {
        return gender;
    }

    public static Gender getGender(String gender) {

        if (gender.equals("Male"))
            return MALE;
        if (gender.equals("Female"))
            return FEMALE;
        return null;
    }
}

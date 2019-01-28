package com.reynaldiwijaya.crudapi.model.User;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("id")
    private int id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("avatar")
    private String gambar;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGambar() {
        return gambar;
    }
}

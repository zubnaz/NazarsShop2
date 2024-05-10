package com.example.nazarsshop2.userInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("firstName")
    @Expose
    private String FirstName;
    @SerializedName("lastName")
    @Expose
    private String LastName;
    @SerializedName("email")
    @Expose
    private String Email;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @SerializedName("image")
    @Expose
    private String Image;
    @SerializedName("role")
    @Expose
    private String Role;
}

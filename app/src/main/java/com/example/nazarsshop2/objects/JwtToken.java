package com.example.nazarsshop2.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JwtToken {
    @SerializedName("token")
    @Expose
    private String token = "";
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}

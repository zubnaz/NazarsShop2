package com.example.nazarsshop2.interfaces;

import com.example.nazarsshop2.objects.Category;
import com.example.nazarsshop2.objects.JwtToken;
import com.example.nazarsshop2.objects.Login;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @GET("/api/Categories/")
    public Call<List<Category>> getCategory ();
    @POST("/api/Account/login")
    public Call<JwtToken> Login (@Body Login login);
}

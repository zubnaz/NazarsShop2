package com.example.nazarsshop2.interfaces;

import com.example.nazarsshop2.objects.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi {
    @GET("/api/Categories/")
    public Call<List<Category>> getCategory ();
}

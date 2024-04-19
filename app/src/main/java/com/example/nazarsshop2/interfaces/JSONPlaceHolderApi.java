package com.example.nazarsshop2.interfaces;

import com.example.nazarsshop2.objects.Category;
import com.example.nazarsshop2.objects.CategoryCreateDto;
import com.example.nazarsshop2.objects.CategoryEditDto;
import com.example.nazarsshop2.objects.JwtToken;
import com.example.nazarsshop2.objects.Login;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
    @GET("/api/Categories/")
    public Call<List<Category>> getCategory();

    @POST("/api/Account/login")
    public Call<JwtToken> Login(@Body Login login);

    /*@Multipart
    @POST("/api/Categories/")
    public Call<Category> Create(@Part("model") CategoryCreateDto model);*/
    @Multipart
    @POST("/api/Categories/")
    public Call<Category> Create(@Part("name") RequestBody name,
                                 @Part("description") RequestBody description,
                                 @Part MultipartBody.Part image);
    @DELETE("/api/Categories/{id}")
    public Call<Void> Delete(@Path("id") int id);

    @PUT("/api/Categories")
    public Call<Void> Edit(@Body CategoryEditDto model);
}

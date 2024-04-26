package com.example.nazarsshop2;

import com.example.nazarsshop2.interfaces.JSONPlaceHolderApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService networkService;
    //private String URL = "https://spu111.itstep.click/";
    private String URL = "http://192.168.0.105:5182/";
    private Retrofit retrofit;

    public NetworkService(){
        HttpLoggingInterceptor interceptor =  new  HttpLoggingInterceptor ();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

        OkHttpClient.Builder client =  new  OkHttpClient.Builder ()
                .addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static NetworkService GetNetworkService(){
        if(networkService==null){
            networkService = new NetworkService();
        }
        return networkService;
    }
    public JSONPlaceHolderApi getApi(){return retrofit.create(JSONPlaceHolderApi.class);}

}

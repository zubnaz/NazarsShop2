package com.example.nazarsshop2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nazarsshop2.adapter.CategoryAdapter;
import com.example.nazarsshop2.objects.Category;
import com.example.nazarsshop2.objects.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Log.d("token --->",CookieManager.getInstance().getCookie("token"));

        //String cookies = CookieManager.getInstance().getCookie("192.168.0.105:5182");
        String cookies = Token.getToken();


        RecyclerView recyclerView = findViewById(R.id.categoryView);
        NetworkService
                .GetNetworkService()
                .getApi()
                .getCategory("Bearer " + cookies)
                .enqueue(
                        new Callback<List<Category>>() {
                            @Override
                            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                                if(response.body()!=null){
                                    int count = response.body().size();
                                    List<Category> categories = response.body();
                                    //NetworkService
                                            //.GetNetworkService()
                                            //.getApi()
                                            //.checkRole("Bearer "+cookies)
                                           // .enqueue(
                                                    //new Callback<Boolean>() {
                                                        //@Override
                                                        //public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                                            Log.d("Count --->",String.valueOf(count));


                                                            CategoryAdapter adapter = new CategoryAdapter(getBaseContext(),categories,MainActivity.this);

                                                            recyclerView.setAdapter(adapter);
                                                            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                                        //}

                                                       // @Override
                                                        //public void onFailure(Call<Boolean> call, Throwable t) {

                                                        //}
                                                   // }
                                            //);

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Category>> call, Throwable t) {
                                Log.e("Error REST","Something went wrong");
                            }
                        }
                );
    }
}
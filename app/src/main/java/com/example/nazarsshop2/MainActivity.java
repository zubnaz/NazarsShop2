package com.example.nazarsshop2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

        RecyclerView recyclerView = findViewById(R.id.categoryView);
        NetworkService
                .GetNetworkService()
                .getApi()
                .getCategory()
                .enqueue(
                        new Callback<List<Category>>() {
                            @Override
                            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                                if(response.body()!=null){
                                    int count = response.body().size();
                                    Log.d("Count --->",String.valueOf(count));
                                    List<Category> categories = response.body();
                                    CategoryAdapter adapter = new CategoryAdapter(getBaseContext(),categories,MainActivity.this);

                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
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
package com.example.nazarsshop2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nazarsshop2.objects.CategoryEditDto;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryEditActivity extends BaseActivity {
    TextInputLayout tlName;
    TextInputLayout tlDescription;
    Button btnCategoryEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_edit);
        tlName = findViewById(R.id.tlCategoryNameEdit);
        tlDescription = findViewById(R.id.tlCategoryDescriptionEdit);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        tlName.getEditText().setText(name);
        String description = intent.getStringExtra("Description");
        tlDescription.getEditText().setText(description);

        btnCategoryEdit = findViewById(R.id.btnCategoryEdit);
        btnCategoryEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tlName.getEditText().getText().toString().isEmpty() || tlDescription.getEditText().getText().toString().isEmpty())
                        {
                            Toast.makeText(CategoryEditActivity.this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            CategoryEditDto model = new CategoryEditDto();
                            model.setId(intent.getIntExtra("Id",-1));
                            model.setName(tlName.getEditText().getText().toString());
                            model.setDescription(tlDescription.getEditText().getText().toString());
                            NetworkService
                                    .GetNetworkService()
                                    .getApi()
                                    .Edit(model)
                                    .enqueue(
                                            new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    if(response.isSuccessful()){
                                                        Intent intent = new Intent(CategoryEditActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {

                                                }
                                            }
                                    );
                        }
                    }
                }
        );
    }

}
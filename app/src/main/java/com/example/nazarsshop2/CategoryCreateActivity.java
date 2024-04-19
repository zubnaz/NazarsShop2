package com.example.nazarsshop2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nazarsshop2.adapter.CategoryAdapter;
import com.example.nazarsshop2.objects.Category;
import com.example.nazarsshop2.objects.CategoryCreateDto;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;
    Button btnGetPhoto;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView image;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_create);

        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);
        btnGetPhoto= findViewById(R.id.buttonGetPhoto);
        image = findViewById(R.id.imageView);
        RegisterResult();
        btnGetPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
                        activityResultLauncher.launch(intent);
                    }
                }
        );
    }
    public void onClickCreateCategory(View view){
        try {
            //CategoryCreateDto newCategory = new CategoryCreateDto();
            String name = tlCategoryName.getEditText().getText().toString().trim();
            String description = tlCategoryDescription.getEditText().getText().toString().trim();

            RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody descriptionPart = RequestBody.create(MediaType.parse("text/plain"), description);

            //newCategory.setName(name);
            //newCategory.setDescription(description);

            Drawable drawable = image.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Description", null);
            Uri imageUri = Uri.parse(path);
            File imageFile = new File(getRealPathFromURI(imageUri));

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
            //newCategory.setImage(imagePart);

            NetworkService
                    .GetNetworkService()
                    .getApi()
                    .Create(namePart,descriptionPart,imagePart)
                    .enqueue(
                            new Callback<Category>() {
                                @Override
                                public void onResponse(Call<Category> call, Response<Category> response) {
                                    if(response.isSuccessful()){
                                        Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Category> call, Throwable t) {
                                    Log.e("Error REST","Something went wrong");
                                }
                            }
                    );
        } catch (Exception ex) {
            Log.e("---Category create activity---","Problem create"+ex.getMessage());
        }

    }
    private void RegisterResult(){
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {
                            uri = o.getData().getData();
                            //image.setImageURI(uri);
                            Glide.with(getApplicationContext())
                                    .load(uri)
                                    .apply(new RequestOptions().override(400))
                                    .into(image);
                        }catch (Exception ex){
                            Toast.makeText(CategoryCreateActivity.this, "Some mistake was made!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

}
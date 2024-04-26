package com.example.nazarsshop2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryEditActivity extends BaseActivity {
    TextInputLayout tlName;
    TextInputLayout tlDescription;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Button btnCategoryEdit;
    Button btnGetPhoto;
    ImageView image;
    Uri uri;

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
        btnGetPhoto = findViewById(R.id.buttonGetPhotoEdit);
        btnGetPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
                        activityResultLauncher.launch(intent);
                    }
                }
        );
        image = findViewById(R.id.imageViewEdit);
        Glide.with(getApplicationContext())
                .load(intent.getStringExtra("Image"))
                .apply(new RequestOptions().override(400))
                .into(image);

        RegisterResult();
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
                            int id = intent.getIntExtra("Id",-1);
                            RequestBody idPart = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(id));
                            RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), tlName.getEditText().getText().toString());
                            RequestBody descriptionPart = RequestBody.create(MediaType.parse("text/plain"), tlDescription.getEditText().getText().toString());
                            Drawable drawable = image.getDrawable();
                            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Description", null);
                            Uri imageUri = Uri.parse(path);
                            File imageFile = new File(getRealPathFromURI(imageUri));

                            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
                            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
                            /*CategoryEditDto model = new CategoryEditDto();
                            model.setId(intent.getIntExtra("Id",-1));
                            model.setName(tlName.getEditText().getText().toString());
                            model.setDescription(tlDescription.getEditText().getText().toString());*/
                            NetworkService
                                    .GetNetworkService()
                                    .getApi()
                                    .Edit(idPart,namePart,descriptionPart,imagePart)
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
                            Toast.makeText(CategoryEditActivity.this, "Some mistake was made!", Toast.LENGTH_SHORT).show();
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
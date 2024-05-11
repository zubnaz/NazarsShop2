package com.example.nazarsshop2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nazarsshop2.objects.Category;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends BaseActivity {

    EditText firstName,lastName,email,password;
    Button btnGetPhoto;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView image;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
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
    public void onClickRegister(View view){
        try {
            //CategoryCreateDto newCategory = new CategoryCreateDto();
            String first_Name = firstName.getText().toString().trim();
            String last_Name = lastName.getText().toString().trim();
            String _email = email.getText().toString().trim();
            String _passwrod = password.getText().toString().trim();

            RequestBody firstNamePart = RequestBody.create(MediaType.parse("text/plain"), first_Name);
            RequestBody lastNamePart = RequestBody.create(MediaType.parse("text/plain"), last_Name);
            RequestBody emailPart = RequestBody.create(MediaType.parse("text/plain"), _email);
            RequestBody passwordPart = RequestBody.create(MediaType.parse("text/plain"), _passwrod);


            Drawable drawable = image.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "IMG_" + Calendar.getInstance().getTime(), null);
            Uri imageUri = Uri.parse(path);
            File imageFile = new File(getRealPathFromURI(imageUri));

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);


            NetworkService
                    .GetNetworkService()
                    .getApi()
                    .Register(firstNamePart,lastNamePart,emailPart,passwordPart,imagePart)
                    .enqueue(
                            new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        Intent intent = new Intent(Register.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("Error REST","Something went wrong");
                                }
                            }
                    );
        } catch (Exception ex) {
            Log.e("---Register activity---","Problem create"+ex.getMessage());
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
                            Toast.makeText(Register.this, "Some mistake was made!", Toast.LENGTH_SHORT).show();
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
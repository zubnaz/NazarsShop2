package com.example.nazarsshop2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nazarsshop2.userInfo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {

    ImageView avatar;
    TextView tvFirstName,tvLastName,tvEmail,tvRole;
    Button exitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

        String cookies = CookieManager.getInstance().getCookie("192.168.0.105:5182");
        avatar = findViewById(R.id.imageProfile);
        tvFirstName = findViewById(R.id.textViewFirstName);
        tvLastName = findViewById(R.id.textViewLastName);
        tvEmail = findViewById(R.id.textViewEmail);
        tvRole = findViewById(R.id.textViewRole);
        exitButton = findViewById(R.id.buttonExit);
        NetworkService
                .GetNetworkService()
                .getApi()
                .getInfo("Bearer "+cookies)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        String url = "http://192.168.0.105:5182/images/"+user.getImage();
                        Glide.with(getApplicationContext()).load(url).apply(new RequestOptions().override(500)).into(avatar);
                        tvFirstName.setText(user.getFirstName());
                        tvLastName.setText(user.getLastName());
                        tvEmail.setText(user.getEmail());
                        tvRole.setText(user.getRole());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
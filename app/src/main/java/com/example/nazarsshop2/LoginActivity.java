package com.example.nazarsshop2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nazarsshop2.objects.JwtToken;
import com.example.nazarsshop2.objects.Login;
import com.example.nazarsshop2.objects.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText email = findViewById(R.id.editTextTextEmailAddress);
                        EditText password = findViewById(R.id.editTextTextPassword);
                        if(email.getText().toString()!="" && password.getText().toString()!=""){
                            Login login = new Login();
                            login.setEmail(email.getText().toString());
                            login.setPassword(password.getText().toString());
                            NetworkService
                                    .GetNetworkService()
                                    .getApi()
                                    .Login(login)
                                    .enqueue(
                                            new Callback<JwtToken>() {
                                                @Override
                                                public void onResponse(Call<JwtToken> call, Response<JwtToken> response) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                                    if(response.isSuccessful()) {
                                                        Log.i("333", "You're welcome!");

                                                        builder.setMessage("You're welcome!");
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        String token = response.body().getToken().toString();
                                                        Log.i("Token-login1",token);
                                                        Token.setToken(token);
                                                        Log.i("Token-login2",Token.getToken());
                                                        /*CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
                                                            @Override
                                                            public void onReceiveValue(Boolean aBoolean) {
                                                                if (aBoolean) {
                                                                    Log.d("Cookies", "All cookies removed");
                                                                } else {
                                                                    Log.e("Cookies", "Failed to remove cookies");
                                                                }
                                                            }
                                                        });
                                                        CookieManager.getInstance().setCookie("192.168.0.105:5182",token);*/


                                                        //intent.putExtra("token",response.body().getToken());
                                                        LoginActivity.this.finish();
                                                        startActivity(intent);
                                                    }
                                                    else {
                                                        builder.setMessage("Invalid parameters!");
                                                    }
                                                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.cancel();
                                                            }
                                                        });
                                                        AlertDialog dialog = builder.create();
                                                        dialog.show();
                                                }

                                                @Override
                                                public void onFailure(Call<JwtToken> call, Throwable t) {

                                                }
                                            }
                                    );
                        }
                    }
                }
        );
        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
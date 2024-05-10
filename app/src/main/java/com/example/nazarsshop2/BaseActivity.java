package com.example.nazarsshop2;

import static com.example.nazarsshop2.R.*;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nazarsshop2.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }

    public BaseActivity() {
        CommonUtils.setContext(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int select = item.getItemId();
        if(select==R.id.m_home){
            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(select== id.m_profile){
            Intent intent = new Intent(BaseActivity.this, UserProfile.class);
            startActivity(intent);
            return true;
        }
        else if(select== id.m_create){
            Intent intent = new Intent(BaseActivity.this, CategoryCreateActivity.class);
            startActivity(intent);
            return true;
        }
        else if(select== id.m_register){
            Intent intent = new Intent(BaseActivity.this, Register.class);
            startActivity(intent);
            return true;
        }
        else if(select== id.m_exit){

            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            startActivity(intent);
        return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

}

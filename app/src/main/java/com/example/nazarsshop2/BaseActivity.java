package com.example.nazarsshop2;

import static com.example.nazarsshop2.R.*;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int select = item.getItemId();
        if(select==R.id.m_home){
            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
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
        else if(select== id.m_login){
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

}

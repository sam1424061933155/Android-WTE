package com.example.sam14240619331555.food_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.sam14240619331555.food_app.MainActivity;

import java.io.File;

/**
 * Created by sam14240619331555 on 2016/1/23.
 */
public class login extends Activity {    //extend 要記得改


    Button login;
    TextView register;
    EditText data_account;
    String account=null;
    public SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        data_account =(EditText) findViewById(R.id.data_account);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account=data_account.getText().toString();
                setting = getSharedPreferences("LoginInfo", 0);
                setting.edit().putString("account",account).commit();
                Log.v("log", "login in login");
                Intent in = new Intent();
                in.setClass(login.this, SplashScreenActivity.class);
                startActivity(in);
                MainActivity.instance.finish();
                login.this.finish();
            }
        });

        register = (TextView)findViewById(R.id.register);
    }

    public void register(View view) {
        // 呼叫這個方法結束Activity元件
        Intent intent = new Intent();
        intent.setClass(login.this, register.class);
        startActivity(intent);
    }




}

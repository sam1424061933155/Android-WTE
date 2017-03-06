package com.example.sam14240619331555.food_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.afollestad.materialdialogs.AlertDialogWrapper;

public class response extends AppCompatActivity {

    EditText resp_editext;
    String resp="";
    RatingBar resp_star;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView comm_upload =(ImageView)findViewById(R.id.comm_upload);

        resp_editext = (EditText) findViewById(R.id.resp_editext);

        comm_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resp = resp_editext.getText().toString();
                if (resp.equals("")) {
                    new AlertDialogWrapper.Builder(response.this)
                            .setTitle("提醒")
                            .setMessage("請填寫回應內容")
                            .setNegativeButton("了解", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("放棄", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();

                } else {
                    new AlertDialogWrapper.Builder(response.this)
                            .setTitle("提醒")
                            .setMessage("上傳成功")
                            .setNegativeButton("了解", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                }
            }
        });

    }

    public void  starShow(View view){
        resp_star = (RatingBar) findViewById(R.id.resp_star);
        resp_star.setVisibility(View.VISIBLE);

    }

}

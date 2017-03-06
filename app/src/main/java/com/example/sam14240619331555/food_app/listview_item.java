package com.example.sam14240619331555.food_app;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by sam14240619331555 on 2016/1/15.
 */
public class listview_item {
    private String title;
    private String count;
    private String type;
    private String lifecycle;



    public listview_item(String count,String title,String type,String lifecycle) {
        this.count = count;
        this.title = title;
        this.type = type;
        this.lifecycle=lifecycle;



    }
    public String getTitle(){

        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getCount(){
        //this.time="時間: "+time;
        return count;
    }
    public void setCount(String count){
        this.count =count;
    }
    public String getType(){
        //this.dist="距離: "+dist;
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getLifecycle(){

        return lifecycle;
    }



}


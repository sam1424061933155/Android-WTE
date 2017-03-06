package com.example.sam14240619331555.food_app;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by sam14240619331555 on 2016/3/17.
 */
public class listview_company_good_item {

    private Bitmap company_url;
    private String company_title;

    public listview_company_good_item ( Bitmap company_url, String company_title) {
        this.company_url = company_url;
        this.company_title= company_title;



    }
    public Bitmap getCompany_url(){

        return company_url;
    }
    public void setCompany_url(Bitmap company_logo){
        this.company_url=company_url;
    }
    public String getCompany_title(){

        return company_title;
    }
    public void setCompany_title(String company_title){
        this.company_title=company_title;
    }
}

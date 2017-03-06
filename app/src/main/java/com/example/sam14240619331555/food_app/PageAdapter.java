package com.example.sam14240619331555.food_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PageAdapter extends BaseAdapter {
    private ArrayList<String> list;
    LayoutInflater inflater;
    Context context;
    public PageAdapter(Context context,ArrayList<String> list) {
        this.context = context;
        this.list=list;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CacheView cacheView;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_page, null);
            cacheView=new CacheView();
            cacheView.item_image=(ImageView) convertView.findViewById(R.id.item_image);
            //cacheView.imgv_img=(ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(cacheView);
        }else{
            cacheView=(CacheView) convertView.getTag();
        }
        Picasso.with(context).load(list.get(position).toString() + ".jpg").resize(550, 600).into(cacheView.item_image);

        return convertView;
    }

    private static class CacheView{
        //TextView tv_des;
        ImageView item_image;
    }
}

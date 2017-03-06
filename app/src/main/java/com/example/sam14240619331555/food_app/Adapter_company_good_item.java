package com.example.sam14240619331555.food_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by sam14240619331555 on 2016/3/17.
 */
public class Adapter_company_good_item extends BaseAdapter {


    private LayoutInflater myInflater;
    private List<listview_company_good_item> adapter_company_good_item;
    Context adapter_context;


    public Adapter_company_good_item(Context context, List<listview_company_good_item> adapter_company_good_item){
        adapter_context = context;
        myInflater = LayoutInflater.from(context);
        this.adapter_company_good_item = adapter_company_good_item;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView adapter_company_good_item_pc;
        TextView adapter_company_good_item_tv;



        public ViewHolder(ImageView adapter_company_good_item_pc, TextView adapter_company_good_item_tv){
            this.adapter_company_good_item_pc = adapter_company_good_item_pc;
            this.adapter_company_good_item_tv = adapter_company_good_item_tv;
        }
    }

    @Override
    public int getCount() {
        return adapter_company_good_item.size();
    }

    @Override
    public Object getItem(int arg0) {
        return adapter_company_good_item.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return adapter_company_good_item.indexOf(getItem(position));
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.adapter_company_good_item, null);
            holder = new ViewHolder(
                    (ImageView) convertView.findViewById(R.id.adapter_company_good_item_pc),
                    (TextView) convertView.findViewById(R.id.adapter_company_good_item_tv)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        listview_company_good_item adapter_company_good_item = (listview_company_good_item)getItem(position);




        holder.adapter_company_good_item_pc.setImageBitmap(adapter_company_good_item.getCompany_url());
        holder.adapter_company_good_item_tv.setText(adapter_company_good_item.getCompany_title());




        holder.adapter_company_good_item_pc.setVisibility(View.VISIBLE);
        holder.adapter_company_good_item_tv .setVisibility(View.VISIBLE);




        return convertView;
    }

}

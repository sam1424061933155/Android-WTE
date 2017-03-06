package com.example.sam14240619331555.food_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sam14240619331555 on 2016/1/15.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<listview_item> food;
    public  int pos;
    public MyAdapter(Context context,List<listview_item> food){
        myInflater = LayoutInflater.from(context);
        this.food = food;
    }
    /*private view holder class*/
    private class ViewHolder {
        TextView txtCount;
        TextView txtTitle;
        TextView txtType;



        public ViewHolder(TextView txtCount, TextView txtTitle,TextView txtType){
            this.txtCount = txtCount;
            this.txtTitle = txtTitle;
            this.txtType = txtType;


        }
    }

    @Override
    public int getCount() {
        return food.size();
    }

    @Override
    public Object getItem(int arg0) {
        return food.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return food.indexOf(getItem(position));
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        pos =position;
        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.custom_listview, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.item_count),
                    (TextView) convertView.findViewById(R.id.item_title),
                    (TextView) convertView.findViewById(R.id.item_type)


            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        listview_item food = (listview_item)getItem(position);


        holder.txtCount.setText(food.getCount());
        holder.txtTitle.setText(food.getTitle());
        holder.txtType.setText(food.getType() + "料理/" + food.getLifecycle());







        holder.txtCount.setVisibility(View.VISIBLE);
        holder.txtTitle.setVisibility(View.VISIBLE);
        holder.txtType.setVisibility(View.VISIBLE);



        return convertView;
    }

}
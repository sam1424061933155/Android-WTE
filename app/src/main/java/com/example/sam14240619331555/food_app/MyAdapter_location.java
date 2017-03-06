package com.example.sam14240619331555.food_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sam14240619331555 on 2016/1/15.
 */
public class MyAdapter_location extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<listview_item_location> location;
    public  int pos;
    public MyAdapter_location(Context context,List<listview_item_location> location){
        myInflater = LayoutInflater.from(context);
        this.location = location;
    }
    /*private view holder class*/
    private class ViewHolder {
        TextView txtlocation;
        TextView txtselect;
        public ViewHolder(TextView txtlocation){
            this.txtlocation = txtlocation;
            //this.txtselect = txtselect;
        }
    }

    @Override
    public int getCount() {
        return location.size();
    }

    @Override
    public Object getItem(int arg0) {
        return location.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return location.indexOf(getItem(position));
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        pos =position;
        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.location_listview, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.item_location)//,
                    //(TextView) convertView.findViewById(R.id.item_select)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        listview_item_location location = (listview_item_location)getItem(position);



        holder.txtlocation.setText(location.getLocation());
        //holder.txtselect.setText(location.getSelect());
        holder.txtlocation.setVisibility(View.VISIBLE);
        //holder.txtselect.setVisibility(View.VISIBLE);
        return convertView;
    }

}
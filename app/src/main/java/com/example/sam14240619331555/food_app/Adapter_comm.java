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
 * Created by sam14240619331555 on 2016/1/30.
 */
public class Adapter_comm extends BaseAdapter {

    private LayoutInflater myInflater;
    private List<listview_comm> comm;
    public  int pos;

    public Adapter_comm(Context context,List<listview_comm> comm){
        myInflater = LayoutInflater.from(context);
        this.comm = comm;
    }
    /*private view holder class*/
    private class ViewHolder {
        ImageView image;
        TextView txtfloor;
        TextView txtComm;



        public ViewHolder(ImageView image, TextView txtfloor,TextView txtComm){
            this.image=image;
            this.txtfloor=txtfloor;
            this.txtComm = txtComm;

        }
    }

    @Override
    public int getCount() {
        return comm.size();
    }

    @Override
    public Object getItem(int arg0) {
        return comm.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return comm.indexOf(getItem(position));
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        pos =position;
        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.comm_listview, null);
            holder = new ViewHolder(
                    (ImageView) convertView.findViewById(R.id.user),
                    (TextView) convertView.findViewById(R.id.list_floor),
                    (TextView) convertView.findViewById(R.id.list_comm)

            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        listview_comm comm = (listview_comm)getItem(position);



        holder.txtfloor.setText("B"+comm.getFloor());
        holder.txtComm.setText(comm.getComm() );





        holder.image.setVisibility(View.VISIBLE);
        holder.txtfloor.setVisibility(View.VISIBLE);
        holder.txtComm.setVisibility(View.VISIBLE);


        return convertView;
    }

}

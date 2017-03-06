package com.example.sam14240619331555.food_app;

/**
 * Created by sam14240619331555 on 2016/1/15.
 */
public class listview_item_location {
    private String location;
    private int select;

    public listview_item_location(String location,int select) {
        this.location = location;
        this.select = select;
    }
    public String getLocation(){

        return  location;
    }
    public void setLocation(String  location){
        this. location= location;
    }
    public int getSelect(){
        return select;
    }
    public void setSelect(int select){
        this.select =select;
    }


}


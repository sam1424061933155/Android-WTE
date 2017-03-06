package com.example.sam14240619331555.food_app;

/**
 * Created by sam14240619331555 on 2016/1/30.
 */
public class listview_comm {
    private String floor;
    private String comm;

    public listview_comm(String floor,String comm) {
        this.floor=floor;
        this.comm = comm;



    }
    public String getComm(){

        return comm;
    }
    public void setComm(String comm){
        this.comm=comm;
    }
    public String getFloor(){

        return floor;
    }
    public void setFloor(String floor){
        this.floor=floor;
    }
}

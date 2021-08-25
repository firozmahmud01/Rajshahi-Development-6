package com.fire.mahmud.raj_6;

import java.util.ArrayList;

public class Data {
    private ArrayList<OnlineData>ndata;
    private String picdata[];
    public Data(ArrayList<OnlineData>ndata,String picdata[]){
        this.ndata=ndata;
        this.picdata=picdata;
    }
    public String[] getPicData(){
        return this.picdata;
    }
    public ArrayList<OnlineData> getAllData(){
        return this.ndata;
    }
}

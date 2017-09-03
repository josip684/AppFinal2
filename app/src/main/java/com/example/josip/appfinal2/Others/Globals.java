package com.example.josip.appfinal2.Others;

import android.app.Application;

public class Globals extends Application {
    private static Globals instance=null;
    public int Test;//pamti prvu razinu dugmića
    public String emailReg;
    public String emailReg2;
    //public boolean enable_disable;
    public int Test2;//pamti drugu razinu dugmića
    public int Start;//pamti početni broj u json stringu za tu i tu teoriju
    public int End;//pamti zadnji broj u json stringu za tu i tu teoriju
    public String dohvatiKey;
    public Globals(){}

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}

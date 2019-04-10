package com.example.shalin.myapplication;

/**
 * Created by Shalin on 05-03-2019.
 */

public class Pojo {

    private String id;
    private String datavalue;

    // public Pojo() { }

    public Pojo(String id, String datavalue) {

        this.id = id;
        this.datavalue = datavalue;
    }

    public String getId() {
        return id;
    }

    public String getDatavalue() {
        return datavalue;
    }
}

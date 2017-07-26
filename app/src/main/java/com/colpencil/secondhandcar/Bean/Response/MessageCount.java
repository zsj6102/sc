package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

public class MessageCount implements Serializable{
    private int type;
    private int id;
    private int is_new;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int isNew() {
        return is_new;
    }

    public void setNew(int aNew) {
        is_new = aNew;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

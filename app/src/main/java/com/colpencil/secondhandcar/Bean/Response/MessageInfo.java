package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */
public class MessageInfo implements Serializable {

    private int id;
    private String title;
    private long create_time;
    private int hurry;
    private boolean isLook;

    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getHurry() {
        return hurry;
    }

    public void setHurry(int hurry) {
        this.hurry = hurry;
    }
}

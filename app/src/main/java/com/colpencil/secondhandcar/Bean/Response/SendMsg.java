package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15.
 */
public class SendMsg implements Serializable {

    private int id;
    private String title;
    private long create_time;
    private int hurry;

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

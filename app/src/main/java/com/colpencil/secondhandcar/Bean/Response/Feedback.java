package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/4.
 */
public class Feedback implements Serializable {

    private int id;
    private String content;
    private long submit_time;
    private int member_id;
    private String reply_content;
    private long reply_time;
    private int state;
    private String phone;
    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getReply_time() {
        return reply_time;
    }

    public void setReply_time(long reply_time) {
        this.reply_time = reply_time;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public long getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(long submit_time) {
        this.submit_time = submit_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

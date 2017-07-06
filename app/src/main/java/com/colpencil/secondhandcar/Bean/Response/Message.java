package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */
public class Message implements Serializable {

    private String reduce_message;
    private String ins_message;
    private String sys_message;
    private long sys_time;
    private int sys_hurry;
    private long reduce_time;
    private long ins_time;

    public String getReduce_message() {
        return reduce_message;
    }

    public void setReduce_message(String reduce_message) {
        this.reduce_message = reduce_message;
    }

    public String getIns_message() {
        return ins_message;
    }

    public void setIns_message(String ins_message) {
        this.ins_message = ins_message;
    }

    public String getSys_message() {
        return sys_message;
    }

    public void setSys_message(String sys_message) {
        this.sys_message = sys_message;
    }

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public int getSys_hurry() {
        return sys_hurry;
    }

    public void setSys_hurry(int sys_hurry) {
        this.sys_hurry = sys_hurry;
    }

    public long getReduce_time() {
        return reduce_time;
    }

    public void setReduce_time(long reduce_time) {
        this.reduce_time = reduce_time;
    }

    public long getIns_time() {
        return ins_time;
    }

    public void setIns_time(long ins_time) {
        this.ins_time = ins_time;
    }
}

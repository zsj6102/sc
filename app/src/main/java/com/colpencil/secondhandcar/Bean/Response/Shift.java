package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */
public class Shift implements Serializable {

    private int provalues;
    private String op_name;
    private boolean isChecked;

    public Shift(String name){
        this.op_name = name;
    }

    public int getProvalues() {
        return provalues;
    }

    public void setProvalues(int provalues) {
        this.provalues = provalues;
    }

    public String getOp_name() {
        return op_name;
    }

    public void setOp_name(String op_name) {
        this.op_name = op_name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

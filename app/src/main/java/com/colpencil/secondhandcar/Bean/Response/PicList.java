package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 * 图片列表
 */
public class PicList implements Serializable {

    private String pic;
    private String remark;
    private List<String> other_pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getOther_pic() {
        return other_pic;
    }

    public void setOther_pic(List<String> other_pic) {
        this.other_pic = other_pic;
    }

    public int getPic_type() {
        return pic_type;
    }

    public void setPic_type(int pic_type) {
        this.pic_type = pic_type;
    }

    private int pic_type;
}

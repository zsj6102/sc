package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */
public class Home implements Serializable {

    private String custom_phone;
    private List<KeyWord> keywordList;
    private List<Adv> advList;
    private int popup;

    public int getPopup() {
        return popup;
    }

    public void setPopup(int popup) {
        this.popup = popup;
    }

    public String getCustom_phone() {
        return custom_phone;
    }

    public void setCustom_phone(String custom_phone) {
        this.custom_phone = custom_phone;
    }

    public List<KeyWord> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<KeyWord> keywordList) {
        this.keywordList = keywordList;
    }

    public List<Adv> getAdvList() {
        return advList;
    }

    public void setAdvList(List<Adv> advList) {
        this.advList = advList;
    }
}

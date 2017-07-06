package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */
public class ApplyData implements Serializable {

    private int disabled;
    private String bank_account_name;
    private long apply_time;
    private String tel;
    private String refuse_reason;
    private List<Adv> advList;

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public void setBank_account_name(String bank_account_name) {
        this.bank_account_name = bank_account_name;
    }

    public long getApply_time() {
        return apply_time;
    }

    public void setApply_time(long apply_time) {
        this.apply_time = apply_time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public List<Adv> getAdvList() {
        return advList;
    }

    public void setAdvList(List<Adv> advList) {
        this.advList = advList;
    }
}

package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/26.
 */
public class PersonInfo implements Serializable {

    private String bank_account_name;
    private String bank_account_number;
    private String bank_name;
    private String bank_place;
    private String id_number;
    private String tel;
    private String invite_code;

    public String getBank_account_name() {
        return bank_account_name;
    }

    public void setBank_account_name(String bank_account_name) {
        this.bank_account_name = bank_account_name;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(String bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_place() {
        return bank_place;
    }

    public void setBank_place(String bank_place) {
        this.bank_place = bank_place;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }
}

package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15.
 */
public class HomeSystem implements Serializable {

    private String url;
    private SendMsg sendMessage;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SendMsg getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMsg sendMessage) {
        this.sendMessage = sendMessage;
    }
}

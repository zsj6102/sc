package com.colpencil.secondhandcar.Bean.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public class Props implements Serializable {

    private String props_name;
    private List<Shift> options;

    public String getProps_name() {
        return props_name;
    }

    public void setProps_name(String props_name) {
        this.props_name = props_name;
    }

    public List<Shift> getOptions() {
        return options;
    }

    public void setOptions(List<Shift> options) {
        this.options = options;
    }
}

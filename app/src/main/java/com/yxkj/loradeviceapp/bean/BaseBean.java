package com.yxkj.loradeviceapp.bean;

import java.io.Serializable;

public class BaseBean implements Serializable {
    public Integer code;
    public String message;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code2) {
        this.code = code2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }
}
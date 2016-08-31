package com.dragon.phone_mobilesafe.bean;

/**
 * Created by pc on 2016-08-04.
 * 1电话+短信拦截
 * 2电话
 * 3短信
 */
public class BlackNumberInfo {
    private  String number;
    private  String mode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}

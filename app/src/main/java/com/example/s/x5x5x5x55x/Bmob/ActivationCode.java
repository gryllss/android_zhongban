package com.example.s.x5x5x5x55x.Bmob;

import cn.bmob.v3.BmobObject;

public class ActivationCode extends BmobObject {
    private String activationCode;
    private Boolean isUsed;
    private String usederPhone;
    private String jiHuoTime;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public String getUsederPhone() {
        return usederPhone;
    }

    public void setUsederPhone(String usederPhone) {
        this.usederPhone = usederPhone;
    }

    public String getJiHuoTime() {
        return jiHuoTime;
    }

    public void setJiHuoTime(String jiHuoTime) {
        this.jiHuoTime = jiHuoTime;
    }
}

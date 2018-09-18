package com.example.s.x5x5x5x55x.Bmob;

import android.widget.Toast;

import java.util.Date;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {

    private String outTime;
    private String currentTimeMillisVer;
    private String userImei;

    public String getOutTime() {
        return outTime;
    }



    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getCurrentTimeMillisVer() {
        return currentTimeMillisVer;
    }

    public void setCurrentTimeMillisVer(String currentTimeMillisVer) {
        this.currentTimeMillisVer = currentTimeMillisVer;
    }

    public String getUserImei() {
        return userImei;
    }

    public void setUserImei(String userImei) {
        this.userImei = userImei;
    }
}

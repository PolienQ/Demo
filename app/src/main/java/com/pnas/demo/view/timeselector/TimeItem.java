package com.pnas.demo.view.timeselector;

/**
 * Created by Administrator on 2016/1/9.
 */
public class TimeItem {
    public String duration;
    public String startTime;
    public boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getDuration() {
        return duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

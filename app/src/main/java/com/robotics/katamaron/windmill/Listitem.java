package com.robotics.katamaron.windmill;

public class Listitem {

    private String log;
    private String datime;

    public Listitem(String log, String datime) {
        this.log = log;
        this.datime = datime;
    }

    public String getLog() {
        return log;
    }

    public String getDatime() {
        return datime;
    }
}
